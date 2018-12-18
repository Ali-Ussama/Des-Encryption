package com.example.aliosama.des.Model;

import java.io.Serializable;

/**
 * Created by aliosama on 12/17/2017.
 */

public class LR_Model implements Serializable{
    String L;
    String R;
    public LR_Model(String l, String r) {
        super();
        this.L = l;
        this.R = r;
    }
    public String getL() {
        return L;
    }
    public void setL(String l) {
        L = l;
    }
    public String getR() {
        return R;
    }
    public void setR(String r) {
        R = r;
    }
}
