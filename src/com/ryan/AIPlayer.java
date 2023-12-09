package com.ryan;
import java.util.Random;

public class AIPlayer extends Player {
    public AIPlayer(Game game, boolean white) {
        super(game, white);
        // idek what else to do here
    }

    public Move getMove() {
        Random random = new Random();
        Piece piece;
        while (true) {
            piece = game.pieces[random.nextInt(game.pieces.length)];
            if (!piece.canSee.isEmpty()) {
                break;
            }
        }
        return new Move(piece, (byte) piece.canSee.toArray()[0]);
    }

}
