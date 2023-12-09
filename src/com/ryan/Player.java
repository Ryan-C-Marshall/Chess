package com.ryan;


import java.util.concurrent.CountDownLatch;

public class Player {
    final protected Game game;
    final protected boolean white;

    public Player(Game game, boolean white) {
        this.game = game;
        this.white = white;
    }
    public Move getMove() {
        return null;
    }

    public void setMove(Move m) {}

    public int getPromotion() {
        return 0;
    }

    public void setPromotion(int promotion) {}
}
