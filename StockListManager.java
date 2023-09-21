import java.io.*;
import java.util.*;

public class StockListManager {
    private final String stockDatabaseFile;
    private final List<Stock> stocks;
    private final Map<String, Stock> codeIndex;
    private final Map<String, Stock> nameIndex;

    StockListManager(String stockDatabaseFile) throws Exception {
        this.stockDatabaseFile = stockDatabaseFile;
        stocks = new ArrayList<>();
        codeIndex = new HashMap<>();
        nameIndex = new HashMap<>();
        LoadAllStocks();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    private void LoadAllStocks() throws Exception {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(this.stockDatabaseFile)); //读文件
        reader.readLine();
        String entry;
        while((entry = reader.readLine()) != null) { //逐行读数据
            try {
                String[] data = entry.split("\t");
                String code = data[0];
                ProductName productName = new ProductName(data[1]);
                Market market = Market.parseMarket(data[2]);
                long sharesIssued = Long.parseLong(data[3]);
                Stock stock = new Stock(code, productName, market, sharesIssued);
                stocks.add(stock);
                codeIndex.put(stock.getCode(), stock);
                nameIndex.put(stock.getProductName(), stock);
            }catch (Exception e) {}
        }
    }

    public void AddStock(Stock stock) {
        stocks.add(stock);
        codeIndex.put(stock.getCode(), stock);
        nameIndex.put(stock.getProductName(), stock);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(stockDatabaseFile, true));
            writer.newLine();
            writer.write(stock.getCode() + "\t" + stock.getProductName() + "\t" + stock.getMarket() + "\t" + stock.getSharesIssued());
            writer.close();
        } catch (IOException e) {
            System.out.println("データベースのファイルは存在しません。");
        }
    }
    public boolean containsStockByCode(String code) {
        return codeIndex.containsKey(code);
    }
    public boolean containsStockByName(ProductName name) {
        return nameIndex.containsKey(name.getValue());
    }
    public Stock getByName(ProductName name) {
        return nameIndex.get(name.getValue());
    }
}
