package io;

import controller.ClickController;
import controller.GameController;
import model.ChessColor;
import view.Chessboard;

import javax.swing.*;

import static view.StartMenuFrame.mainFrame;

public class CountDown extends Thread {
    private static final int roundTime = 10;

    private static int time = roundTime;
    private static ChessColor color;

    private Chessboard chessboard;
    private GameController gameController;
    private ClickController clickController;


    public CountDown(Chessboard chessboard){
        this.chessboard = chessboard;
        gameController = new GameController(chessboard);
        clickController = new ClickController(chessboard);
    }

    private final Object lock = new Object();
    private JLabel countLabel = mainFrame.getCount();
    private JLabel messageLabel = mainFrame.getMessageLabel();

    private boolean pause = false;

    public boolean getPause(){
        return pause;
    }

    public int getTime(){
        return time;
    }

    public void minusTime(){
        time--;
    }

    public void pauseThread() {
        this.pause = true;
    }

    public void resumeThread() {
        this.pause = false;
        synchronized (lock) {
            lock.notify();
        }
    }

    public void onPause() {
        synchronized (lock) {
            try {
                lock.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run(){
        super.run();
        while (true){
            if (getPause()){
               onPause();
            }
            countLabel.setText(String.valueOf(getTime()));
            resumeThread();
            minusTime();
            try {
                Thread.sleep(1000);
                if ( getTime() == 0) {
                    //todo: 倒计时结束
                    clickController.swapPlayer();
                    messageLabel.setText("Time Out! Change Player");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void restart(){
        time = roundTime;
    }

}