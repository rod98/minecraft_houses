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

class ComboHBox extends JComboBox {
    public List<IdNamePair> houselist;

    public int getId() {
        return houselist.get(getSelectedIndex()).id();
    }

    public void setHouseSelectionList(final int pid, final SQLTalker talker, final Font myFont) {
        houselist = new ArrayList<IdNamePair>();
        removeAllItems();
        
        try {
            houselist = talker.getHouseList(pid);
            for (IdNamePair pair: houselist)
                addItem(pair.data());
        } catch (Exception e) {
            new WindowInfo("Error", 30 + " " + e, myFont);
        }
    }
}
public class WinMainPanel extends JPanel {
    private List<IdNamePair> playerList;
    private final JComboBox playerSelectBox = new JComboBox();
    private final ComboHBox houseSelectBox  = new ComboHBox();
    private final JTable    houseTable;
    private final Font      myFont;
    private final SQLTalker talker;

    public void updateEntries() {
        try {
            playerList = talker.getPlayerList();
            // final List<IdNamePair> localPlayerList = playerList;
            playerSelectBox.removeAllItems();

            if (playerList.size() > 0)// final int pid = playerList.get(playerSelectBox.getSelectedIndex()).id();
                houseSelectBox.setHouseSelectionList(playerList.get(0).id(), talker, myFont);
            else 
                new WindowInfo("Warning", "Add some players maybe?", myFont);

            for (IdNamePair pair: playerList)
                playerSelectBox.addItem(pair.data());

            try {
                ((MyTableModel)houseTable.getModel()).updateData(houseSelectBox.getId());
            }
            catch (Exception ei) {
                System.out.println(ei);
            }
            // ((MyTableModel)houseTable.getModel()).updateData(houseSelectBox.getId());

            
        } catch (Exception e) {
            new WindowInfo("Error", 66 + " " + e, myFont);
        }
    }
    public WinMainPanel(final SQLTalker talker, final Font myFont) {
        final JLabel    playerSelectLbl = new JLabel("Select player & house:");
        final JPanel    innerPanel   = new JPanel(new GridLayout(1, 3, 5, 5));
        this.myFont = myFont;
        this.talker = talker;

        GridBagConstraints c = new GridBagConstraints();

        houseTable = new JTable(new MyTableModel(talker));

        this.setLayout(new GridBagLayout());
        houseTable.setFillsViewportHeight(true);
        
        playerSelectLbl.setFont(myFont);
        playerSelectBox.setFont(myFont);
        houseSelectBox.setFont(myFont);
        houseTable.setFont(myFont);

        innerPanel.add(playerSelectLbl);
        innerPanel.add(playerSelectBox);
        innerPanel.add(houseSelectBox);

        // innerPanel.add(houseTable, BorderLayout.PAGE_END);

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weighty = 0.1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(innerPanel, c);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 0.8;
        c.gridx = 0;
        c.gridy = 1;

        this.add(houseTable, c);
        updateEntries();

        try {
            playerSelectBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (playerSelectBox.getSelectedIndex() >= 0 && playerList.size() > 0) {               
                        final int pid = playerList.get(playerSelectBox.getSelectedIndex()).id();
                        houseSelectBox.setHouseSelectionList(pid, talker, myFont);
                    }
                }
            });
            houseSelectBox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (playerSelectBox.getSelectedIndex() >= 0 && houseSelectBox.getSelectedIndex() >= 0 && playerList.size() > playerSelectBox.getSelectedIndex() && houseSelectBox.houselist.size() > houseSelectBox.getSelectedIndex())                        
                        ((MyTableModel)houseTable.getModel()).updateData(houseSelectBox.getId());
                    else 
                        ((MyTableModel)houseTable.getModel()).nukeData();
                    
                }
            });
        } catch (Exception e) {
            System.out.println(e);
            new WindowInfo("Error", 127 + " " + e, myFont);
        }
    }
}
