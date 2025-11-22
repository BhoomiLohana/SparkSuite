package currency;
public interface ConversionStrategy {
    double convert(Currency from, Currency to, double amount);
}

// Default implementation of the strategy
class StandardConversion implements ConversionStrategy {
    @Override
    public double convert(Currency from, Currency to, double amount) {
        return from.convertTo(to, amount);
    }
}
