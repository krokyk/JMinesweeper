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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kroky.commons.utils.SwingUtils;

/**
 *
 * @author Krokavec Peter
 */
public class Tile extends JLabel {

    private static final Logger LOG = LogManager.getFormatterLogger();

    private static final Border UNREVEALED_BORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border REVEALED_BORDER = BorderFactory.createBevelBorder(BevelBorder.LOWERED);

    private final int posX;
    private final int posY;
//    private Tile northwestTile;
//    private Tile northTile;
//    private Tile northeastTile;
//    private Tile eastTile;
//    private Tile southeastTile;
//    private Tile southTile;
//    private Tile southwestTile;
//    private Tile westTile;
    private final List<Tile> neighbours = new ArrayList<>();
    private boolean trapped = false;
    private boolean revealed = false;
    private boolean marked = false;

    public Tile(int x, int y) {
        this.posX = x;
        this.posY = y;
        init();
    }

    private void init() {
        this.setBorder(UNREVEALED_BORDER);
        this.setMaximumSize(new Dimension(24, 24));
        this.setMinimumSize(new Dimension(24, 24));
        this.setPreferredSize(new Dimension(24, 24));
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setVerticalAlignment(SwingConstants.CENTER);
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    revealTile();
                } else {
                    toggleFlag();
                }
            }
        });
    }

    private void revealTile() {
        if (isRevealed() || isMarked()) {
            return;
        }
        LOG.info("Revealing [%s,%s]", posX, posY);
        this.setRevealed(true);
        this.setBorder(REVEALED_BORDER);
        this.checkTile(this);
    }

    private void toggleFlag() {
        this.setMarked(!isMarked());
        this.setIcon(isMarked() ? SwingUtils.getIcon("/icons/flag.png", new Dimension(18, 18)) : null);
    }

    private void checkTile(Tile tile) {
        if (tile.isTrapped()) {
            tile.setText("*");
        } else {
            int surroundingMines = (int) tile.getNeighbours().stream().filter(t -> t.isTrapped()).count();
            if (surroundingMines > 0) {
                tile.setText("" + surroundingMines);
            } else {
                tile.getNeighbours().forEach(t -> t.revealTile());
            }
        }
    }

    public boolean addNeighbour(Tile tile) {
        if (tile != null) {
            return getNeighbours().add(tile);
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

    /**
     * @return the revealed
     */
    public boolean isRevealed() {
        return revealed;
    }

    /**
     * @param revealed the revealed to set
     */
    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

    /**
     * @return the neighbours
     */
    public List<Tile> getNeighbours() {
        return neighbours;
    }

    /**
     * @return the marked
     */
    public boolean isMarked() {
        return marked;
    }

    /**
     * @param marked the marked to set
     */
    public void setMarked(boolean marked) {
        this.marked = marked;
    }
}
