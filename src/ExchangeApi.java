import com.google.gson.Gson;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
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

    public List<List<String>> getSupported_codes() throws IOException, InterruptedException {
        return supported_codes;
    }

    public static String askCurrency(String baseOrDestiny) {
        return InputUser.readInput("Ingrese siglas o nombre de la moneda "+ baseOrDestiny);
    }

    public static void toListCurrency(List<List<String>> list) throws IOException, InterruptedException {
        do{
            System.out.println("Lista de monedas que coinciden \n");
            list.forEach(System.out::println);
            System.out.println("Ingrese una sigla por favor ");

            if (list.isEmpty()) {
                System.out.println("Intentelo de nuevo, moneda no encontrada");
            }
        }while (list.isEmpty());

    }



    public static HttpResponse<String> requestCurrencyConvert(String currency1, String currency2, BigDecimal valueConvert) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(APIKEY + Y +
                        "pair"+ Y +
                        currency1 + Y +
                        currency2 + Y +
                        valueConvert))
                .build();

        return  client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public static String checkCurrency(List<List <String>> listCurrency) throws IOException, InterruptedException {
        String currency = InputUser
                .readInput("Si no moneda no se encuentra dentro de la lista ingrese 0")
                .toUpperCase();

        List<String> abbreviations = listCurrency.stream().map(List::getFirst).toList();
        if (currency.equals("0")){
            String opcion = ExchangeApi.askCurrency("");
            ExchangeApi.toListCurrency(ExchangeApi.filterCurrency(opcion));
            return checkCurrency(listCurrency);

        }else if (!abbreviations.contains(currency)){
            System.out.println("Sigla no permitida\nIntentelo de nuevo por favor");
            return checkCurrency(listCurrency);
        }
        return currency;

    }

    public static List<List<String>> filterCurrency(String userValue) throws IOException, InterruptedException {


        return getCodes().getSupported_codes().stream()
                .filter(list -> list.stream().
                        anyMatch(s -> s.
                                matches("(?i).*" + Pattern.quote(userValue) + ".*")))
                .toList();

    }

    public void setResult(String result) {
        this.result = result;
    }


    public void setSupported_codes(List<List<String>>  supported_codes) {
        this.supported_codes = supported_codes;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {

        return "ExchangeApi{" +
                "supported_codes=" + supported_codes +
                ", result='" + result + '\'' +
                '}';
    }

}




