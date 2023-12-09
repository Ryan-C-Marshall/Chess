package com.ryan;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

public class Piece {
    final byte startingPos;
    final boolean white;
    final String pieceID;
    final int index;
    final BufferedImage icon;

    byte pos;
    boolean alive;

    // x and y are the absolute positions of the pieces.
    int x;
    int y;


    Set<Byte> canSee;

    public Piece(byte starting_pos, boolean white, String pieceID, int index, BufferedImage icon) {
        this.startingPos = pos = starting_pos;
        this.alive = true;
        this.white = white;  // boolean
        this.pieceID = pieceID;  // e.g. wr1
        this.index = index; // 0 to 31
        this.icon = icon; // the buffered image sprite
        this.canSee = new HashSet<>();
    }

    public void setCoordinates(byte pos, double SQ_SIZE, boolean inverted) {
        this.x = (int) ((pos/10) * SQ_SIZE);
        this.y = (int) ((7 - pos%10) * SQ_SIZE);  // y is inverted, then multiplied by sq_size

        if (inverted) {  // basically rotate the board over both axes
            this.x = (int) (7*SQ_SIZE - this.x);
            this.y = (int) (7*SQ_SIZE - this.y);
        } // x + S/2 = 8*S - (x + S/2) --> x = 8*S - x - S --> x = 7*S - x
        System.out.println(pieceID + " coordinates set: " + "pos : " + x + ", " + y);
    }

    public boolean move_pos(byte new_pos, double SQ_SIZE, boolean inverted) {
        boolean ret_val;
        if (this.canSee.contains(new_pos) || new_pos > 77) {
            // can_see only takes into account where the piece could move on an empty board. Legality is considered in game.movePiece
            this.pos = new_pos;
            ret_val = true;
        } else { // not a valid move; put the piece back where it's supposed to be
            System.out.println("Can see didn't contain that position.");
            ret_val = false;
        }

        setCoordinates(new_pos, SQ_SIZE, inverted);

        return ret_val;
    }

    // TODO make an efficient move_pos for minimax that only changes can_see and pos (not x and y)
    // also implements a more efficient update_can_see() instead of reevaluating the whole thing

    public void move(int x, int y, int SQ_SIZE) {
        this.x = x - SQ_SIZE/2;
        this.y = y - SQ_SIZE/2;
        // puts the middle of the piece on your mouse (the x and y arguments) -- images are drawn from the top left
    }

    public void paint(Graphics2D g2d, int SQ_SIZE) {
        g2d.drawImage(this.icon, this.x, this.y, SQ_SIZE, SQ_SIZE, null);
    }

    public void paint(Graphics2D g2d, int SQUARE_SIZE, int x, int y) {

    }

    // TODO make this for all the pieces. Logic:
    // if a piece is moved, update all the pieces.
    // Pass the moved pieces'  beginning and ending coordinates into the can see function.
    // If the piece couldn't see the moved piece both before and after, its available moves won't have changed
    // therefore skip the update.
    public void update_can_see(byte[] piece_positions) {
    }

    boolean piece_there(byte[] piece_positions, byte pos, boolean white) {
        int n;
        if (white)
            n = 0;
        else
            n = 16;

        for (int i = n; i < n + 16; i++) {
            if (pos == piece_positions[i])
                return true;
        }
        return false;
    }

    boolean piece_there(byte[] piece_positions, byte pos) {
        for (int i = 0; i < 32; i++) {
            if (pos == piece_positions[i])
                return true;
        }
        return false;
    }

}
