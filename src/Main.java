import com.google.gson.Gson;
import dto.ConversorDto;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static String elegirMoneda(List<List<String>> lista){
        if(lista.isEmpty()) {
            return lista.getFirst().getFirst();
        }else {
            System.out.println("Lista de monedas que coinciden \nelija una por favor");
            Scanner read = new Scanner(System.in);
            System.out.println("Elija la sigla de la moneda correspondiente");
            lista.forEach(System.out::println);
            return read.next();
        }

    }

    public static void main(String[] args) {
        String salir;
        Scanner read = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();
        do{
            try {

                System.out.println("Ingrese siglas o nombre de la primera moneda");
                String opcion1 = read.next();
                String coin1 =  elegirMoneda(ExchangeApi.filtarMonedas(opcion1));

                System.out.println("Ingrese siglas o nombre de la primera moneda");
                String opcion2 = read.next();
                String coin2 =  elegirMoneda(ExchangeApi.filtarMonedas(opcion2));

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
            System.out.println("si desea salir ingrese 0");
            salir = read.next();

        } while (salir != "0");


        /*    System.out.println("Ingrese siglas de la primera moneda");
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


        }

        read.close();*/

        //todo arreglar bucle

    }
}
