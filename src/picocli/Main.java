package picocli;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean aktif = true;
        while (aktif) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("$ ");
            String input = scanner.nextLine();
            if (input.equals("exit")) {
                aktif = false;
            } else {
                input = input.replace(" /", " ");
                new CommandLine(new FileClient()).execute(input.split(" "));
            }
        }
    }
}
