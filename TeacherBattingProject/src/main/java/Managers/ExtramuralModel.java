/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.ExtraMural;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

/**
 *
 * @author janch
 */
public class ExtramuralModel
{
    private ArrayList<ExtraMural> extramurals = new ArrayList<>();
    
    public ExtramuralModel() throws SQLException
    {
        ResultSet rs = DatabaseManager.INSTANCE.query("Select `Name`, `DayOfWeek`, `Duration` from tblExtramural");
        
        while (rs.next())
        {
            extramurals.add(
                    new ExtraMural(
                            rs.getString(1), 
                            rs.getDouble(3), 
                            rs.getInt(2))
            );
        }
    }
    
    public ArrayList getExtramurals()
    {
        return extramurals;
    }
    
    public ListModel<String> getListModel()
    {
        DefaultListModel<String> dlm = new DefaultListModel<>();
        
        for (ExtraMural em : extramurals)
        {
            dlm.addElement(em.toString());
        }
        
        return dlm;
    }
    
    public void addExtramural(ExtraMural em) throws SQLException
    {
        DatabaseManager.INSTANCE.update("Insert into tblExtramural(`Name`, `DayOfWeek`, `Duration`) Values(\""+ em.getExtraMuralName() +"\", "+ em.getWeekday() +", "+ em.getDuration() +")");
        extramurals.add(em);
    }
    
    public void deleteExtramural(ExtraMural em) throws SQLException
    {
        DatabaseManager.INSTANCE.update("Delete from tblExtramural where Name = \""+ em.getExtraMuralName() +"\" AND DayOfWeek = \""+ em.getWeekday() +"\"");
        
        
        for(int i = 0; i < extramurals.size(); i++)
        {
            if (extramurals.get(i).toString().equals(em.toString())) 
                extramurals.remove(i);
        }
    }
    
    public void updateExtramural(ExtraMural originalEm, ExtraMural newEm) throws SQLException
    {
        DatabaseManager.INSTANCE.update("Update tblExtramural Set `Name` = "+ newEm.getExtraMuralName() +" AND `DayOfWeek` = "+ newEm.getWeekday() + " AND `Duration` = " + newEm.getDuration()
                + " where Name = \""+ originalEm.getExtraMuralName() +"\" AND DayOfWeek = \""+ originalEm.getWeekday() +"\"");
        
        for(int i = 0; i < extramurals.size(); i++)
        {
            if (extramurals.get(i).toString().equals(originalEm.toString())) 
                extramurals.set(i, newEm);
        }
    }

    public ExtraMural getExtramural(String toStringValue)
    {
        for (ExtraMural em : extramurals)
        {
            if (em.toString().equals(toStringValue)) return em;
        }
        
        return null;
    }
}
