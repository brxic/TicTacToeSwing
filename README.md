# TicTacToe Game (Java Swing)

A classic **TicTacToe** game built using **Java Swing**, supporting **Human vs. Human** mode with a clean and interactive user interface.  
The project is designed with a **clear separation** between the game logic and the UI to ensure **easy testing and maintainability**.  

> **Note:** The project documentation and code comments are in German because this project was created as part of an assignment at Work in Switzerland.

---

## Features

- **Two-Player Mode:** Play against another human on the same device.
- **Player Name Input:** Custom name entry for both players.
- **Score Tracking:** Displays and updates scores automatically.
- **Visual Turn Indicator:** Shows which player's turn it is.
- **Keyboard Control:** Option to play using the **Numpad** for quick tile selection.
- **Popup Messages:** Alerts for **win**, **draw**, and **invalid actions** (e.g., same player names).
- **End Game Options:** Reset the board or quit the game.
- **Validation for Unique Names:** Ensures players cannot enter the same name through a warning popup.

---

## Installation

1. **Clone the repository:**
```bash
git clone https://github.com/brxic/TicTacToeSwing.git
```
2. Open the project in your IDE (e.g., IntelliJ IDEA).
3. Build and run the project:
```bash
mvn clean install
mvn exec:java -Dexec.mainClass="com.example.library.forms.TicTacToe"
```

---

# How to Play
 
1. Start the application.  
2. Enter unique names for both players.  
3. Take turns selecting grid tiles by clicking or using the Numpad  
4. A popup message will indicate the game outcome.  
5. Choose to play again or quit using the buttons.  

---

# Project Structure

1. Classes  
**TicTacToe**: Main class handling the UI and player interactions as well as the 3x3 grid and individual tile behavior.  
**GameManager**: Contains the core game logic, such as move validation, win/draw detection, and score management. In this Class there is just raw Java without Swing.  
**Tile**: Manages the colors in the Project.  
2. Java Swing Elements Used  
**JFrame**: Main application window.  
**JPanel**: For structuring the grid and score display.  
**JButton**: Represents tiles in the grid and action buttons (e.g., "Quit").  
**JLabel**: Displays player turns and scores.  
**JOptionPane**: Shows popups for game results and name validation errors.  
**GridLayout**: Organizes the 3x3 grid layout.  

---

# Testing

**Approach**
The project is tested using JUnit5, focusing on the GameManager class to keep the UI separate.
**The tests include**:  
- Correct handling of win and draw scenarios.  
- Ensuring the score updates correctly.


**Run Tests**

To execute the tests, run the following command:
```bash
mvn test  
```

---

# License

This project is licensed under the MIT License. Feel free to use, modify, and distribute it as needed.
