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
import com.example.onboarding.databinding.ActivityWithViewpagerBinding;

public class ActivityWithViewPager extends AppCompatActivity {
    ActivityWithViewpagerBinding binding;

    // adapter ka instance jo ki initialize hoga tb le k chla jaega context ko
    MyViewPagerAdapter adapter;

    // jo apn ne dotIndicatorLinearLayout liya he in the activity with viewpager usme apn ye Textviews daalenge
    TextView[] dots;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWithViewpagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // passing context to the constructor of adapter
        adapter=new MyViewPagerAdapter(this);
        // viewpager ko link kiya with adapter
        binding.viewPager.setAdapter(adapter);

        // by default show indicator to 0 position
        setDotIndicator(0);
        // change listener lga diya he viewpager pe
        binding.viewPager.addOnPageChangeListener(viewPagerListener);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0)>0)
                {
                    binding.viewPager.setCurrentItem(getItem(-1),true);
                }
            }
        });
        binding.nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getItem(0)<2)
                {
                    binding.viewPager.setCurrentItem(getItem(1),true);
                }
                else
                {
                    Intent intent=new Intent(ActivityWithViewPager.this, GoToMainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
        binding.skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityWithViewPager.this, GoToMainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }   // onCreate ends here

    public void setDotIndicator(int position)
    {
        dots=new TextView[3];
        binding.dotIndicatorLinearLayout.removeAllViews();

        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                dots[i].setText(Html.fromHtml("&#9786",Html.FROM_HTML_MODE_LEGACY));
                dots[i].setTextSize(35);
                dots[i].setTextColor(Color.parseColor("#808080"));
                binding.dotIndicatorLinearLayout.addView(dots[i]);
            }
        }
        dots[position].setTextColor(Color.parseColor("#8692f7"));
    }

    private int getItem(int i)
    {
        return binding.viewPager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener viewPagerListener=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position)
        {
            // calling setDotIndicator and passing position jaha pe active dot ko set krna he
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
        public void onPageScrollStateChanged(int state) {}

    };
}