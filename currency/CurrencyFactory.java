package currency;
import java.util.Map;

public class CurrencyFactory {
    private static final Map<String, Currency> currencyMap = Map.of(
        "USD", new Dollar(),
        "EUR", new Euro(),
        "PKR", new Rupee(),
        "GBP", new Pound(),
        "JPY", new Yen(),
        "INR", new IndianRupee(),
        "AUD", new AustralianDollar(),
        "CAD", new CanadianDollar(),
        "CNY", new Yuan(),
        "SAR", new Riyal()
    );

    public static Currency getCurrency(String code) {
        return currencyMap.get(code);
    }
}
