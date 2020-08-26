package com.example.diettabletest;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RecyclerViewAdapter.DataTransferInterface {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;


    private ViewPager viewPager;
    private DrawerLayout drawer;
    public String title;
    private RecyclerViewAdapter mAdap;

    private TabLayout tabLayout;
    private String[] pageTitle = {"밥류", "죽류", "면류"};
    private Toolbar toolbar;

    Button btn_bucket, btn_order;
    ArrayList<RecyclerViewModel> getList = new ArrayList<>();
    ArrayList<RecyclerViewModel> resultList = new ArrayList<>();


    ArrayList<RecyclerViewModel> realList = new ArrayList<>();
    ArrayList<RecyclerViewModel> realrealList = new ArrayList<>();





    public FoodActivity() {
    }


    //어댑터에서 보낸 리스트 받음
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            resultList = (ArrayList<RecyclerViewModel>) intent.getSerializableExtra("result");

            realList.addAll(resultList);

            //resultList.addAll((ArrayList<RecyclerViewModel>) intent.getSerializableExtra("result"));

            System.out.println("받았어!!!" + resultList.size() + "    " + realList.size());
            //Toast.makeText(FoodActivity.this, resultList).size( , Toast.LENGTH_SHORT).show();

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);



        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.view_pager);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
        }


        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        assert navigationView != null;
        navigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        //change ViewPager page when tab selected
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        if (savedInstanceState == null) {
            navigationView.getMenu().performIdentifierAction(R.id.nav_camera, 0);
        }



        ////////////@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        recyclerView = (RecyclerView) findViewById(R.id.clist);
       // recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);





       // final Intent intent = new Intent(getApplicationContext(), NextActivity.class);

        btn_bucket= (Button)findViewById(R.id.btn_bucket);
//        btn_bucket.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                //어댑터에서 보낸 리스트 받음
//                 BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
//                    @Override
//                    public void onReceive(Context context, Intent intent) {
//                        resultList = (ArrayList<RecyclerViewModel>) intent.getSerializableExtra("result");
//
//                        realList.addAll(resultList);
//
//                        //resultList.addAll((ArrayList<RecyclerViewModel>) intent.getSerializableExtra("result"));
//
//                        System.out.println("받았어!!!" + resultList.size() + "    " + realList.size());
//                        //Toast.makeText(FoodActivity.this, resultList).size( , Toast.LENGTH_SHORT).show();
//
//                    }
//                };



                LocalBroadcastManager.getInstance(FoodActivity.this).registerReceiver(mMessageReceiver, new IntentFilter("custom-message"));
               // mAdap = new RecyclerViewAdapter(FoodActivity.this);
                //mAdap.buttonClick();
               // realrealList.addAll(mAdap.getOrderedFList());






                System.out.println(("장바구니에 담았습니다 / resultList 크기는 : " + resultList.size()));
                System.out.println(("장바구니에 담았습니다 / resultList 크기는 : " + realList.size()));
                System.out.println(("장바구니에 담았습니다 / resultList 크기는 : " + realrealList.size()));
    //        }
      //  });


        //최종 주문 발주 넣으면 Next Activity 로 데이터 넘어감
     //   btn_order = (Button)findViewById(R.id.btn_order);
//        btn_order.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //버튼의 이벤트가 감지되었을 때 호출되는 메소드
//                //버튼일 클릭되었을 때 하고자하는 작업을 이곳에서 한다.
//                //mAdap.choiceFoods();
//                //mAdap.getOrderedFList();
//
//                mAdap = new RecyclerViewAdapter(FoodActivity.this);
//                recyclerView.setAdapter(mAdap);
//
//                System.out.println(("FList : ??? " + resultList.size()));
//                intent.putExtra("orderedFList", (Serializable) resultList);        //여기서 못 받아와서 null 값 보내게 됨.
//
//
//
//
//                System.out.println("보낼 갯수 " + mAdap.getOrderedFList().size());
//
//
//
//            }
//        });







    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        FragmentManager fm = getSupportFragmentManager();
        switch (item.getItemId()) {
            case R.id.nav_camera:
                title = "Sample App";
                viewPager.setCurrentItem(0);
                break;

            case R.id.nav_gallery:
                viewPager.setCurrentItem(1);
                break;

            case R.id.nav_slideshow:
                viewPager.setCurrentItem(2);
                break;

            case R.id.nav_manage:
                Intent intent = new Intent(FoodActivity.this, NextActivity.class);
                startActivity(intent);
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        item.setChecked(true);
        getSupportActionBar().setTitle(title);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

}
