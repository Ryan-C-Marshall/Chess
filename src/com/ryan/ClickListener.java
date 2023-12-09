package com.ryan;

import java.awt.event.*;
import java.util.concurrent.CountDownLatch;

public class ClickListener implements MouseListener, MouseMotionListener {
    GamePanel gamePanel;
    ControlPanel controlPanel;
    Game game;
    Piece[] pieces;

    protected Piece currentPiece;
    private byte[] currentMove;

    public ClickListener(Game game, Piece[] pieces) {
        this.game = game;
        this.pieces = pieces;
    }

    public void addPanels(GamePanel gamePanel, ControlPanel controlPanel) {
        this.gamePanel = gamePanel;
        this.controlPanel = controlPanel;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        game.inputPlayer.setPromotion(0);  // FIXME should be a square and stuff, make that in the (game? gamePanel?)
        // TODO show possible places for the piece (piece already set by pressed)
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressed in " + Thread.currentThread());
        if (e.getComponent() == controlPanel) {  // FIXME fix this with buttons and stuff (probably button.press will start the game)
            System.out.println("control panel");
            game.initializationLatch.countDown();  // starts the game
        } else { // component is gamePanel
            currentPiece = game.piece_at(e.getX(), e.getY());
            // set that piece to be the currently selected piece
            gamePanel.repaint();
            // TODO add functionality to click and move pieces
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (currentPiece != null) {  // TODO allow to play around with pieces before the game?
            if (game.inputPlayer != null) {
                game.inputPlayer.setMove(new Move(this.currentPiece, game.coordsToPos(e.getX(), e.getY())));
            } else { // FIXME this is kinda whack, also after a game is finished it is.
                System.out.println("move piece");
                game.movePiece(new Move(currentPiece, game.coordsToPos(e.getX(), e.getY())));
                gamePanel.repaint();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (currentPiece != null) {
            currentPiece.move(e.getX(), e.getY(), (int) gamePanel.SQ_SIZE);
            gamePanel.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    // FIXME obsolete?
    /*
    public byte[] getMove() {
        try {
            game.setGetMoveLatch().await();
        } catch (InterruptedException ignored) {}
        System.out.println("click listener got move.");
        return this.currentMove;
    }

     */
}
