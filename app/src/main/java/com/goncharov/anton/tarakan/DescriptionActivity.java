package com.goncharov.anton.tarakan;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class DescriptionActivity extends AppCompatActivity {

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

}
