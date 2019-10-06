package com.example.pocapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pocapp.viewpager.MyPagerAdapter;
import com.example.pocapp.viewpager.ScreenSlide;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FirstActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyPagerAdapter pagerAdapter;
    private List<ScreenSlide> slideList = new ArrayList<>();
    private TabLayout tabLayout;
    private Button buttonNext, buttonCont;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        tabLayout = findViewById(R.id.tabLayout);
        buttonNext = findViewById(R.id.button_next);
        buttonCont = findViewById(R.id.button_continue);

        if (restoreData()){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        slideList.add(new ScreenSlide("1", "First page"));
        slideList.add(new ScreenSlide("2", "Second page"));
        slideList.add(new ScreenSlide("3", "Third page"));

        viewPager = findViewById(R.id.viewPager);
        pagerAdapter = new MyPagerAdapter(this, slideList);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = viewPager.getCurrentItem();
                if (position < slideList.size()){
                    position ++;
                    viewPager.setCurrentItem(position);
                }
                if (position == slideList.size() - 1){
                    loadContinueButton();
                }
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == slideList.size()-1){
                    loadContinueButton();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        buttonCont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
                actionSave();
                finish();
            }
        });
    }

    private boolean restoreData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        return pref.getBoolean("wasOpened", false);
    }

    private void actionSave() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("wasOpened", true);
        editor.apply();
    }

    private void loadContinueButton() {
        buttonNext.setVisibility(View.INVISIBLE);
        buttonCont.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.INVISIBLE);
    }
}
