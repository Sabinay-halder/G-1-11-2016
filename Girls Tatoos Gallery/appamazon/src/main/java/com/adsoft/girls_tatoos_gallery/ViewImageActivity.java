package com.adsoft.girls_tatoos_gallery;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by mercury-four on 18/11/15.
 */
public class ViewImageActivity extends Activity {


    private TouchImageView viewImage;
//    private ImageView viewImage;
    private LinearLayout cancleBtn, shareBtn2;


    @Override
    protected void onPause() {
        super.onPause();
        if(Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying()){
            Myapplication.isPlaying=1;
                    }
        Myapplication.tag = 0;
        if (Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying() && Myapplication.tag == 0) {
            Myapplication.mediaPlayer.pause();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_image_layout);

        Bundle intent = getIntent().getExtras();
        final int image = intent.getInt("image");

        viewImage = (TouchImageView) findViewById(R.id.viewImage);
//        viewImage = (ImageView) findViewById(R.id.viewImage);
        cancleBtn = (LinearLayout) findViewById(R.id.crossImage);
        shareBtn2 = (LinearLayout) findViewById(R.id.shareBtn2);
        viewImage.setImageResource(image);

        shareBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shareImage(image);
            }
        });

        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void shareImage(int res) {

        Bitmap bm = BitmapFactory.decodeResource(getResources(), res);
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        File file = new File(extStorageDirectory, Util.ShareImageName);
        try {
            FileOutputStream outStream = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {

        }
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
        share.putExtra(Intent.EXTRA_TEXT, Util.ShareText);
        startActivity(Intent.createChooser(share, "Share Image"));
        if (Myapplication.mediaPlayer != null && Myapplication.mediaPlayer.isPlaying()) {
            Myapplication.mediaPlayer.pause();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Myapplication.mediaPlayer != null && !Myapplication.mediaPlayer.isPlaying() && Myapplication.isPlaying == 1) {
            Myapplication.mediaPlayer.start();
        }
    }
}
