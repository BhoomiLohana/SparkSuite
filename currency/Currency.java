package currency;
public abstract class Currency {
    protected String code;
    protected double rateToUSD;

    public Currency(String code, double rateToUSD) {
        this.code = code;
        this.rateToUSD = rateToUSD;
    }

    public String getCode() {
        return code;
    }

    public double getRateToUSD() {
        return rateToUSD;
    }

    public double convertTo(Currency targetCurrency, double amount) {
        return (amount / this.rateToUSD) * targetCurrency.getRateToUSD();
    }
}

// All subclasses now just inherit the logic without overriding convertTo
class Dollar extends Currency { public Dollar() { super("USD", 1.0); } }
class Euro extends Currency { public Euro() { super("EUR", 0.92); } }
class Rupee extends Currency { public Rupee() { super("PKR", 278.0); } }
class Pound extends Currency { public Pound() { super("GBP", 0.79); } }
class Yen extends Currency { public Yen() { super("JPY", 154.36); } }
class IndianRupee extends Currency { public IndianRupee() { super("INR", 83.4); } }
class AustralianDollar extends Currency { public AustralianDollar() { super("AUD", 1.52); } }
class CanadianDollar extends Currency { public CanadianDollar() { super("CAD", 1.37); } }
class Yuan extends Currency { public Yuan() { super("CNY", 7.24); } }
class Riyal extends Currency { public Riyal() { super("SAR", 3.75); } }
