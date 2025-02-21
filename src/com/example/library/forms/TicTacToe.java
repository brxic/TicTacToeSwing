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
        setupKeyboardControls();
        this.setFocusable(true);
        this.requestFocusInWindow();
        gamePanel.setFocusable(false);
    }

    // keyboard controls definieren
    private void KeyPress(int keyCode) {
        int row = 1, col = -1;
        switch (keyCode) {
            case KeyEvent.VK_NUMPAD7: case KeyEvent.VK_Q: row = 0; col = 0; break;
            case KeyEvent.VK_NUMPAD8: case KeyEvent.VK_W: row = 0; col = 1; break;
            case KeyEvent.VK_NUMPAD9: case KeyEvent.VK_E: row = 0; col = 2; break;
            case KeyEvent.VK_NUMPAD4: case KeyEvent.VK_R: row = 1; col = 0; break;
            case KeyEvent.VK_NUMPAD5: case KeyEvent.VK_T: row = 1; col = 1; break;
            case KeyEvent.VK_NUMPAD6: case KeyEvent.VK_Z: row = 1; col = 2; break;
            case KeyEvent.VK_NUMPAD1: case KeyEvent.VK_U: row = 2; col = 0; break;
            case KeyEvent.VK_NUMPAD2: case KeyEvent.VK_I: row = 2; col = 1; break;
            case KeyEvent.VK_NUMPAD3: case KeyEvent.VK_O: row = 2; col = 2; break;
            default:
                return;

        }

        if (row != -1 && col != -1) {
            JButton btn = buttons[row][col];
            if (btn.getText().equals("")) {
                buttonClicked(btn);
            }
        }

    }

    private String getUniquePlayerName(String prompt, String otherPlayerName) {
        String name;
        do {
            name = JOptionPane.showInputDialog(this, prompt);
            if (name == null || name.trim().isEmpty()) {
                name = "Player"; // standard name für spieler
            }
            if (name.equalsIgnoreCase(otherPlayerName)) {
                JOptionPane.showMessageDialog(this, "Players cannot have the same name! Choose a different one.", "Name Conflict", JOptionPane.WARNING_MESSAGE);
            }
        } while (name.equalsIgnoreCase(otherPlayerName)); // wiederholen wenn der spieler zu blöd ist einen andern namen einzugeben
        return name;
    }


    private void initializeGame() {
        // holt die spielernamen vom input popup
        player1Name = getUniquePlayerName("Enter name for Player 1 (X):", "");
        player2Name = getUniquePlayerName("Enter name for Player 2 (O):", player1Name);

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
                btn.setBackground(Color.GRAY);
                btn.addActionListener(e -> buttonClicked(btn));
                gamePanel.add(btn);
                buttons[i][j] = btn;
            }
        }

        gamePanel.revalidate();
        gamePanel.repaint();
    }

    // keyboard controls initialisieren
    private void setupKeyboardControls() {
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                KeyPress(evt.getKeyCode());
            }
        });
        this.setFocusable(true);
        this.requestFocusInWindow();
    }

    // knöpfe auf dem grid drücken
    private void buttonClicked(JButton btn) {
        if (!btn.getText().equals("")) return;

        // setzt symbol basierend auf dem derzeitigen spieler
        String symbol = currentPlayer.equals(player1Name) ? "X" : "O";
        btn.setText(symbol);

        // button farbe
        btn.setForeground(symbol.equals("X") ? Color.RED : Color.CYAN);

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
                btn.setBackground(Color.GRAY);
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

        // quitbutton listener
        quitButton.addActionListener(e -> quitGame());

        // toppanel mit den namen und scores
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(player1ScoreLabel);
        topPanel.add(turnLabel);
        topPanel.add(player2ScoreLabel);

        // Gesamt layout
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);

        // konfiguratives
        setTitle("TicTacToe by Basil");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setVisible(true);
    }

    // UI Komponenten
    private JPanel gamePanel;
    private JLabel turnLabel, player1ScoreLabel, player2ScoreLabel;
}


