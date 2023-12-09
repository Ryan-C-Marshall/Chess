package com.ryan;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        byte[] pieces_pos = {0, 10, 20, 30, 40, 50, 60, 70,
        1, 11, 21, 31, 41, 51, 61, 71,
        7, 17, 27, 37, 47, 57, 67, 77,
        6, 16, 26, 36, 46, 56, 66, 76};
        byte pos = pieces_pos[10]; // c2
        char[] pieces_board_pos = new char[] {(char) (pieces_pos[10]/10 + 97), (char) (pieces_pos[10]%10 + 49)};
        // System.out.println(pieces_board_pos);

        new Game();
    }
}
