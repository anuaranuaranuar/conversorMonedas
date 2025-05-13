import com.google.gson.Gson;


import java.io.IOException;
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




    public static String pedirMoneda(String baseOrDestino) {
        return EntradaUsuario.leerTexto("Ingrese siglas o nombre de la moneda "+ baseOrDestino);

    }




    public static ExchangeApi  getCodes() throws IOException, InterruptedException {
        HttpRequest req = HttpRequest
                .newBuilder()
                .uri(URI.create(APIKEY + "codes"))
                .build();

        HttpResponse<String> res = client.send(req, HttpResponse.BodyHandlers.ofString());
        return gson.fromJson(res.body(), ExchangeApi.class);
    }

    public static void listarMoneda(List<List<String>> lista) throws IOException, InterruptedException {

        if (lista.isEmpty()) {
            System.out.println("Intentelo de nuevo, moneda no encontrada");
            listarMoneda(filtarMonedas(pedirMoneda("")));

        } else {
            System.out.println("Lista de monedas que coinciden \n");
            lista.forEach(System.out::println);
            System.out.println("Ingrese una sigla por favor ");
        }


    }

    public static HttpResponse<String> requestCoinConver(String coin1, String coin2, Double valueConver) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(APIKEY + Y +
                        "pair"+ Y +
                        coin1 + Y +
                        coin2 + Y +
                        valueConver))
                .build();

        return  client.send(request, HttpResponse.BodyHandlers.ofString());

    }

    public static String validarMoneda(List<List <String>> listMonedas) throws IOException, InterruptedException {
        String moneda = EntradaUsuario
                .leerTexto("Si no su moneda no se encuentra dentro de la lista ingrese 0")
                .toUpperCase();

        List<String> siglas = listMonedas.stream().map(List::getFirst).toList();
        if (moneda.equals("0")){
            String opcion = ExchangeApi.pedirMoneda("");
            ExchangeApi.listarMoneda(ExchangeApi.filtarMonedas(opcion));
            return validarMoneda(listMonedas);

        }else if (!siglas.contains(moneda)){
            System.out.println("Sigla no permitida\nIntentelo de nuevo por favor");
            return validarMoneda(listMonedas);
        }
        return moneda;

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
    public List<List<String>>  getSupported_codes() throws IOException, InterruptedException {
        return supported_codes;
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