package com.example.aliosama.des.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.aliosama.des.Model.Data;
import com.example.aliosama.des.Model.Key;
import com.example.aliosama.des.Model.KeyModel;
import com.example.aliosama.des.Model.OutputModel;
import com.example.aliosama.des.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText KeyET,MessageET;
    RadioButton Hexa , Binary, Normal;
    RadioGroup mRadioGroup;
    Button Encrypt, Decrypt;
    OutputModel mOutputModel;
    ArrayList<KeyModel> SixteenSubKeys;
    String PlainText;
    String CipherText;

    Key mKey ;
    Data mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            init();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private void init() {
        KeyET = findViewById(R.id.ETKey);
        MessageET = findViewById(R.id.ETMessage);
        Normal = findViewById(R.id.RBNormal);
        Hexa = findViewById(R.id.RBHexa);
        Binary = findViewById(R.id.RBBinary);
        mRadioGroup = findViewById(R.id.RBGroup);
        Encrypt = findViewById(R.id.BtnEncrypt);
        Decrypt = findViewById(R.id.BtnDecrypt);

        MessageET.setText("Your lips are smoother than vaseline");
        KeyET.setText("133457799BBCDFF1");
        SixteenSubKeys = new ArrayList<>();
    }


    public void OnClickEncryption(View view) {
        try {
            String key = KeyET.getText().toString();
            PlainText = MessageET.getText().toString();
            mKey = new Key();
            mData = new Data();
            SixteenSubKeys = mKey.Generate_16_Subkeys(key);
            if(SixteenSubKeys != null){
                mOutputModel = mData.Encryption(PlainText,SixteenSubKeys);
                Intent mIntent = new Intent(this,OutputActivity.class);
                mIntent.putExtra("output",mOutputModel);
                mIntent.putExtra("type","Encryption");
                startActivity(mIntent);
            }else {
                Toast.makeText(this,"Key Odd Parity False",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClickDecryption(View view) {
        try {
            String key = KeyET.getText().toString();
            CipherText = MessageET.getText().toString();
            mKey = new Key();
            mData = new Data();
            SixteenSubKeys = mKey.Generate_16_Subkeys(key);
            if(SixteenSubKeys != null){
                mOutputModel = mData.Decryption(CipherText, SixteenSubKeys);
                Intent mIntent = new Intent(this,OutputActivity.class);
                mIntent.putExtra("output",mOutputModel);
                mIntent.putExtra("type","Decryption");
                startActivity(mIntent);
            }else {
                Toast.makeText(this,"Key Odd Parity False",Toast.LENGTH_LONG).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
