/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itext;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Andriy
 */
public class ThreadController {
    
    private Object[] data;
    
    private List<ThreadUnit> threadUnits;
    
    private int threadNumber;
    private int threadsDone = 0;
    private int threadIds = 1;
    
    private int currentInit = 0;
    private int amount = 0;
    
    private UnitInstructions unitInstructions;
    
    public ThreadController(Object[] data, int threadNumber) {
        if (threadNumber > data.length) {
            threadNumber = data.length;
        }
        
        this.data = data;
        this.threadNumber = threadNumber;
        
        init();
    }
    
    private void init() {
        amount = data.length / threadNumber;
        threadUnits = new ArrayList<>();
    }

    public UnitInstructions getUnitInstructions() {
        return unitInstructions;
    }

    public void setUnitInstructions(UnitInstructions unitInstructions) {
        this.unitInstructions = unitInstructions;
    }

    public Object[] getData() {
        return data;
    }

    public void setData(Object[] data) {
        this.data = data;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
    
    public void run() {
        for (int i = 0; i < threadNumber; i++) {
            new Thread(createUnit()).start();
        }
    }
    
    public ThreadUnit createUnit() {
        ThreadUnit threadUnit = new ThreadUnit(threadIds, currentInit, amount, this);
        threadIds++;
        currentInit += amount;
        threadUnits.add(threadUnit);
        
        return threadUnit;
    }
    
    private void allDone() {
        System.out.println("Done!");
        unitInstructions.allDone();
    }
    
    public synchronized void threadUnitIsDone(ThreadUnit threadUnit) {
        threadsDone++;
        System.out.println("Threads done: " + threadsDone);
        if (threadsDone == threadNumber) {
            allDone();
        }
    }
    
}
