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
public interface GameStateChangedListener {

    void gameStateChange(GameStateChangedEvent evt);

    void win(GameStateChangedEvent evt);

    void lose(GameStateChangedEvent evt);
}
