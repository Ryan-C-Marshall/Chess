package com.ryan;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {


    // TODO remove game as a parameter?
    public MainFrame(Game game, Piece[] pieces, int HEIGHT, int GAME_PANEL_WIDTH, int CONTROL_PANEL_WIDTH,
                     GamePanel gamePanel, ControlPanel controlPanel, ClickListener clickListener) {

        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_WIDTH, HEIGHT));
        gamePanel.setBackground(Color.GREEN);

        controlPanel.setPreferredSize(new Dimension(CONTROL_PANEL_WIDTH, HEIGHT));
        controlPanel.setBackground(Color.GRAY);

        clickListener.addPanels(gamePanel, controlPanel);

        JPanel iHaveNoIdeaHowToCode = new JPanel();
        iHaveNoIdeaHowToCode.add(controlPanel, BorderLayout.WEST);
        iHaveNoIdeaHowToCode.add(gamePanel, BorderLayout.EAST);


        this.add(iHaveNoIdeaHowToCode);
        this.pack();

        this.setResizable(false);
        this.setTitle("Chess");
        this.setLocationRelativeTo(null);  // puts it in the center of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
