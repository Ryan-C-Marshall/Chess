package com.ryan;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

public class Game {

    Piece[] pieces;
    byte[] pieces_positions;
    Piece[][] board;
    byte en_passant;

    // boolean[] castling_rights;
    int white_pieces_taken;
    int dark_pieces_taken;

    BufferedImage whiteRookIcon;
    BufferedImage whiteKnightIcon;
    BufferedImage whiteBishopIcon;
    BufferedImage whiteQueenIcon;
    BufferedImage whiteKingIcon;
    BufferedImage whitePawnIcon;
    BufferedImage blackRookIcon;
    BufferedImage blackKnightIcon;
    BufferedImage blackBishopIcon;
    BufferedImage blackQueenIcon;
    BufferedImage blackKingIcon;
    BufferedImage blackPawnIcon;

    // initialize all the pieces' sprites
    {
        try {
            whiteRookIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_wr.png")));
            whiteKnightIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_wn.png")));
            whiteBishopIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_wb.png")));
            whiteQueenIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_wq.png")));
            whiteKingIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_wk.png")));
            whitePawnIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_wp.png")));
            blackRookIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_br.png")));
            blackKnightIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_bn.png")));
            blackBishopIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_bb.png")));
            blackQueenIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_bq.png")));
            blackKingIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_bk.png")));
            blackPawnIcon = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("sprite_bp.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // create all the pieces
    Piece wr1;
    Piece wn1;
    Piece wb1;
    Piece wq;
    Piece wk;
    Piece wb2;
    Piece wn2;
    Piece wr2;
    Piece wp1;
    Piece wp2;
    Piece wp3;
    Piece wp4;
    Piece wp5;
    Piece wp6;
    Piece wp7;
    Piece wp8;
    Piece br1;
    Piece bn1;
    Piece bb1;
    Piece bq;
    Piece bk;
    Piece bb2;
    Piece bn2;
    Piece br2;
    Piece bp1;
    Piece bp2;
    Piece bp3;
    Piece bp4;
    Piece bp5;
    Piece bp6;
    Piece bp7;
    Piece bp8;

    // Physical values
    final int HEIGHT;
    // this is the height of the window.
    final int GAME_PANEL_WIDTH;
    final int CONTROL_PANEL_WIDTH;
            //(int) ((Toolkit.getDefaultToolkit().getScreenSize().width - GAME_PANEL_WIDTH) * 0.9);
    final double SQ_SIZE;  // height == board size

    // game values
    ClickListener clickListener;
    GamePanel gamePanel;
    ControlPanel controlPanel;

    boolean whiteIsHuman;
    boolean darkIsHuman;

    boolean inverted;
    boolean game_running;
    boolean whiteTurn;
    Player inputPlayer;

    // initialization latch
    final CountDownLatch initializationLatch;

    // TODO will be used later to provide PGN codes
    private String PGN;

    public Game() {
        System.out.println("Game constructor: " + Thread.currentThread());
        wr1 = new Rook((byte) 0, true, "wr1", 0, whiteRookIcon);
        wn1 = new Knight((byte) 10, true, "wn1", 1, whiteKnightIcon);
        wb1 = new Bishop((byte) 20, true, "wb1", 2, whiteBishopIcon);
        wq = new Queen((byte) 30, true, "wq1", 3, whiteQueenIcon);
        wk = new King((byte) 40, true, "wk1", 4, whiteKingIcon);
        wb2 = new Bishop((byte) 50, true, "wb2", 5, whiteBishopIcon);
        wn2 = new Knight((byte) 60, true, "wn2", 6, whiteKnightIcon);
        wr2 = new Rook((byte) 70, true, "wr2", 7, whiteRookIcon);
        wp1 = new Pawn((byte) 1, true, "wp1", 8, whitePawnIcon);
        wp2 = new Pawn((byte) 11, true, "wp2", 9, whitePawnIcon);
        wp3 = new Pawn((byte) 21, true, "wp3", 10, whitePawnIcon);
        wp4 = new Pawn((byte) 31, true, "wp4", 11, whitePawnIcon);
        wp5 = new Pawn((byte) 41, true, "wp5", 12, whitePawnIcon);
        wp6 = new Pawn((byte) 51, true, "wp6", 13, whitePawnIcon);
        wp7 = new Pawn((byte) 61, true, "wp7", 14, whitePawnIcon);
        wp8 = new Pawn((byte) 71, true, "wp8", 15, whitePawnIcon);
        br1 = new Rook((byte) 7, false, "br1", 16, blackRookIcon);
        bn1 = new Knight((byte) 17, false, "bn1", 17, blackKnightIcon);
        bb1 = new Bishop((byte) 27, false, "bb1", 18, blackBishopIcon);
        bq = new Queen((byte) 37, false, "bq1", 19, blackQueenIcon);
        bk = new King((byte) 47, false, "bk1", 20, blackKingIcon);
        bb2 = new Bishop((byte) 57, false, "bb2", 21, blackBishopIcon);
        bn2 = new Knight((byte) 67, false, "bn2", 22, blackKnightIcon);
        br2 = new Rook((byte) 77, false, "br2", 23, blackRookIcon);
        bp1 = new Pawn((byte) 6, false, "bp1", 24, blackPawnIcon);
        bp2 = new Pawn((byte) 16, false, "bp2", 25, blackPawnIcon);
        bp3 = new Pawn((byte) 26, false, "bp3", 26, blackPawnIcon);
        bp4 = new Pawn((byte) 36, false, "bp4", 27, blackPawnIcon);
        bp5 = new Pawn((byte) 46, false, "bp5", 28, blackPawnIcon);
        bp6 = new Pawn((byte) 56, false, "bp6", 29, blackPawnIcon);
        bp7 = new Pawn((byte) 66, false, "bp7", 30, blackPawnIcon);
        bp8 = new Pawn((byte) 76, false, "bp8", 31, blackPawnIcon);

        // this is a list that keeps track of all the pieces in the order:
        // left to right, pieces then pawns. White first, then black. e.g. wr1, wn1, ..., wp1 ... wp8, br1 ... bp1, bp8.
        pieces = new Piece[]{
                wr1, wn1, wb1, wq, wk, wb2, wn2, wr2, wp1, wp2, wp3, wp4, wp5, wp6, wp7, wp8,
                br1, bn1, bb1, bq, bk, bb2, bn2, br2, bp1, bp2, bp3, bp4, bp5, bp6, bp7, bp8
        };

        // keeps track of the board.
        this.board = new Piece[][] {
                {wr1, wp1, null, null, null, null, bp1, br1},
                {wn1, wp2, null, null, null, null, bp2, bn1},
                {wb1, wp3, null, null, null, null, bp3, bb1},
                {wq, wp4, null, null, null, null, bp4, bq},
                {wk, wp5, null, null, null, null, bp5, bk},
                {wb2, wp6, null, null, null, null, bp6, bb2},
                {wn2, wp7, null, null, null, null, bp7, bn2},
                {wr2, wp8, null, null, null, null, bp8, br2}};


        // this is a list that keeps track of all the pieces' byte positions in the same order as the pieces' list:
        this.pieces_positions = new byte[]
               {0, 10, 20, 30, 40, 50, 60, 70,
                1, 11, 21, 31, 41, 51, 61, 71,
                7, 17, 27, 37, 47, 57, 67, 77,
                6, 16, 26, 36, 46, 56, 66, 76, -1};  // starting pos (necessary?)
        // the -1 is for en passant

        // physical values
        HEIGHT = (int) (Toolkit.getDefaultToolkit().getScreenSize().height * 0.825);
        // this is the height of the window.
        GAME_PANEL_WIDTH = (int) (HEIGHT * 1.5);
        CONTROL_PANEL_WIDTH = (int) (GAME_PANEL_WIDTH * 0.2);
        //(int) ((Toolkit.getDefaultToolkit().getScreenSize().width - GAME_PANEL_WIDTH) * 0.9);
        SQ_SIZE = (double) HEIGHT/8;  // height == board size

        // objects
        clickListener = new ClickListener(this, pieces);
        gamePanel = new GamePanel(clickListener, HEIGHT, pieces);
        controlPanel = new ControlPanel(clickListener);

        // do necessary preparations
        setUpPieces();

        // create the frame
        new MainFrame(this, pieces, HEIGHT, GAME_PANEL_WIDTH, CONTROL_PANEL_WIDTH, gamePanel, controlPanel, clickListener);

        // TODO are these necessary?
        white_pieces_taken = 0;
        dark_pieces_taken = 0;

        inverted = false;
        game_running = false;

        whiteIsHuman = true;
        darkIsHuman = false;


        initializationLatch = new CountDownLatch(1);

        // this method waits for "start" button or (maybe) the first move to be made. Then, startGame will continue.
        startGame();
    }

    public void startGame() {
        System.out.println("Starting game (before) ... " + Thread.currentThread());

        // wait for control panel to start the game
        try {
            initializationLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.print("Starting game (after) ... " + Thread.currentThread() + " - ");

        Player white_player;
        Player dark_player;
        if (whiteIsHuman) {
            white_player = new HumanPlayer(this, true, clickListener);
            System.out.print("human, ");
        } else {
            white_player = new AIPlayer(this, true);
        }
        if (darkIsHuman) {
            dark_player = new HumanPlayer(this, false, clickListener);
            System.out.println("human");
        } else {
            dark_player = new AIPlayer(this, false);
        }
        game_running = true;
        whiteTurn = true;
        inputPlayer = white_player;
        game_loop(white_player, dark_player);
    }

    private void game_loop(Player whitePlayer, Player darkPlayer) {
        Player thisPlayer;

        while (game_running) {

            // set the current player
            if (whiteTurn) { thisPlayer = whitePlayer;
            } else { thisPlayer = darkPlayer; }

            // get the player's  move
            boolean validMove = false;
            Move move = null;
            while (!validMove) {
                System.out.println("Getting move ...");
                move = thisPlayer.getMove(); // this gets a premove or a clicked move or a calculated move

                // call movePiece
                validMove = checkLegality(move);
                System.out.println("Valid move: " + validMove);
                // if not good, player.getMove() waits until it's updated before returning a move.
            } // end of while loop

            movePiece(move);
            System.out.println("Got valid move.");

            // FIXME make checkmate check
            if (!pieces[4].alive) {
                game_running = false;
                System.out.println("Checkmate");
            } else if (!pieces[4 + 16].alive) {
                game_running = false;
                System.out.println("Checkmate");
            }

            System.out.println("No checkmate");

            switchTurns(whitePlayer, darkPlayer);
        }
    }
    
    private void switchTurns(Player whitePlayer, Player darkPlayer) {
        whiteTurn = !whiteTurn;

        if (inputPlayer == whitePlayer) {
            inputPlayer = darkPlayer;
        } else {
            inputPlayer = whitePlayer;
        }
        
        if (inputPlayer.getClass() == HumanPlayer.class) {
            setInverted(!whiteTurn);
        }
        gamePanel.repaint();  // this paints the piece's correct position and the invert
    }

    public boolean movePiece_coords(Piece piece, int x, int y) { // FIXME is this necessary
        byte new_pos = (byte) (10 * x_to_pos(x) + y_to_pos(y));
        // System.out.println("Move piece to " + x + ", " + y + ",\nog_pos = " + og_pos + ",\nnew_pos = " + new_pos);
        if (inverted){
            new_pos = invert_pos(new_pos);
        }
        // System.out.println("inv. new_pos = " + new_pos);
        return movePiece(new Move(piece, new_pos));
    }

    // TODO make one for before the game starts so you can play around with the board?

    private boolean checkLegality(Move move) {
        Piece piece = move.getPiece();
        byte new_pos = move.getPosition();
        int x_pos = piece.pos/10;
        int y_pos = piece.pos%10;


        // check if it's the right colour piece to move
        if (piece.white != whiteTurn && game_running) {
            piece.setCoordinates(piece.pos, SQ_SIZE, inverted);  // put the piece back
            gamePanel.repaint(); // paint the reset position
            return false;
        }

        // check if the piece sees the square
        if (!piece.canSee.contains(new_pos)) {
            return false;
        }

        // check if it puts the king in check
        // if inCheck()

        // TODO check legality. If not legal, new_pos = og_pos:
        // TODO are you putting your king in check (or through check when castling)
        // TODO idk what else

        return true;
    }
    public boolean movePiece(Move move) {
        System.out.println("Moving piece: " + move.getPiece().pieceID);
        Piece piece = move.getPiece();
        byte new_pos = move.getPosition();
        int x_pos = piece.pos/10;
        int y_pos = piece.pos%10;

        // check if the piece can move there, move the piece.
        if (piece.move_pos(new_pos, SQ_SIZE, inverted)) {  // moves the piece within the piece class, if possible. Updates the piece's x and y to match.
            // After this point, the move is deemed legal.
            if (board[new_pos/10][new_pos%10] != null) { // check if it's taking anything
                takePiece(board[new_pos/10][new_pos%10]);
            } else if (Math.abs(new_pos/10 - x_pos) == 2 && piece.getClass() == King.class) { // a king is castling
                castle(new_pos);
            } else if (Math.abs(x_pos - new_pos/10) == 1 && piece.getClass() == Pawn.class) {  // en passant is happening, as we already checked for taking
                takePiece(board[new_pos/10][y_pos]);  // takes piece at the new x pos but old y pos
            }


            // ~~ pawn things: ~~
            pieces_positions[32] = -1;  // remove en passant if there isn't one

            if (Math.abs(y_pos - new_pos%10) == 2 && piece.getClass() == Pawn.class) {  // moving 2 spaces, creating en passant
                pieces_positions[32] = (byte) (10 * x_pos + (y_pos + new_pos%10)/2);  // in between (average of) the two positions
                System.out.println("Setting en passant: " + pieces_positions[32]);
            } else if ((new_pos % 10 == 0 || new_pos % 10 == 7) && piece.getClass() == Pawn.class) { // pawn is promoting
                    System.out.println("Promotion!");
                    piece = promotePiece(piece, x_pos, y_pos, inputPlayer.getPromotion());  // they have to promote
            }


            board[x_pos][y_pos] = null;
            board[new_pos/10][new_pos%10] = piece;
            pieces_positions[piece.index] = new_pos;

            for (Piece piece1 : pieces) {
                piece1.update_can_see(pieces_positions);
            }
            return true;
        }
        piece.setCoordinates(piece.pos, SQ_SIZE, inverted);  // put the piece back
        gamePanel.repaint(); // paint the reset position
        return false;
    }

    // TODO make a move piece method that works for minimax
    public boolean movePiece(/* args that will be easier for minimax */) {
        return true;
    }

    public void takePiece(Piece piece) {
        piece.alive = false;

        if (piece.white) {
            piece.move_pos((byte) (87 + 10 * (white_pieces_taken%4) - (white_pieces_taken/4)), SQ_SIZE, inverted);
            white_pieces_taken += 1;
        } else {
            piece.move_pos((byte) (80 + 10 * (dark_pieces_taken%4) + (dark_pieces_taken/4)), SQ_SIZE, inverted);
            dark_pieces_taken += 1;
        }
        pieces_positions[piece.index] = piece.pos;
    }

    private void castle(byte new_pos) {
        if (new_pos/10 == 2) {  // castling queenside
            movePiece(new Move(board[0][new_pos%10], (byte) (new_pos + 10)));  // put the rook on its row next to it (to the right)
        } else {  // castling kingside
            movePiece(new Move(board[7][new_pos%10], (byte) (new_pos - 10)));  // put the rook on its row next to it (to the left)
        }
    }

    private Piece promotePiece(Piece piece, int x_pos, int y_pos, int type) { // 0 = queen, 1 = rook, 2 = bishop, 3 = knight
        Piece newPiece;
        BufferedImage icon;
        switch (type) {
            case 0 -> {
                if (piece.white) { icon = whiteQueenIcon;
                } else { icon = blackQueenIcon; }
                newPiece = new Queen(piece.pos, piece.white, piece.pieceID + "-promQ", piece.index, icon);
            }
            case 1 -> {
                if (piece.white) { icon = whiteRookIcon;
                } else { icon = blackRookIcon; }
                newPiece = new Rook(piece.pos, piece.white, piece.pieceID + "-promR", piece.index, icon);
            }
            case 2 -> {
                if (piece.white) { icon = whiteBishopIcon;
                } else { icon = blackBishopIcon; }
                newPiece = new Bishop(piece.pos, piece.white, piece.pieceID + "-promB", piece.index, icon);
            }
            case 3 -> {
                if (piece.white) { icon = whiteKnightIcon;
                } else { icon = blackKnightIcon; }
                newPiece = new Knight(piece.pos, piece.white, piece.pieceID + "-promN", piece.index, icon);
            }
            default -> throw new IllegalStateException("Unexpected type value: " + type);
        }
        board[x_pos][y_pos] = newPiece;
        pieces[piece.index] = newPiece;
        // the new piece takes the index of the old piece in piecePositions
        newPiece.setCoordinates(newPiece.pos, SQ_SIZE, inverted);
        // this is so the new piece spawns in the correct position

        return newPiece;
    }

    private boolean inCheckAfterMove(Piece[] pieces, boolean whitePlayer, Move move) {
        // if (move.getPiece().p
        return false;
    }

    private void setUpPieces() {
        for (Piece piece : pieces) {
            piece.move_pos(piece.pos, SQ_SIZE, inverted);
            piece.update_can_see(pieces_positions);
        }
    }

    public Piece piece_at(int x, int y) {
        if (x > 8 * SQ_SIZE) {  // out of bounds
            return null;
        }

        byte pos_x = x_to_pos(x);
        byte pos_y = y_to_pos(y);
        if (inverted) {
            pos_x = invert_one_pos(pos_x);
            pos_y = invert_one_pos(pos_y);
        }
        // System.out.println("pos x: " + pos_x + ", pos y: " + pos_y);
        return board[pos_x][pos_y];
    }

    private void setInverted(boolean inv) {
        if (inv == inverted) {
            return;
        }

        for (Piece piece : pieces) {
            if (piece.x < SQ_SIZE * 7) {  // if it's in the board, invert x
                piece.x = (int) (SQ_SIZE * 7 - piece.x);
            }
            piece.y = (int) (SQ_SIZE * 7 - piece.y);  // otherwise, only invert y (the piece is taken)
        } // x + S/2 = 8*S - (x + S/2) --> x = 8*S - x - S --> x = 7*S - x
        inverted = inv;
    }

    public byte coordsToPos(int x, int y) {
        byte pos = (byte) (10 * x_to_pos(x) + y_to_pos(y));
        // System.out.println("Move piece to " + x + ", " + y + ",\nog_pos = " + og_pos + ",\nnew_pos = " + new_pos);
        if (inverted){
            pos = invert_pos(pos);
        }
        return pos;
    }

    private byte x_to_pos(int x) {
        return (byte) (x / SQ_SIZE);
        // x --> 10 times (x / sq_size)
    }

    private byte y_to_pos(int y) {
        return (byte) (7 - (byte) (y / SQ_SIZE));
        // y --> y / sq_size, but then flip it around because y goes from 0 at the bottom to 7 at the top.
    }
    private byte invert_pos(byte pos) {
        // System.out.println("Invert pos: " + pos + ", " + (10*(7 - pos/10) + 7 - pos%10));
        return (byte) (10*(7 - pos/10) + 7 - pos%10);
    }

    private byte invert_one_pos(byte pos) {
        return (byte) (7 - pos);
    }

    // TODO
    public String get_FEN() {
        return "";
    }

    public String get_PGN() {
        return this.PGN;
    }

    // TODO PGN will be updated every move
    private void update_PGN() {}
}
