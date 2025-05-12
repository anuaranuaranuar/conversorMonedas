import com.google.gson.Gson;
import dto.ConversorDto;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {



    public static void main(String[] args) {
        String salir;
        Scanner read = new Scanner(System.in);
        Gson gson = new Gson();

        do{
            try {

              ExchangeApi monedas = ExchangeApi.getCodes();

                String opcion1 = ExchangeApi.pedirMoneda("base");
                ExchangeApi.listarMoneda(ExchangeApi.filtarMonedas(opcion1));
                String coin1 = ExchangeApi.validarMoneda(monedas.getSupported_codes());

                String opcion2 = ExchangeApi.pedirMoneda("destino");
                ExchangeApi.listarMoneda(ExchangeApi.filtarMonedas(opcion2));
                String coin2 = ExchangeApi.validarMoneda(monedas.getSupported_codes());

                System.out.println("Ingrese valor a convertir de " + coin1 + " a " + coin2);
                Double valueToConver = ExchangeApi.validarValor();

                HttpResponse<String> res = ExchangeApi.requestCoinConver(coin1,coin2,valueToConver);


                System.out.println(res.body());
                ConversorDto dto = gson.fromJson(res.body(), ConversorDto.class);

                System.out.println("El resultado es " + dto.conversion_result());

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("ingrese si/no para salir");
            salir = read.next().toUpperCase();

        } while (!salir.equals("SI"));


    }
}
