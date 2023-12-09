package com.ryan;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

public class Queen extends Piece {

    public Queen(byte starting_pos, boolean white, String pieceID, int index, BufferedImage icon) {
        super(starting_pos, white, pieceID, index, icon);
    }

    @Override
    public void update_can_see(byte[] piece_positions) {
        this.canSee.clear();
        if (!this.alive) {
            return;
        }

        int last_ur = 11 * Math.min(7 - pos/10, 7 - pos%10) + pos;  // keeps track of the position of the last possible up-right move
        int last_dr = 9 * Math.min(7 - pos/10, pos%10) + pos;  // same, for down and left
        int last_ul = -9 * Math.min(pos/10, 7 - pos%10) + pos;  //
        int last_dl = -11 * Math.min(pos/10, pos%10) + pos;
        int last_r = 70 + pos%10;  // keeps track of the position of the last possible right move
        int last_l = pos%10;  // same, for left
        int last_u = 7 + 10 * (pos/10);  //
        int last_d = 10 * (pos/10);

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
            for (int i = 1; i < 8 - pos/10; i++) {
                if (position == pos + 10 * i && position < last_r) { // right
                    last_r = position;
                    break;
                }
            }
            for (int i = 1; i < pos/10; i++) {
                if (position == pos - 10 * i && position > last_l) { // left
                    last_l = position;
                    break;  // since we're going backwards, the first one found will be the one
                }
            }
            for (int i = 1; i < 8 - pos%10; i++) {
                if (position == pos + i && position < last_u) { // up
                    last_u = position;
                    break;
                }
            }
            for (int i = 1; i < pos%10 + 1; i++) {
                if (position == pos - i && position > last_d) { // down
                    last_d = position;
                    break;
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
        if (last_r != pos && piece_there(piece_positions, (byte) last_r, this.white)) { // the spot had one of our pieces there
            last_r -= 10;  // add 1 col and 1 row
        }
        if (last_l != pos && piece_there(piece_positions, (byte) last_l, this.white)) {
            last_l += 10;
        }
        if (last_u != pos && piece_there(piece_positions, (byte) last_u, this.white)) {
            last_u -= 1;
        }
        if (last_d != pos && piece_there(piece_positions, (byte) last_d, this.white)) {
            last_d += 1;
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
        // add everything within the bounds to can_see
        for (byte p = (byte) last_l; p < (last_r + 10); p += 10) {  // run through all the possible places on the left/right
            if (p != pos) {
                canSee.add(p);
            }
        }
        for (byte p = (byte) last_d; p < last_u + 1; p += 1) {  // all down/up
            if (p != pos) {
                canSee.add(p);
            }
        }

        if (Objects.equals(this.pieceID, "bq1")) {
            System.out.println("Updated can_see with piece coords:\n" + Arrays.toString(piece_positions) + "\nCan see:\n" + canSee);
        }
    }
}
