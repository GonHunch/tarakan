package com.goncharov.anton.tarakan;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.widget.Toast;

import java.io.IOException;

public class SoundEngine {

    private int mStreamID;
    private int tarakanStreamID;
    public SoundPool mSoundPool;

    private AssetManager mAssetManager;
    private AssetManager tarakanAssetManager;

    public SoundEngine() {
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            // Для устройств до Android 5
            createOldSoundPool();
        } else {
            // Для новых устройств
            createNewSoundPool();
        }
    }

    public int getCurrentStreamID() {
        return mStreamID;
    }

    public int playSound(int sound) {
        if (sound > 0) {
            mStreamID = mSoundPool.play(sound, 1, 1, 1, 0, 1);
        }
        return mStreamID;
    }

    public int playTarakanSound(int sound) {
        if (sound > 0) {
            tarakanStreamID = mSoundPool.play(sound, 1, 1, 2, 0, 1);
        }
        return tarakanStreamID;
    }

    private Context getContextKitchenActivity(){
        return KitchenActivity.getContext();
    }

    public int loadSound(String fileName) {

        mAssetManager = getContextKitchenActivity().getAssets();
        AssetFileDescriptor afd;

        try {
            afd = mAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContextKitchenActivity(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    public int loadSoundTarakan(String fileName) {
        tarakanAssetManager = getContextKitchenActivity().getAssets();
        AssetFileDescriptor afd;
        try {
            afd = tarakanAssetManager.openFd(fileName);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContextKitchenActivity(), "Не могу загрузить файл " + fileName,
                    Toast.LENGTH_SHORT).show();
            return -1;
        }
        return mSoundPool.load(afd, 1);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void createNewSoundPool() {
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
}
