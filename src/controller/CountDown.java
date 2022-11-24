package controller;

import model.ChessColor;
import view.ChessGameFrame;


import javax.swing.*;

public class CountDown extends Thread {
    private JLabel countLable = ChessGameFrame.getCount();
    private GameController gameController;

    private static final long time = 20;

    public static long midTime = time;
    public static ChessColor color;

    private final Object lock = new Object();

    private boolean pause = false;

    public void pauseThread(){
        this.pause = true;
    }

    public void resumeThread(){
        this.pause = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void onPause(){
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

//    @Override
//    public void run() {
//        super.run();
//        while (true){
//            if (pause){
//                onPause();
//            }
//            this.resumeThread();
//            midTime--;
//            countLable.setText(String.valueOf(midTime));
//            try {
//                Thread.sleep(1000);
//                if ( midTime == 0) {
//
//                }
//            }
//        }
//    }




}
