package com.example.library;

import com.example.library.forms.TicTacToe;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatDarculaLaf;

import javax.swing.*;
import java.net.StandardSocketOptions;

public class Main {
    public static void main(String[] args) {

        // flatlaf design damit nicht alles so veraltet aussieht
        try {
            UIManager.setLookAndFeel(new FlatDarculaLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        new TicTacToe();
    }
}

