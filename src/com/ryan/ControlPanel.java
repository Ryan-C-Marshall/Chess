package com.ryan;

import javax.swing.*;

public class ControlPanel extends JPanel {
    final ClickListener clickListener;

    public ControlPanel(ClickListener clickListener) {
        this.clickListener = clickListener;
        this.addMouseMotionListener(this.clickListener);
        this.addMouseListener(this.clickListener);
    }

}
