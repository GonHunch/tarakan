package com.goncharov.anton.tarakan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_win);

        //Запрещаем переворачивание экрана
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button againButton = (Button)findViewById(R.id.buttonAgainWin);
        againButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Создаём Intent для перехода на другую активность
                Intent intent = new Intent(WinActivity.this, KitchenActivity.class);
                startActivity(intent);
            }
        });

        Button exitButton = (Button)findViewById(R.id.buttonExitWin);
        exitButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //Убиваем активность
                Intent intent = new Intent(WinActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        moveTaskToBack(true);
        //System.runFinalization();
        System.exit(0);
    }
}
