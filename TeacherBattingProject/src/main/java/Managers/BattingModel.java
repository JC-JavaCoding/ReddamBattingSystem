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
    private static ArrayList<BattingLesson> battingLessons;
    private ArrayList<BattingLesson> battingLessonsForSession;
    private ArrayList <Teacher> freeTeachers; 

    public  BattingModel() throws SQLException
    {
        battingLessons = new ArrayList<>();
        freeTeachers = new ArrayList<>();
        
        //Populate the arraylist from the database
         ResultSet rs = DatabaseManager.instance.query("Select `FullName`, tblLessons.`LessonID`, `Subject`, `Grade`, `SlotID`, `ClassOfGrade`, `Date` from tblBattingLessons, tblTeachers, tblLessons, tblSubjects "
                 + "Where tblBattingLessons.LessonID = tblLessons.LessonID "
                        + "AND tblBattingLessons.ReplacementTeacherID = tblTeachers.TeacherID "
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
                            LocalDate.parse(rs.getString(7), DateTimeFormatter.ofPattern("dd-MM-yyyy"))
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
                return bl.getTeacherOnDuty().getFullName();
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
                bl.setTeacherOnDuty(TeacherModel.instance.getTeacher((String) aValue));
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
        battingLessonsForSession = new ArrayList<>();
        for(Teacher presentT : TeacherModel.instance.getTeachers())
        {
            boolean isAbsent = false;
            for (Teacher absentT: absentTeachers)
            {
                if ( presentT.getFullName().equals(absentT.getFullName()) ) 
                    isAbsent = true;
            }
            if (!isAbsent) freeTeachers.add(presentT);
        }

        int dayOfWeek = dateAbsent.getDayOfWeek().getValue();
        
        String[][] absentTeacherBattingStringArr = new String[absentTeachers.size()][(dayOfWeek == 5? 13 : 15)];
        ArrayList<BattingLesson[]> absentTeacherBattingArrList = new ArrayList<>();
        
        ArrayList<Teacher> takenTeachers = new ArrayList<>();
        for (Teacher absentT : absentTeachers)
        {
            BattingLesson [] battingsForTeacher = new BattingLesson[(dayOfWeek == 5? 12 : 14)];
            int i = absentTeachers.indexOf(absentT);
            absentTeacherBattingStringArr[i][0] = (absentT.getFullName());
            // need to be able to go to each lesson, change the selected teacher, then make final string arr.
            for (int lessonNumber : lessonsAbsent[i])
            {
                Lesson lesson = absentT.getLessonAt(dayOfWeek, lessonNumber);
                if (lesson != null) 
                {
                    ArrayList<Teacher> teachersForLesson = getAvailableTeachers(lesson, dayOfWeek);
                    Teacher tOnDuty = teachersForLesson.get(0);
                    for(BattingLesson[] blArr : absentTeacherBattingArrList)
                    {
                        for(Teacher t : teachersForLesson)
                        {
                            if(blArr[lessonNumber-1] != null)
                            {
                                if(blArr[lessonNumber-1].getTeacherOnDuty() != t)
                                {
                                    tOnDuty = t;
                                }
                            }
                        }
                    }
                    
                    BattingLesson bl = new BattingLesson(
                            tOnDuty, 
                            lesson, 
                            dateAbsent);
                    bl.setAvailableTeachers(teachersForLesson);
                    battingsForTeacher[lessonNumber-1] = bl;
                    battingLessonsForSession.add(bl);
                    absentTeacherBattingStringArr[i][lessonNumber] = bl.toString();
                }
            }
            absentTeacherBattingArrList.add(battingsForTeacher);
            //iterate through to make sure there are no repeats in the array
            
        }
        String[] colsFriday = {"Teacher","L1","L2","L3","L4","L5","L6","L7","L8","L9","L10","L11", "L12"};
        String[] colsNotFriday = {"Teacher","L1","L2","L3","L4","L5","L6","L7","L8","L9","L10","L11", "L12", "L13","L14"};

        tblModel.setDataVector(absentTeacherBattingStringArr, ((dayOfWeek == 5)? colsFriday : colsNotFriday));

        return tblModel;
    }
    public ArrayList<Teacher> getAvailableTeachers(Lesson lesson, int dayOfWeek) throws SQLException
    {
        ArrayList <Teacher> availableTeachers = new ArrayList<>();
        for (Teacher teacher : freeTeachers)
        {
            if (!teacher.hasLessonAt(lesson.getSlotNr()))
            {
                if(availableTeachers.size() < 4) 
                        availableTeachers.add(teacher);
                else if (availableTeachers.get(0).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(3, availableTeachers.get(2));
                    availableTeachers.set(2, availableTeachers.get(1));
                    availableTeachers.set(1, availableTeachers.get(0));
                    availableTeachers.set(0, teacher);
                }
                else if (availableTeachers.get(1).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(2, availableTeachers.get(1));
                    availableTeachers.set(1, availableTeachers.get(0));
                    availableTeachers.set(0, teacher);
                }
                else if (availableTeachers.get(2).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(2, availableTeachers.get(1));
                    availableTeachers.set(1, teacher);
                }
                else if (availableTeachers.get(3).getBattingWeight(dayOfWeek) > teacher.getBattingWeight(dayOfWeek))
                {
                    availableTeachers.set(2, teacher);
                }
                
            }
        }
 
        //sortAvailableTeachers(availableTeachers, dateAbsent.getDayOfWeek().getValue());
        return availableTeachers;
    }

    public int getNumBattings(String fullName) throws SQLException
    {
        ResultSet rs = DatabaseManager.instance.query("Select COUNT(*) from tblBattingLessons Where tblBattingLessons.ReplacementTeacherID = (SELECT TeacherID FROM tblTeachers WHERE FullName = \""+ fullName +"\")");
        rs.next();
        return rs.getInt(1);
    }

    public void addBattings(ArrayList<BattingLesson> battings) throws SQLException
    {
        String executableString = "Insert into tblBattingLessons(`Date`, `LessonID`, `ReplacementTeacherID`, `Description`) Values ";
        
        for(BattingLesson bl : battings)
        {
            ResultSet rs = DatabaseManager.instance.query("Select TeacherID FROM tblTeachers WHERE `FullName` = \""+  bl.getTeacherOnDuty().getFullName() +"\"");
            rs.next();
            int rtID = rs.getInt(1);
            String lessonID = bl.getLesson().getLessonID();
            executableString += "(\"" + bl.getDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +"\", "+ lessonID +", "+ rtID +", \""+ bl.toString() +"\")"+ (battings.indexOf(bl)+1 == battings.size()? "":", ");
        }
        DatabaseManager.instance.update(executableString);
    }

    public ArrayList<BattingLesson> getBattingLessons()
    {
        return battingLessonsForSession;
    }
}
