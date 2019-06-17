package com.example.a2019cap_ver2;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {
    MainActivity mainAct;

    MainActivity.User me;


    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mainAct = (MainActivity)getActivity();

        me = mainAct.me;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mainAct = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);

        LinearLayout v = view.findViewById(R.id.layoutMyInfo);

        //접속된 세션이 있다면 로그인 화면 구성해서 보여주기
        TextView txtNickName = new TextView(mainAct);
        TextView txtNicknameLabel = new TextView(mainAct);
        ImageView imgProfileImgSrc = new ImageView(mainAct);

        if(me != null){
            //로그인 정보 받아오기
            String strNickname = me.nickname;;
            String strAuthor = me.authority;
            String strProfile = me.profilePath;

            //Nickname레이블 설정
            txtNicknameLabel.setTextSize(20);
            txtNicknameLabel.setText("Nickname");

            //닉네임 및 사진정보 설정
            txtNickName.setText(strNickname);
            Glide.with(this).load(strProfile).apply(bitmapTransform(new CircleCrop())).into(imgProfileImgSrc);

            //레이블과 닉네임을 붙일 레이아웃을 하나 생성
            LinearLayout layout = new LinearLayout(mainAct);
            layout.setOrientation(LinearLayout.VERTICAL);

            //레이블과 닉네임을 붙일 레이아웃에 대한 parameter설정
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParam.gravity = Gravity.CENTER;
            layoutParam.leftMargin = 40;

            //닉네임에 대해 margin설정
            LinearLayout.LayoutParams txtNicknameParam = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            txtNicknameParam.topMargin = 10;

            //레이아웃에 자식 view(레이블과 닉네임) 추가
            layout.addView(txtNicknameLabel);
            layout.addView(txtNickName, txtNicknameParam);

            //헤더View 설정 및 자식View추가
            v.setOrientation(LinearLayout.HORIZONTAL);
            v.addView(imgProfileImgSrc, new LinearLayout.LayoutParams(220, 220));
            v.addView(layout, layoutParam);
        }


        return view;
    }

}
