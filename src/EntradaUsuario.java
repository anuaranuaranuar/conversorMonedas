import java.util.Scanner;

public class EntradaUsuario {
    private static final Scanner read = new Scanner(System.in);

    public static String leerTexto(String mensaje) {
        System.out.println(mensaje);
        return read.next();
    }


}
