package com.example.helmi.pjbdata.db;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.helmi.pjbdata.R;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView img;
    TextView Name,ATxt,BTxt,CTxt;
    ItemClickListener itemClickListener;

    public MyHolder(View itemView) {
        super(itemView);

        //ASSIGN
        Name= (TextView)itemView.findViewById(R.id.txtNama);
        ATxt= (TextView) itemView.findViewById(R.id.txtA);
        BTxt= (TextView) itemView.findViewById(R.id.txtB);
        CTxt= (TextView) itemView.findViewById(R.id.txtC);


        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(v,getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener ic)
    {
        this.itemClickListener=ic;
    }
}
