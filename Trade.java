import java.math.BigDecimal;
import java.time.LocalDateTime;



public class Trade {
    private LocalDateTime timestamp;
    private ProductName productName;
    private String code;
    private boolean tradeType; //sell: false, buy: true
    private int amount;
    private BigDecimal pricePerShare;
    public Trade(LocalDateTime timestamp,
                                  ProductName productName,
                                  String code,
                                  boolean tradeType,
                                  int amount,
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


    public int getAmount() {
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
}
