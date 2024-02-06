package org.example.fractalJulia;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.Callable;

public class JuliaFractalTask implements Callable<Void> {
    private final BufferedImage image;
    private final int width;
    private final int height;
    private final double minX;
    private final double maxX;
    private final double minY;
    private final double maxY;
    private final int maxIterations;
    private final int threadIndex;
    private final int numThreads;
    private final double cX;
    private final double cY;

    public JuliaFractalTask(BufferedImage image, int width, int height, double minX, double maxX,
                            double minY, double maxY, int maxIterations, int i, int numThreads,
                            double cX, double cY) {
        this.image = image;
        this.width = width;
        this.height = height;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
        this.maxIterations = maxIterations;
        this.threadIndex = i;
        this.numThreads = numThreads;
        this.cX = cX;
        this.cY = cY;
    }

    @Override
    public Void call() {
        int pixelsPerThread = width * height / numThreads;
        int startPixel = threadIndex * pixelsPerThread;
        int endPixel = (threadIndex == numThreads - 1) ? width * height : startPixel + pixelsPerThread;

        for (int pixelIndex = startPixel; pixelIndex < endPixel; pixelIndex++) {
            int x = pixelIndex % width;
            int y = pixelIndex / width;
            double zx = map(x, 0, width, minX, maxX);
            double zy = map(y, 0, height, minY, maxY);
            int iteration = computeJulia(zx, zy, maxIterations);
            int color = (iteration == maxIterations) ? Color.BLACK.getRGB() : Color.HSBtoRGB(iteration / 256f, 1, iteration / (iteration + 6f));
            image.setRGB(x, y, color);
        }
        return null;
    }
    private double map(int value, int start1, int stop1, double start2, double stop2) {
        return start2 + (stop2 - start2) * ((value - start1) / (double) (stop1 - start1));
    }

    private int computeJulia(double zx, double zy, int maxIterations) {
        int iteration = 0;
        while (zx * zx + zy * zy < 4 && iteration < maxIterations) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iteration++;
        }
        return iteration;
    }
}
