/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

/**
 *
 * @author Krokavec Peter
 */
public class Tile extends JLabel {

    private static final Border UNREVEALED_BORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border REVEALED_BORDER = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

    private final int posX;
    private final int posY;
    private Tile northwestTile;
    private Tile northTile;
    private Tile northeastTile;
    private Tile eastTile;
    private Tile southeastTile;
    private Tile southTile;
    private Tile southwestTile;
    private Tile westTile;
    private final List<Tile> neighbours = new ArrayList<>();
    private boolean trapped = false;

    public Tile(int x, int y) {
        this.posX = x;
        this.posY = y;
        init();
    }

    private void init() {
        this.setBorder(UNREVEALED_BORDER);
        this.setMaximumSize(new Dimension(20, 20));
        this.setMinimumSize(new Dimension(20, 20));
        this.setPreferredSize(new Dimension(20, 20));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                revealTile();
            }

        });
    }

    private void revealTile() {
        if (isRevealed()) {
            return;
        }
        this.setBorder(REVEALED_BORDER);
        if (this.isTrapped()) {
            //BOOM
        } else {
            this.setText("" + neighbours.stream().filter(t -> t.isTrapped()).count());
        }

    }

    public boolean addNeighbour(Tile tile) {
        if (tile != null) {
            return neighbours.add(tile);
        }
        return false;
    }

    /**
     * @return the posX
     */
    public int getPosX() {
        return posX;
    }

    /**
     * @return the posY
     */
    public int getPosY() {
        return posY;
    }

    /**
     * @return the northwestTile
     */
    public Tile getNorthwestTile() {
        return northwestTile;
    }

    /**
     * @param northwestTile the northwestTile to set
     */
    public void setNorthwestTile(Tile northwestTile) {
        this.northwestTile = northwestTile;
    }

    /**
     * @return the northTile
     */
    public Tile getNorthTile() {
        return northTile;
    }

    /**
     * @param northTile the northTile to set
     */
    public void setNorthTile(Tile northTile) {
        this.northTile = northTile;
    }

    /**
     * @return the northeastTile
     */
    public Tile getNortheastTile() {
        return northeastTile;
    }

    /**
     * @param northeastTile the northeastTile to set
     */
    public void setNortheastTile(Tile northeastTile) {
        this.northeastTile = northeastTile;
    }

    /**
     * @return the eastTile
     */
    public Tile getEastTile() {
        return eastTile;
    }

    /**
     * @param eastTile the eastTile to set
     */
    public void setEastTile(Tile eastTile) {
        this.eastTile = eastTile;
    }

    /**
     * @return the southeastTile
     */
    public Tile getSoutheastTile() {
        return southeastTile;
    }

    /**
     * @param southeastTile the southeastTile to set
     */
    public void setSoutheastTile(Tile southeastTile) {
        this.southeastTile = southeastTile;
    }

    /**
     * @return the southTile
     */
    public Tile getSouthTile() {
        return southTile;
    }

    /**
     * @param southTile the southTile to set
     */
    public void setSouthTile(Tile southTile) {
        this.southTile = southTile;
    }

    /**
     * @return the southwestTile
     */
    public Tile getSouthwestTile() {
        return southwestTile;
    }

    /**
     * @param southwestTile the southwestTile to set
     */
    public void setSouthwestTile(Tile southwestTile) {
        this.southwestTile = southwestTile;
    }

    /**
     * @return the westTile
     */
    public Tile getWestTile() {
        return westTile;
    }

    /**
     * @param westTile the westTile to set
     */
    public void setWestTile(Tile westTile) {
        this.westTile = westTile;
    }

    private boolean isRevealed() {
        return this.getBorder().equals(REVEALED_BORDER);
    }

    /**
     * @return the trapped
     */
    public boolean isTrapped() {
        return trapped;
    }

    /**
     * @param trapped the trapped to set
     */
    public void setTrapped(boolean trapped) {
        this.trapped = trapped;
    }
}
