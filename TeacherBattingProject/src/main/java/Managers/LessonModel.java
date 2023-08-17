/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.Lesson;
import DataTypes.Teacher;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTextArea;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author janch
 */
public class LessonModel implements TableModel
{
    private ArrayList<Lesson> lessons = new ArrayList<>();
    
    public LessonModel(Teacher teacher) throws SQLException
    {
        lessons = teacher.getLessonsArrayList();
    }
    @Override
    public int getRowCount()
    {
        return 5;//work days in a week
    }

    @Override
    public int getColumnCount()
    {
        return 15;//14 lessons and the days-collumn
    }

    @Override
    public String getColumnName(int columnIndex)
    {
        return (columnIndex == 0)? "Day": "L"+ columnIndex; 
    }

    @Override
    public Class<?> getColumnClass(int columnIndex)
    {
        return (columnIndex == 0)? String.class : JTextArea.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex)
    {
        return (columnIndex != 0);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        /*
        *   5 rows, 15 collumns, SlotID = 14 * 5 = 70 slots
        *   rowIndex*GetColCount -1 + colIndex = slotID
        *   
        */
        String [] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        
        int slot = rowIndex * (getColumnCount() -1) +columnIndex;
        if (columnIndex == 0) return days[rowIndex];
        
        for (Lesson lesson : lessons)
        {
            if (lesson.getSlotNr() == slot) return lesson.toString();
        }
        
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) 
    {
        
            /*
            *   Update the database from the value specified.
            *   5 rows, 15 collumns, SlotID = 14 * 5 = 70 slots
            *   rowIndex*GetColCount -1 + colIndex = slotID
            */
        try 
        {
            if (aValue == null) 
            {
                deleteValueAt(rowIndex, columnIndex);
            }
            else
            {
                int slot = rowIndex * (getColumnCount() - 1) + columnIndex;
                String originalLessonID = "";
                //update the arraylist
                Lesson updatedLesson = (Lesson) aValue;
                for (Lesson lesson : lessons)
                {
                    if (lesson.getSlotNr() == slot) 
                    {
                        originalLessonID = lesson.getLessonID();
                        lesson = updatedLesson;
                    }
                }

                //update the database
                DatabaseManager.instance.update("Update tblLessons " //(`LessonID`, `TeacherID`, `SubjectID`, `Grade`)
                        + "Set `SlotID` = "+ updatedLesson.getSlotNr()
                        + " AND `SubjectID` = (Select `SubjectID` from tblSubjects WHERE `Subject` =  "+ updatedLesson.getSubject() +")"
                        + " AND `Grade = "+ updatedLesson.getGrade()
                        + " AND `ClassOfGrade` = "+ updatedLesson.getClassOfGrade()
                        + " WHERE `LessonID` = "+ originalLessonID);
            }
        }catch(SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l)
    {
        
    }

    @Override
    public void removeTableModelListener(TableModelListener l)
    {
     
    }

    private void deleteValueAt(int rowIndex, int columnIndex) throws SQLException
    {
        int slot = rowIndex * (getColumnCount() - 1) + columnIndex;
        //delete from db
        
        //delete from arraylist
        for (Lesson lesson : lessons)
        {
            if (lesson.getSlotNr() == slot) 
            {
                DatabaseManager.instance.update("Delete from tblLessons WHERE `LessonID` = "+ lesson.getLessonID());
                lessons.remove(lesson);
            }
        }
    }
    
}
