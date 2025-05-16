import java.math.BigDecimal;
import java.util.Scanner;

public class InputUser{
    private static final Scanner read = new Scanner(System.in);

    public static String readInput(String message) {
        System.out.println(message);
        return read.next().trim();
    }

    public static BigDecimal readBigDecimal(String message) {
        String input;
        do {
            input = readInput(message);
            if (!input.matches("\\d+(\\.\\d+)?"))
                System.out.println("Valor no válido. Intente de nuevo.");

        } while (!input.matches("\\d+(\\.\\d+)?"));

        return new BigDecimal(input);
    }
}



