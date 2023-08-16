/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author janch
 */
public class MultiLineTableCellRenderer extends JTextArea implements TableCellRenderer
{

    public MultiLineTableCellRenderer()
    {
        setLineWrap(true);
        setWrapStyleWord(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        if (isSelected)
        {
            setBackground(table.getSelectionBackground());
        } else
        {
            setBackground(table.getBackground());
        }

        setText((value == null) ? "" : value.toString());
        return this;
    }
}
