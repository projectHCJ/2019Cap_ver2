package com.example.a2019cap_ver2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class MainFragment extends Fragment {
    private ViewPager viewPager ;
    private MyAdPagerAdapter pagerAdapter ;

    private MainActivity mainAct;

    public MainFragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainAct = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainAct = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //뷰페이저 설정
        viewPager = view.findViewById(R.id.viewPager) ;
        pagerAdapter = new MyAdPagerAdapter(getContext()) ;
        viewPager.setAdapter(pagerAdapter) ;

        view.findViewById(R.id.txtRecommend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.changeManager.replaceFragment("Recommend");
            }
        });

        view.findViewById(R.id.tvBookmarkMore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainAct.changeManager.replaceFragment("Bookmark");
            }
        });

        //북마크, 최근 검색 리사이클 뷰 설정
        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        ArrayList<MainActivity.Cafe> arr = mainAct.cafeArr;

        MyOverViewBookmarkAdapter myAdapter = new MyOverViewBookmarkAdapter(mainAct, arr);

        mRecyclerView = view.findViewById(R.id.rcycBookmark);

        mLayoutManager = new LinearLayoutManager(mainAct);
        ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(myAdapter);

        return view;
    }

}
