package com.example.a2019cap_ver2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyAdPagerAdapter extends PagerAdapter {
    private final int pageNum = 10;
    private Context mContext;

    private int[] resId = { R.drawable.bs06, R.drawable.bs15, R.drawable.bs17};

    MyAdPagerAdapter(Context con){
        mContext = con;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if(mContext != null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.pager_ad, container, false);

            ImageView imgView = view.findViewById(R.id.imgPageItem);
            imgView.setImageResource(resId[position]);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        return resId.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }
}
