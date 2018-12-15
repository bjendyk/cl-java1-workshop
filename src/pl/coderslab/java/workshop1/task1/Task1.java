package pl.coderslab.java.workshop1.task1;

import java.util.Random;
import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        Random random = new Random();
        int randomNumber = random.nextInt(100) + 1;
        int number = -1;
        int attempts = 0;

        if (args.length > 0 && args[0].equals("iddqd")) { // DOOM GOD MODE :)
            System.out.println(String.format("*** wylosowana liczba: %d ***", randomNumber));
        }

        do {
           number = getNumber("Podaj liczbe: ");

           if (number > randomNumber) {
               System.out.print("Za duzo! ");
           } else if (number < randomNumber) {
               System.out.print("Za malo! ");
           }
           attempts++;
        } while (randomNumber != number);

        System.out.println(String.format("Zgadles w %d probach!", attempts));
    }

    static int getNumber(String prompt) {
        System.out.print(prompt);
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
            System.out.print("To nie jest liczba! ");
            System.out.print(prompt);
        }
        return scanner.nextInt();
    }
}
