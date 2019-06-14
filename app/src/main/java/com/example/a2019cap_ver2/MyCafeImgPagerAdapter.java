package com.example.a2019cap_ver2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MyCafeImgPagerAdapter extends PagerAdapter {

    Context mContext;
    String[] imgSrcs;

    MyCafeImgPagerAdapter(Context context, String[] cafeImgName){
        super();
        this.mContext = context;
        this.imgSrcs = cafeImgName;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = null;
        if(mContext != null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.cafe_image_page, container, false);

            int resId = mContext.getResources().getIdentifier(imgSrcs[position], "drawable", mContext.getPackageName());

            ImageView imgView = view.findViewById(R.id.imgCafeImg);
            imgView.setImageResource(resId);
        }

        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return imgSrcs.length;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return (view == o);
    }
}
