package com.example.aliosama.des.Model;

import java.io.Serializable;

/**
 * Created by aliosama on 12/17/2017.
 */

public class KeyModel implements Serializable {

    String Key;

    public KeyModel(String key){
        this.Key = key;
    }

    public String getKey() {
        return Key;
    }

    public void setKey(String key) {
        Key = key;
    }
}
