package com.example.onboarding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.onboarding.Adapters.MyViewPagerAdapter;
import com.example.onboarding.databinding.ActivityNavigationBinding;

public class NavigationActivity extends AppCompatActivity {
    ActivityNavigationBinding binding;

    MyViewPagerAdapter adapter;
    TextView[] dots;

    ViewPager.OnPageChangeListener viewPagerListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setDotIndicator(position);
            if (position>0)
            {
                binding.backBtn.setVisibility(View.VISIBLE);
            }
            else
            {
                binding.backBtn.setVisibility(View.INVISIBLE);
            }
            if(position==2)
            {
                binding.nextBtn.setText("Finish");
            }
            else
            {
                binding.nextBtn.setText("Next");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter=new MyViewPagerAdapter(this);
        binding.slideViewPager.setAdapter(adapter);

        setDotIndicator(0);
        binding.slideViewPager.addOnPageChangeListener(viewPagerListener);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0)>0)
                {
                    binding.slideViewPager.setCurrentItem(getItem(-1),true);
                }
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0)<2)
                {
                    binding.slideViewPager.setCurrentItem(getItem(1),true);
                }
                else
                {
                    Intent intent=new Intent(NavigationActivity.this,GetStarted.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NavigationActivity.this,GetStarted.class);
                startActivity(intent);
                finish();
            }
        });
    }
    public void setDotIndicator(int position)
    {
        dots=new TextView[3];
        binding.dotIndicator.removeAllViews();

        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].setText(Html.fromHtml("&#8226",Html.FROM_HTML_MODE_LEGACY));
                dots[i].setTextSize(35);
                dots[i].setTextColor(Color.parseColor("#808080"));
                binding.dotIndicator.addView(dots[i]);
            }
        }
        dots[position].setTextColor(Color.parseColor("#8692f7"));
    }
    private int getItem(int i)
    {
        return binding.slideViewPager.getCurrentItem() + i;
    }
}