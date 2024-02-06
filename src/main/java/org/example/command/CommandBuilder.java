package org.example.command;

import java.util.Scanner;

public class CommandBuilder {

    public void getManual(){
        System.out.println("Пример ввода команды: myapp -d 3840;2160 -c 0.285;0.01 -o 123.png");
        System.out.println("Параметры командой строки:");
        System.out.println("-d размер итогового изображения как: Width ; Height");
        System.out.println("-c значение постоянного слагаемоего как: Real-part ; Imaginary-part");
        System.out.println("-o путь до файла для сохранения");
        System.out.println();
        System.out.println("exit - для выхода из приложения");
        System.out.println();
        Scanner scanner = new Scanner(System.in);
        getCommand(scanner);
    }

    public void getCommand(Scanner scanner){
        System.out.println("Введите команду:");
        String[] line = scanner.nextLine().split(" ");
        returnCommand(line, scanner);
    }

    public void returnCommand(String[] line, Scanner scanner){
        switch (line[0]){
            case "myapp":
                Command command = new CommandJuliaFractal();
                command.execute(line);
                getCommand(scanner);
            case "exit":
                System.exit(0);
            default:
                System.out.println("Неверная команда!");
                getManual();
        }
    }
}
