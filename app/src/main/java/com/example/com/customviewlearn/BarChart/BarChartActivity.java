package com.example.com.customviewlearn.BarChart;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.com.customviewlearn.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by WangZH on 2016/11/15.
 */

public class BarChartActivity extends AppCompatActivity implements View.OnClickListener{

    @InjectView(R.id.chart)
    BarChart barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.barchart);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.btn_click,R.id.btn_scroll,R.id.btn_notnull})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_click:
                 barchart.setCanClickable(true);
                break;
            case R.id.btn_scroll:
                barchart.setCanScrollable(true);
                break;
            case R.id.btn_notnull:
                barchart.setCanClickable(false);
                barchart.setCanScrollable(false);
                break;
        }
    }
}
