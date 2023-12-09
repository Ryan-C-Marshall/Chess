package com.ryan;

import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class GamePanel extends JPanel {
    final int BOARD_SIZE;
    final double SQ_SIZE;

    // final ImageIcon[] pieceList = {};
    final Piece[] pieces;

    final ClickListener clickListener;

    Color dark_colour = new Color(100, 100, 250);  // Color.DARK_GRAY;
    Color light_colour = Color.LIGHT_GRAY;

    byte[] boardState;

    public GamePanel(ClickListener clickListener, int BOARD_SIZE, Piece[] pieces) {
        this.clickListener = clickListener;
        this.addMouseMotionListener(this.clickListener);
        this.addMouseListener(this.clickListener);

        this.BOARD_SIZE = BOARD_SIZE;
        this.SQ_SIZE = (double) BOARD_SIZE/8;
        this.pieces = pieces;

        this.boardState = new byte[] {0, 10, 20, 30, 40, 50, 60, 70,
                1, 11, 21, 31, 41, 51, 61, 71,
                7, 17, 27, 37, 47, 57, 67, 77,
                6, 16, 26, 36, 46, 56, 66, 76};
    }

    public void repaintGP(boolean[] pieces, boolean drag, int[] piece_coords, byte[] boardState) {
        if (drag) {

        } else {
            this.boardState = boardState;
        }
        repaint();
    }


    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        // the squares

        g2d.setColor(light_colour);
        for (int r = 0; r < 8; r++) {
            for (int c = r%2; c < 8; c += 2) {
                g2d.fill(new Rectangle2D.Double(c * SQ_SIZE, r * SQ_SIZE, SQ_SIZE, SQ_SIZE));
            }
        }

        g2d.setColor(dark_colour);
        for (int r = 0; r < 8; r++) {
            for (int c = (r+1)%2; c < 8; c+=2) {
                g2d.fill(new Rectangle2D.Double(c * SQ_SIZE, r * SQ_SIZE, SQ_SIZE, SQ_SIZE));
            }
        }

        // pieces
        for (Piece piece : pieces) {
            if (this.clickListener.currentPiece != piece) {
                piece.paint(g2d, (int) SQ_SIZE);
            }
            if (this.clickListener.currentPiece != null) {
                this.clickListener.currentPiece.paint(g2d, (int) SQ_SIZE);
            }
        }

        // get piece info from board_state

    }
}
