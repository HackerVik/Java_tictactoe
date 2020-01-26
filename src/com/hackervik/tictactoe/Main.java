package com.hackervik.tictactoe;

import com.hackervik.tictactoe.model.Board;
import com.hackervik.tictactoe.model.Player;

import java.util.Random;

import static com.hackervik.tictactoe.service.Tictactoe.*;

public class Main {
    public static void main(String[] args) {
        int mode = printMenu();
        Random random = new Random();
        Board board = initBoard();
        Player player = new Player();
        switch (mode) {
            case 1:
                humanToHuman(random, board, player);
                quitGame("\nIt's a tie!");
            case 2:
                humanToAi(random, board, player);
                quitGame("\nIt's a tie!");
            case 3:
                quitGame("Program is exiting...");
        }
    }

}