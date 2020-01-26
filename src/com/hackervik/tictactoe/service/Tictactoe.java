package com.hackervik.tictactoe.service;

import com.hackervik.tictactoe.model.Board;
import com.hackervik.tictactoe.model.Player;

import java.io.IOException;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;


public class Tictactoe {
    public static Board initBoard() {
        Board board = new Board();
        board.setBoard("000000000");
        return board;
    }

    public static int getMove(Player player) {
        int move = 0;
        int moveInputFirst = 0;
        Scanner scanner = new Scanner(System.in);
        String printableSign;
        if (player.getPlayer() == 1) printableSign = "X";
        else printableSign = "O";
        System.out.print("\n\nEnter move player '" + printableSign + "': ");
        String moveInput = scanner.nextLine().toUpperCase();
        char moveInputFirstLetter = moveInput.charAt(0);
        if (String.valueOf(moveInputFirstLetter).equals("B")) {
            moveInputFirst = 3;
        }
        if (String.valueOf(moveInputFirstLetter).equals("C")) {
            moveInputFirst = 6;
        }
        move += Integer.parseInt(String.valueOf(moveInput.charAt(1))) - 1 + moveInputFirst;
        return move;
    }

    public static int getAiMove(Board board) {
        Random random = new Random();
        int aiMove = 0;
        if (isEmpty(board)) {
            aiMove = 4;
        } else {
            StringBuilder availablePlaces = new StringBuilder();
            for (int i = 0; i < board.getBoard().length(); i++) {
                if (String.valueOf(board.getBoard().charAt(i)).equals("0")) {
                    availablePlaces.append(i);
                }
            }
            int startPosition = random.nextInt(availablePlaces.length());
            aiMove = Integer.parseInt(availablePlaces.substring(startPosition, startPosition + 1));
        }
        return aiMove;
    }

    public static void mark(Board board, Player player, int move) {
        StringBuilder newBoard = new StringBuilder();
        if (Objects.equals(String.valueOf(board.getBoard().charAt(move)), "0")) {
            newBoard.append(board.getBoard(), 0, move);
            newBoard.append(player.getPlayer());
            newBoard.append(board.getBoard().substring(move + 1));
            board.setBoard(String.valueOf(newBoard));
            player.setPlayer(player.getPlayer() == 1 ? 2 : 1);
        } else {
            System.out.println("That is already taken!");
        }
        printBoard(board);
    }

    public static boolean hasWon(Board board, Player player) {
        String winSign;
        String boardString = board.getBoard();
        if (player.getPlayer() == 1) {
            winSign = "222";
        } else {
            winSign = "111";
        }
        return boardString.substring(0, 3).equals(winSign) ||
                boardString.substring(3, 6).equals(winSign) ||
                boardString.substring(6, 9).equals(winSign) ||
                (boardString.substring(0, 1) + boardString.substring(3, 4) + boardString.substring(6, 7)).equals(winSign) ||
                (boardString.substring(1, 2) + boardString.substring(4, 5) + boardString.substring(7, 8)).equals(winSign) ||
                (boardString.substring(2, 3) + boardString.substring(5, 6) + boardString.substring(8, 9)).equals(winSign) ||
                (boardString.substring(0, 1) + boardString.substring(4, 5) + boardString.substring(8, 9)).equals(winSign) ||
                (boardString.substring(2, 3) + boardString.substring(4, 5) + boardString.substring(6, 7)).equals(winSign);
    }

    public static boolean isFull(Board board) {
        boolean boardIsFull = true;
        for (int i = 0; i < board.getBoard().length(); i++) {
            if (Integer.parseInt(String.valueOf(board.getBoard().charAt(i))) == 0) {
                boardIsFull = false;
            }
        }
        return boardIsFull;
    }

    public static boolean isEmpty(Board board) {
        boolean boardIsEmpty = true;
        for (int i = 0; i < board.getBoard().length(); i++) {
            if (Integer.parseInt(String.valueOf(board.getBoard().charAt(i))) != 0) {
                boardIsEmpty = false;
            }
        }
        return boardIsEmpty;
    }

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (IOException | InterruptedException ex) {
        }
    }

    public static void printBoard(Board board) {
        clearScreen();
        StringBuilder header = new StringBuilder();
        StringBuilder divider = new StringBuilder("+");
        for (int i = 0; i < Math.sqrt(board.getBoard().length()); i++) {
            header.append(i + 1).append("   ");
            divider.append("---+");
        }
        System.out.println("\n    " + header + "\n  " + divider);
        int letter = 64;
        for (int i = 0; i < board.getBoard().length(); i++) {
            if (i == 0) {
                letter++;
                System.out.print((char) letter + " |");
            }
            if (i % 3 == 0 && i != 0) {
                letter++;
                System.out.print("\n  " + divider + "\n" + (char) letter + " |");
            }
            System.out.print(" " + String.valueOf(board.getBoard().charAt(i))
                    .replace("0", "  |")
                    .replace("1", "X |")
                    .replace("2", "O |"));
        }
        System.out.print("\n  " + divider);
    }

    public static void printResult(Player player, Board board) {
        printBoard(board);
        switch (player.getPlayer()) {
            case 1:
                System.out.println("\n\nThe winner is player O!");
                System.exit(0);
            case 2:
                System.out.println("\n\nThe winner is player X!");
                System.exit(0);
            default:
                System.out.println("\n\nIt's a tie!");
                System.exit(0);
        }
    }

    public static int printMenu() {
        System.out.println("\nWelcome to Tic-Tac-Toe!\n\n1) Human to Human\n2) Human to AI\n3) Quit");
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nEnter mode: ");
        return Integer.parseInt(scanner.nextLine());
    }

    public static void setRandomPlayer(Random random, Player player) {
        player.setPlayer(random.nextInt(2) + 1);
    }

    public static void humanToAi(Random random, Board board, Player player) {
        int move;
        setRandomPlayer(random, player);
        printBoard(board);
        while (!isFull(board)) {
            if (hasWon(board, player)) {
                printResult(player, board);

            }
            if (player.getPlayer() == 1) {
                move = getAiMove(board);
            } else {
                move = getMove(player);
            }
            mark(board, player, move);
        }
    }

    public static void humanToHuman(Random random, Board board, Player player) {
        int move;
        setRandomPlayer(random, player);
        printBoard(board);
        while (!isFull(board)) {
            if (hasWon(board, player)) {
                printResult(player, board);

            }
            move = getMove(player);
            mark(board, player, move);
        }
    }

    public static void quitGame(String s) {
        System.out.println(s);
        System.exit(0);
    }
}
