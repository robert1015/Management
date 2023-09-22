import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class StockTradeManager {

    static public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String tradeDatabaseFile;
    public StockTradeManager(String tradeDatabaseFile) throws IOException{
        this.tradeDatabaseFile = tradeDatabaseFile;
        FileReader reader = new FileReader(tradeDatabaseFile);
    }
    public void addNewTrade(Trade log) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tradeDatabaseFile, true));
            writer.newLine();
            String entry = "";
            entry += log.getTimestamp().format(formatter);
            entry += "\t" + log.getCode() + "\t" + log.getProductName() + "\t" + (log.isSell()?"SELL":"BUY") + "\t" + log.getAmount() + "\t" + log.getPricePerShare();
            writer.write(entry);
            writer.close();
        } catch (IOException e) {
            System.out.println("ERROR: データベースのファイルは存在しません。");
        }
    }

    public List<Trade> loadAllTrades(){
        List<Trade> tradeList = new ArrayList<>();;
        try {
            BufferedReader reader;
            reader = new BufferedReader(new FileReader(this.tradeDatabaseFile)); //读文件
            reader.readLine();
            String entry;
            while ((entry = reader.readLine()) != null) { //逐行读数据
                String[] data = entry.split("\t");
                LocalDateTime time = LocalDateTime.parse(data[0], formatter);
                String code = data[1];
                ProductName productName = new ProductName(data[2]);
                boolean tradeType = data[3].equals("BUY") ? true : false;
                long amount = Long.parseUnsignedLong(data[4]);
                BigDecimal pricePerShare = BigDecimal.valueOf(Double.parseDouble(data[5]));
                Trade trade = new Trade(time, productName, code, tradeType, amount, pricePerShare);
                tradeList.add(trade);
            }
            tradeList.sort(new Trade.CompareByDate());
        } catch (IOException e) {
            System.out.println("ERROR: データベースのファイルは存在しません。");
        }
        return tradeList;
    }
}
