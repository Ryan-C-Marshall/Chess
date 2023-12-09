package com.ryan;

import java.util.concurrent.CountDownLatch;

public class HumanPlayer extends Player {

    private Move move;
    private int promotion;
    private CountDownLatch getMoveLatch;
    private CountDownLatch getPromotionLatch;

    public HumanPlayer(Game game, boolean white, ClickListener clickListener) {
        super(game, white);
        this.getMoveLatch = new CountDownLatch(1);  // we start not having a move
        this.getPromotionLatch = new CountDownLatch(0); // we start not needing a promotion
    }

    @Override
    public Move getMove() {
        try { getMoveLatch.await(); } catch (InterruptedException e) { throw new RuntimeException(e); }
        // makes sure we have a move currently
        Move retVal = this.move;
        unsetMove();  // we've used the move, so delete it
        return retVal;
    }

    // promotion is requested after a pawn is moved (move completed) to the end of the board.
    // Square appears in the middle of the board with the four options. Knight, Bishop, and rook work as usual.
    // if you click anywhere other than those 3, it becomes a queen. (Queen is still shown on the square for continuity reasons)
    public int getPromotion() {
        getPromotionLatch = new CountDownLatch(1);
        try { getPromotionLatch.await(); } catch (InterruptedException e) { throw new RuntimeException(e); }
        return this.promotion;
    }

    @Override
    public void setMove(Move move) {
        this.move = move;
        getMoveLatch.countDown();
    }

    public void setPromotion(int promotion) { // 0 = queen, 1 = rook, 2 = bishop, 3 = knight
        this.promotion = promotion;
        getPromotionLatch.countDown();
    }

    public void unsetMove() {
        this.move = null;
        getMoveLatch = new CountDownLatch(1);  // we no longer have a move stored
    }

}
