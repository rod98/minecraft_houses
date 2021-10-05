package org.mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WindowInfo extends JFrame {
    public WindowInfo(String title, String info, Font myFont) {
        setTitle(title);
        javax.swing.JComponent[] array = {
            new JLabel(info)
        };
        setSize(600, array.length * 30/2 + 100);
        JPanel     grid   = new JPanel();
        GridLayout layout = new GridLayout(1, 1, 3, 3);
        // layout.marginHeight = 20;
        // layout.marginWidth = 30;
        
        for (int i = 0; i < array.length; i += 1) {
            if (i % 2 == 0)
                array[i].setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            array[i].setFont(myFont);
            grid.add(array[i]);
        }
        grid.setLayout(layout);
        getContentPane().add(grid);
        setVisible(true);
    }
}
