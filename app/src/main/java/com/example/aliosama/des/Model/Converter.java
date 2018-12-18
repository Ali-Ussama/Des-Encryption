package com.example.aliosama.des.Model;

import java.math.BigInteger;
import java.util.ArrayList;

/**
 * Created by aliosama on 12/17/2017.
 */

public class Converter {

    public static String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString((int) ch));
        }
        return hex.toString();
    }

    public static String DecToBin(String number){
        String bin = new BigInteger(number, 10).toString(2);
        bin = String.format("%4s", bin).replace(" ", "0");
        return bin;
    }

    public static int BinToDec(String binaryStr){
        return Integer.parseInt(binaryStr,2);
    }

    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder("");

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }
        return output.toString();
    }

    public static String hexToBin(String s) {
        String bin = new BigInteger(s, 16).toString(2);
        bin = String.format("%64s", bin).replace(" ", "0");

        // put the ignored zeros before the binary string.
        if(bin.length()%64 != 0){
            int x = (64 - (bin.length()%64));
            for(int i = 0 ; i <x ; i++){
                bin = "0".concat(bin);
            }
        }
        return bin;
    }

    public static String BinToHex(String binaryStr){
        BigInteger bigInteger = new BigInteger(binaryStr,2);
        String hexStr  = bigInteger.toString(16).toUpperCase();
        hexStr = String.format("%16s",hexStr).replace(" ", "0");
        return hexStr;
    }

    public static String Padding(String Message){
        ArrayList<String> alpha = new ArrayList<>();
        for(char a = 'A'; a <= 'Z' ; a++){
            alpha.add(String.valueOf(a));
        }
        if((Message.length()%8) != 0){
            int PaddingLength = 8 - (Message.length()%8);

            for(int i = 0 ; i < PaddingLength; i++)
                Message += alpha.get(i);
            System.out.println("Padding :"+Message);
        }
        return Message;
    }

}
