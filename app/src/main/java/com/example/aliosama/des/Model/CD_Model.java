package com.example.aliosama.des.Model;

import java.io.Serializable;

/**
 * Created by aliosama on 12/17/2017.
 */

public class CD_Model implements Serializable {
    String C;
    String D;

    public CD_Model(String c,String d){
        this.C = c;
        this.D = d;
    }

    public String getC() {
        return C;
    }

    public void setC(String c) {
        C = c;
    }

    public String getD() {
        return D;
    }

    public void setD(String d) {
        D = d;
    }
}
