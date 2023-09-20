import java.io.*;
import java.time.format.DateTimeFormatter;

public class StockTransactionManager {

    static public DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private final String transactionDatabaseFile;
    public StockTransactionManager(String transactionDatabaseFile) throws IOException{
        this.transactionDatabaseFile = transactionDatabaseFile;
        FileReader reader = new FileReader(transactionDatabaseFile);
    }
    public void AddNewTransaction(Transaction log) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(transactionDatabaseFile, true));
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
