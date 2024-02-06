package org.example.command;


import org.example.Image;
import org.example.fractalJulia.FractalParameters;
import org.example.fractalJulia.JuliaFractalCalculator;
import java.awt.image.BufferedImage;


public class CommandJuliaFractal implements Command {
    @Override
    public void execute(String[] line) {
        FractalParameters parameters = parserLine(line);
        BufferedImage image = new BufferedImage(parameters.getWidth(), parameters.getHeight(), BufferedImage.TYPE_INT_RGB);
        int numThreads = Runtime.getRuntime().availableProcessors();
        JuliaFractalCalculator calculator = new JuliaFractalCalculator(image, parameters, numThreads);
        calculator.computeFractal();
        Image image1 = new Image();
        image1.saveImage(image, parameters.getOutputPath(), parameters.getImageResolution());

    }

    private FractalParameters parserLine(String[] line){
        FractalParameters parameters = new FractalParameters();
        for (int i = 1; i < line.length; i++) {
            String option = line[i];
            switch (option) {
                case "-d":
                    String[] dimensions = line[i + 1].split(";");
                    parameters.setWidth(Integer.parseInt(dimensions[0]));
                    parameters.setHeight(Integer.parseInt(dimensions[1]));
                    i++;
                    break;
                case "-c":
                    String[] constants = line[i + 1].split(";");
                    parameters.setcX(Double.parseDouble(constants[0]));
                    parameters.setcY(Double.parseDouble(constants[1]));
                    i++;
                    break;
                case "-o":
                    parameters.setOutputPath(line[i + 1]);
                    String[] imageResolution = line[i + 1].split("\\.");
                    parameters.setImageResolution(imageResolution[1]);
                    i++;
                    break;
                default:
            }
        }
        return parameters;
    }
}
