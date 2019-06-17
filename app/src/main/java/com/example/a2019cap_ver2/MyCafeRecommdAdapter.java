package com.example.a2019cap_ver2;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyCafeRecommdAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgCafeImg;
        TextView tvCafeName;

        MyViewHolder(View view){
            super(view);
            imgCafeImg = view.findViewById(R.id.imgRecommdCafe);
            tvCafeName = view.findViewById(R.id.tvRecommdName);
        }
    }

    Context mContext;
    private ArrayList<MainActivity.Cafe> cafeArrayList;
    int itemNum = 3;

    MyCafeRecommdAdapter(Context context, ArrayList<MainActivity.Cafe> cafeArrayList){
        this.mContext = context;
        this.cafeArrayList = cafeArrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_recommend, parent, false);

        MyCafeRecommdAdapter.MyViewHolder myView = new MyCafeRecommdAdapter.MyViewHolder(v);

        return myView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyCafeRecommdAdapter.MyViewHolder myViewHolder = (MyCafeRecommdAdapter.MyViewHolder) holder;

        int resId = mContext.getResources().getIdentifier(cafeArrayList.get(position).imgSrcs[0], "drawable", mContext.getPackageName());
        myViewHolder.imgCafeImg.setImageResource(resId);
        myViewHolder.tvCafeName.setText(cafeArrayList.get(position).cafeName);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainAct = (MainActivity)mContext;
                Bundle bundle = new Bundle();
                bundle.putInt("CafeIndex", position);
                mainAct.changeManager.changeArgument("DetailedCafe", bundle);
                mainAct.changeManager.replaceFragment("DetailedCafe");
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemNum;
    }
}
