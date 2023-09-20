public class Stock {
    private String code;
    private String productName;
    private String market;
    private int sharesIssued;
    Stock(String[] data) {
        code = data[0];
        productName = data[1];
        market = data[2];
        sharesIssued = Integer.parseInt(data[3]);
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getMarket() {
        return market;
    }
    public void setMarket(String market) {
        this.market = market;
    }
    public int getSharesIssued() {
        return sharesIssued;
    }
    public void setSharesIssued(int sharesIssued) {
        this.sharesIssued = sharesIssued;
    }
    Stock(){}
}

