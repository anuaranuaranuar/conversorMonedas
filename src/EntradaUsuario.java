import java.util.Scanner;

public class EntradaUsuario {
    private static final Scanner read = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        System.out.println(mensaje);
        return read.nextLine().trim();
    }

    public static Double leerDecimal(String mensaje) {
        String input;
        do {
            System.out.println(mensaje);
            input = read.nextLine().trim();
            if (!input.matches("\\d+(\\.\\d+)?")) {
                System.out.println("Valor no válido. Intente de nuevo.");
            }
        } while (!input.matches("\\d+(\\.\\d+)?"));

        return Double.parseDouble(input);
    }
}



