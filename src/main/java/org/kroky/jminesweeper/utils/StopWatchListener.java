/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 *
 * @author Peter Krokavec
 */
public class StopWatchListener implements ActionListener {

    private int time;
    private final JComponent timeCounter;

    public StopWatchListener(JComponent timeCounter) {
        this.timeCounter = timeCounter;
        setText("00:00");
        this.time = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        time++;
        String text = String.format("%02d:%02d", time / 60, time % 60);
        setText(text);
    }

    private void setText(String text) {
        if (timeCounter instanceof JLabel) {
            ((JLabel) timeCounter).setText(text);
        } else if (timeCounter instanceof JButton) {
            ((JButton) timeCounter).setText(text);
        }
    }

    void reset() {
        setText("00:00");
        time = 0;
    }

}
