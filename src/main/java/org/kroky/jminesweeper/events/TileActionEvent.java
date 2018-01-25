/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper.events;

import org.kroky.jminesweeper.Tile;

/**
 *
 * @author Peter Krokavec
 */
public class TileActionEvent {

    private final Tile source;

    public TileActionEvent(Tile source) {
        this.source = source;
    }

    /**
     * @return the source
     */
    public Tile getSource() {
        return source;
    }

}
