package com.example.com.customviewlearn;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.com.customviewlearn.BarChart.BarChart;
import com.example.com.customviewlearn.BarChart.BarChartActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.ll_1)
    LinearLayout ll_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.ll_1})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_1:
                startIntent(BarChartActivity.class);
                break;
        }
    }

    private<T> void startIntent(Class<T> target){
        startActivity(new Intent(MainActivity.this,target));
    }
}
