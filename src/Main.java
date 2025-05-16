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
            try {

                ExchangeApi currencies = ExchangeApi.getCodes();

                String option1 = ExchangeApi.askCurrency("base");
                ExchangeApi.toListCurrency(ExchangeApi.filterCurrency(option1));
                String currency1 = ExchangeApi.checkCurrency(currencies.getSupported_codes());

                String option2 = ExchangeApi.askCurrency("destino");
                ExchangeApi.toListCurrency(ExchangeApi.filterCurrency(option2));
                String currency2 = ExchangeApi.checkCurrency(currencies.getSupported_codes());

                BigDecimal valueToConvert = InputUser
                        .readBigDecimal("Ingrese valor a convertir de " + currency1 + " a " + currency2);
                System.out.println(valueToConvert);

                HttpResponse<String> res = ExchangeApi
                        .requestCurrencyConvert(currency1,currency2,valueToConvert);

                ConversorDto dto = gson.fromJson(res.body(), ConversorDto.class);
                System.out.println("El resultado es " + dto.conversion_result());

            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            exit = InputUser.readInput("Ingrese si/no para salir");

        } while (!exit.equalsIgnoreCase("SI"));

    }
}
