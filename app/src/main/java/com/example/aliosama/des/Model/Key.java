package com.example.aliosama.des.Model;

import java.util.ArrayList;

/**
 * Created by aliosama on 12/17/2017.
 */

public class Key {


    static ArrayList<KeyModel> SubKeys;
    static ArrayList<String> Key_8_blocks;
    static String Key;
    static ArrayList<CD_Model> C_D;
    static int[][] PC1 =
            {{57,49, 41, 33, 25, 17, 9},
                    {1, 58, 50, 42, 34, 26, 18},
                    {10, 2, 59, 51, 43, 35, 27},
                    {19, 11, 3, 60, 52, 44, 36},
                    {63, 55, 47, 39, 31, 23, 15},
                    {7, 62, 54, 46, 38, 30, 22},
                    {14, 6, 61, 53, 45, 37, 29},
                    {21, 13, 5, 28, 20, 12, 4,}};
    static int[][] PC2 =
            {{14 ,17, 11, 24, 1, 5},
                    {3, 28, 15, 6, 21, 10},
                    {23, 19, 12, 4, 26, 8},
                    {16, 7, 27, 20, 13, 2},
                    {41, 52, 31, 37, 47, 55},
                    {30, 40, 51, 45, 33, 48},
                    {44, 49, 39, 56, 34, 53},
                    {46, 42, 50, 36, 29, 32}};

    public Key(){
        SubKeys = new ArrayList<>();
        Key_8_blocks = new ArrayList<>();
        Key = "";
        C_D = new ArrayList<>();
    }
    public ArrayList<KeyModel> Generate_16_Subkeys(String keyString){
        // latest modification.
//        keyString = Converter.Padding(keyString);//---------Padding the Key Length
//        keyString = Converter.asciiToHex(keyString);//---------Convert ASCII to Hex.
        // old version.
        String BinaryKey = Converter.hexToBin(keyString);//---------Convert Hex to Binary
        Key_8_blocks = divide_Key_into_blocks(BinaryKey);//---------Divide key into 8 Blocks
        boolean CheckOddParity = false;
        CheckOddParity = check_odd_parity(Key_8_blocks);//--Check the odd parity bit
        if(!CheckOddParity){
            System.out.println("8 Blocks of Key :\n" +Key_8_blocks);
            System.out.println("check Odd Parity : False");
            return null;
        }
        Key = apply_pc1(Key_8_blocks);//-----------------------------Apply PC1
        C_D = split_key_into_C0_and_D0(Key);//-----------------------Split C0 D0
        C_D = apply_shift(C_D);//------------------------------------apply Shift;
        SubKeys = apply_pc2(C_D,SubKeys);//--------------------------Key(i) = C(i) + D(i)

        print(Key_8_blocks,CheckOddParity,Key,C_D,SubKeys);

        return SubKeys;
    }
    private static void print(ArrayList<String> key_8_blocks2, boolean Check, String key2, ArrayList<CD_Model> c_D2,
                              ArrayList<KeyModel> subKeys2) {
        if(Check){
            System.out.println("8 Blocks of Key :\n" +key_8_blocks2);
            System.out.println("check Odd Parity : True");
            System.out.println("Key After PC1 :\n"+key2);
            int count = 0;
            System.out.println("C D keys:");
            for (CD_Model cd_Model : C_D) {
                System.out.println(count++ + " " + cd_Model.getC() + " " + cd_Model.getD());
            }
            System.out.println("\n Final 16 Sub Keys :");
            count = 0;
            for (KeyModel keyModel : SubKeys) {
                System.out.println(count++ + " " + keyModel.getKey());
            }
        }


    }
    private static ArrayList<String> divide_Key_into_blocks(String Key) {
        ArrayList<String>result = new ArrayList<String>();
        for(int i = 0 ; i < 64; i+=8)
            result.add(Key.substring(i,i+8));

        return result;
    }
    private static boolean check_odd_parity(ArrayList<String> Key) {
        for(int i = 0 ; i < Key.size(); i++){
            int counter = 0;
            for(int index = 0 ; index < 8 ; index++){
                if(Key.get(i).charAt(index) == '1')
                    counter++;
            }
            if(counter%2 == 0)
                return false;
        }
        return true;
    }
    private static String apply_pc1(ArrayList<String> key) {
        String keyString = "";
        for (String string : key) keyString += string;

        String result = "";
        for(int i = 0 ; i < 8 ; i++)
            for(int j = 0; j < 7; j++)
                result += keyString.charAt(PC1[i][j]-1);
        return result;
    }
    private static ArrayList<CD_Model> split_key_into_C0_and_D0(String Key) {
        ArrayList<CD_Model> result = new ArrayList<>();
        result.add(new CD_Model(Key.substring(0,28), Key.substring(28,Key.length())));
        return result;
    }
    private static ArrayList<CD_Model> apply_shift(ArrayList<CD_Model> c_d) {
        for(int i = 0 ; i < 16 ; i++){
            String C = c_d.get(i).getC();
            String D = c_d.get(i).getD();
            if(i+1 == 1 ||i+1 == 2 ||i+1 == 9 ||i+1 == 16 ){
                C = C.substring(1,C.length()) + C.charAt(0);
                D = D.substring(1,D.length()) + D.charAt(0);
                c_d.add(new CD_Model(C,D));
            }else{
                C = C.substring(2,C.length()) + C.substring(0,2);
                D = D.substring(2,D.length()) + D.substring(0,2);
                c_d.add(new CD_Model(C,D));
            }
        }
        return c_d;
    }
    private static ArrayList<KeyModel> apply_pc2(ArrayList<CD_Model> cd,ArrayList<KeyModel> subKeys) {
        String oldkey = "";
        subKeys = new ArrayList<>();
        for (int index = 1 ; index < cd.size(); index++) {

            oldkey = cd.get(index).getC()+cd.get(index).getD();
            String newKey = "";
            for(int i = 0 ; i < PC2.length; i++){
                for(int j = 0 ; j < PC2[0].length; j++){
                    newKey += oldkey.charAt(PC2[i][j]-1);
                }
            }
            subKeys.add(new KeyModel(newKey));
        }
        return subKeys;
    }


}
