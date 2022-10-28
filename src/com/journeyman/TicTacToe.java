package com.journeyman;

import org.jetbrains.annotations.NotNull;
import java.util.*;
import static java.lang.System.err;
import static java.lang.System.out;
import static java.text.MessageFormat.format;

@SuppressWarnings("ReassignedVariable")
public class TicTacToe {
    final static ArrayList<Integer> playerPositions = new ArrayList<>();
    final static ArrayList<Integer> cpuPositions = new ArrayList<>();

    public static void main(String[] args) {
        char[][] gameBoard = {{' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
                {'-', '+', '-', '+', '-'},
                {' ', '|', ' ', '|', ' '},
        };
        printGameBoard(gameBoard);

        while (true) {
            var scan = new Scanner(System.in);
            out.print("\nEnter your placement (1 - 9): ");

            var playerPos = scan.nextInt();

            while (playerPositions.contains(playerPos) || cpuPositions.contains(playerPos)) {
                err.println("Position taken! Enter a correct position: ");
                playerPos = scan.nextInt();
            }
            placePiece(gameBoard, playerPos, "player");

            printGameBoard(gameBoard);

            String result = checkWinner();
            if (result.length() > 0) {
                out.println(result);
                break;
            }

            Random rand = new Random();
            int cpuPos = rand.nextInt(9) + 1;

            while (playerPositions.contains(cpuPos) || cpuPositions.contains(cpuPos)) {
                cpuPos = rand.nextInt(9) + 1;
            }
            placePiece(gameBoard, cpuPos, "cpu");
            
            out.println(format("\nCPU placed at position: {0}", cpuPos));

            printGameBoard(gameBoard);

            result = checkWinner();
            if (result.length() > 0) {
                out.println(result);
                break;
            }
        }
    }

    public static void printGameBoard(char @NotNull [][] gameBoard) {
        for (char[] row : gameBoard) {
            for (char c : row) {
                out.print(c);
            }
            out.println();
        }
    }

    public static void placePiece(char[][] gameBoard, int pos, @NotNull String user) {
        char symbol = ' ';

        if (user.equals("player")) {
            symbol = 'X';
            playerPositions.add(pos);
        } else if (user.equals("cpu")) {
            symbol = 'O';
            cpuPositions.add(pos);
        }

        switch (pos) {
            case 1 -> gameBoard[0][0] = symbol;
            case 2 -> gameBoard[0][2] = symbol;
            case 3 -> gameBoard[0][4] = symbol;
            case 4 -> gameBoard[2][0] = symbol;
            case 5 -> gameBoard[2][2] = symbol;
            case 6 -> gameBoard[2][4] = symbol;
            case 7 -> gameBoard[4][0] = symbol;
            case 8 -> gameBoard[4][2] = symbol;
            case 9 -> gameBoard[4][4] = symbol;
        }
    }

    @SuppressWarnings({"rawtypes", "SuspiciousMethodCalls"})
    public static @NotNull String checkWinner() {

        List<Integer> topRow = Arrays.asList(1, 2, 3);
        List<Integer> midRow = Arrays.asList(4, 5, 6);
        List<Integer> botRow = Arrays.asList(7, 8, 9);

        List<Integer> lefCol = Arrays.asList(1, 4, 7);
        List<Integer> midCol = Arrays.asList(2, 5, 8);
        List<Integer> rigCol = Arrays.asList(3, 6, 9);

        List<Integer> cross1 = Arrays.asList(1, 5, 9);
        List<Integer> cross2 = Arrays.asList(3, 5, 7);

        List<List> winning = new ArrayList<>();
        winning.add(topRow);
        winning.add(midRow);
        winning.add(botRow);
        
        winning.add(lefCol);
        winning.add(midCol);
        winning.add(rigCol);
        
        winning.add(cross1);
        winning.add(cross2);
        
        for (List l : winning) {
            if (playerPositions.containsAll(l)) {
                return "Congratulations you won!";
            } else if (cpuPositions.containsAll(l)) {
                return "CPU wins!!!";
            } else if (playerPositions.size() + cpuPositions.size() == 9) {
                return "CAT!";
            }
        }
    
        return "";
    }
}

