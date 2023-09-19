public class Stock {
    String code;
    String productName;
    String market;
    int sharesIssued;
    Stock(String[] data) {
        code = data[0];
        productName = data[1];
        market = data[2];
        sharesIssued = Integer.parseInt(data[3]);
    }
    Stock(){}
}

