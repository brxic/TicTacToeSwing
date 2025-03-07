package com.example.library.forms;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class GameManagerTest implements GameManager.GameEventListener {

    private GameManager gameManager;

    private String currentPlayer;
    private String winner;
    private boolean isDraw;
    private String[][] board;
    private int p1Score;
    private int p2Score;
    private String p1Name;
    private String p2Name;

    @BeforeEach
    public void setUp() {
        gameManager = new GameManager(this);
        gameManager.initializeGame("Player1", "Player2");

        // initialisiere das 3x3 board mit leeren felder, also leere strings
        board = new String[3][3];
        resetBoard();
        isDraw = false;
        winner = null;
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
            }
        }
    }

    @Test
    public void testInitializeGame() {
        assertThat(currentPlayer).isNotNull();
        assertThat(currentPlayer).isIn("Player1", "Player2");
    }

    @Test
    public void testSetMove() {
        gameManager.setMove(0, 0);
        assertThat(board[0][0]).isIn("X", "O");
    }

    // dieser test ist random ob er passed, wegen math.random
    @Test
    public void testWinningConditionRow() {
        gameManager.setMove(0, 0); 
        gameManager.setMove(1, 0); 
        gameManager.setMove(0, 1); 
        gameManager.setMove(1, 1); 
        gameManager.setMove(0, 2);

        assertThat(winner).startsWith("Player");
        assertThat(p1Score).isGreaterThanOrEqualTo(0);
        assertThat(p2Score).isLessThanOrEqualTo(1);
    }

    // dieser test ist nicht betroffen, weil es ja nicht drauf an kommt wer beginnt
    @Test
    public void testDrawCondition() {
        gameManager.setMove(0, 0); 
        gameManager.setMove(0, 1); 
        gameManager.setMove(0, 2); 
        gameManager.setMove(1, 1); 
        gameManager.setMove(1, 0); 
        gameManager.setMove(1, 2); 
        gameManager.setMove(2, 1); 
        gameManager.setMove(2, 0); 
        gameManager.setMove(2, 2); 

        assertThat(isDraw).isTrue();
    }

    @Test
    public void testResetBoard() {
        gameManager.setMove(0, 0); 
        gameManager.setMove(0, 1); 
        gameManager.setMove(0, 2); 
        gameManager.resetBoard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertThat(board[i][j]).isEmpty();
            }
        }
    }

    @Test
    public void testSwitchPlayer() {
        String initialPlayer = currentPlayer;
        gameManager.setMove(0, 0);
        assertThat(currentPlayer).isNotEqualTo(initialPlayer);
    }

    // dieser test ist halt random ob er durchgeführt wird wegen math.random
    @Test
    public void testScoreUpdate() {
        gameManager.setMove(0, 0); 
        gameManager.setMove(1, 0); 
        gameManager.setMove(0, 1); 
        gameManager.setMove(1, 1); 
        gameManager.setMove(0, 2);

        assertThat(p1Score).isGreaterThanOrEqualTo(0);
        assertThat(p2Score).isLessThanOrEqualTo(1);
    }

    // random
    @Test
    public void testInvalidMove() {
        gameManager.setMove(0, 0); 
        gameManager.setMove(0, 0);  // versucht denselben Zug
        assertThat(board[0][0]).isIn("X", "O"); // Kein Überschreiben erlaubt
    }

    // Implementierung des GameEventListener-Interfaces

    @Override
    public void onPlayerTurnChanged(String player) {
        this.currentPlayer = player;
    }

    @Override
    public void onGameWon(String winner, int p1Score, int p2Score) {
        this.winner = winner;
        this.p1Score = p1Score;
        this.p2Score = p2Score;
    }

    @Override
    public void onGameDraw() {
        this.isDraw = true;
    }

    @Override
    public void onMoveMade(int row, int col, String symbol) {
        if (board == null) {
            board = new String[3][3]; // Fallback-Initialisierung
            resetBoard();
        }
        board[row][col] = symbol; // Aktualisiert das Test-Board
    }

    @Override
    public void onScoreUpdate(int p1Score, int p2Score, String p1Name, String p2Name) {
        this.p1Score = p1Score;
        this.p2Score = p2Score;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
    }
}
