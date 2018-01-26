/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper.utils;

import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author Peter Krokavec
 */
public class StopWatch extends Timer {

    private long lastTick;

    public StopWatch(int delay, JLabel label) {
        super(delay, (ActionEvent e) -> {

            label.setText("");
        });
    }

    @Override
    public void start() {
        lastTick = System.currentTimeMillis();
        super.start(); //To change body of generated methods, choose Tools | Templates.
    }

    public long getLastTick() {
        return lastTick;
    }

}
