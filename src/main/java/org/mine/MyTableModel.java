package org.mine;

import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import java.util.*;

public class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {"Enchantment name", "Is present"};
    // private Object[][] data = {
    //     {"hmmm", false},
    //     {"data", true}, 
    //     {"nya", true},
    // };
    final private int status_column = 1;
    final private SQLTalker talker;
    private List<IdNamePair> data = new ArrayList<IdNamePair>();
    private int house_id;

    public MyTableModel(SQLTalker talker) {
        this.talker = talker;
    }

    public void updateData(int house_id) {
        // System.out.println(house_idx);
        this.house_id = house_id;
        try {
            // List<IdNamePair> elist = talker.getEnchInHouseList(house_id);
            data = talker.getEnchInHouseList(house_id);

            System.out.println();
            for (IdNamePair pair : data) {
                System.out.println(pair.id() + " " + ((IdNamePair)pair.data()).data());
                // data[0]
                // fireTableCellUpdated(0, 0);
                // fireTableCellUpdated(0, 1);
            }
        } catch (Exception e) {
            System.out.println(e);
            // TODO: throw it up again lol
        }
        fireTableDataChanged();
    }

    public void nukeData() {
        data = new ArrayList<IdNamePair>();
        fireTableDataChanged();
    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.size();
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        // return data[row][col];
        if (col == status_column)
            return data.get(row).id() > 0;
        else 
            return ((IdNamePair)data.get(row).data()).data();
    }

    public Class getColumnClass(int col) {
        // return getValueAt(0, c).getClass();
        
        return getValueAt(0, col).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < status_column) {
            return false;
        } else {
            return true;
        }
    }

    /*
     * Don't need to implement this method unless your table's
     * data can change.
     */
    public void setValueAt(Object value, int row, int col) {
        if (col == status_column) {
            System.out.println(row + " " + data.get(row).id() + " " + ((IdNamePair)data.get(row).data()).id());
            int recId = data.get(row).id();
            int encId = ((IdNamePair)data.get(row).data()).id();

            if (recId == 0) {
                // TODO: insert
                try {
                    int new_id = talker.insertEnchInHouse(house_id, encId);
                    data.get(row).updateId(new_id);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }
            else {
                try {
                    talker.deleteEnchInHouse(data.get(row).id());
                    data.get(row).updateId(0);
                }
                catch (Exception e) {
                    System.out.println(e);
                }
            }

            fireTableCellUpdated(row, col);
        }
    }
    
}