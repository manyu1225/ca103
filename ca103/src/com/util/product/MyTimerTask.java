package com.util.product;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    private String name;
    public MyTimerTask(String inputName) {
        name = inputName;
    }

    @Override
    public void run() {
        // 打印当前name的内容
        System.out.println("Current exec name is " + name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}