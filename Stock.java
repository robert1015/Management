
public class Stock {
    private final String code;
    private final ProductName productName;
    private final Market market;
    private final long sharesIssued;
    Stock(String code, ProductName productName, Market market, long sharesIssued){
        this.code = code;
        this.productName = productName;
        this.market = market;
        this.sharesIssued = sharesIssued;
    }
    public String getCode() {
        return code;
    }
    public String getProductName() {
        return productName.getValue();
    }
    public Market getMarket() {
        return market;
    }
    public long getSharesIssued() {
        return sharesIssued;
    }
}

