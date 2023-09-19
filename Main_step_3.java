import java.util.*;
import java.text.DecimalFormat;

public class Main_step_3 {
    static String stockDatabaseFile = "trading-app/src/main/java/Database.csv";
    public static void main(String[] args) {
        System.out.println("株式取引管理システムを開始します。");
        boolean running = true;
        StockListManager stockManager = new StockListManager(stockDatabaseFile);
        while(running) {
            System.out.println("操作するメニューを選んでください。");
            System.out.println("  1. 銘柄マスタ一覧表示");
            System.out.println("  2. 銘柄マスタ新規登録");
            System.out.println("  3. 取引を登録");
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
        System.out.println("|" + "=".repeat(61) + "|" );
        // 4+2 25+2 8+2 15+2
        System.out.println("| Code | Product Name              | Market   | Shares Issued |");
        System.out.println("|------+---------------------------+----------+---------------|");
        for(Stock stock: stockListManager.stocks) { //逐行读数据
            System.out.print("| " + stock.code + " ");
            if(stock.productName.length() > 22)
                System.out.print("| " + stock.productName.substring(0,22) + "... ");
            else
                System.out.print("| " + stock.productName + " ".repeat(26-stock.productName.length()));
            DecimalFormat df = new DecimalFormat("#,###");
            System.out.printf("| %-8s | %13s |\n", stock.market, df.format(stock.sharesIssued));
        }
        System.out.println("|" + "=".repeat(61) + "|" );
    }

    static void AddNewStock(StockListManager stockManager) {
        String[] data = new String[4];
        Scanner sc = new Scanner(System.in);
        System.out.println("新規株式銘柄マスタを登録します");

        //输入名字
        while(true) {
            System.out.print("銘柄名> ");
            String productName;
            productName = sc.nextLine();
            if(productName.equals("exit")) //输入exit退出
                return;
            if(stockManager.containsStockByName(productName)) {
                System.out.println("ERROR: この銘柄名はすでに存在している。");
            } else {
                data[1] = productName;
                break;
            }
        }

        //输入code
        while(true){
            System.out.print("銘柄コード> ");
            String code = sc.nextLine();
            if(code.equals("exit"))
                return;
            if(code.length() == 4 &&
                    code.charAt(0) >= '0' && code.charAt(0) <= '9' &&
                    code.charAt(1) >= '0' && code.charAt(1) <= '9' &&
                    code.charAt(2) >= '0' && code.charAt(2) <= '9' &&
                    code.charAt(3) >= '0' && code.charAt(3) <= '9') {
                if(stockManager.containsStockByCode(code)){
                    System.out.println("ERROR: この銘柄コードはすでに存在している。");
                } else {
                    data[0] = code;
                    break;
                }
            } else {
                System.out.println("ERROR: 銘柄コードは4桁の半角数字で入力してください。");
            }
        }

        //输入market
        while(true){
            System.out.print("上場市場> ");
            String market = sc.nextLine();
            if(market.equals("exit"))
                return;
            if(market.equals("PRIME") || market.equals("STANDARD") || market.equals("GROWTH")) {
                data[2] = market;
                break;
            } else {
                System.out.println("ERROR: 上場市場はPRIME、STANDARD、GROWTHのいずれかを入力してください。");
            }
        }

        //输入shares
        while(true) {
            System.out.print("発行済み株式数> ");
            String shares = sc.nextLine();
            if (shares.equals("exit"))
                return;
            try {
                Integer.parseInt(shares);
                data[3] = shares;
                break;
            } catch (NumberFormatException e) {
                System.out.println("ERROR: 発行済み株式数は整数で入力してください。");
            }
        }
        stockManager.AddStock(new Stock(data));
        System.out.println(data[1] + "を新規銘柄として登録しました");
    }

}


