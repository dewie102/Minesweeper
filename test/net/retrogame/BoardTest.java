package net.retrogame;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest {
    private static Board board;

    @Before
    public void setUp() {
        board = new Board(9, 9, 10);
        board.instantiateBoard();
    }

    @Test
    public void testWinConditionWorks() {
        for(int row = 0; row < board.getRows(); row++) {
            for(int column = 0; column < board.getColumns(); column++) {
                Tile tile = board.getTile(row, column);
                if(!tile.isBomb() && tile.getCurrentState() != TileState.UNCOVERED) {
                    tile.setState(TileState.UNCOVERED);
                }
            }
        }

        assertEquals(0, board.getRemainingTiles());
    }
}