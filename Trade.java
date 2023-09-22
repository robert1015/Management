import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.SortedSet;


public class Trade {
    private LocalDateTime timestamp;
    private ProductName productName;
    private String code;
    private boolean tradeType; //sell: false, buy: true
    private long amount;
    private BigDecimal pricePerShare;
    public Trade(LocalDateTime timestamp,
                                  ProductName productName,
                                  String code,
                                  boolean tradeType,
                                  long amount,
                                  BigDecimal pricePerShare) {
        this.timestamp = timestamp;
        this.code = code;
        this.productName = productName;
        this.tradeType = tradeType;
        this.amount = amount;
        this.pricePerShare = pricePerShare;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public String getProductName() {
        return productName.getValue();
    }

    public String getCode() {
        return code;
    }


    public long getAmount() {
        return amount;
    }


    public BigDecimal getPricePerShare() {
        return pricePerShare;
    }

    public boolean isSell() {
        return !tradeType;
    }
    public boolean isBuy() {
        return tradeType;
    }

    public static class CompareByDate implements Comparator<Trade> {
        public int compare(Trade trade1, Trade trade2) {
            return -trade1.getTimestamp().compareTo(trade2.getTimestamp());
        }
    }
}
