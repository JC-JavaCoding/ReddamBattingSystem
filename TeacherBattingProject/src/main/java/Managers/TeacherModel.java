/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.BattingLesson;
import DataTypes.ExtraMural;
import DataTypes.Lesson;
import DataTypes.Teacher;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author janch
 */
public class TeacherModel implements TableModel
{
    private ArrayList<Teacher> teachers = new ArrayList<>();
    
    public TeacherModel() throws SQLException 
    {
        //insert all the teachers into an arrayList from the DB
        ResultSet rs = DatabaseManager.instance.query("Select * From tblTeachers");//need to select multiple tables to get all relevant information for teacher  
        
        while (rs.next())
        {
            int curTeacherID = rs.getInt(0);
            String teacherName = rs.getString(1);
            ArrayList <ExtraMural> extramurals = new ArrayList<>();
            ArrayList <Lesson> lessons = new ArrayList<>();
            
            
            //add the extramurals into an arraylist
            ResultSet extramuralRS = DatabaseManager.instance.query("Select `Name`, `Duration`, `DayOfWeek` from tblTeacherExtraMurals, tblExtramural WHERE tblTeacherExtraMurals.TeacherID = "+ curTeacherID+ " AND "
                    + "tblTeacherExtraMurals.ExtraMuralID = tblExtraMural.ExtramuralID");
            while(extramuralRS.next())
            {
                extramurals.add(
                        new ExtraMural(
                                extramuralRS.getString(1), 
                                extramuralRS.getDouble(2), 
                                extramuralRS.getInt(3)
                        )
                );
                
                //add lessons to the arraylist
                ResultSet lessonRS =  DatabaseManager.instance.query("Select `LessonID`, `Subject`, `Grade`, `SlotID` from tblLessons, tblSubjects"
                                                                                                        + "WHERE tblLessons.SubjectID = tblSubjects.SubjectID"
                                                                                                        + "AND TeacherID = "+ curTeacherID +";");
                while (lessonRS.next())
                {
                    lessons.add(
                            new Lesson(
                                    lessonRS.getString(1), 
                                    lessonRS.getString(2), 
                                    lessonRS.getInt(3), 
                                    lessonRS.getInt(4)
                            )
                    );
                }
            }
            
            teachers.add(new Teacher(teacherName, extramurals, lessons));
        }
    }
    
    @Override
    public int getRowCount()
    {
        return teachers.size(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getColumnCount()
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
