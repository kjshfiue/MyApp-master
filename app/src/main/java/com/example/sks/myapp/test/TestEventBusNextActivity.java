package com.example.sks.myapp.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.example.sks.myapp.BaseActivity;
import com.example.sks.myapp.R;
import com.example.sks.myapp.test.event.ItemEvent;
import com.yalantis.ucrop.UCrop;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileNotFoundException;

public class TestEventBusNextActivity extends BaseActivity {

    private File picFile;
    private ImageView m_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event_bus_next);
        m_img = (ImageView) findViewById(R.id.m_img);

        findViewById(R.id.m_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ItemEvent("这是订阅得来的数据"));
            }
        });
        findViewById(R.id.m_btn_img).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                } else {
                    intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                startActivityForResult(intent, 1);


            }
        });

        findViewById(R.id.m_btn_img2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //如果改的是头像
                String dirPath = Environment.getExternalStorageDirectory().toString();
                picFile = new File(dirPath, "icon.jpg");

                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(picFile));
                startActivityForResult(intent1, 2);


            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            if(resultUri == null) return;
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(resultUri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
//            m_img.setImageBitmap(bitmap);
            m_img.setImageURI(resultUri);

//            File file = new File(resultUri.getPath());
//            if (file.exists()) {
//                Bitmap bitmap = Util.getBitmapFromFile(file, 800, 800);
//
//                m_img.setImageBitmap(bitmap);
//            }



        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }
        if (resultCode == RESULT_OK && requestCode == 1) {
            if (data == null) return;
            Uri uri = data.getData();

            Uri destinationUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString(), "sdfsdf.jpg"));

            UCrop.of(uri, destinationUri)
                    .withAspectRatio(1, 1)
                    .withMaxResultSize(800, 800)
                    .start(TestEventBusNextActivity.this);

        }
        if (resultCode == RESULT_OK && requestCode == 2) {

            if (picFile != null && picFile.exists()) {

                Uri uri = Uri.fromFile(picFile);
                Uri destinationUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString(),"sdfsdf.jpg"));

                UCrop.of(uri, destinationUri)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(800, 800)
                        .start(TestEventBusNextActivity.this);
            } else {
                toast("照片不存在");
            }


        }

    }
}
