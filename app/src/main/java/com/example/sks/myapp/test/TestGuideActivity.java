package com.example.sks.myapp.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.example.sks.myapp.R;
import com.example.sks.myapp.test.shizhefei.guide.GuideHelper;
import com.example.sks.myapp.test.shizhefei.guide.GuideHelper.TipData;
import com.example.sks.myapp.test.shizhefei.guide.DisplayUtils;

public class TestGuideActivity extends AppCompatActivity {

    private View iconView;
    private View citysView;
    private View infoLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_guide);
        iconView = findViewById(R.id.icon);
        citysView = findViewById(R.id.citys);
        infoLayout = findViewById(R.id.infoLayout);

        View button = findViewById(R.id.button1);

        button.setOnClickListener(onClickListener);
        iconView.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button1:


                    GuideHelper guideHelper = new GuideHelper(TestGuideActivity.this);

                    TipData tipData1 = new TipData(R.drawable.tip1, Gravity.RIGHT | Gravity.BOTTOM, iconView);
                    tipData1.setLocation(0, -DisplayUtils.dipToPix(v.getContext(), 50));
                    guideHelper.addPage(tipData1);
                    //
                    TipData tipData2 = new TipData(R.drawable.tip2, citysView);
                    guideHelper.addPage(tipData2);
                    //

                    TipData tipData3 = new TipData(R.drawable.tip3, infoLayout);
                    guideHelper.addPage(tipData3);

                    guideHelper.addPage(tipData1, tipData2, tipData3);
                    guideHelper.setAutoGuide(false);

                    guideHelper.show();
                    break;

                case R.id.icon:
                    Toast.makeText(TestGuideActivity.this, "这里是头像", Toast.LENGTH_SHORT).show();
                    break;

            }

        }
    };
}
