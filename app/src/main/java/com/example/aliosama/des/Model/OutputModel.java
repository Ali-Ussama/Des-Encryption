package com.example.aliosama.des.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by aliosama on 12/18/2017.
 */

public class OutputModel implements Serializable{
    private ArrayList<OutputDetails> mOutputDetails;
    private String outputResult;

    public OutputModel(ArrayList<OutputDetails> mOutputDetails, String outputResult) {
        this.mOutputDetails = mOutputDetails;
        this.outputResult = outputResult;
    }

    public ArrayList<OutputDetails> getmOutputDetails() {
        return mOutputDetails;
    }

    public String getOutputResult() {
        return outputResult;
    }

    public static class OutputDetails implements Serializable{
        String IP;
        String MessageBlock;
        ArrayList<LR_Model> L_R;
        LR_Model LRSwap;
        String IPInverse;

        public OutputDetails(String IP, String messageBlock, ArrayList<LR_Model> l_R, LR_Model LRSwap, String IPInverse) {
            this.IP = IP;
            MessageBlock = messageBlock;
            L_R = l_R;
            this.LRSwap = LRSwap;
            this.IPInverse = IPInverse;
        }

        public String getIP() {
            return IP;
        }

        public String getMessageBlock() {
            return MessageBlock;
        }

        public ArrayList<LR_Model> getL_R() {
            return L_R;
        }

        public LR_Model getLRSwap() {
            return LRSwap;
        }

        public String getIPInverse() {
            return IPInverse;
        }
    }
}
