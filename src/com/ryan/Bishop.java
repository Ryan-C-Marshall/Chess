package com.ryan;

import java.awt.image.BufferedImage;

public class Bishop extends Piece {


    public Bishop(byte starting_pos, boolean white, String pieceID, int index, BufferedImage icon) {
        super(starting_pos, white, pieceID, index, icon);
    }

    @Override
    public void update_can_see(byte[] piece_positions) {
        this.canSee.clear();
        if (!this.alive) {
            return;
        }
        // do all four directions
        // up, right

        // find the first piece along that diagonal
        int last_ur = 11 * Math.min(7 - pos/10, 7 - pos%10) + pos;  // keeps track of the position of the last possible up-right move
        int last_dr = 9 * Math.min(7 - pos/10, pos%10) + pos;  // same, for down and left
        int last_ul = -9 * Math.min(pos/10, 7 - pos%10) + pos;  //
        int last_dl = -11 * Math.min(pos/10, pos%10) + pos;

        for (byte position : piece_positions) {
            for (int i = 1; i < 8 - pos/10; i++) {
                if (position == pos + 11 * i && position < last_ur) { // up, right
                    last_ur = pos + 11 * i;
                }
                else if (position == pos + 9 * i && position < last_dr) { // + 10i, -1i = down, right
                    last_dr = pos + 9 * i;
                }
            }
            for (int i = 1; i < pos/10 + 1; i++) {
                if (position == pos - 9 * i && position > last_ul) { // +i, -10i = up, left
                    last_ul = pos - 9 * i;
                }
                else if (position == pos - 11 * i && position > last_dl) { // -i, -10i = down, left
                    last_dl = pos - 11 * i;
                }
            }
        }


        // check if the final piece is this colour or the other colour
        if (last_ur != pos && piece_there(piece_positions, (byte) last_ur, this.white)) { // the spot had one of our pieces there
            last_ur -= 11;  // add 1 col and 1 row
        }
        if (last_dr != pos && piece_there(piece_positions, (byte) last_dr, this.white)) {
            last_dr -= 9;
        }
        if (last_ul != pos && piece_there(piece_positions, (byte) last_ul, this.white)) {
            last_ul += 9;
        }
        if (last_dl != pos && piece_there(piece_positions, (byte) last_dl, this.white)) {
            last_dl += 11;
        }

        // add everything within the bounds to can_see
        for (byte p = (byte) last_dl; p < (last_ur + 11); p += 11) {  // run through all the possible places on the positive diagonal
            if (p != pos) {
                canSee.add(p);
            }
        }
        for (byte p = (byte) last_ul; p < last_dr + 9; p += 9) {
            if (p != pos) {
                canSee.add(p);
            }
        }
    }
}
