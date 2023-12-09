package com.ryan;

import java.awt.image.BufferedImage;

public class Pawn extends Piece {

    public Pawn(byte starting_pos, boolean white, String pieceID, int index, BufferedImage icon) {
        super(starting_pos, white, pieceID, index, icon);
    }

    @Override
    public void update_can_see(byte[] piece_positions) {
        this.canSee.clear();
        if (!this.alive) {
            return;
        }

        byte check_pos;

        // moving forward
        int forward;

        if (white) {
            forward = 1;
        } else {
            forward = -1;
        }

        check_pos = (byte) (pos + forward);
        if (!piece_there(piece_positions, check_pos, !this.white)) {  // if there's no black pieces there
            this.canSee.add((byte) (pos + forward));
        }
        // no need to check if it's still in the board,
        // because it can't go sideways and if it reaches the end it's a different piece

        if (this.pos == this.startingPos) {  // also check two spaces up
            check_pos = (byte) (pos + 2 * forward);
            if (!piece_there(piece_positions, check_pos, !this.white)) {
                this.canSee.add(check_pos);
            }
        }

        // taking
        for (int i = -1; i < 2; i+= 2) {  // -1 and 1
            check_pos = (byte) (this.pos + 10 * i + forward);
            if (check_pos >= 0 && check_pos <= 77) {
                if (piece_there(piece_positions, check_pos, !white)) {
                    canSee.add(check_pos);
                } else if (piece_positions[32] == check_pos) {
                    System.out.println("Adding en passant to " + this.pieceID + " can see: " + check_pos);
                    canSee.add(check_pos);
                }
            }
        }
    }
}
