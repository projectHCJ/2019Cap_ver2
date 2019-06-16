package com.example.a2019cap_ver2;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecommendFragment extends Fragment {

    private MainActivity mainAct;

    public RecommendFragment() {
        // Required empty public constructor
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_recommend, container, false);

        RecyclerView mRecyclerView;
        RecyclerView.LayoutManager mLayoutManager;

        ArrayList<MainActivity.Cafe> arr = mainAct.cafeArr;

        MyCafeRecommdAdapter myAdapter = new MyCafeRecommdAdapter(mainAct, arr);

        mRecyclerView = view.findViewById(R.id.recyclerRecommend);

        mLayoutManager = new LinearLayoutManager(mainAct);
        ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(myAdapter);

        return view;
    }

}
