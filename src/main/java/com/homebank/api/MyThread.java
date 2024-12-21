package com.homebank.api;

public class MyThread extends Thread {

    @Override
    public void run() {
        System.out.println("MyThread running");
    }

    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start();
    }
}
