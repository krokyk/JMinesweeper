/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kroky.commons.utils.SwingUtils;
import org.kroky.jminesweeper.events.TileActionEvent;
import org.kroky.jminesweeper.events.TileActionListener;

/**
 *
 * @author Krokavec Peter
 */
public class Tile extends JLabel {

    private static final Logger LOG = LogManager.getFormatterLogger();

    private static final Border UNREVEALED_BORDER = BorderFactory.createBevelBorder(BevelBorder.RAISED);
    private static final Border REVEALED_BORDER = BorderFactory.createLineBorder(Color.GRAY);

    private final int posX;
    private final int posY;
    private final List<Tile> neighbours = new ArrayList<>();
    private boolean trapped = false;
    private boolean revealed = false;
    private boolean flagged = false;
    private final List<TileActionListener> tileActionListeners = new ArrayList<>();

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
        try {
            this.setFont(SwingUtils.getFont("/fonts/Cousine-Bold.ttf", Font.BOLD, 16));
        } catch (FontFormatException | IOException ex) {
            LOG.warn("Unable to initialize font for the tile. Fallback to Times New Roman");
            this.setFont(new Font("Times New Roman", Font.BOLD, 16));
        }
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                if (evt.getButton() == MouseEvent.BUTTON1) {
                    reveal();
                } else {
                    toggleFlag();
                }
                renderIcon();
            }

        });
    }

    private void reveal() {
        if (isRevealed() || isFlagged()) {
            return;
        }
        LOG.info("Revealing [%s,%s]", posX, posY);
        this.setRevealed(true);
        this.setBorder(REVEALED_BORDER);
        this.checkTile(this);
        fireTileRevealed(new TileActionEvent(this));
    }

    private void toggleFlag() {
        this.setFlagged(!isFlagged());

        fireFlagToggled(new TileActionEvent(this));
    }

    private void checkTile(Tile tile) {
        if (!tile.isTrapped()) {
            int surroundingMines = (int) tile.getNeighbours().stream().filter(t -> t.isTrapped()).count();
            if (surroundingMines > 0) {
                tile.setForeground(Colors.getNumberColor(surroundingMines));
                tile.setText("" + surroundingMines);
            } else {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        tile.getNeighbours().forEach(t -> t.reveal());
                    }
                });
            }
        }
    }

    private void renderIcon() {
        if (isFlagged()) {
            this.setIcon(SwingUtils.getIcon("/icons/flag.png", new Dimension(18, 18)));
        } else if (isRevealed() && isTrapped()) {
            this.setIcon(SwingUtils.getIcon("/icons/mine_exploded.png", new Dimension(18, 18)));
        } else {
            this.setIcon(null);
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
     * @return the flagged
     */
    public boolean isFlagged() {
        return flagged;
    }

    /**
     * @param flagged the flagged to set
     */
    public void setFlagged(boolean flagged) {
        this.flagged = flagged;
    }

    public void addTileActionListener(TileActionListener tileActionListener) {
        this.tileActionListeners.add(tileActionListener);
    }

    private void fireFlagToggled(TileActionEvent tileActionEvent) {
        tileActionListeners.stream().forEach(l -> l.tileFlagToggled(tileActionEvent));
    }

    private void fireTileRevealed(TileActionEvent tileActionEvent) {
        tileActionListeners.stream().forEach(l -> l.tileRevealed(tileActionEvent));
    }
}
