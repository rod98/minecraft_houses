package org.mine;

import javax.swing.*;
import java.awt.event.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.*;
// import javafx.util;

import javax.swing.JFrame;

public class WindowCreateHouse extends JFrame {
    public WindowCreateHouse(final SQLTalker talker, final WinMainPanel uipanel, final Font myFont) {
        // System.out.println("nya");
        final JComboBox owner_box  = new JComboBox();
        final JLabel    stat_label = new JLabel("");
        final javax.swing.JComponent[] array = {
            new JLabel("Name"),  new JFormattedTextField(),
            new JLabel("Owner"), owner_box,
            new JLabel("X"),     new JFormattedTextField(),
            new JLabel("Y"),     new JFormattedTextField(),
            new JLabel("Z"),     new JFormattedTextField(),
            stat_label,          new JButton("Add house"),
            //new Label(""),      new Button()
        };
        final List<IdNamePair> playerList;

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

        try {
            playerList = talker.getPlayerList();   
            for (IdNamePair pair: playerList) {
                // System.out.println(pair.id());
                owner_box.addItem (pair.data());
            } 

            ((JButton)array[array.length - 1]).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                stat_label.setText("");

                try {
                    talker.insertHouse(
                        ((JFormattedTextField)array[1]).getText(), 
                        playerList.get(owner_box.getSelectedIndex()).id(),
                        Integer.parseInt(((JFormattedTextField)array[5]).getText()), 
                        Integer.parseInt(((JFormattedTextField)array[7]).getText()), 
                        Integer.parseInt(((JFormattedTextField)array[9]).getText())
                    );

                    ((JFormattedTextField)array[1]).setText("");
                    ((JFormattedTextField)array[5]).setText("");
                    ((JFormattedTextField)array[7]).setText("");
                    ((JFormattedTextField)array[9]).setText("");

                    stat_label.setText("Success");
                    uipanel.updateEntries();
                } catch (Exception e) {
                    System.out.println(e);
                    new WindowInfo("Error", 72 + " " + e.getLocalizedMessage(), myFont);
                }
            }
        });
        } catch (Exception e) {
            System.out.println(e);
            new WindowInfo("Error", 78 + " " + e.getLocalizedMessage(), myFont);
        }

        getContentPane().add(grid);
        // this.pack();
        setVisible(true);
        // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
