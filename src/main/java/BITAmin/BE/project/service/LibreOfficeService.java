package BITAmin.BE.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@Service
public class LibreOfficeService {
    public File convertToPdf(MultipartFile file) throws IOException, InterruptedException {
        File tempPptx = File.createTempFile("upload-", ".pptx");
        file.transferTo(tempPptx);
        File outputPdf = new File(tempPptx.getParent(), UUID.randomUUID() + ".pdf");
        ProcessBuilder pb = new ProcessBuilder(
                "/usr/bin/libreoffice",
                "--headless",
                "--convert-to", "pdf",
                tempPptx.getAbsolutePath(),
                "--outdir", tempPptx.getParent()
        );
        pb.redirectErrorStream(true);
        Process process = pb.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[LibreOffice] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("LibreOffice 변환 실패. exit code = " + exitCode);
        }
        File generatedPdf = new File(tempPptx.getParent(),
                tempPptx.getName().replace(".pptx", ".pdf"));

        if (generatedPdf.exists()) {
            generatedPdf.renameTo(outputPdf);
        }
        tempPptx.delete();
        return outputPdf;
    }

    public void cleanTempFiles(File pdfFile) {
        try {
            if (pdfFile != null && pdfFile.exists()) {
                pdfFile.delete();
            }
        } catch (Exception ignored) {}
    }
}
