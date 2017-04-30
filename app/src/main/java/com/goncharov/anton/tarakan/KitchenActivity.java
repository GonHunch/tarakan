package com.goncharov.anton.tarakan;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

public class KitchenActivity extends Activity {

    private SoundPool mSoundPool;
    private AssetManager mAssetManager;
    private AssetManager tarakanAssetManager;
    private String button1number;
    private int btn1number;
    private int mStreamID;
    private int tarakanStreamID;
    private int soundButton1;
    private int soundTarakan;
    private int soundTarakanFound;
    MediaPlayer mMediaPlayer;
    Tarakan tarakan = new Tarakan();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);


        tarakan.setPosition();
        Toast.makeText(this, tarakan.getPosition(), Toast.LENGTH_SHORT).show();


        mMediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0.3f, 0.3f);
        mMediaPlayer.start();

        final Context context = this;

        Button button1 = (Button)findViewById(R.id.button1);
        button1number = (String)button1.getTag();
        Toast.makeText(this, button1number, Toast.LENGTH_SHORT).show();
        btn1number = Integer.parseInt(button1number);

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(context, tarakan.getPosition(), Toast.LENGTH_SHORT).show();
                switch (v.getId()) {
                    case R.id.button1:
                        playSound(soundButton1);
                        break;
                }
                Thread tarakanThread = new Thread();
                try {
                    tarakanThread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (tarakan.checkPosition(btn1number)) {
                    playTarakanSound(soundTarakanFound);
                } else {
                    playTarakanSound(soundTarakan);
                }
                tarakan.setPosition();
            }
        });
    }

    private int playSound(int sound) {
        if (sound > 0) {
           // mSoundPool.setPriority(mStreamID, 0);
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    private int playTarakanSound(int sound) {
        if (sound > 0) {
            //mSoundPool.setPriority(tarakanStreamID, 1);
            tarakanStreamID = mSoundPool.play(sound, 1, 1, 2, 0, 1);
        }
        return tarakanStreamID;
    }

    private int loadSound(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    private int loadSoundTarakan(String fileName) {
        AssetFileDescriptor afd;
        try {
            afd = tarakanAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }

        mAssetManager = getAssets();
        tarakanAssetManager = getAssets();

        // получим идентификаторы
        soundButton1 = loadSound("chicken.ogg");
        soundTarakan = loadSoundTarakan("tarakan_laugh.mp3");
        soundTarakanFound = loadSoundTarakan("applause.mp3");
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSoundPool.release();
        mSoundPool = null;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void createNewSoundPool() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();
        mSoundPool = new SoundPool.Builder()
                .setMaxStreams(3)
                .setAudioAttributes(attributes)
                .build();
    }

    @SuppressWarnings("deprecation")
    private void createOldSoundPool() {
        mSoundPool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);
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

