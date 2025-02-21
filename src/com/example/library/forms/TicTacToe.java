package com.example.library.forms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class TicTacToe extends javax.swing.JFrame {

    private String player1Name, player2Name;  // spielernamen
    private String currentPlayer;             // wer ist dran?
    private int player1Score = 0, player2Score = 0;  // Score
    private JButton[][] buttons;  // Grid 3x3
    private int turnCount = 0;     // zählt moves damit erkennt werden kann wenn ein draw ist

    public TicTacToe() {
        initComponents();
        initializeGame();
    }

    private void initializeGame() {
        // holt die spielernamen vom input popup
        player1Name = JOptionPane.showInputDialog(this, "Enter name for Player 1 (X):");
        player2Name = JOptionPane.showInputDialog(this, "Enter name for Player 2 (O):");

        // stellt sicher dass ein "name" vorhanden ist
        if (player1Name == null || player1Name.trim().isEmpty()) player1Name = "Player 1";
        if (player2Name == null || player2Name.trim().isEmpty()) player2Name = "Player 2";

        // setzt den startenden spieler
        currentPlayer = (Math.random() < 0.5) ? player1Name : player2Name;
        updateTurnLabel();

        // inizialisiert das 3x3 panel
        buttons = new JButton[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, 60));
                btn.setFocusPainted(false);
                btn.setBackground(Color.LIGHT_GRAY);
                btn.addActionListener(e -> buttonClicked(btn));
                gamePanel.add(btn);
                buttons[i][j] = btn;
            }
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // knöpfe auf dem grid drücken
    private void buttonClicked(JButton btn) {
        if (!btn.getText().equals("")) return;

        // setzt symbol basierend auf dem derzeitigen spieler
        String symbol = currentPlayer.equals(player1Name) ? "X" : "O";
        btn.setText(symbol);

        // button farbe
        btn.setForeground(symbol.equals("X") ? Color.BLUE : Color.RED);

        turnCount++;

        // überprüft win oder draw
        if (checkWin(symbol)) {
            if (symbol.equals("X")) player1Score++;
            else player2Score++;

            JOptionPane.showMessageDialog(this, currentPlayer + " wins!");
            updateScore();
            resetBoard();
        } else if (turnCount == 9) {
            JOptionPane.showMessageDialog(this, "Draw!");
            resetBoard();
        } else {
            switchPlayer();
            updateTurnLabel();
        }
    }

    //wechselt spieler der dran ist
    private void switchPlayer() {
        currentPlayer = currentPlayer.equals(player1Name) ? player2Name : player1Name;
    }

    // label update wer dran ist
    private void updateTurnLabel() {
        turnLabel.setText("Turn: " + currentPlayer);
    }

    // scoreboard updater
    private void updateScore() {
        player1ScoreLabel.setText(player1Name + ": " + player1Score);
        player2ScoreLabel.setText(player2Name + ": " + player2Score);
    }

    // win condition also checkt ob ein symbol horizontal, vertikal oder diagonal drei nacheinander hat
    private boolean checkWin(String symbol) {
        // checkt die linien
        return (checkLine(0, 0, 0, 1, 0, 2, symbol) ||  // Row 1
                checkLine(1, 0, 1, 1, 1, 2, symbol) ||  // Row 2
                checkLine(2, 0, 2, 1, 2, 2, symbol) ||  // Row 3
                checkLine(0, 0, 1, 0, 2, 0, symbol) ||  // Column 1
                checkLine(0, 1, 1, 1, 2, 1, symbol) ||  // Column 2
                checkLine(0, 2, 1, 2, 2, 2, symbol) ||  // Column 3
                checkLine(0, 0, 1, 1, 2, 2, symbol) ||  // Diagonal
                checkLine(0, 2, 1, 1, 2, 0, symbol));   // Diagonal
    }

    // Findet den spezifischen Spieler der gewonnen hat
    private boolean checkLine(int r1, int c1, int r2, int c2, int r3, int c3, String symbol) {
        return buttons[r1][c1].getText().equals(symbol) &&
                buttons[r2][c2].getText().equals(symbol) &&
                buttons[r3][c3].getText().equals(symbol);
    }

    // resetted das 3x3 board
    private void resetBoard() {
        for (JButton[] row : buttons) {
            for (JButton btn : row) {
                btn.setText("");
                btn.setBackground(Color.LIGHT_GRAY);
            }
        }
        turnCount = 0;
        switchPlayer(); // switcht den spieler der startet
        updateTurnLabel();
    }

    // quit button
    private void quitGame() {
        System.exit(0);
    }

    // alle komponenten im UI aufrufen
    private void initComponents() {
        gamePanel = new JPanel(new GridLayout(3, 3));
        turnLabel = new JLabel("Turn: ", SwingConstants.CENTER);
        player1ScoreLabel = new JLabel("", SwingConstants.LEFT);
        player2ScoreLabel = new JLabel("", SwingConstants.RIGHT);
        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener(e -> quitGame());

        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(player1ScoreLabel);
        topPanel.add(turnLabel);
        topPanel.add(player2ScoreLabel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        setTitle("TicTacToe by Basil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setVisible(true);
    }

    // UI Komponenten
    private JPanel gamePanel;
    private JLabel turnLabel, player1ScoreLabel, player2ScoreLabel;
}


