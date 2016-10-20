package com.app.fan.idcardscanproject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.fan.idcardscanproject.util.BitMapUtils;
import com.app.fan.idcardscanproject.util.TecentHttpUtil;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button btn_take_photo;
    private TextView tv_info;
    private int IMAGE_PICKER = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_take_photo = (Button) findViewById(R.id.btn_take_photo);
        tv_info = (TextView) findViewById(R.id.tv_info);
        btn_take_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_take_photo:
                tv_info.setText("");
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, IMAGE_PICKER);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == IMAGE_PICKER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                if (images != null && images.size() == 1) {
                    Toast.makeText(MainActivity.this, images.get(0).path + "=====" + images.get(0).name, Toast.LENGTH_LONG).show();
                    String path = BitMapUtils.zoomBitMapFromPath(this, images.get(0).path);
                    Bitmap bmp = BitMapUtils.getBitmapFromSDCard(path);
                    TecentHttpUtil.uploadIdCard(BitMapUtils.bitmapToBase64(bmp), "0", new TecentHttpUtil.SimpleCallBack() {
                        @Override
                        public void Succ(String result) {
                            if (result.length() > 500) {
                                tv_info.setText(result.substring(0, 500));
                            } else {
                                tv_info.setText(result);
                            }


                        }

                        @Override
                        public void error() {

                        }
                    });

                }
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
