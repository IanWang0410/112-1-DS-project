import java.io.IOException;
import java.security.Key;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("Input Keyword: ");
        String keyword = sc.nextLine();
        System.out.println("Input type");
        int type = sc.nextInt();

        GoogleQuery handler = new GoogleQuery(keyword);

        Keyword mainKeyword = new Keyword(keyword, 100);

        // TODO create additional keywords





        try {
            handler.buildWebPageTrees();
        } catch (Exception e) {
            
        }







        sc.close();
    }
}
