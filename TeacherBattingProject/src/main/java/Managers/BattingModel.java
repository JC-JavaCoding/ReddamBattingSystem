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
import java.util.Date;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author janch
 */
public class BattingModel implements TableModel
{
    private ArrayList<BattingLesson> battingLessons = new ArrayList<>();
    private ArrayList <Teacher> freeTeachers; //*******************************Necessary?****************************
    private TeacherModel tm;
    public BattingModel() throws SQLException
    {
        tm = new TeacherModel();
        //Populate the arraylist from the database
         ResultSet rs = DatabaseManager.instance.query("Select `FullName`, tblLessons.`LessonID`, `Subject`, `Grade`, `SlotID`, `ClassOfGrade`, `Date` from tblBattingLessons, tblTeachers, tblLessons, tblSubjects "
                 + "Where tblBattingLessons.LessonID = tblLessons.LessonID "
                        + "AND tblBattingLessons.ReplacementTeacherID = tblTeachers.TeacherID "
                        + "AND tblTeachers.TeacherID = tblLessons.TeacherID "
                        + "AND tblLessons.SubjectID = tblSubjects.SubjectID");//need to select multiple tables to get all relevant information for teacher  
        
        while (rs.next())
        {
            battingLessons.add(
                    new BattingLesson(
                            tm.getTeacher(rs.getString(1)),
                            new Lesson(
                                    rs.getString(2), 
                                    rs.getString(3), 
                                    rs.getInt(4),
                                    rs.getInt(5),
                                    rs.getString(6)
                            ), 
                            LocalDate.parse(rs.getString(7), DateTimeFormatter.ofPattern("yy-MM-dd"))
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
                return "Teacher Name";
            case 1:
                return "LessonID";
            case 2: 
                return "Subject";
            case 3: 
                return "Grade";
            case 4: 
                return "Lesson Number";
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
                return String.class;
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
                return bl.getLesson().getLessonID();
            case 2: 
                return bl.getLesson().getSubject();
            case 3: 
                return bl.getLesson().getGrade();
            case 4: 
                int dow = bl.getDate().getDayOfWeek().getValue();
                int slot = (dow - 1) * (14) + bl.getLesson().getSlotNr();
                return slot;
            case 5:
                return bl.getDate();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex)
    {
        BattingLesson bl = (BattingLesson) getValueAt(rowIndex, columnIndex);
        
        switch (columnIndex)
        {
            case 0:
                bl.setTeacherOnDuty((Teacher) aValue);
            case 1:
                bl.getLesson().setLessonID((String) aValue);
            case 2: 
                bl.getLesson().setSubject((String) aValue);
            case 3: 
                bl.getLesson().setGrade( Integer.parseInt( (String) aValue ) );
            case 4: 
                int dow = bl.getDate().getDayOfWeek().getValue();
                int slot = (dow - 1) * (14) + bl.getLesson().getSlotNr();
                bl.getLesson().setSlotNr(slot);
            case 5:
                bl.setDate(LocalDate.parse((String) aValue, DateTimeFormatter.ofPattern("yy/MM/dd")));
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
    
    public DefaultTableModel getBattingTable(Teacher absentTeacher) throws SQLException //**************************************
    {
        DefaultTableModel tblModel = new DefaultTableModel();
        
        //for each lesson, find a replacement teacher
        TeacherModel teacherModel = new TeacherModel();

        for(Lesson lesson : absentTeacher.getLessonsArrayList())
        {
            int lessonNr = lesson.getSlotNr()/14;
            LocalDate dateNow = LocalDate.now();
            int doy = dateNow.getDayOfYear();
            int dow = dateNow.getDayOfWeek().getValue();
            int week;
            if (dow > 5) week = dow/7 + 1;
            else week = dow/7;
            BattingLesson bl = new BattingLesson(null, lesson,dateNow);
            for (Teacher teacher : teacherModel.getTeachersFree(lesson))
            {
                
            }
        }
        return tblModel;
    }
}
