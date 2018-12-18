package com.example.aliosama.des.View;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aliosama.des.Model.LR_Model;
import com.example.aliosama.des.Model.OutputModel;
import com.example.aliosama.des.R;

import java.util.ArrayList;

/**
 * Created by aliosama on 12/18/2017.
 */

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.ViewHolder> {
    ArrayList<OutputModel.OutputDetails> data;

    public RecAdapter(ArrayList<OutputModel.OutputDetails> data) {
        this.data = data;
    }

    @Override
    public RecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.output_rec_item_view,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecAdapter.ViewHolder holder, int position) {
        try {
            String LRAll = "\n";
            ArrayList<LR_Model> LR = data.get(position).getL_R();
            for (int i = 0 ; i < LR.size(); i++) {
                String L = "L "+String.valueOf(i)+" : "+LR.get(i).getL()+"\n\n";
                String R = "R "+String.valueOf(i)+" : " +LR.get(i).getR()+"\n\n";
                System.out.println(L+R);
                LRAll += L+R;
            }
            LR_Model LRSwap = data.get(position).getLRSwap();
            String LRswap = ("L : "+LRSwap.getL())+("\nR : "+LRSwap.getR()+"\n");

            holder.IP.setText(data.get(position).getIP());
            holder.MessageBlock.setText(data.get(position).getMessageBlock());
            holder.LR.setText(LRAll);
            holder.Swap.setText(LRswap);
            holder.IPInverse.setText(data.get(position).getIPInverse());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView IP,IPInverse,LR,MessageBlock, Swap;

        public ViewHolder(View itemView) {
            super(itemView);
            IP = itemView.findViewById(R.id.IPTV);
            IPInverse = itemView.findViewById(R.id.IPInverse);
            LR = itemView.findViewById(R.id.LRTV);
            MessageBlock = itemView.findViewById(R.id.MessageBlockTV);
            Swap = itemView.findViewById(R.id.LRSwapTV);
        }
    }
}
