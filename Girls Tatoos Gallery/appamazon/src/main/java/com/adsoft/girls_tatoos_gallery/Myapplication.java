package com.adsoft.girls_tatoos_gallery;

import android.app.Application;
import android.media.MediaPlayer;

/**
 * Created by mercury-four on 21/11/15.
 */
public class Myapplication extends Application {

    public static MediaPlayer mediaPlayer;
    public volatile static int tag = 0;
    public static int isPlaying = 0;

    @Override
    public void onCreate() {
        super.onCreate();
      /*  mediaPlayer = new MediaPlayer();
        mediaPlayer.reset();
        mediaPlayer = MediaPlayer.create(this, R.raw.harek_rishna);
        mediaPlayer.start();

        Myapplication.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                isPlaying = 1;
            }
        });*/
    }

}
