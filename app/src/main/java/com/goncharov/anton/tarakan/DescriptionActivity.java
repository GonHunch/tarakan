package com.goncharov.anton.tarakan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DescriptionActivity extends AppCompatActivity {

    MediaPlayer sMediaPlayer = MainActivity.getMediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Создаём кнопку и присваиваем ей слушатель
        Button descriptionButton = (Button)findViewById(R.id.button_desc);
        descriptionButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Создаём Intent для перехода на другую активность
                Intent intent = new Intent(DescriptionActivity.this, KitchenActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sMediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sMediaPlayer.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    private void releaseMP() {
        if (sMediaPlayer != null) {
            try {
                sMediaPlayer.reset();
                sMediaPlayer.prepare();
                sMediaPlayer.stop();
                sMediaPlayer.release();
                sMediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
