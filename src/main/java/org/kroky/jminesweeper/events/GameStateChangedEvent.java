/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper.events;

/**
 *
 * @author Peter Krokavec
 */
public class GameStateChangedEvent {

    private final int flagsRemaining;

    public GameStateChangedEvent(int flagsRemaining) {
        this.flagsRemaining = flagsRemaining;
    }

    public int getFlagsRemaining() {
        return flagsRemaining;
    }

}
