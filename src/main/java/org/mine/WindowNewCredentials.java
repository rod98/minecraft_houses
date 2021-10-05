package org.mine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class WindowNewCredentials extends JFrame {
    public WindowNewCredentials(final CredentialsIO credio, final Font myFont, final AppStarter starter) {
        final javax.swing.JComponent[] array = {
            new JLabel("URL"),      new JFormattedTextField(),
            new JLabel("type"),     new JFormattedTextField("postgresql"),
            new JLabel("DB"),       new JFormattedTextField("minehouses"),
            new JLabel("user"),     new JFormattedTextField(),
            new JLabel("password"), new JFormattedTextField(),
            new JLabel("SSL"),      new JFormattedTextField("false"),
            new JLabel(""),         new JButton("Create file"),
            //new Label(""),      new Button()
        };

        setSize(600, array.length * 40/2);
        
        JPanel     grid   = new JPanel();
        GridLayout layout = new GridLayout(0, 2, 3, 3);

        ((JButton)array[array.length - 1]).addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    credio.write(
                        ((JFormattedTextField)array[ 1]).getText(), // url
                        ((JFormattedTextField)array[ 3]).getText(), // type
                        ((JFormattedTextField)array[ 5]).getText(), // db
                        ((JFormattedTextField)array[ 7]).getText(), // user
                        ((JFormattedTextField)array[ 9]).getText(), // password
                        ((JFormattedTextField)array[11]).getText()  // ssl
                    );
                } catch (Exception e) {
                    new WindowInfo("Error", 38 + " " + e.getLocalizedMessage(), myFont);
                }
                setVisible(false);
                starter.start_app();
            }
        });
        // layout.marginHeight = 20;
        // layout.marginWidth = 30;
        grid.setLayout(layout);

        for (int i = 0; i < array.length; i += 1) {
            if (i % 2 == 0)
                array[i].setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
            array[i].setFont(myFont);
            grid.add(array[i]);
        }

        getContentPane().add(grid);
        // this.pack();
        setVisible(true);
    }
}
