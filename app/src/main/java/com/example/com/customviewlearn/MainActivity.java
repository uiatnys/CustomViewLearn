package com.example.com.customviewlearn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.com.customviewlearn.BarChart.BarChartActivity;
import com.example.com.customviewlearn.LineChart_Dual_Y_Axis.LineChartDualYAxisActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.ll_1,R.id.ll_2})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_1:
                startIntent(BarChartActivity.class);
                break;
            case R.id.ll_2:
                startIntent(LineChartDualYAxisActivity.class);
                break;
        }
    }

    private<T> void startIntent(Class<T> target){
        startActivity(new Intent(MainActivity.this,target));
    }
}
