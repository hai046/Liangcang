package com.liangcang.util;

public enum TaoboCredit {
    ONE_LEVEL(0), TWO_LEVEL(0), THREE_LEVEL(0), FOUR_LEVEL(0);
    private int num;

    TaoboCredit(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
