package BITAmin.BE.project.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

/**
 * 큰 PDF를 Ghostscript로 재압축합니다. gs가 없거나 실패하면 원본 파일을 그대로 둡니다.
 */
@Slf4j
@Service
public class PdfGhostscriptService {

    private static final String[] GS_CANDIDATES = {
            "gs",
            "/usr/bin/gs",
            "/usr/local/bin/gs",
            "/opt/homebrew/bin/gs"
    };

    private static final long GS_TIMEOUT_SEC = 300L;

    /**
     * PDF가 {@code maxBytes}보다 크면 /ebook → 필요 시 /screen 순으로 재압축을 시도합니다.
     */
    public File shrinkPdfIfOverLimit(File pdf, long maxBytes) throws IOException, InterruptedException {
        if (pdf == null || !pdf.exists() || pdf.length() <= maxBytes) {
            return pdf;
        }

        String gs = resolveGhostscriptPath();
        if (gs == null) {
            log.warn("Ghostscript(gs)를 찾지 못해 PDF 추가 압축을 건너뜁니다. size={} bytes", pdf.length());
            return pdf;
        }

        File current = pdf;
        for (String preset : new String[]{"/ebook", "/screen"}) {
            if (current.length() <= maxBytes) {
                break;
            }
            File next = runGhostscriptPdfShrink(gs, current, preset);
            if (next == null) {
                continue;
            }
            if (next != current) {
                if (current.exists()) {
                    //noinspection ResultOfMethodCallIgnored
                    current.delete();
                }
                current = next;
            }
        }

        if (current.length() > maxBytes) {
            log.warn("Ghostscript 후에도 PDF가 {} bytes로 {} bytes 한도를 넘습니다. 압축본을 그대로 사용합니다.",
                    current.length(), maxBytes);
        }

        return current;
    }

    private static String resolveGhostscriptPath() {
        for (String candidate : GS_CANDIDATES) {
            try {
                ProcessBuilder pb = new ProcessBuilder(candidate, "--version");
                pb.redirectErrorStream(true);
                Process p = pb.start();
                drainQuietly(p);
                if (p.waitFor(5, TimeUnit.SECONDS) && p.exitValue() == 0) {
                    return candidate;
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }

    private File runGhostscriptPdfShrink(String gsPath, File input, String pdfSettings) throws IOException, InterruptedException {
        File out = File.createTempFile("pdf-gs-", ".pdf");
        ProcessBuilder pb = new ProcessBuilder(
                gsPath,
                "-sDEVICE=pdfwrite",
                "-dCompatibilityLevel=1.4",
                "-dPDFSETTINGS=" + pdfSettings,
                "-dNOPAUSE",
                "-dQUIET",
                "-dBATCH",
                "-sOutputFile=" + out.getAbsolutePath(),
                input.getAbsolutePath()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                log.debug("[gs] {}", line);
            }
        }

        boolean finished = process.waitFor(GS_TIMEOUT_SEC, TimeUnit.SECONDS);
        if (!finished) {
            process.destroyForcibly();
            //noinspection ResultOfMethodCallIgnored
            out.delete();
            log.warn("Ghostscript 시간 초과 (preset={})", pdfSettings);
            return null;
        }

        if (process.exitValue() != 0) {
            //noinspection ResultOfMethodCallIgnored
            out.delete();
            log.warn("Ghostscript 실패 exitCode={} preset={}", process.exitValue(), pdfSettings);
            return null;
        }

        if (!out.exists() || out.length() == 0 || out.length() >= input.length()) {
            //noinspection ResultOfMethodCallIgnored
            out.delete();
            log.warn("Ghostscript 출력이 비었거나 원본보다 작지 않음 preset={}", pdfSettings);
            return null;
        }

        log.info("Ghostscript PDF 압축 preset={} {} bytes -> {} bytes", pdfSettings, input.length(), out.length());
        return out;
    }

    private static void drainQuietly(Process p) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            while (reader.readLine() != null) {
            }
        } catch (IOException ignored) {
        }
    }
}
