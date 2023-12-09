package com.ryan;

import java.awt.image.BufferedImage;

public class King extends Piece {

    boolean[] castling_rights;

    public King(byte starting_pos, boolean white, String pieceID, int index, BufferedImage icon) {
        super(starting_pos, white, pieceID, index, icon);
        this.castling_rights = new boolean[] {true, true}; // queenside, kingside.
    }

    @Override
    public void update_can_see(byte[] piece_positions) {
        this.canSee.clear();
        byte check_pos;

        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                check_pos = (byte) (pos + 10 * i + j);
                if (check_pos >= 0 && check_pos/10 <= 7 && check_pos%10 <= 7 && check_pos != pos && !piece_there(piece_positions, check_pos, this.white)) {
                    this.canSee.add(check_pos);
                    System.out.println(pieceID + ", " + check_pos);
                }
            }
        }

        if (white) {
            if (castling_rights[0]) {
                if (piece_positions[0] == 0) {  // if the rook hasn't moved
                    if (!piece_there(piece_positions, (byte) 10) && !piece_there(piece_positions, (byte) 20) &&
                            (this.canSee.contains((byte) 30) && !piece_there(piece_positions, (byte) 30, false))) {  // white is always true
                        this.canSee.add((byte) 20);
                    }
                } else {
                    castling_rights[0] = false;
                }
            }
            if (castling_rights[1]) {
                if (piece_positions[7] == 70) {  // the rook hasn't moved
                    if (!piece_there(piece_positions, (byte) 60) &&
                            (this.canSee.contains((byte) 50) && !piece_there(piece_positions, (byte) 50, false))) {
                        this.canSee.add((byte) 60);
                    }
                } else {
                    castling_rights[1] = false;
                }
            }
        } else {
            if (castling_rights[0]) {
                if (piece_positions[16] == 7) {  // the rook hasn't moved
                    if (!piece_there(piece_positions, (byte) 17) && !piece_there(piece_positions, (byte) 27) &&
                            (canSee.contains((byte) 37) && !piece_there(piece_positions, (byte) 37, true))) {
                        this.canSee.add((byte) 27);
                    }
                } else {
                    castling_rights[0] = false;
                }
            }
            if (castling_rights[1]) {
                if (piece_positions[23] == 77) {  // rook hasn't moved
                    if (!piece_there(piece_positions, (byte) 67) &&
                            (this.canSee.contains((byte) 57) && !piece_there(piece_positions, (byte) 57, true))) {
                        this.canSee.add((byte) 67);
                    }
                } else {
                    castling_rights[1] = false;
                }
            }
        }
    }

    @Override
    public boolean move_pos(byte new_pos, double SQ_SIZE, boolean inverted) {
        boolean real_move = new_pos != pos;
        // need to evaluate this here because the ifs have a specific structure, but move_pos changes pos to equal new pos

        if (super.move_pos(new_pos, SQ_SIZE, inverted)) {
            if (real_move) {
                this.castling_rights = new boolean[]{false, false};
            }
            return true;
        }
        return false;
    }
}
