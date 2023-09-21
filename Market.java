import java.util.InvalidPropertiesFormatException;

public enum Market {
    PRIME, GROWTH, STANDARD;
    static Market parseMarket(String input) throws InvalidPropertiesFormatException{
        switch (input) {
            case "PRIME": return Market.PRIME;
            case "GROWTH": return Market.GROWTH;
            case "STANDARD": return Market.STANDARD;
            default: throw new InvalidPropertiesFormatException("");
        }
    }
}
