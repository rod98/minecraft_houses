package org.mine;

import javax.swing.*;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.TableModel;

public class WindowMain extends JFrame {
    
    public WindowMain(final SQLTalker talker, final Font[] fonts) {
        final Font main_font = fonts[0];
        final Font smol_font = fonts[1];
        final Font bold_font = fonts[2];
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        // final SQLTalker talker = new SQLTalker(cred_path);

        //Creating the MenuBar and adding components
        JMenuBar mb = new JMenuBar();
        mb.setFont(bold_font);
        JMenu menu = new JMenu("Add");
        menu.setFont(bold_font);
        mb.add(menu);
        JMenuItem mi_newhouse = new JMenuItem("House");
        JMenuItem mi_newplayr = new JMenuItem("Player");
        JMenuItem mi_newencht = new JMenuItem("Enchantment");
        mi_newhouse.setFont(smol_font);
        mi_newplayr.setFont(smol_font);
        mi_newencht.setFont(smol_font);
        menu.add(mi_newhouse);
        menu.add(mi_newplayr);
        menu.add(mi_newencht);

        final WinMainPanel ipanel = new WinMainPanel(talker, main_font);
        
        mi_newhouse.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WindowCreateHouse(talker, ipanel, main_font);
            }
        });
        mi_newplayr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WindowCreatePlayer(talker, ipanel, main_font);
            }
        });
        mi_newencht.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new WindowCreateEnch(talker, ipanel, main_font);
            }
        });

        getContentPane().add(BorderLayout.NORTH, mb);
        getContentPane().add(ipanel, BorderLayout.CENTER);
        
        setVisible(true);
    }
}