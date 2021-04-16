package application.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

@Component
public class QRCodeGenerator {

    public static void generateQRCodeImage(String text, int width, int height, String filePath)
            throws WriterException, IOException {
        String crunchifyFileType = "png";
        File crunchifyFile = new File(filePath);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);


        int CrunchifyWidth = bitMatrix.getWidth();

        // The BufferedImage subclass describes an Image with an accessible buffer of crunchifyImage data.
        BufferedImage crunchifyImage = new BufferedImage(CrunchifyWidth, CrunchifyWidth,
                BufferedImage.TYPE_INT_RGB);

        // Creates a Graphics2D, which can be used to draw into this BufferedImage.
        crunchifyImage.createGraphics();

        // This Graphics2D class extends the Graphics class to provide more sophisticated control over geometry, coordinate transformations, color management, and text layout.
        // This is the fundamental class for rendering 2-dimensional shapes, text and images on the Java(tm) platform.
        Graphics2D crunchifyGraphics = (Graphics2D) crunchifyImage.getGraphics();

        // setColor() sets this graphics context's current color to the specified color.
        // All subsequent graphics operations using this graphics context use this specified color.
        crunchifyGraphics.setColor(Color.WHITE);

        // fillRect() fills the specified rectangle. The left and right edges of the rectangle are at x and x + width - 1.
        crunchifyGraphics.fillRect(0, 0, CrunchifyWidth, CrunchifyWidth);

        // TODO: Please change this color as per your need
        crunchifyGraphics.setColor(Color.BLUE);

        for (int i = 0; i < CrunchifyWidth; i++) {
            for (int j = 0; j < CrunchifyWidth; j++) {
                if (bitMatrix.get(i, j)) {
                    crunchifyGraphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // A class containing static convenience methods for locating
        // ImageReaders and ImageWriters, and performing simple encoding and decoding.
        ImageIO.write(crunchifyImage, crunchifyFileType, crunchifyFile);


//	        Path path = FileSystems.getDefault().getPath(filePath);
//	        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static void generateQRCodeImageBlack(String text, int width, int height, String filePath)
            throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);


        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

    }

    public static byte[] getQRCodeImage(String text, int width, int height) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", pngOutputStream);
        byte[] pngData = pngOutputStream.toByteArray();
        return pngData;
    }

}
