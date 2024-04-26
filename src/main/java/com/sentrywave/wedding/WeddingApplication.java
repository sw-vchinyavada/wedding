package com.sentrywave.wedding;

import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class WeddingApplication {

    public static void main(String[] args) throws IOException, FontFormatException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Guest Name: ");
        String guestName = scanner.nextLine();
        generate(guestName);
    }

    // https://www.baeldung.com/java-add-text-to-image
    private static void generate(String guestName) throws IOException, FontFormatException {
        ImagePlus tempImage = IJ.openImage("./invite-template.png");
        ImagePlus image = IJ.openImage("./invite-template.png");
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream("Montserrat-SemiBold.ttf");
        if (stream != null) {
            Font font = Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(50f);

            ImageProcessor lengthIp = tempImage.getProcessor();
            lengthIp.setColor(Color.BLACK);
            lengthIp.setFont(font);
            lengthIp.drawString(guestName, 0, 1015);

            ImageProcessor ip = image.getProcessor();
            ip.setColor(Color.BLACK);
            ip.setFont(font);
            int textLength = lengthIp.getStringWidth(guestName);
            ip.drawString(guestName, getStartingPoint(lengthIp.getWidth(), textLength), 1015);

            File outputfile = new File("output/" + guestName.toLowerCase().replace(" ", "_") + ".png");
            ImageIO.write(ip.getBufferedImage(), "png", outputfile);
        }
    }

    private static int getStartingPoint(int width, int textLength) {
        Double halfPage = (double) width / 2;
        Double halfText = (double) textLength / 2;
        double v = halfPage - halfText;
        return (int) v;
    }

}
