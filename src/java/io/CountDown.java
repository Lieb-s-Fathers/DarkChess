package io;

import controller.ClickController;
import controller.WriteController;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;

import static view.StartMenuFrame.mainFrame;

public class CountDown extends Thread {
    private static final int roundTime = 100000;
    private static int time = roundTime;

    private Chessboard chessboard;
    private ClickController clickController;
    private WriteController defaultWriteController;
    private final Object lock = new Object();

    private JLabel countLabel = ChessGameFrame.getCount();
    private JLabel messageLabel = ChessGameFrame.getMessageLabel();

    private boolean pause = false;


    public CountDown(Chessboard chessboard) {
        this.chessboard = chessboard;
        clickController = new ClickController(chessboard);
        defaultWriteController = new WriteController(chessboard.getGameData());
    }



    public boolean getPause() {
        return pause;
    }

    public int getTime() {
        return time;
    }

    public void minusTime() {
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
    public void run() {
        super.run();
        while (true) {
            if (getPause()) {
                onPause();
            }
            try {
                countLabel.setText(String.valueOf(getTime()));
            } catch (RuntimeException ignored){};

            resumeThread();
            minusTime();
            try {
                Thread.sleep(1000);
                if (getTime() == 0) {
                    synchronized (Chessboard.class){
                        clickController.swapPlayer();
                        chessboard.addChessBoardData();
                        defaultWriteController.save();
                        System.out.println(getPause());
                        messageLabel.setText("Time Out! Change Player");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }

    public void restart() {
        time = roundTime;
    }

    public void close() {
        restart();
        pauseThread();
    }
}