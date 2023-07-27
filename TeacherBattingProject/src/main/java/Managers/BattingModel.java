/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.BattingLesson;
import DataTypes.Lesson;
import DataTypes.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author janch
 */
public class BattingModel implements TableModel
{
    private ArrayList<BattingLesson> battingLessons = new ArrayList<>();
    
    public BattingModel() throws SQLException
    {
        //Populate the arraylist from the database
         ResultSet rs = DatabaseManager.instance.query("Select `FullName`, `LessonID`, `Subject`, `Grade`, `SlotID`, `Date` from tblBattingLessons, tblTeachers, tblLessons, tblSubjects"
                 + "Where tblBattingLessons.LessonID = tblLessons.LessonID"
                        + "AND tblBattingLessons.ReplacementTeacherID = tblTeachers.TeacherID"
                        + "AND tblTeachers.TeacherID = tblLessons.TeacherID"
                        + "AND tblBattingLessons.SubjectID = tblSubjects.SubjectID");//need to select multiple tables to get all relevant information for teacher  
        
        while (rs.next())
        {
            battingLessons.add(
                    new BattingLesson(
                            rs.getString(1),
                            new Lesson(
                                    rs.getString(2), 
                                    rs.getString(3), 
                                    rs.getInt(4),
                                    rs.getInt(5)
                            ), 
                            LocalDate.parse(rs.getString(6), DateTimeFormatter.ofPattern("yy-MM-dd"))
                    )
            );
        }
    }
    
    @Override
    public int getRowCount()
    {
        return battingLessons.size();
    }

    @Override
    public int getColumnCount()
    {
        return 6;
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return "TeacherName";
            case 1:
                return "LessonID";
            case 2: 
                return "Subject";
            case 3: 
                return "Grade";
            case 4: 
                return "Lesson";
            case 5:
                return "Date";
        }
        return columnIndex + " out of bounds for number columns: "+ getColumnCount();
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        switch (columnIndex)
        {
            case 0:
                return String.class;
            case 1:
                return int.class;
            case 2: 
                return String.class;
            case 3: 
                return int.class;
            case 4: 
                return int.class;
            case 5:
                return LocalDate.class;
        }
        return null;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        BattingLesson bl = battingLessons.get(rowIndex);
        
        switch (columnIndex)
        {
            case 0:
                return bl.getTeacherOnDuty();
            case 1:
                return bl.getLesson();
            case 2: 
                return bl.get;
            case 3: 
                return int.class;
            case 4: 
                return int.class;
            case 5:
                return LocalDate.class;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
