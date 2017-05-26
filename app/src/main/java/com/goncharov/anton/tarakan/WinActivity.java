package com.goncharov.anton.tarakan;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Запускаем фоновую музыку фанфаров
        createMP();

        Button againButton = (Button)findViewById(R.id.buttonAgainWin);
        againButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                //Создаём Intent для перехода на другую активность
                Intent intent = new Intent(WinActivity.this, KitchenActivity.class);

                //Устанавливаем флаг для очистки активности из истории
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Button exitButton = (Button)findViewById(R.id.buttonExitWin);
        exitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                //Убиваем активность
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                //Извлекаем имя компонента
                ComponentName cn = intent.getComponent();

                //Запускаем рестарт на MainActivity
                Intent mainIntent = IntentCompat.makeRestartActivityTask(cn);
                startActivity(mainIntent);
            }
        });
    }

    public void createMP() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.final_fanfary);
        mMediaPlayer.setLooping(false);
        mMediaPlayer.setVolume(0.6f, 0.6f);
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

        //Закрывает приложение с очисткой истории
        moveTaskToBack(true);
        System.runFinalization();
        System.exit(0);
    }

    //Освобождаем ресурсы
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
