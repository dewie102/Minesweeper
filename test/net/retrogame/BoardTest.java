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
}