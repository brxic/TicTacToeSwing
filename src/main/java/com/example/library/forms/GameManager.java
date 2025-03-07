package com.example.library.forms;

public class GameManager {
    private String player1Name, player2Name; // Spielernamen
    private String currentPlayer; // Aktueller Spieler
    private int player1Score = 0, player2Score = 0; // Punktestand der Spieler
    private int turnCount = 0; // Anzahl der Züge zur Überprüfung auf Unentschieden
    private String[][] board; // 3x3 Spielfeld
    private GameEventListener eventListener;

    public GameManager(GameEventListener eventListener) {
        this.eventListener = eventListener;
        board = new String[3][3];
        resetBoard();
    }

    public void initializeGame(String player1Name, String player2Name) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        // random wer startet
        currentPlayer = (Math.random() < 0.5) ? player1Name : player2Name;
        eventListener.onPlayerTurnChanged(currentPlayer);
        resetBoard();
    }

    public void setMove(int row, int col) {
        if (!board[row][col].isEmpty()) return; // merkt, wenn das feld schon voll ist
        String symbol = currentPlayer.equals(player1Name) ? "X" : "O";
        board[row][col] = symbol; // Aktualisiere das Spielfeld
        turnCount++;
        eventListener.onMoveMade(row, col, symbol);
        // testet auf Sieg oder draw
        if (checkWin(symbol)) {
            if (symbol.equals("X")) player1Score++;
            else player2Score++;
            eventListener.onGameWon(currentPlayer, player1Score, player2Score);
            resetBoard();
        } else if (turnCount == 9) {
            eventListener.onGameDraw();
            resetBoard();
        } else {
            switchPlayer();
            eventListener.onPlayerTurnChanged(currentPlayer);
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1Name) ? player2Name : player1Name;
    }

    boolean checkWin(String symbol) {
        return (checkLine(0, 0, 0, 1, 0, 2, symbol) ||  // Zeile 1
                checkLine(1, 0, 1, 1, 1, 2, symbol) ||  // Zeile 2
                checkLine(2, 0, 2, 1, 2, 2, symbol) ||  // Zeile 3
                checkLine(0, 0, 1, 0, 2, 0, symbol) ||  // Spalte 1
                checkLine(0, 1, 1, 1, 2, 1, symbol) ||  // Spalte 2
                checkLine(0, 2, 1, 2, 2, 2, symbol) ||  // Spalte 3
                checkLine(0, 0, 1, 1, 2, 2, symbol) ||  // Diagonale
                checkLine(0, 2, 1, 1, 2, 0, symbol));   // Umgekehrte Diagonale
    }

    private boolean checkLine(int r1, int c1, int r2, int c2, int r3, int c3, String symbol) {
        return board[r1][c1].equals(symbol) &&
                board[r2][c2].equals(symbol) &&
                board[r3][c3].equals(symbol);
    }

    void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = "";
                eventListener.onMoveMade(i, j, ""); // UI reset
            }
        }
        turnCount = 0;
        eventListener.onScoreUpdate(player1Score, player2Score, player1Name, player2Name);
        eventListener.onPlayerTurnChanged(currentPlayer); // Aktualisiert den Spieler im turn label
    }

    public interface GameEventListener {
        void onPlayerTurnChanged(String player);
        void onGameWon(String winner, int p1Score, int p2Score);
        void onGameDraw();
        void onMoveMade(int row, int col, String symbol);
        void onScoreUpdate(int p1Score, int p2Score, String p1Name, String p2Name);
    }
}
