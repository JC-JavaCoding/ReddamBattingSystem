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
    public static BattingModel instance;
    
    private ArrayList<BattingLesson> battingLessons;
    private ArrayList <Teacher> freeTeachers; //*******************************Necessary?****************************

    private  BattingModel() throws SQLException
    {
        
        battingLessons = new ArrayList<>();
        freeTeachers = new ArrayList<>();
        
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
                            TeacherModel.instance.getTeacher(rs.getString(1)),
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
    
    public static void init() throws SQLException
    {
        if (instance == null)
        {
            instance = new BattingModel();
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
    
    public DefaultTableModel getBattingTableModel(ArrayList<Teacher> absentTeachers, int [][] lessonsAbsent, LocalDate dateAbsent) throws SQLException //**************************************
    {
        DefaultTableModel tblModel = new DefaultTableModel();
        
        for(Teacher presentT : TeacherModel.instance.getTeachers())
        {
            for (Teacher absentT: absentTeachers)
            {
                if ( !presentT.getFullName().equals(absentT.getFullName()) ) 
                    freeTeachers.add(presentT);
            }
        }

        int dayOfWeek = dateAbsent.getDayOfWeek().getValue();
        
        String[][] absentTeacherBattingArrList = new String[absentTeachers.size()][(dayOfWeek == 5? 13 : 15)];
        
        for (Teacher absentT : absentTeachers)
        {
            ArrayList<String> teacherBattings = new ArrayList<>();
            teacherBattings.add(absentT.getFullName());
            
            for (int lessonNumber : lessonsAbsent[absentTeachers.indexOf(absentT)])
            {
                ArrayList<Teacher> teachersForLesson = new ArrayList<>();
                Lesson lesson = absentT.getLessonAt(dayOfWeek, lessonNumber);
                if (lesson != null) 
                {
                    teachersForLesson = getAvailableTeachers(lesson, dayOfWeek);
                    teacherBattings.add(
                            new BattingLesson(
                                    teachersForLesson.get(0), 
                                    lesson, 
                                    dateAbsent
                            ).toString()
                    );
                }
            }
            
            absentTeacherBattingArrList[absentTeachers.indexOf(absentT)] = teacherBattings.toArray(new String[teacherBattings.size()]);
        }
        String[] colsFriday = {"Teacher","L1","L2","L3","L4","L5","L6","L7","L8","L9","L10","L11"};
        String[] colsNotFriday = {"Teacher","L1","L2","L3","L4","L5","L6","L7","L8","L9","L10","L11", "L12", "L13","L14"};

        tblModel.setDataVector(absentTeacherBattingArrList, ((dayOfWeek == 5)? colsFriday : colsNotFriday));

        return tblModel;
    }
    public ArrayList<Teacher> getAvailableTeachers(Lesson lesson, int dayOfWeek) throws SQLException
    {
        ArrayList <Teacher> availableTeachers = new ArrayList<>();
        for (Teacher teacher : freeTeachers)
        {
            if (!teacher.hasLessonAt(lesson.getSlotNr()))
            {
                if(availableTeachers.size() < 3) 
                        availableTeachers.add(teacher);
                else if (availableTeachers.get(0).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(2, availableTeachers.get(1));
                    availableTeachers.set(1, availableTeachers.get(0));
                    availableTeachers.set(0, teacher);
                }
                else if (availableTeachers.get(1).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(2, availableTeachers.get(1));
                    availableTeachers.set(1, teacher);
                }
                else if (availableTeachers.get(2).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(2, teacher);
                }
                
            }
        }
 
        //sortAvailableTeachers(availableTeachers, dateAbsent.getDayOfWeek().getValue());
        return availableTeachers;
    }
    private void sortAvailableTeachers(ArrayList<Teacher> availableTeachers, int dayOfWeek) throws SQLException
    {
        for (int i = 0; i < 3; i++)
        {
            Teacher startT = availableTeachers.get(i);
            Teacher smallestWTeacher = startT;
            for (int j = i+1; j < availableTeachers.size(); j++)
            {
                Teacher iteratableTeacher = availableTeachers.get(j);
                
                if(startT.getBattingWeight(dayOfWeek) < iteratableTeacher.getBattingWeight(dayOfWeek))
                {
                    smallestWTeacher = iteratableTeacher;
                }
            }
            int temp = availableTeachers.indexOf(smallestWTeacher);
            availableTeachers.set(i, smallestWTeacher);
            availableTeachers.set(temp, startT);
            
        }
    }

    public int getNumBattings(String fullName) throws SQLException
    {
        ResultSet rs = DatabaseManager.instance.query("Select COUNT(*) from tblBattingLessons Where tblBattingLessons.ReplacementTeacherID = (SELECT TeacherID FROM tblTeachers WHERE FullName = \""+ fullName +"\")");
        rs.next();
        return rs.getInt(1);
    }
}
