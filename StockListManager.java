import java.io.*;
import java.util.*;

public class StockListManager {
    private final String stockDatabaseFile;
    public List<Stock> stocks;
    private final Map<String, Stock> codeIndex;
    private final Map<String, Stock> nameIndex;

    StockListManager(String stockDatabaseFile) throws IOException {
        this.stockDatabaseFile = stockDatabaseFile;
        stocks = new ArrayList<>();
        codeIndex = new HashMap<>();
        nameIndex = new HashMap<>();
        LoadAllStocks();
    }
    private void LoadAllStocks() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(this.stockDatabaseFile)); //读文件
        reader.readLine();
        String entry;
        while((entry = reader.readLine()) != null) { //逐行读数据
            String[] data = entry.split("\t");
            Stock stock = new Stock(data);
            stocks.add(stock);
            codeIndex.put(stock.code, stock);
            nameIndex.put(stock.productName, stock);
        }
    }

    public void AddStock(Stock stock) {
        stocks.add(stock);
        codeIndex.put(stock.code, stock);
        nameIndex.put(stock.productName, stock);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(stockDatabaseFile, true));
            writer.newLine();
            writer.write(stock.code + "\t" + stock.productName + "\t" + stock.market + "\t" + stock.sharesIssued);
            writer.close();
        } catch (IOException e) {
            System.out.println("データベースのファイルは存在しません。");
        }
    }
    public boolean containsStockByCode(String code) {
        return codeIndex.containsKey(code);
    }
    public boolean containsStockByName(String name) {
        return nameIndex.containsKey(name);
    }
    public Stock getByName(String name) {
        return nameIndex.get(name);
    }
}
