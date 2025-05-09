import com.google.gson.Gson;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;

import java.util.List;
import java.util.regex.Pattern;

public class ExchangeApi {

    private List<List<String>> supported_codes;
    private String result;


    final static Gson gson = new Gson();
    final static String APIKEY = "https://v6.exchangerate-api.com/v6/aafd4024a38845e16e3a0cd5/";
    final static String Y = "/";
    final static HttpClient client = HttpClient.newHttpClient();

    public static ExchangeApi  getCodes() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(URI.create(APIKEY + "codes"))
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());

        return gson.fromJson(res.body(), ExchangeApi.class);
    }



    @Override
    public String toString() {
        return "ExchangeApi{" +
                "supported_codes=" + supported_codes +
                ", result='" + result + '\'' +
                '}';
    }

    public static List<List<String>> filtarMonedas(String valorUsuario) throws IOException, InterruptedException {

        return getCodes().getSupported_codes().stream()
                .filter(list -> list.stream().
                        anyMatch(s -> s.
                                matches("(?i).*" + Pattern.quote(valorUsuario) + ".*")))
                .toList();

    }

    public void setResult(String result) {
        this.result = result;
    }
    public List<List<String>>  getSupported_codes() {
        return supported_codes;
    }

    public void setSupported_codes(List<List<String>>  supported_codes) {
        this.supported_codes = supported_codes;
    }

    public String getResult() {
        return result;
    }
}
