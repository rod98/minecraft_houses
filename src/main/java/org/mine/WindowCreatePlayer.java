package org.mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// import javafx.util;

import javax.swing.JFrame;

public class WindowCreatePlayer extends JFrame {
    public WindowCreatePlayer(final SQLTalker talker,  final WinMainPanel uipanel, final Font myFont) {
        // System.out.println("nya");
        javax.swing.JComponent[] array = {
            new JLabel("Name"),  new JFormattedTextField(),
            new JLabel(""),      new JButton("Add player"),
        };
        final JFormattedTextField name_input = (JFormattedTextField)array[1];
        final JLabel              stat_label = (JLabel)array[2];

        setSize(600, array.length * 40/2);
        
        JPanel     grid   = new JPanel();
        GridLayout layout = new GridLayout(0, 2, 3, 3);
        // layout.marginHeight = 20;
        // layout.marginWidth = 30;
        grid.setLayout(layout);

        for (int i = 0; i < array.length; i += 1) {
            if (i % 2 == 0)
                array[i].setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            array[i].setFont(myFont);
            grid.add(array[i]);
        }

        ((JButton)array[array.length - 1]).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    stat_label.setText("");
                    talker.insertPlayer(name_input.getText().trim());
                    stat_label.setText("Success!");
                    name_input.setText("");
                    uipanel.updateEntries();
                } catch (Exception e) {
                    new WindowInfo("Error", 44 + " " + e.getLocalizedMessage(), myFont);
                }
            }
        });

        getContentPane().add(grid);
        // this.pack();
        setVisible(true);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
