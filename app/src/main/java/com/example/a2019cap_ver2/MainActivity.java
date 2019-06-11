package com.example.a2019cap_ver2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentChangeManager changeManager;
    private SessionCallback sessionCallback;

    @Override
    protected void onResume() {
        super.onResume();
        /*
        NavigationView navigationView = findViewById(R.id.nav_view);
        LinearLayout v = (LinearLayout)navigationView.getHeaderView(0);

        if(sessionCallback.mNickname == null){
            //접속된 세션이 없다면 로그인 버튼 표시
            com.kakao.usermgmt.LoginButton button = new com.kakao.usermgmt.LoginButton(this);
            v.addView(button);
        }else{
            //접속된 세션이 있다면 로그인 화면 구성해서 보여주기
            TextView txtNickName = new TextView(this);
            TextView txtProfileImgSrc = new TextView(this);

            String strNickname = sessionCallback.mNickname;
            String strProfile = sessionCallback.mProfile;

            txtNickName.setText(strNickname);
            txtProfileImgSrc.setText(strProfile);

            v.setOrientation(LinearLayout.VERTICAL);

            v.addView(txtNickName);
            v.addView(txtProfileImgSrc);
        }
        */
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //해쉬키 확인용
        getHashKey();

        //툴바 인플레이션
        Toolbar toolbar = findViewById(R.id.toolbar);

        //툴바 설정
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //드로어레이아웃 인플레이션
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //드로어 이벤트 리스너 설정
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //네비게이션뷰 인플레이션 및 이벤트 리스너 설정
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent intent = getIntent();
        LinearLayout v = (LinearLayout)navigationView.getHeaderView(0);

        if(intent.getStringExtra("Nickname") == null){
            com.kakao.usermgmt.LoginButton button = new com.kakao.usermgmt.LoginButton(this);
            button.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    //터치할 시에 로그인시도
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        Log.d("D", "Touch");
                        sessionCallback = new SessionCallback();
                        Session.getCurrentSession().addCallback(sessionCallback);
                        Session.getCurrentSession().checkAndImplicitOpen();
                    }

                    return true;
                }
            });
            v.addView(button);
        }else{
            //접속된 세션이 있다면 로그인 화면 구성해서 보여주기
            TextView txtNickName = new TextView(this);
            ImageView imgProfileImgSrc = new ImageView(this);

            String strNickname = intent.getStringExtra("Nickname");
            String strProfile = intent.getStringExtra("Profile");

            txtNickName.setText(strNickname);
            Glide.with(this).load(strProfile).apply(bitmapTransform(new CircleCrop())).into(imgProfileImgSrc);

            LinearLayout linear = new LinearLayout(this);

            v.setOrientation(LinearLayout.HORIZONTAL);

            v.addView(imgProfileImgSrc, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            v.addView(txtNickName, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        }

        //FrameLayout에 Fragment 넣기
        changeManager = new FragmentChangeManager(getSupportFragmentManager());
        changeManager.putFragment("Main", new MainFragment());
        changeManager.putFragment("Recommend", new RecommendFragment());

        //첫 화면은 Main화면
        changeManager.replaceFragment("Main");
    }

    private void getHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.a2019cap_ver2", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("SS", "key_hash=" + Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //메뉴 인플레이션
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // 메뉴 선택
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        //햄버거 메뉴에서 아이템 클릭 시
        int id = item.getItemId();
        item.setChecked(false);

        if (id == R.id.nav_home) {
            changeManager.replaceFragment("Main");
        } else if (id == R.id.nav_bookmark) {

        } else if (id == R.id.nav_mypage) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);

            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }


    public class SessionCallback implements ISessionCallback {

        SessionCallback(){

        }

        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    intent.putExtra("Nickname", result.getNickname());
                    intent.putExtra("Profile", result.getProfileImagePath());
                    Log.d("D", "GoddSession");
                    startActivity(intent);
                    finish();
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
        }

    }


}
