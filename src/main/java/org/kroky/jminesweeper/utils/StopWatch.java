/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper.utils;

import javax.swing.Timer;

/**
 *
 * @author Krokavec Peter
 */
public class StopWatch extends Timer {

    private StopWatchListener listener;

    public StopWatch(int delay, StopWatchListener listener) {
        super(delay, listener);
        this.listener = listener;
    }

    @Override
    public void start() {
        listener.reset();
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

}
