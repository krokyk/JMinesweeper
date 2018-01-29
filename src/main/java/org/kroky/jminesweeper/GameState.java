/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kroky.jminesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kroky.jminesweeper.events.GameStateChangedEvent;
import org.kroky.jminesweeper.events.GameStateChangedListener;
import org.kroky.jminesweeper.events.TileActionEvent;
import org.kroky.jminesweeper.events.TileActionListener;

/**
 *
 * @author Peter Krokavec
 */
public class GameState {

    private static final Logger LOG = LogManager.getFormatterLogger();
    private static final GameState INSTANCE = new GameState();
    private Tile[][] tiles;
    private int mineCount;
    private boolean gameFinished;

    private final TileActionListener tileActionListener = new TileActionListener() {
        @Override
        public void tileRevealed(TileActionEvent evt) {
            final Tile tile = evt.getSource();
            LOG.debug("Reveal event coming from tile [%s,%s]", tile.getPosX(), tile.getPosY());
            if (tile.isTrapped()) {
                tileStream().forEach(t -> t.setGameOverIcon());
                gameFinished = true;
                fireGameOver(new GameStateChangedEvent(getRemainingFlagCount()));
            } else if (isWin()) {
                gameFinished = true;
                tileStream().forEach(t -> t.setWinIcon());
                fireWin(new GameStateChangedEvent(getRemainingFlagCount()));
            }
        }

        @Override
        public void tileFlagToggled(TileActionEvent evt) {
            Tile tile = evt.getSource();
            LOG.debug("Flag toggled event coming from tile [%s,%s]. Tile is now %s.", tile.getPosX(), tile.getPosY(), tile.isFlagged() ? "flagged" : "unflagged");
            fireFlagChange(new GameStateChangedEvent(getRemainingFlagCount()));
        }
    };
    private final List<GameStateChangedListener> gameStateChangedListeners = new ArrayList<>();
    private int tileCount;

    private GameState() {
    }

    public static GameState getInstance() {
        return INSTANCE;
    }

    public int getFlagCount() {
        return (int) tileStream().filter(tile -> tile.isFlagged()).count();
    }

    private Stream<Tile> tileStream() {
        return Arrays.stream(tiles).flatMap(row -> Arrays.stream(row));
    }

    public boolean flagLimitReached() {
        return getFlagCount() == getMineCount();
    }

    /**
     * @return the mineCount
     */
    public int getMineCount() {
        return mineCount;
    }

    public int getRemainingFlagCount() {
        return getMineCount() - getFlagCount();
    }

    public boolean isWin() {
        return getRevealedTilesCount() == getTileCount() - getMineCount();
    }

    public boolean areMinesCorrectlyFlagged() {
        return !tileStream().filter(tile -> (tile.isTrapped() && !tile.isFlagged())
                || (!tile.isTrapped() && tile.isFlagged())).findAny().isPresent();
    }

    public int getRevealedTilesCount() {
        return (int) tileStream().filter(tile -> tile.isRevealed()).count();
    }

    public int getTileCount() {
        return tileCount;
    }

    private void fireGameOver(GameStateChangedEvent gameStateChangedEvent) {
        this.gameStateChangedListeners.forEach(l -> l.lose(gameStateChangedEvent));
    }

    private void fireFlagChange(GameStateChangedEvent gameStateChangedEvent) {
        this.gameStateChangedListeners.forEach(l -> l.gameStateChange(gameStateChangedEvent));
    }

    private void fireWin(GameStateChangedEvent gameStateChangedEvent) {
        this.gameStateChangedListeners.forEach(l -> l.win(gameStateChangedEvent));
    }

    public void addGameStateChangedListener(GameStateChangedListener gameStateChangedListener) {
        this.gameStateChangedListeners.add(gameStateChangedListener);
    }

    /**
     * @return the gameFinished
     */
    public boolean isGameFinished() {
        return gameFinished;
    }

    public void init(Tile[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException("Tiles cannot be null");
        }
        this.tiles = tiles;
        this.tileCount = tiles.length * tiles[0].length;
        this.mineCount = (int) tileStream().filter(tile -> tile.isTrapped()).count();
        tileStream().forEach(tile -> tile.addTileActionListener(tileActionListener));
        this.gameFinished = false;
        fireFlagChange(new GameStateChangedEvent(getRemainingFlagCount()));
    }
}
