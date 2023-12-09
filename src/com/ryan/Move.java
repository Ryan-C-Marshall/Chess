package com.ryan;

public class Move {
    private final Piece piece;
    private final byte position;

    public Move(Piece piece, byte position) {
        this.piece = piece;
        this.position = position;
    }

    public Piece getPiece() {
        return piece;
    }

    public byte getPosition() {
        return position;
    }
}
