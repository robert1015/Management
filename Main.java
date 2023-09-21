import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String stockDatabaseFile = "Database.csv";
    static String transactionDatabaseFile = "Transaction.csv";
    public static void main(String[] args) {
        System.out.println("株式取引管理システムを開始します。");
        boolean running = true;
        StockListManager stockManager;
        try {
            stockManager = new StockListManager(stockDatabaseFile);
        }catch(IOException e) {
            System.out.println("ERROR: データベースのファイルは存在しません。");
            return;
        }catch(Exception e) {
            System.out.println("ERROR: データベースの読み込みに失敗しました。");
            return;
        }
        StockTradeManager tradeManager;
        try {
            tradeManager = new StockTradeManager(transactionDatabaseFile);
        }catch (IOException e) {
            System.out.println("ERROR: 取引記録のファイルは存在しません。");
            return;
        }
        while(running) {
            System.out.println("操作するメニューを選んでください。");
            System.out.println("  1. 銘柄マスタ一覧表示");
            System.out.println("  2. 銘柄マスタ新規登録");
            System.out.println("  3. 取引登録");
            System.out.println("  9. アプリケーションを終了する");
            while(true) {
                System.out.print("入力してください: ");
                Scanner sc = new Scanner(System.in);
                String input = sc.next();
                int inputNum;
                try {
                    inputNum = Integer.parseInt(input); //把一个String转换成Int，如果不是整数的话报错。
                } catch (NumberFormatException e) {
                    System.out.println("\"" + input + "\" に対応するメニューは存在しません。");
                    continue;
                }
                if(inputNum == 1) {
                    System.out.println("「銘柄マスタ一覧表示」が選択されました。");
                    ShowAllStocks(stockManager);
                    System.out.println("---");
                    break;
                } else if(inputNum == 2) {
                    System.out.println("「銘柄マスタ新規登録」が選択されました。");
                    AddNewStock(stockManager);
                    System.out.println("---");
                    break;
                }  else if(inputNum == 3) {
                    System.out.println("「取引登録」が選択されました。");
                    AddNewTransAction(stockManager, tradeManager);
                    System.out.println("---");
                    break;
                } else if(inputNum == 9) {
                    running = false;
                    break;
                } else{
                    System.out.println("\"" + input + "\" に対応するメニューは存在しません。");
                }
            }
        }
        System.out.println("アプリケーションを終了します。");
    }
    static void ShowAllStocks(StockListManager stockListManager) {
        if(stockListManager.getStocks().size() == 0) {
            System.out.println("ERROR: 登録された銘柄マスタはありません。");
            return;
        }
        System.out.println("|" + "=".repeat(61) + "|" );
        // 4+2 25+2 8+2 15+2
        System.out.println("| Code | Product Name              | Market   | Shares Issued |");
        System.out.println("|------+---------------------------+----------+---------------|");
        for(Stock stock: stockListManager.getStocks()) { //逐行读数据
            System.out.print("| " + stock.getCode() + " ");
            if(stock.getProductName().length() > 22)
                System.out.print("| " + stock.getProductName().substring(0,22) + "... ");
            else
                System.out.print("| " + stock.getProductName() + " ".repeat(26-stock.getProductName().length()));
            DecimalFormat df = new DecimalFormat("#,###");
            System.out.printf("| %-8s | %13s |\n", stock.getMarket(), df.format(stock.getSharesIssued()));
        }
        System.out.println("|" + "=".repeat(61) + "|" );
    }


    static void AddNewStock(StockListManager stockManager) {
        Scanner sc = new Scanner(System.in);
        System.out.println("新規株式銘柄マスタを登録します");

        //输入名字
        ProductName productName;
        while(true) {
            System.out.print("銘柄名> ");
            String input = sc.nextLine();
            if(input.equals("exit")) //输入exit退出
                return;
            try {
                productName = new ProductName(input);
                if(stockManager.containsStockByName(productName)) {
                    System.out.println("ERROR: この銘柄名はすでに存在している。");
                } else break;
            } catch (Exception e) {
                System.out.println("不正な銘柄名の入力。「a-z, A-Z, 0-9, .と非連続スペース」");
            }
        }

        //输入code
        String code;
        while(true){
            System.out.print("銘柄コード> ");
            code = sc.nextLine();
            if(code.equals("exit"))
                return;
            Pattern pattern = Pattern.compile("^[0-9]{4}$");
            Matcher matcher = pattern.matcher(code);
            if(matcher.matches()) {
                if(stockManager.containsStockByCode(code)){
                    System.out.println("ERROR: この銘柄コードはすでに存在している。");
                } else break;
            } else {
                System.out.println("ERROR: 銘柄コードは4桁の半角数字で入力してください。");
            }
        }

        //输入market
        Market market;
        while(true){
            System.out.print("上場市場> ");
            String input = sc.nextLine();
            if(input.equals("exit"))
                return;
            try {
                market = Market.parseMarket(input);
                break;
            }catch (Exception e) {
                System.out.println("ERROR: 上場市場はPRIME、STANDARD、GROWTHのいずれかを入力してください。");
            }
        }

        //输入shares
        long sharesIssued;
        while(true) {
            System.out.print("発行済み株式数(0-2,000,000,000)> ");
            String input = sc.nextLine();
            if (input.equals("exit"))
                return;
            if(input.length() > 10) {
                System.out.println("範囲内の正の整数を入力してください。");
            } else {
                try {
                    sharesIssued = Long.parseUnsignedLong(input);
                    if(sharesIssued < 0 || sharesIssued > 2000000000) {
                        System.out.println("範囲内の正の整数を入力してください。");
                    } else break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: 発行済み株式数は正の整数で入力してください。");
                }
            }
        }
        stockManager.AddStock(new Stock(code, productName, market, sharesIssued));
        System.out.println(productName + "を新規銘柄として登録しました");
    }

    static void AddNewTransAction(StockListManager stockManager, StockTradeManager transactionManager) {
        Scanner sc = new Scanner(System.in);
        System.out.println("新規取引を登録します");
        LocalDateTime time;
        while(true) {
            System.out.print("取引日時（yyyy-MM-dd HH:mm）> ");
            String timeStr = sc.nextLine();
            if(timeStr.equals("exit"))
                return;
            try {
                time = LocalDateTime.parse(timeStr, StockTradeManager.formatter);
                if(time.isAfter(LocalDateTime.now())) {
                    System.out.println("ERROR: 取引日時の入力値は現在時間よりも過去の日時である必要がある。");
                }else if(!(time.getDayOfWeek().getValue()>=1 &&
                        time.getDayOfWeek().getValue()<=5 &&
                        time.getHour()>=9 &&
                        time.getHour()<15)) {
                    System.out.println("ERROR: 取引日時は平日 (月曜〜金曜) の 9:00〜15:00 の間に収まるもののみを受け付けてください");
                } else break;
            } catch (Exception e) {
                System.out.println("ERROR: 取引日時の形式は\"yyyy-MM-dd HH:mm\"");
            }
        }

       ProductName productName;
        while(true) {
            System.out.print("銘柄名> ");
            String input = sc.nextLine();
            if(input.equals("exit"))
                return;
            try {
                productName = new ProductName(input);
                if(!stockManager.containsStockByName(productName)) {
                    System.out.println("ERROR: 入力された銘柄名は登録されてない。");
                } else break;
            } catch (Exception e) {
               System.out.println("不正な銘柄名の入力。「a-z, A-Z, 0-9, .と非連続スペース」");
            }
        }
        String code = stockManager.getByName(productName).getCode();

        boolean tradeType;
        while(true) {
            System.out.print("売買区分「買/売」> ");
            String tradeTypeStr = sc.nextLine();
            if(tradeTypeStr.equals("exit"))
                return;
            if(tradeTypeStr.equals("売")) {
                tradeType = false;
                break;
            } else if(tradeTypeStr.equals("買")) {
                tradeType = true;
                break;
            }else {
                System.out.println("ERROR: 入力は「買/売」でなければならない。");
            }
        }

        int amount;
        while (true) {
            System.out.print("数量> ");
            String input = sc.nextLine();
            if (input.equals("exit"))
                return;
            try {
                amount = Integer.parseInt(input);
                if(amount % 100 != 0) {
                    System.out.println("ERROR: 取引数量は100株単位で扱わなければならない。");
                } else if(amount <= 0) { //交易数量是个正数
                    System.out.println("ERROR: 取引数量は正数で入力してください。");
                } else {
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: 取引数量は整数で入力してください。");
            }
        }

        BigDecimal pricePerShare;
        while (true) {
            System.out.print("取引単価> ");
            String input = sc.nextLine();
            if (input.equals("exit"))
                return;
            try {
                double price = Double.parseDouble(input);
                if(price <= 0) { //判断交易单价是个正数
                    System.out.println("ERROR: 取引単価は正数で入力してください。");
                } else {
                    pricePerShare = BigDecimal.valueOf(price);
                    pricePerShare = pricePerShare.setScale(2, RoundingMode.HALF_UP);
                    break;
                }
            } catch (NumberFormatException e) {
                System.out.println("ERROR: 取引単価は数字で入力してください。");
            }
        }
        Trade log = new Trade(time, productName, code, tradeType, amount, pricePerShare);
        transactionManager.AddNewTransaction(log);

        System.out.println("取引記録を新規銘柄として登録しました");
    }

}


