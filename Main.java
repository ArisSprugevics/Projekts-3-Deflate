// 191REB140 Āris Spruģevics 5. grupa

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
public class Main {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String choiseStr;
        String sourceFile, resultFile, firstFile, secondFile;
        String tempSource = "File1.html";
        String tempResult = "CompressedFile";
        String tempUncompressed = "Uncompressed1.html";

        loop: while (true) {

            choiseStr = sc.next();

            switch (choiseStr) {
                case "comp":
                    System.out.print("source file name: ");
                    sourceFile = tempSource;//sc.next();
                    System.out.print("archive name: ");
                    resultFile = tempResult;//sc.next();
                    comp(sourceFile, resultFile);
                    break;
                case "decomp":
                    System.out.print("archive name: ");
                    sourceFile = tempResult;//sc.next();
                    System.out.print("file name: ");
                    resultFile = tempUncompressed;//sc.next();
                    decomp(sourceFile, resultFile);
                    break;
                case "size":
                    System.out.print("file name: ");
                    sourceFile = sc.next();
                    size(sourceFile);
                    break;
                case "equal":
                    System.out.print("first file name: ");
                    firstFile = tempSource;//sc.next();
                    System.out.print("second file name: ");
                    secondFile = tempUncompressed;//sc.next();
                    System.out.println(equal(firstFile, secondFile));
                    break;
                case "about":
                    about();
                    break;
                case "exit":
                    break loop;
            }
        }
        sc.close();
    }

    public static void comp(String sourceFile, String resultFile) {
        // TODO: implement comp method
        // LZ77
        try {
            FileInputStream fails = new FileInputStream(sourceFile);
            String text = new String(fails.readAllBytes(), StandardCharsets.UTF_8);

            String middle = LZ77.compress(text);


        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public static void decomp(String sourceFile, String resultFile) {
        // TODO: implement decomp method
    }

    public static void size(String sourceFile) {
        try {
            FileInputStream f = new FileInputStream(sourceFile);
            System.out.println("size: " + f.available());
            f.close();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static boolean equal(String firstFile, String secondFile) {
        try {
            FileInputStream f1 = new FileInputStream(firstFile);
            FileInputStream f2 = new FileInputStream(secondFile);
            int k1, k2;
            byte[] buf1 = new byte[1000];
            byte[] buf2 = new byte[1000];
            do {
                k1 = f1.read(buf1);
                k2 = f2.read(buf2);
                if (k1 != k2) {
                    f1.close();
                    f2.close();
                    return false;
                }
                for (int i=0; i<k1; i++) {
                    if (buf1[i] != buf2[i]) {
                        f1.close();
                        f2.close();
                        return false;
                    }

                }
            } while (k1 == 0 && k2 == 0);
            f1.close();
            f2.close();
            return true;
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static void about() {
        // TODO insert information about authors
        System.out.println("191REB140 Āris Spruģevics");
        System.out.println("111RDB111 Ilze Programmētāja");
        System.out.println("111RDB111 Ilze Programmētāja");
    }

}

public class LZ77 { // TODO LZ77 class
    private static final int WINDOW_SIZE = 32768;
    private static final int LOOKAHEAD_SIZE = 20;
    public static String compress(String input){
        String output;
        char[] matching;
        int pos = 0;
        while(pos < input.length()){
            matching = findLongestMatch(input, pos);
            output =
        }
    }
    public static String decompress(String input){

    }
    public static char[] findLongestMatch(String input, int position){

    }

}
