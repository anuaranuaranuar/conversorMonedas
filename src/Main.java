import com.google.gson.Gson;
import dto.ConversorDto;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpResponse;


public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        String exit;
        Gson gson = new Gson();

        do{


                CurrencyCode.toListCurrency("Ingrese nombre o sigla de la moneda base");
                String currency1 = CurrencyCode.check("base");

                CurrencyCode.toListCurrency("Ingrese nombre o sigla de la moneda destino");
                String currency2 = CurrencyCode.check("destino");

                BigDecimal valueToConvert = InputUser
                        .readBigDecimal("Ingrese valor a convertir de " + currency1 + " a " + currency2);
                System.out.println(valueToConvert);

                HttpResponse<String> res = ExchangeApi
                        .requestCurrencyConvert(currency1,currency2,valueToConvert);

                ConversorDto dto = gson.fromJson(res.body(), ConversorDto.class);
                System.out.println("El resultado es " + dto.conversion_result());



            exit = InputUser.readInput("Ingrese si/no para salir");

        } while (!exit.equalsIgnoreCase("SI"));

    }
}
