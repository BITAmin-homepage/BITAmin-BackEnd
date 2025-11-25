package BITAmin.BE.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class LibreOfficeService {

    public File convertToPdf(MultipartFile file) throws IOException, InterruptedException {
        File tempPptx = File.createTempFile("upload-", ".pptx");
        file.transferTo(tempPptx);
        File outputPdf = new File(tempPptx.getParent(), "converted.pdf");
        ProcessBuilder pb = new ProcessBuilder(
                "libreoffice",
                "--headless",
                "--convert-to",
                "pdf",
                tempPptx.getAbsolutePath(),
                "--outdir",
                tempPptx.getParent()
        );
        Process process = pb.start();
        process.waitFor();

        return outputPdf;
    }
}
