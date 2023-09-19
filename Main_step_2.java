import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.text.DecimalFormat;

public class Main_step_2 {
    public static void main(String[] args) {
        System.out.println("株式取引管理システムを開始します。");
        boolean running = true;
        while(running) {
            System.out.println("操作するメニューを選んでください。");
            System.out.println("  1. 銘柄マスタ一覧表示");
            System.out.println("  2. 銘柄マスタ新規登録");
            System.out.println("  9. アプリケーションを終了する");
            while(true) {
                System.out.print("入力してください: ");
                Scanner sc = new Scanner(System.in);
                String input = sc.nextLine();
                int inputNum;
                try {
                    inputNum = Integer.parseInt(input); //把一个String转换成Int，如果不是整数的话报错。
                } catch (NumberFormatException e) {
                    System.out.println("\"" + input + "\" に対応するメニューは存在しません。");
                    continue;
                }
                if(inputNum == 1) {
                    System.out.println("「銘柄マスタ一覧表示」が選択されました。");
                    ShowAllStocks();
                    System.out.println("---");
                    break;
                } else if(inputNum == 2) {
                    System.out.println("「銘柄マスタ新規登録」が選択されました。");
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
    static void ShowAllStocks() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader("Database.csv")); //读文件
            reader.readLine();
            String line;
            System.out.println("|" + "=".repeat(61) + "|" );
            // 4+2 25+2 8+2 15+2
            System.out.println("| Code | Product Name              | Market   | Shares Issued |");
            System.out.println("|------+---------------------------+----------+---------------|");
            while((line = reader.readLine()) != null) { //逐行读数据
                String[] data = line.split("\t");
                Stock thisStock = new Stock(); //存储每一行数据对应的股票
                thisStock.code = data[0];
                thisStock.productName = data[1];
                thisStock.market = data[2];
                thisStock.sharesIssued = Integer.parseInt(data[3]);

                System.out.print("| " + thisStock.code + " ");
                if(thisStock.productName.length() > 22)
                    System.out.print("| " + thisStock.productName.substring(0,22) + "... ");
                else
                    System.out.print("| " + thisStock.productName + " ".repeat(26-thisStock.productName.length()));
                DecimalFormat df = new DecimalFormat("#,###");
                System.out.printf("| %-8s | %13s |\n", thisStock.market, df.format(thisStock.sharesIssued));
            }
            System.out.println("|" + "=".repeat(61) + "|" );
        } catch (IOException e) {
            System.out.println("データベースのファイルは存在しません。");
        }
    }

}

