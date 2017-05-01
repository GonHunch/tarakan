package com.goncharov.anton.tarakan;

class Tarakan {
    private int position;

    public void setPosition() {
        int a = 1;
        int b = 15;
        position = (int) (a + (Math.random() * b));
    }

    public String getPosition() {
        return "" + position;
    }

    public boolean checkPosition(int id) {
        if (position == id) {
            return true;
        } else {
            return false;
        }
    }
}
