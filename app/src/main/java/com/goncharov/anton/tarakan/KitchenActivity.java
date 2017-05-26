package com.goncharov.anton.tarakan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class KitchenActivity extends Activity {

    private int soundTarakan;
    private int soundTarakanFound;
    private int soundTarakanMissed;
    Tarakan tarakan = new Tarakan();
    SoundEngine soundEngine = new SoundEngine();

    TableLayout tableLayout;
    private static Context mContext;
    private int clickCount = 0;
    MediaPlayer mMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Сохраняем Context для класса SoundEngine
        mContext = this;

        //Задаём таракану позицию
        tarakan.setPosition();

        //Запускаем фоновую музыку
        createMP();

        //Разметка идёт с помощью TableLayout (GridLayout, GridView и прочие не подошли)
        //Находим ID TableLayout'а
        tableLayout = (TableLayout) findViewById(R.id.kitchenAct);

        //Создаём двумерный массив звуковых файлов для 15-ти кнопок
        String[][] sounds = {{"air_slash.mp3", "bottles_kicking.mp3", "stone_throwing.mp3"},
                {"glass_crashing.mp3", "squeze.mp3", "cups_breaking.mp3"},
                {"door_screeching.mp3", "water_boiling.mp3", "opening_bottle.mp3"},
                {"toy_screeching.mp3","plates_breaking.mp3", "hammer_hits.mp3"},
                {"man_sneezing.mp3", "cat_screaming.mp3", "woman_screaming.mp3"}};

        //TableLayout работает через TableRow, поэтому создаём массив из 5 TableRow
        TableRow[] tableRows = new TableRow[5];

        //Обходим все кнопки и создаём для каждой свой обработчик
        for (int i = 0; i < 5; i++) {

            //Извлекаем номер строки
            tableRows[i] = (TableRow) tableLayout.getChildAt(i);
            for (int j = 0; j < 3; j++) {

                //Из строки поочерёдно извлекаем кнопки
                Button btn = (Button)tableRows[i].getChildAt(j);

                //Загружаем звуковой файл для кнопки
                final int soundButton = soundEngine.loadSound(sounds[i][j]);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent;

                        final View view = v;

                        //Активируем все кнопки
                        v.setEnabled(true);
                        v.setVisibility(View.VISIBLE);

                        //Если таракан не найден
                        if (clickCount == 9) {

                            //Деактивируем все кнопки
                            v.setEnabled(false);
                            soundEngine.playTarakanSound(soundTarakanMissed);
                            intent = new Intent(KitchenActivity.this, GameOverActivity.class);
                            startActivity(intent);
                            clickCount = 0;
                            return;
                        }

                        //Проигрываем разные звуки клеток с помощью массива sounds[i][j]
                        soundEngine.playSound(soundButton);

                        Thread tarakanThread = new Thread();
                        try {
                            tarakanThread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        Handler handler = new Handler();

                        //Если таракан найден
                        if (tarakan.checkPosition(Integer.parseInt((String) v.getTag()))) {
                            v.setEnabled(false);
                            soundEngine.playTarakanSound(soundTarakanFound);
                            v.setBackgroundResource(R.mipmap.tarakan_icon);
                            //Устанавливаем задержку в 2 секунды
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    view.setBackgroundResource(R.drawable.back);
                                    Intent intent = new Intent(KitchenActivity.this, WinActivity.class);
                                    startActivity(intent);
                                }
                            }, 2000);

                            clickCount = 0;

                            //Принудительно выходим из цикла
                            return;

                        } else {
                            //Если не попали по таракану
                            soundEngine.playTarakanSound(soundTarakan);
                            v.setBackgroundResource(R.mipmap.oops);
                            //Устанавливаем задержку в 2 секунды
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    view.setBackgroundResource(R.drawable.back);
                                }
                            }, 2000);
                        }
                        tarakan.setPosition();
                        clickCount++;
                    }
                });
            }
        }
    }

    //Создаём MediaPlayer и запускаем фоновую музыку
    public void createMP() {
        mMediaPlayer = MediaPlayer.create(this, R.raw.background_music);
        mMediaPlayer.setLooping(true);
        mMediaPlayer.setVolume(0.3f, 0.3f);
        mMediaPlayer.start();
    }

    //Получаем Context класса для класса SoundEngine
    public static Context getContext() {
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

