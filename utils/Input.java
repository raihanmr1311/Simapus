package utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Input {
    private Scanner input;

    public Input() {
        input = new Scanner(System.in);
    }

    public int getNumber() {
        int value = 0;
        boolean invalidInput = false;

        do {
            try {
                value = input.nextInt();
                invalidInput = false;
            } catch (Exception e) {
                System.out.print("Masukan tidak valid, harap ketik kembali = ");
                input.next();
                invalidInput = true;
            }
        } while (invalidInput);

        return value;
    }

    public int getNumber(int limit) {
        int number;
        do {
            number = getNumber();

            if (number <= limit) {
                System.out.print("Angka yang dimasukkan tidak boleh kurang dari atau sama dengan " + limit);
                System.out.print("\nKetik kembali = ");
            }
        } while (number <= limit);

        return number;
    }

    public int getNumber(int startLimit, int endLimit) {
        int number;
        do {
            number = getNumber();

            if (!(number >= startLimit && number <= endLimit)) {
                System.out.print("Angka yang dimasukkan harus diantara " + startLimit + " dan " + endLimit);
                System.out.print("\nKetik kembali = ");
            }
        } while (!(number >= startLimit && number <= endLimit));

        return number;
    }

    public boolean checkDate(LocalDate firstDate, LocalDate secondDate) {
        return firstDate.isAfter(secondDate);
    }

    public String inputDate() {
        String date = null;
        boolean isValid = false;

        do {
            try {
                date = input.next();
                LocalDate.parse(date);
                isValid = true;
            } catch (DateTimeParseException e) {
                System.out.println(e.getMessage());
                System.out.print("Format tanggal salah, harap ketik kembali = ");
                isValid = false;
            }

        } while (!isValid);

        return date;
    }

    public String inputAndCompareDate(LocalDate dateData) {
        boolean isAfter = false;
        String dateInput = null;

        do {
            dateInput = inputDate();
            LocalDate dateInputFormatted = LocalDate.parse(dateInput);
            if (dateInputFormatted.isAfter(dateData)) {
                isAfter = true;
            } else {
                isAfter = false;
                System.out.print("Tanggal yang dimasukkan harus lebih besar, ketik kembali => ");
            }
            
        } while (!isAfter);

        return dateInput;
    }
}
