import java.util.InvalidPropertiesFormatException;
import java.util.Locale;

public enum Market {
    PRIME, GROWTH, STANDARD;
    static Market parseMarket(String input) throws InvalidPropertiesFormatException{
        input = input.toUpperCase();
        switch (input) {
            case "PRIME": return Market.PRIME;
            case "GROWTH": return Market.GROWTH;
            case "STANDARD": return Market.STANDARD;
            default: throw new InvalidPropertiesFormatException("");
        }
    }
}