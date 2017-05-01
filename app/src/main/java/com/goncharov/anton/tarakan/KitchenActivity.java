package com.goncharov.anton.tarakan;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class KitchenActivity extends Activity {

    private SoundPool mSoundPool;
    private String button1number;
    private String button2number;
    private int btn1number;
    private int btn2number;
    private int soundButton1;
    private int soundButton2;
    private int soundTarakan;
    private int soundTarakanFound;
    MediaPlayer mMediaPlayer;
    Tarakan tarakan = new Tarakan();
    SoundEngine soundEngine = new SoundEngine();

    private static Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        mContext = this;

        tarakan.setPosition();
        Toast.makeText(this, tarakan.getPosition(), Toast.LENGTH_SHORT).show();

        mMediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0.3f, 0.3f);
        mMediaPlayer.start();

        Button button1 = (Button)findViewById(R.id.button1);
        Button button2 = (Button)findViewById(R.id.button2);

        button1number = (String)button1.getTag();
        button2number = (String)button2.getTag();
        //Toast.makeText(this, button1number, Toast.LENGTH_SHORT).show();
        btn1number = Integer.parseInt(button1number);
        btn2number = Integer.parseInt(button2number);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, tarakan.getPosition(), Toast.LENGTH_SHORT).show();
                soundEngine.playSound(soundButton1);
                Thread tarakanThread = new Thread();
                try {
                    tarakanThread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tarakan.checkPosition(btn1number)) {
                    soundEngine.playTarakanSound(soundTarakanFound);
                } else {
                    soundEngine.playTarakanSound(soundTarakan);
                }
                tarakan.setPosition();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                soundEngine.playSound(soundButton2);
                Thread tarakanThread = new Thread();
                try {
                    tarakanThread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tarakan.checkPosition(btn2number)) {
                    soundEngine.playTarakanSound(soundTarakanFound);
                } else {
                    soundEngine.playTarakanSound(soundTarakan);
                }
                tarakan.setPosition();
            }
        });
    }

    public static Context getContext(){
        return mContext;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // получим идентификаторы
        soundButton1 = soundEngine.loadSound("chicken.ogg");
        soundButton2 = soundEngine.loadSound("cups_breaking.wav");
        soundTarakan = soundEngine.loadSoundTarakan("tarakan_laugh.mp3");
        soundTarakanFound = soundEngine.loadSoundTarakan("applause.mp3");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMP();
    }

    private void releaseMP() {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}

