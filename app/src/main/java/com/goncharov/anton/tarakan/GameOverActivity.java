package com.goncharov.anton.tarakan;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button againButton = (Button)findViewById(R.id.buttonAgainLose);
        againButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {

                //Создаём Intent для перехода на другую активность
                Intent intent = new Intent(GameOverActivity.this, KitchenActivity.class);

                //Устанавливаем флаг для очистки активности из истории
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        Button gameOverButton = (Button)findViewById(R.id.buttonGameOver);
        gameOverButton.setOnClickListener(new View.OnClickListener(){
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //Закрывает приложение с очисткой истории
        moveTaskToBack(true);
        System.runFinalization();
        System.exit(0);
    }


}
