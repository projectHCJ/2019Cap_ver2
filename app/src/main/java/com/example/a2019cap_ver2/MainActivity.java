package com.example.a2019cap_ver2;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    class User{

        User(String userCode, String nickname, String authority){
            this.userCode = userCode;
            this.nickname = nickname;
            this.authority = authority;
        }
        private String userCode;
        private String nickname;
        private String authority;
    }

    class Cafe{
        Cafe(String cafeName, String tellNum, String location, String Theme){
            this.cafeName = cafeName;
            this.tellNum = tellNum;
            this.location = location;
            this.Theme = Theme;
        }
        public String[] imgSrcs = {"@drawable/cafe1", "@drawable/cafe2", "@drawable/cafe3"};
        public String[] menu = {"아메리카노", "카페라떼", "카푸치노"};
        public String[] price = {"1800", "2500", "2600"};
        public String descript = "저희 카페의 커피는 아주아주 맛있습니다\n많은 이용 부탁드려요!";

        public int[] convenience = {R.drawable.charging, R.drawable.parking};

        public String cafeName;
        public String tellNum;
        public String location;
        public String Theme;
    }

    User me;
    ArrayList<Cafe> cafeArr;

    private void createTempData(){
        me = new User("110013", "홍충재", "유저");

        cafeArr = new ArrayList<>();
        cafeArr.add(new Cafe("카페리움", "02-784-0034", "서울시 종로구 행촌동", "수면"));
        cafeArr.add( new Cafe("MJ Cafe", "02-345-6785", "서울시 서대문구 북가좌동", "플라워"));
        cafeArr.add( new Cafe("고양이와 함께 춤을", "02-389-2355", "서울시 은평구 진관동", "동물"));
    }

    FragmentChangeManager changeManager;
    private SessionCallback sessionCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //임시 데이터 생성
        createTempData();

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

        if(intent.getLongExtra("ID", -1) == -1){
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
            TextView txtNicknameLabel = new TextView(this);
            ImageView imgProfileImgSrc = new ImageView(this);

            //로그인 정보 받아오기
            String strID = String.valueOf(intent.getLongExtra("ID", -1));
            String strNickname = intent.getStringExtra("Nickname");
            String strProfile = intent.getStringExtra("Profile");

            Log.d("D","User_ID = " + strID);

            //Nickname레이블 설정
            txtNicknameLabel.setTextSize(20);
            txtNicknameLabel.setText("Nickname");

            //닉네임 및 사진정보 설정
            txtNickName.setText(strNickname);
            Glide.with(this).load(strProfile).apply(bitmapTransform(new CircleCrop())).into(imgProfileImgSrc);


            //레이블과 닉네임을 붙일 레이아웃을 하나 생성
            LinearLayout layout = new LinearLayout(this);
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

        //FrameLayout에 Fragment 넣기
        changeManager = new FragmentChangeManager(getSupportFragmentManager());
        changeManager.putFragment("Main", new MainFragment());
        changeManager.putFragment("Recommend", new RecommendFragment());
        changeManager.putFragment("MyPage", new MyPageFragment());
        changeManager.putFragment("DetailedCafe", new DetailedCafeFragment());
        changeManager.putFragment("RegistCafe", new RegistCafeFragment());
        changeManager.putFragment("RegistOwner", new RegistOwnerFragment());
        changeManager.putFragment("Bookmark", new BookmarkFragment());

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

                    intent.putExtra("ID", result.getId());
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
