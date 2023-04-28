// 191REB140 Āris Spruģevics 5. grupa

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.Objects;
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
            String result = LZ77.decompress(middle);
            System.out.println("compressed= " + middle);
            System.out.println("decompressed= " + result);


        }
        catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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

    public static class LZ77 { // TODO LZ77 class
        private static final int WINDOW_SIZE = 32768;
        private static final int LOOKAHEAD_SIZE = 20;
        public static String compress(String input){
            StringBuilder str1 = new StringBuilder();
            String[] matching;
            int pos = 0;
            while(pos < input.length()){
                matching = findLongestMatch(input, pos);
                str1.append(matching[0]);
                if(Objects.equals(matching[1], "0")){
                    pos = pos + 1;
                }
                else if(Integer.parseInt(matching[1]) <= 4){
                    pos = pos + Integer.parseInt(matching[1])+1;
                }
                else{
                    pos = pos + Integer.parseInt(matching[1]);
                }
            }
            return str1.toString();
        }
        public static String decompress(String input){
            System.out.println("enters decompress");
            int start, end;
            int pos = 0;
            StringBuilder str1 = new StringBuilder();
            while(pos < input.length()){
                System.out.println(pos);
                System.out.println(input.charAt(pos));
                if(input.charAt(pos) == '('){
                    int i = 1;
                    while(i < 15){
                        if(input.charAt(pos + i) == ')'){//TODO decompress LZ77
                            String sortable = input.substring(pos,pos+i+1);
                            sortable = sortable.replaceAll("[()]","");
                            String[] numbers = sortable.split(",");
                            start = pos - Integer.parseInt(numbers[0]);
                            end = Integer.parseInt(numbers[1]);
                            int j=0;
                            System.out.println(sortable);
                            while(j < end){
                                str1.append(input.charAt(start+j));
                                j++;
                            }
                            pos = pos + sortable.length()+1;
                            break;
                        }
                        else if(input.charAt(pos + i) == '('){
                            pos++;
                            break;
                        }
                        else{
                            i++;
                        }
                        if(i == 15){
                            pos++;
                        }
                    }
                }
                else{
                    str1.append(input.charAt(pos));
                    pos++;
                }

            }
            return str1.toString();
        }
        public static String[] findLongestMatch(String input, int position){

            int start,end;
            if(position <= WINDOW_SIZE) {
                start = 0;
            }
            else {
                start = position - WINDOW_SIZE;
            }

            end = Math.min(position + LOOKAHEAD_SIZE, input.length());

            int matchPos = 0;
            int matchLen = 0;

            for(int i = start; i < position; i++){
                for(int j = position; j < end; j++){
                    int len = 0;
                    if(input.charAt(i) != input.charAt(j)){
                        break;
                    }
                    while(j + len < input.length() && input.charAt(i + len) == input.charAt(j + len) && len < LOOKAHEAD_SIZE && input.charAt(i) == input.charAt(j)){
                        len++;
                    }
                    if(len > matchLen){
                        matchLen = len;
                        matchPos = position-i;
                    }

                }//for(int j = position; j < end; j++)
            }//for(int i = start; ii < position; i++)
            StringBuilder str2 = new StringBuilder();
            String[] output = new String[2];
            if(matchLen == 0){
                //str2.append("(0,0)");
                str2.append(input.charAt(position));
                output[0] = str2.toString();
                output[1] = "0";
            }
            else if (matchLen <= 4) {
                //str2.append("(0,0)");
                for(int i = 0; i <= matchLen && (position+i) < input.length(); i++){
                    str2.append(input.charAt(position+i));
                }
                output[0] = str2.toString();
                output[1] = Integer.toString(matchLen);
            }
            else{
                    str2.append("(");
                    str2.append(matchPos);
                    str2.append(",");
                    str2.append(matchLen);
                    str2.append(")");
                    //str2.append(input.charAt(position+matchLen-1));

                    output[0] = str2.toString();
                    output[1] = Integer.toString(matchLen);
                }

            return output;
        }

    }

}

