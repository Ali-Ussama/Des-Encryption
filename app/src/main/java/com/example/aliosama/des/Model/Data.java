package com.example.aliosama.des.Model;

import java.util.ArrayList;

/**
 * Created by aliosama on 12/17/2017.
 */

public class Data {
    private static ArrayList<String> MessageBlocks ;
    private static String Message;
    private static ArrayList<LR_Model> L_R;
    private static String CipherText = "";
    private static LR_Model L0_R0;
    private static ArrayList<String> MessageBlocks_Cipher ;
    private static String Message_Cipher;
    private static ArrayList<LR_Model> L_R_C;
    private static String PlainText = "";
    private static LR_Model L16_R16;
    private static ArrayList<OutputModel.OutputDetails> mOutputDetails;

    public Data(){
        MessageBlocks = new ArrayList<>();
        Message = "";
        L0_R0 = null;
        L_R = new ArrayList<>();
        CipherText = "";
        PlainText = "";
        MessageBlocks_Cipher = new ArrayList<>();
        Message_Cipher = "";
        L_R_C = new ArrayList<>();
        mOutputDetails = new ArrayList<>();
    }

    private static int[][] IP =
                            {{58, 50, 42, 34, 26, 18, 10, 2},
                            {60, 52, 44, 36, 28, 20, 12, 4},
                            {62, 54, 46, 38, 30, 22, 14, 6},
                            {64, 56, 48, 40, 32, 24, 16, 8},
                            {57, 49, 41, 33, 25, 17, 9, 1},
                            {59, 51, 43, 35, 27, 19, 11, 3},
                            {61, 53, 45, 37, 29, 21, 13, 5},
                            {63, 55, 47, 39, 31, 23, 15, 7}};

    private static int[][] P =
                            {{16, 7, 20, 21},
                            {29, 12, 28, 17},
                            {1, 15, 23, 26},
                            {5, 18, 31, 10},
                            {2, 8, 24, 14},
                            {32, 27, 3, 9},
                            {19, 13, 30, 6},
                            {22, 11, 4, 25}};

    private static int[][] IPInverse =
                                    {{40, 8, 48, 16, 56, 24, 64, 32},
                                    {39, 7, 47, 15, 55, 23, 63, 31},
                                    {38, 6, 46, 14, 54, 22, 62, 30},
                                    {37, 5, 45, 13, 53, 21, 61, 29},
                                    {36, 4, 44, 12, 52, 20, 60, 28},
                                    {35, 3, 43, 11, 51, 19, 59, 27},
                                    {34, 2, 42, 10, 50, 18, 58, 26},
                                    {33, 1, 41, 9, 49, 17, 57, 25}};

    public OutputModel Encryption(String PlainText,ArrayList<KeyModel> SixteenSubKeys) {
        try{
            mOutputDetails = new ArrayList<>();
            MessageBlocks = new ArrayList<>();
            Message = PlainText;
			String PaddingMessage = Converter.Padding(Message);
			String HexMessage = Converter.asciiToHex(PaddingMessage);
            String BinaryMessage = Converter.hexToBin(HexMessage);
            MessageBlocks = DivideMessageInto64BinaryBlocks(BinaryMessage);

            //Encrypt Each 64 bits of Message.
            for (String message : MessageBlocks) {
                String IPMessage = ApplyIP(message);
                L_R = new ArrayList<>();
                L0_R0 = CreateL0_R0(IPMessage);
                L_R.add(L0_R0);
                for(int Round = 0 ; Round < 16 ; Round++){
                    L_R.add(Calculate_L_R_FoEachRound(L_R.get(Round),SixteenSubKeys.get(Round)));// Send Ln-1 & Rn-1 & Kn and receive Ln & Rn
                }
                L16_R16 = L_R.get(L_R.size()-1);
                L16_R16 = Swap(L16_R16);
                String IPInverse = ApplyIPInverse(L16_R16);
                CipherText += IPInverse;
                print(IPMessage,message,L_R,L16_R16,IPInverse);
            }
//            System.out.println(CipherText);
            CipherText = Converter.BinToHex(CipherText);
//            CipherText = Converter.hexToAscii(CipherText);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new OutputModel(mOutputDetails,CipherText);
    }

    public OutputModel  Decryption (String CipherText,ArrayList<KeyModel> SixteenSubKeys){
        try{
            mOutputDetails = new ArrayList<>();
            MessageBlocks_Cipher = new ArrayList<>();
            Message_Cipher = CipherText;
            String BinaryMessage = Converter.hexToBin(Message_Cipher);
            MessageBlocks_Cipher = DivideMessageInto64BinaryBlocks(BinaryMessage);
            for (String message : MessageBlocks_Cipher) {
                L_R_C = new ArrayList<>();
                String IPMessage = ApplyIP(message);
                L16_R16 = CreateL0_R0(IPMessage);
                L16_R16 = Swap(L16_R16);
                L_R_C.add(L16_R16);
                int num_key=15;
                for(int Round = 0 ; Round <16 ; Round++){
                    L_R_C.add(Calculate_L_R_C_FoEachRound(L_R_C.get(Round),SixteenSubKeys.get(num_key--)));
                }
                String IPInverse = ApplyIPInverse(L_R_C.get(L_R_C.size()-1));
                PlainText += IPInverse;
                print(IPMessage,message,L_R_C,L16_R16,IPInverse);
            }

            PlainText = Converter.BinToHex(PlainText);
            PlainText = Converter.hexToAscii(PlainText);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return new OutputModel(mOutputDetails, PlainText);

    }

    private static void print(String IPMessage,String Message,ArrayList<LR_Model> L_R,LR_Model L16_R16,String ipInverse){
        mOutputDetails.add(new OutputModel.OutputDetails(IPMessage,Message,L_R,L16_R16,ipInverse));
    }

    private static ArrayList<String> DivideMessageInto64BinaryBlocks(String message) {
        ArrayList<String> result = new ArrayList<String>() ;
        for(int i = 0 ; i < message.length(); i+=64)
            result.add(message.substring(i,i+64));
        return result;
    }

    private static String ApplyIP(String message){
        String result = "";
        for(int i = 0 ; i < IP.length ; i++)
            for(int j = 0 ; j < IP[i].length ; j++){
                result += message.charAt(IP[i][j]-1);
            }
        return result;
    }

    private static LR_Model CreateL0_R0(String message){
        return new LR_Model(message.substring(0, message.length()/2),
                message.substring(message.length()/2, message.length()));
    }

    private static LR_Model Calculate_L_R_FoEachRound(LR_Model mL_R,KeyModel mKeyModel) {
        String Right_n_1 = mL_R.getR(); // right(n-1).
        String Left_n = Right_n_1; // Left(n) = right(n-1).
        String Left_n_1 = mL_R.getL();//Left(n-1).
        String MangFunctionResult = ManglerFunction(Right_n_1,mKeyModel);//Mangler Function = f(right(n-1),Key(n)).
        String Right_n = ApplyXOR(Left_n_1, MangFunctionResult);// Right(n) = Left(n-1) XOR Mangler Function.
        return new LR_Model(Left_n, Right_n);
    }

    private static LR_Model Calculate_L_R_C_FoEachRound(LR_Model mL_R,KeyModel mKeyModel) {
        String Left_n= mL_R.getL();
        String Right_n = mL_R.getR();
        String Right_n_1 = Left_n;
        String MangFunctionResult = ManglerFunction(Left_n,mKeyModel);
        String Left_n_1 = ApplyXOR( Right_n, MangFunctionResult);
        return new LR_Model(Left_n_1, Right_n_1);
    }

    private static String ManglerFunction(String Right, KeyModel mKeyModel) {
        Right = Expand(Right);
        Right = ApplyXOR(Right, mKeyModel.getKey());
        Right = ApplySBox(Right);
        Right = ApplyP(Right);

        return Right;
    }

    private static String Expand(String right) {
        String result = "";
        for(int i = 0 ; i < right.length(); i+=4){
            if(i != 0 && i != right.length()-4){//block in between first & last.
                result += right.charAt(i-1) + right.substring(i,i+5);
            }
            else if(i == 0){//if first block.
                result += right.charAt(right.length()-1) + right.substring(i,i+5);
            }
            else{// if last block.
                result += right.charAt(i-1) + right.substring(i,i+4) + right.charAt(0);
            }
        }
        return result;
    }

    private static String ApplyXOR(String Var1, String Var2){
        String result = "";
        for(int i = 0 ; i < Var1.length(); i++){
            if(Var1.charAt(i) != Var2.charAt(i))
                result += '1';
            else
                result +='0';
        }
        return result;
    }

    private static String ApplySBox(String right) {
        String result = "";
        int SBOX_Number = 0;
        String[][][] Binary_SBox_List = getBinarySbox(new SBox_Model().getSBox());
//		System.out.println(right);
        for(int i = 0 ; i < right.length(); i+=6){//in each block of 6 bits.
            int row = Converter.BinToDec((String)(right.charAt(i) +""+ right.charAt(i+5)));//first bit + last bit
            int col = Converter.BinToDec((String)(right.substring(i+1,i+5))); //bits from 1 to 4.
//			System.out.println(row + " "+ col);
            result += Binary_SBox_List[SBOX_Number++][row][col];
        }
        return result;
    }

    private static String[][][] getBinarySbox(int[][][] sBox) {
        String [][][] Result = new String[sBox.length][sBox[0].length][sBox[0][0].length];

        for(int SBOX = 0 ; SBOX < sBox.length; SBOX++){
            for(int row = 0 ; row < sBox[0].length; row++){
                for(int col = 0 ; col < sBox[0][0].length; col++){
                    Result[SBOX][row][col] = Converter.DecToBin(String.valueOf(sBox[SBOX][row][col]));
                }
            }
        }
        return Result;
    }

    private static String ApplyP(String right) {
        String result = "";
        for(int i = 0 ; i < P.length ; i++)
            for(int j = 0 ; j < P[i].length ; j++){
                result += right.charAt(P[i][j]-1);
            }
        return result;
    }

    private static LR_Model Swap(LR_Model l_R2) {
        String Temp = l_R2.getL();
        l_R2.setL(l_R2.getR());
        l_R2.setR(Temp);
        return l_R2;
    }

    private static String ApplyIPInverse(LR_Model lr_Model) {
        String R16_L16 = (lr_Model.getL()+""+lr_Model.getR());
        String result = "";
        for(int i = 0 ; i < IPInverse.length ; i++)
            for(int j = 0 ; j < IPInverse[i].length ; j++){
                result += R16_L16.charAt(IPInverse[i][j]-1);
            }
        System.out.println(result+"blaaaaah");
        return result;
    }

}
