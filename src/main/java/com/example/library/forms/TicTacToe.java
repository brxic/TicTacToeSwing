package com.example.library.forms;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
public class TicTacToe extends JFrame implements GameManager.GameEventListener {
    private JPanel gamePanel;
    private JLabel turnLabel, player1ScoreLabel, player2ScoreLabel;
    private JButton[][] buttons; // Grid 3x3
    private GameManager gameManager;
    public TicTacToe() {
        initComponents();
        gameManager = new GameManager(this);
        setupKeyboardControls();
        setFocusable(true);
        requestFocusInWindow();
        gamePanel.setFocusable(false);

        // Starte Spielinitialisierung
        initializeGame();
    }
    public void initializeGame() {
        String player1Name = getPlayerName("Enter name for Player 1 (X):");
        String player2Name;
        do {
            player2Name = getPlayerName("Enter name for Player 2 (O):");
            if (player1Name.equalsIgnoreCase(player2Name)) {
                JOptionPane.showMessageDialog(this, "Players cannot have the same name! Choose a different one.",
                        "Name Error", JOptionPane.WARNING_MESSAGE);
            }
        } while (player1Name.equalsIgnoreCase(player2Name));
        gameManager.initializeGame(player1Name, player2Name);
    }
    private String getPlayerName(String prompt) {
        String name = JOptionPane.showInputDialog(this, prompt);
        return (name == null || name.trim().isEmpty()) ? "Player" : name;
    }
    private void initComponents() {
        gamePanel = new JPanel(new GridLayout(3, 3));
        turnLabel = new JLabel("Turn: ", SwingConstants.CENTER);
        player1ScoreLabel = new JLabel("", SwingConstants.LEFT);
        player2ScoreLabel = new JLabel("", SwingConstants.RIGHT);
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        JPanel topPanel = new JPanel(new GridLayout(1, 3));
        topPanel.add(player1ScoreLabel);
        topPanel.add(turnLabel);
        topPanel.add(player2ScoreLabel);
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(gamePanel, BorderLayout.CENTER);
        add(quitButton, BorderLayout.SOUTH);
        setTitle("TicTacToe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setVisible(true);
        
        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton btn = new JButton("");
                btn.setFont(new Font("Arial", Font.BOLD, 60));
                btn.setFocusPainted(false);
                btn.setBackground(Tile.DEFAULT_COLOR);
                final int row = i;
                final int col = j;
                btn.addActionListener(e -> gameManager.setMove(row, col));
                gamePanel.add(btn);
                buttons[i][j] = btn;
            }
        }
    }
    private void setupKeyboardControls() {
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                handleKeyPress(evt.getKeyCode());
            }
        });
    }
    private void handleKeyPress(int keyCode) {
        int row = -1, col = -1;
        switch (keyCode) {
            case KeyEvent.VK_NUMPAD7: case KeyEvent.VK_A: row = 0; col = 0; break;
            case KeyEvent.VK_NUMPAD8: case KeyEvent.VK_S: row = 0; col = 1; break;
            case KeyEvent.VK_NUMPAD9: case KeyEvent.VK_D: row = 0; col = 2; break;
            case KeyEvent.VK_NUMPAD4: case KeyEvent.VK_F: row = 1; col = 0; break;
            case KeyEvent.VK_NUMPAD5: case KeyEvent.VK_G: row = 1; col = 1; break;
            case KeyEvent.VK_NUMPAD6: case KeyEvent.VK_H: row = 1; col = 2; break;
            case KeyEvent.VK_NUMPAD1: case KeyEvent.VK_J: row = 2; col = 0; break;
            case KeyEvent.VK_NUMPAD2: case KeyEvent.VK_K: row = 2; col = 1; break;
            case KeyEvent.VK_NUMPAD3: case KeyEvent.VK_L: row = 2; col = 2; break;
            default: return;
        }
        if (row != -1 && col != -1) {
            gameManager.setMove(row, col);
        }
    }
    @Override
    public void onPlayerTurnChanged(String player) {
        turnLabel.setText("Turn: " + player);
    }
    @Override
    public void onGameWon(String winner, int p1Score, int p2Score) {
        JOptionPane.showMessageDialog(this, winner + " wins!");
        updateScore(p1Score, p2Score);
    }
    @Override
    public void onGameDraw() {
        JOptionPane.showMessageDialog(this, "Draw!");
    }
    private void updateScore(int p1Score, int p2Score) {
        player1ScoreLabel.setText("Player 1: " + p1Score);
        player2ScoreLabel.setText("Player 2: " + p2Score);
    }

    @Override
    public void onMoveMade(int row, int col, String symbol) {
        buttons[row][col].setText(symbol);
        buttons[row][col].setForeground(symbol.equals("X") ? Tile.PLAYER1_COLOR : Tile.PLAYER2_COLOR);
    }

    @Override
    public void onScoreUpdate(int p1Score, int p2Score, String p1Name, String p2Name) {
        player1ScoreLabel.setText(p1Name + ": " + p1Score);
        player2ScoreLabel.setText(p2Name + ": " + p2Score);
    }
}
