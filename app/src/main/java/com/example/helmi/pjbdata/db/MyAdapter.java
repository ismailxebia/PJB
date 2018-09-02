package com.example.helmi.pjbdata.db;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.helmi.pjbdata.DataChart;
import com.example.helmi.pjbdata.DetailActivity;
import com.example.helmi.pjbdata.MainActivity;
import com.example.helmi.pjbdata.R;

import java.util.ArrayList;

/**
 * Created by Hp on 3/17/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyHolder> {
    Context c;
    ArrayList<DataChart> players;

    public MyAdapter(Context ctx,ArrayList<DataChart> players)
    {
        //ASSIGN THEM LOCALLY
        this.c=ctx;
        this.players=players;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //VIEW OBJ FROM XML
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_save,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);

        //holder
        MyHolder holder=new MyHolder(v);

        return holder;
    }

    //BIND DATA TO VIEWS
    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.Name.setText(players.get(position).getName());
        holder.ATxt.setText(players.get(position).getApos());
        holder.BTxt.setText(players.get(position).getBpos());
        holder.CTxt.setText(players.get(position).getCpops());

        //HANDLE ITEMCLICKS
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                //CREATE INTENT
                Intent i=new Intent(c,DetailActivity.class);

                //LOAD DATA
                i.putExtra("NAME",players.get(pos).getName());
                i.putExtra("AVALUE",players.get(pos).getApos());
                i.putExtra("BVALUE",players.get(pos).getBpos());
                i.putExtra("CVALUE",players.get(pos).getCpops());
                i.putExtra("ID",players.get(pos).getId());

                //START ACTIVITY
                c.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}
