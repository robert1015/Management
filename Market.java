import java.util.InvalidPropertiesFormatException;

public enum Market {
    PRIME, GROWTH, STANDARD;
    static Market parseMarket(String input) throws InvalidPropertiesFormatException{
        switch (input) {
            case "PRIME", "prime": return Market.PRIME;
            case "GROWTH", "growth": return Market.GROWTH;
            case "STANDARD", "standard": return Market.STANDARD;
            default: throw new InvalidPropertiesFormatException("");
        }
    }
}
