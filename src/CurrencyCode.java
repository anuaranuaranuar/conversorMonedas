import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

public class CurrencyCode{
    private final static CurrencyCode codes = new CurrencyCode();

    private List<List<String>> currencyCodes;


    private CurrencyCode(){
        try {
            this.currencyCodes = ExchangeApi.getCodes().supported_codes();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            this.currencyCodes = List.of();
        }
    }
    public static CurrencyCode getInstance() {
        return codes;
    }

    public static List<List<String>> filter(String userValue)  {
        return getInstance().getCurrencyCodes().stream()
                .filter(list -> list.stream().
                        anyMatch(s -> s.
                                matches("(?i).*" + Pattern.quote(userValue) + ".*")))
                .toList();
    }

    public static String check( String typeCurrency){
        List<String> abbreviations = getInstance().getCurrencyCodes().stream().map(List::getFirst).toList();
        String currency;
        System.out.println("\nSi la moneda no se encuentra dentro de la lista ingrese 0");
        do {
            currency = InputUser
                    .readInput("Ingrese una sigla por favor")
                    .toUpperCase();

            if (currency.equals("0")) {
                toListCurrency("Ingrese nombre de la moneda "+ typeCurrency);
                System.out.println("\nSi la moneda no se encuentra dentro de la lista ingrese 0");

            } else if (!abbreviations.contains(currency)) {
                System.out.println("Sigla no permitida\nIntentelo de nuevo por favor");
            }
        } while (!abbreviations.contains(currency));
        return currency;
    }

    public static void toListCurrency(String message)  {
        List<List<String>> list;
        do {
            list = filter(InputUser.readInput(message));
            if (list.isEmpty()) {
                System.out.println("Intentelo de nuevo, moneda no encontrada");
            }
        } while (list.isEmpty());

        System.out.println("Lista de monedas que coinciden \n");
        list.forEach(item->
                System.out.println(item.getFirst() +" -> "+ item.get(1)));
    }

    public List<List<String>> getCurrencyCodes() {
        return currencyCodes;
    }

    public void setCurrencyCodes(List<List<String>> currencyCodes) {
        this.currencyCodes = currencyCodes;
    }
}
