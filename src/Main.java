import com.google.gson.Gson;
import dto.ConversorDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner read = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        do{


            System.out.println("Ingrese siglas de la primera moneda");
            String coin1 = read.nextLine().toUpperCase();

            System.out.println("Ingrese siglas de la segunda moneda");
            String coin2 = read.nextLine().toUpperCase();

            System.out.println("Ingrese valor a convertir de " + coin1 + " a " + coin2);
            double valueConver = read.nextDouble();

            final String APIKEY = "https://v6.exchangerate-api.com/v6/aafd4024a38845e16e3a0cd5/pair/";
            final String Y = "/";

            HttpRequest request = HttpRequest
                    .newBuilder()
                    .uri(URI.create(APIKEY + Y +
                            coin1 + Y +
                            coin2 + Y +
                            valueConver))
                    .build();

            try {

                HttpResponse<String> res = client.send(request, HttpResponse.BodyHandlers.ofString());

                System.out.println(res.body());

                ConversorDto dto = gson.fromJson(res.body(), ConversorDto.class);

                System.out.println("dto " + dto);

                System.out.println("El resultado es " + dto.conversion_result());


            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }while (read.hasNext());

        read.close();

        //todo arreglar bucle

    }
}
