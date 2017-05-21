package com.goncharov.anton.tarakan;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Запускаем фоновую музыку
        createMP();

        //Создаём кнопку и присваиваем ей слушатель
        Button mainButton = (Button)findViewById(R.id.button_main);
        mainButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Создаём Intent для перехода на другую активность
                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                startActivity(intent);
            }
        });
    }

    public void createMP() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.start_theme);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0.3f, 0.3f);
        mMediaPlayer.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMediaPlayer.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    private void releaseMP() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.reset();
                mMediaPlayer.prepare();
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
