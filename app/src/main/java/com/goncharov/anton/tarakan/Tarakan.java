package com.goncharov.anton.tarakan;

class Tarakan {
    private int position;

    //Задаём случайную позицию таракану
    public void setPosition() {
        int a = 1;
        int b = 15;
        position = (int) (a + (Math.random() * b));
    }

    //Получаем текущую позицию таракану
    public String getPosition() {
        return "" + position;
    }

    //Проверяем совпадение позиции таракана с нажатой кнопкой на экране
    public boolean checkPosition(int tag) {
        if (position == tag) {
            return true;
        } else {
            return false;
        }
    }
}
