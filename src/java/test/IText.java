/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

/**
 *
 * @author Andriy
 */

class MyThread implements Runnable {
    
    public static int threadId = 1;
    private String threadName = "Thread - ";
    
    private boolean running = true;
    private int runTimes = 0;
    private int counter = 0;
    
    public MyThread(int runTimes) {
        threadName += threadId;
        threadId++;
        this.runTimes = runTimes;
    }
    
    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            if (counter >= runTimes) {
                stop();
            }
            
            System.out.println(threadName);
            counter++;
        }
    }
    
    
}

public class IText {
    
    
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10; i++) {
                new Thread(new MyThread(4)).start();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
