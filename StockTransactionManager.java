import java.io.*;
import java.time.format.DateTimeFormatter;

public class StockTradeManager {

    static public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String tradeDatabaseFile;
    public StockTradeManager(String tradeDatabaseFile) throws IOException{
        this.tradeDatabaseFile = tradeDatabaseFile;
        FileReader reader = new FileReader(tradeDatabaseFile);
    }
    public void AddNewTransaction(Trade log) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(tradeDatabaseFile, true));
            writer.newLine();
            String entry = "";
            entry += log.getTimestamp().format(formatter);
            entry += "\t" + log.getCode() + "\t" + log.getProductName() + "\t" + (log.isSell()?"SELL":"BUY") + "\t" + log.getAmount() + "\t" + log.getPricePerShare();
            writer.write(entry);
            writer.close();
        } catch (IOException e) {
            System.out.println("データベースのファイルは存在しません。");
        }
    }
}
