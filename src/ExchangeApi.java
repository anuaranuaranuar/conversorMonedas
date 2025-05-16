import com.google.gson.Gson;
import dto.CodigoDto;
import dto.ConversorDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ExchangeApi {

    final static Gson gson = new Gson();
    final static String APIKEY = "https://v6.exchangerate-api.com/v6/aafd4024a38845e16e3a0cd5/";
    final static String Y = "/";
    final static HttpClient client = HttpClient.newHttpClient();


    public static HttpResponse<String> requestCurrencyConvert(String currency1, String currency2, BigDecimal valueConvert) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(APIKEY + Y +
                        "pair" + Y +
                        currency1 + Y +
                        currency2 + Y +
                        valueConvert))
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());

    }


    public static CodigoDto getCodes() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(URI.create(APIKEY + "codes"))
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(res.body(), CodigoDto.class);
    }


}




