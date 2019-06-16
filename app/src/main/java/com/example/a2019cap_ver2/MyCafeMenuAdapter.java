package com.example.a2019cap_ver2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class MyCafeMenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvMenuName, tvMenuPrice;

        MyViewHolder(View view){
            super(view);
            tvMenuName = view.findViewById(R.id.tvMenuName);
            tvMenuPrice = view.findViewById(R.id.tvMenuPrice);
        }
    }

    Context mContext;
    private String[] menuName;
    private String[] menuPrice;
    int itemNum = 3;

    MyCafeMenuAdapter(Context context, String[] menuName, String[] menuPrice){
        this.mContext = context;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_cafe_menu, parent, false);

        MyCafeMenuAdapter.MyViewHolder myView = new MyCafeMenuAdapter.MyViewHolder(v);

        return myView;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        MyCafeMenuAdapter.MyViewHolder myViewHolder = (MyCafeMenuAdapter.MyViewHolder) holder;

        myViewHolder.tvMenuName.setText(menuName[position]);
        myViewHolder.tvMenuPrice.setText(menuPrice[position]);
    }

    @Override
    public int getItemCount() {
        return itemNum;
    }
}
