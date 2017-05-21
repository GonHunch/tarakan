package com.goncharov.anton.tarakan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;


public class KitchenActivity extends Activity {

    private int soundTarakan;
    private int soundTarakanFound;
    private int soundTarakanMissed;
    MediaPlayer mMediaPlayer;
    Tarakan tarakan = new Tarakan();
    SoundEngine soundEngine = new SoundEngine();

    TableLayout tableLayout;
    private static Context mContext;
    private int clickCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mContext = this;

        tarakan.setPosition();

        //Запускаем фоновую музыку
        createMP();

        tableLayout = (TableLayout) findViewById(R.id.kitchenAct);

        String[][] sounds = {{"air_slash.mp3", "bottles_kicking.mp3", "stone_throwing.mp3"},
                {"glass_crashing.mp3", "squeze.mp3", "cups_breaking.mp3"},
                {"door_screeching.mp3", "water_boiling.mp3", "opening_bottle.mp3"},
                {"toy_screeching.mp3","plates_breaking.mp3", "hammer_hits.mp3"},
                {"man_sneezing.mp3", "cat_screaming.mp3", "woman_screaming.mp3"}};


        TableRow[] tableRows = new TableRow[5];

        //Обходим все кнопки и создаём для каждой свой обработчик
        for (int i = 0; i < 5; i++) {
            tableRows[i] = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 3; j++) {
                Button btn = (Button)tableRows[i].getChildAt(j);
                final int soundButton = soundEngine.loadSound(sounds[i][j]);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;
                        if (clickCount == 9) {
                            soundEngine.playTarakanSound(soundTarakanMissed);
                            clickCount = 0;
                            intent = new Intent(KitchenActivity.this, GameOverActivity.class);
                            startActivity(intent);
                            return;
                        }
                        //Проигрываем разные звуки клеток с помощью массива sounds[i]
                        soundEngine.playSound(soundButton);
                        Thread tarakanThread = new Thread();
                        try {
                            tarakanThread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (tarakan.checkPosition(Integer.parseInt((String) v.getTag()))) {
                            soundEngine.playTarakanSound(soundTarakanFound);
                            v.setBackgroundResource(R.mipmap.tarakan_icon);
                            clickCount = 0;
                            intent = new Intent(KitchenActivity.this, WinActivity.class);
                            startActivity(intent);
                            return;
                        } else {
                            soundEngine.playTarakanSound(soundTarakan);
                        }
                        tarakan.setPosition();
                        clickCount++;
                    }
                });
            }
        }
    }
    public void createMP() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0.3f, 0.3f);
        mMediaPlayer.start();
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMediaPlayer.start();

        //Получим базовые звуки таракана
        soundTarakan = soundEngine.loadSoundTarakan("tarakan_laugh.mp3");
        soundTarakanFound = soundEngine.loadSoundTarakan("applause.mp3");
        soundTarakanMissed = soundEngine.loadSoundTarakan("game_over.mp3");
    }

    @Override
    protected void onPause() {
        super.onPause();
        soundEngine.mSoundPool.stop(soundEngine.getCurrentStreamID());
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMediaPlayer.pause();
        soundEngine.mSoundPool.stop(soundEngine.getCurrentStreamID());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
        soundEngine.mSoundPool.release();
        soundEngine.mSoundPool = null;
        clickCount = 0;
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

