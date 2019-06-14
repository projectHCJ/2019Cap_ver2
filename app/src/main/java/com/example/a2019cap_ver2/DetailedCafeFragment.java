package com.example.a2019cap_ver2;


import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


public class DetailedCafeFragment extends Fragment {

    private MainActivity mainAct;
    private int cafeIndex;

    MainActivity.Cafe currentCafe;

    public DetailedCafeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainAct = (MainActivity)getActivity();

        cafeIndex = getArguments().getInt("CafeIndex");
        currentCafe = mainAct.cafeArr.get(cafeIndex);
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainAct = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detailed_cafe, container, false);

        //카페 이미지 뷰페이저 설정
        ViewPager viewPager = view.findViewById(R.id.pagerCafeImg) ;
        MyCafeImgPagerAdapter pagerAdapter = new MyCafeImgPagerAdapter(getContext(), currentCafe.imgSrcs) ;
        viewPager.setAdapter(pagerAdapter) ;

        //카페 이름 출력
        ((TextView)view.findViewById(R.id.tvCafeName)).setText(currentCafe.cafeName);

        //카페소개 출력
        ((TextView)view.findViewById(R.id.tvCafeExcplanation)).setText(currentCafe.descript);

        //위치및 전화번호 보기 버튼 클릭 이벤트
        ((Button)view.findViewById(R.id.btnLocation)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //카페 정보 인텐트로 지도화면으로 넘겨주기

                //인텐트 실행
            }
        });


        //카페 메뉴를 위한 뷰사이클러
        MyCafeMenuAdapter menuAdapter = new MyCafeMenuAdapter(mainAct, currentCafe.menu, currentCafe.price);

        RecyclerView menuRecycler = view.findViewById(R.id.rcycleCafeMenu);

        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(mainAct);
        ((LinearLayoutManager)mLayoutManager).setOrientation(LinearLayoutManager.VERTICAL);

        menuRecycler.setLayoutManager(mLayoutManager);
        menuRecycler.setHasFixedSize(true);
        menuRecycler.setAdapter(menuAdapter);


        //편의시설 출력
        LinearLayout layout = view.findViewById(R.id.linearConvenience);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(120, 120);

        for(int i =0 ; i < currentCafe.convenience.length; ++i){

            ImageView tempView = new ImageView(mainAct);
            tempView.setImageResource(currentCafe.convenience[i]);
            tempView.setScaleType(ImageView.ScaleType.FIT_XY);

            param.setMargins(15, 15, 15, 15);
            layout.addView(tempView, param);
        }

        return view;
    }

}
