package org.example.fractalJulia;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JuliaFractalCalculator {
    private final BufferedImage image;
    private final FractalParameters parameters;

    private final double minX = -2.0;;
    private final double maxX = 2.0;;
    private final double minY = -1.5;
    private final double maxY = 1.5;
    private final int maxIterations = 5000;
    private final int numThreads;


    public JuliaFractalCalculator(BufferedImage image, FractalParameters parameters,  int numThreads) {
        this.image = image;
        this.parameters = parameters;
        this.numThreads = numThreads;
    }

    public void computeFractal() {
        ExecutorService executorService = Executors.newFixedThreadPool(numThreads);
        List<Future<Void>> futures = new ArrayList<>();

        for (int i = 0; i < numThreads; i++) {
            Callable<Void> task = new JuliaFractalTask(
                    image, parameters.getWidth(), parameters.getHeight(), minX, maxX, minY, maxY, maxIterations, i, numThreads,
                    parameters.getcX(), parameters.getcY());
            futures.add(executorService.submit(task));
        }

        for (Future<Void> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdown();
    }
}
