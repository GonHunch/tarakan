package com.goncharov.anton.tarakan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //код игры

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //создаём кнопку и присваиваем ей слушатель
        Button mainButton = (Button)findViewById(R.id.button_main);
        mainButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                //создаём Intent для перехода на другую активность
                Intent intent = new Intent(MainActivity.this, DescriptionActivity.class);
                startActivity(intent);
            }
        });
    }
}
