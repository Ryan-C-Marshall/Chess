package com.ryan;

import java.awt.image.BufferedImage;

public class Knight extends Piece {

    public Knight(byte starting_pos, boolean white, String pieceID, int index, BufferedImage icon) {
        super(starting_pos, white, pieceID, index, icon);
    }

    @Override
    public void update_can_see(byte[] piece_positions) {
        this.canSee.clear();
        if (!this.alive) {
            return;
        }

        for (int i = -2; i < 3; i++) {  // trace a diamond around the knight, but ignore the first and last rows of the diamond
            if (i != 0) {
                byte check_pos = (byte) (pos + (10 * i) + (3 - Math.abs(i)));  // up
                if (check_pos / 10 >= 0 && check_pos / 10 < 8 && check_pos % 10 >= 0 && check_pos % 10 < 8
                        && !piece_there(piece_positions, check_pos, white)) {
                    canSee.add(check_pos);
                }
                check_pos = (byte) (pos + (10 * i) - (3 - Math.abs(i)));  // down
                if (check_pos / 10 >= 0 && check_pos / 10 < 8 && check_pos % 10 >= 0 && check_pos % 10 < 8
                        && !piece_there(piece_positions, check_pos, white)) {
                    canSee.add(check_pos);
                }
            }
        }
    }
}
