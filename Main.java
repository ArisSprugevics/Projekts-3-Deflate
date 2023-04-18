import java.util.Scanner;

public class Main {
    public static void main (String[] args){
        System.out.println("Hello world");
        Scanner sc = new Scanner(System.in);
        String input;
        String[] komanda;
        boolean exit = false;

        while(!exit){
            input = sc.nextLine();
            komanda = input.split(" ", 2);

            switch (komanda[0]) {
                case "print":
                    System.out.println(komanda[0]);
                    break;
                case "exit":
                    exit = true;
                    break;
                default:
                    System.out.println("Wrong command");
                    break;
            }

        }
    }
}
