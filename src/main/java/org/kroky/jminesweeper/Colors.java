/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Krokavec Peter
 */
class Colors {

    private static final Map<Integer, Color> MINE_COUNT_COLOR = new HashMap<>();

    static {
        MINE_COUNT_COLOR.put(1, new Color(0x0000ff)); //blue
        MINE_COUNT_COLOR.put(2, new Color(0x008100)); //green
        MINE_COUNT_COLOR.put(3, new Color(0xff1300)); //red
        MINE_COUNT_COLOR.put(4, new Color(0x000083)); //dark blue
        MINE_COUNT_COLOR.put(5, new Color(0x810500)); //dark red
        MINE_COUNT_COLOR.put(6, new Color(0x2a9494)); //turquoise
        MINE_COUNT_COLOR.put(7, new Color(0x000000)); //black
        MINE_COUNT_COLOR.put(8, new Color(0x808080)); //gray
    }

    public static Color getNumberColor(int surroundingMines) {
        return MINE_COUNT_COLOR.get(surroundingMines);
    }

}
