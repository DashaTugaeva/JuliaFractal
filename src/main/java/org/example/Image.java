package org.example;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {
    public void saveImage(BufferedImage image, String outputPath, String format) {
        try {
            File output = new File(outputPath);
            ImageIO.write(image, format, output);
            System.out.println("Изображение сохранено: " + output.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
