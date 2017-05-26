package com.goncharov.anton.tarakan;

import android.widget.Toast;

class Tarakan {
    private int secretPlaces = 3;
    private int[] positions = new int[secretPlaces];


    //Задаём набор позиций таракана равный secretPlaces - 1
    public void setPosition() {
        int a = 1;
        int b = 15;
        for (int i = 0; i < secretPlaces; i++) {
            if (i + 1 < secretPlaces && positions[i] == positions[i+1]) {
                positions[i] = (int) (a + (Math.random() * b));
                Toast.makeText(KitchenActivity.getContext(), String.valueOf(positions[i]), Toast.LENGTH_SHORT).show();
            }
        }
    }

    //Проверяем совпадение позиции таракана с нажатой кнопкой на экране
    public boolean checkPosition(int tag) {
        for (int i = 0; i < secretPlaces; i++) {
            if (positions[i] == tag) {
                return true;
            }
        }
        return false;
    }
}
