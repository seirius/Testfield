/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itext;

/**
 *
 * @author Andriy
 */
public class ThreadUnit implements Runnable {
    
    private ThreadController threadController;
    
    private int threadId;
    private int init;
    private int amount;
    
    
    public ThreadUnit(int threadId, int init, int amount, ThreadController threadController) {
        this.threadId = threadId;
        this.init = init;
        this.amount = amount;
        this.threadController = threadController;
    }
    
    @Override
    public void run() {
        
        for (int i = init; i < init + amount; i++) {
            if (i < threadController.getData().length) {
                threadController.getUnitInstructions().instructions(threadController.getData()[i]);
            }
        }
        
        threadController.threadUnitIsDone(this);
        
    }

    public ThreadController getThreadController() {
        return threadController;
    }

    public void setThreadController(ThreadController threadController) {
        this.threadController = threadController;
    }

    public int getThreadId() {
        return threadId;
    }

    public void setThreadId(int threadId) {
        this.threadId = threadId;
    }

    public int getInit() {
        return init;
    }

    public void setInit(int init) {
        this.init = init;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    
    
}
