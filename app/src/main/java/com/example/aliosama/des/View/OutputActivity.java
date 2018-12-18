package com.example.aliosama.des.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.aliosama.des.Model.OutputModel;
import com.example.aliosama.des.R;

public class OutputActivity extends AppCompatActivity {
    TextView TVOutput,TVOutputType;
    String Type;
    Intent mIntent;
    OutputModel mOutputModel;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_output);
        try {
            Init();
            if (Type.equals("Encryption")){
                    TVOutputType.setText(getResources().getString(R.string.ciphertext));
            }else if(Type.equals("Decryption")){
                    TVOutputType.setText(getResources().getString(R.string.plaintext));
            }
            System.out.println("OutputDetails Size : "+mOutputModel.getmOutputDetails().size());
            TVOutput.setText(mOutputModel.getOutputResult());
            mRecyclerView.setAdapter(new RecAdapter(mOutputModel.getmOutputDetails()));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void Init() throws Exception{
            TVOutput = findViewById(R.id.TVOutput);
            TVOutputType = findViewById(R.id.TVOutputType);
            mIntent = getIntent();
            mOutputModel = (OutputModel) mIntent.getSerializableExtra("output");
            Type = mIntent.getExtras().getString("type");
            mRecyclerView = findViewById(R.id.RecViewDetails);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
