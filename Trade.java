import java.math.BigDecimal;
import java.time.LocalDateTime;



public class Trade {
    private LocalDateTime timestamp;
    private String productName;
    private String code;
    private boolean tradeType; //sell: false, buy: true
    private int amount;
    private BigDecimal pricePerShare;
    public Trade(LocalDateTime timestamp,
                                  String productName,
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

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public BigDecimal getPricePerShare() {
        return pricePerShare;
    }

    public void setPricePerShare(BigDecimal pricePerShare) {
        this.pricePerShare = pricePerShare;
    }

    public boolean isSell() {
        return !tradeType;
    }
    public boolean isBuy() {
        return tradeType;
    }
    public void Sell() {tradeType = false;}
    public void Buy() {tradeType = true;}
}
