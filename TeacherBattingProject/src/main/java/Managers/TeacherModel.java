/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.ExtraMural;
import DataTypes.Lesson;
import DataTypes.Teacher;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 *
 * @author janch
 */
public class TeacherModel
{
    private ArrayList<Teacher> teachers = new ArrayList<>();
    
    public TeacherModel() throws SQLException 
    {
       //insert all the teachers into an arrayList from the DB
       populateTeachersAL();
    }

    public ArrayList<String> getTeachersContaining(String text)
    {
        //returns the full names of all of the teachers whose names contain the letters in the text.
        ArrayList<String> outputAL = new ArrayList<>();
        
        for (Teacher t : teachers)
        {
            if (t.getFullName().contains(text)) 
                outputAL.add(t.getFullName());
        }
        
        return outputAL;
    }


    public ListModel<String> getListModel() throws SQLException
    {
        populateTeachersAL();
        
        DefaultListModel dlm = new DefaultListModel<String>();
        for (Teacher t : teachers)
        {
            dlm.addElement(t.getFullName());
        }
        
        return dlm;
    }

    public void addTeachersNames(File selectedFile) throws SQLException, FileNotFoundException
    {
        String insertQueryStr = "Insert into tblTeachers(`FullName`) Values ";
        Scanner filesc = new Scanner(selectedFile);
        while(filesc.hasNextLine())
        {
            insertQueryStr +="(\""+ filesc.nextLine() +"\")" + ((filesc.hasNextLine())? ",": "");
        }
        
        System.out.println(insertQueryStr);
        DatabaseManager.instance.update(insertQueryStr);
        
        populateTeachersAL();
    }

    private void populateTeachersAL() throws SQLException
    {
        ResultSet rs = DatabaseManager.instance.query("Select * From tblTeachers");//need to select multiple tables to get all relevant information for teacher  
        
        while (rs.next())
        {
            int curTeacherID = rs.getInt(1);
            String teacherName = rs.getString(2);
            boolean hasRegisterClass = rs.getBoolean(3);
            ArrayList <ExtraMural> extramurals = new ArrayList<>();
            ArrayList <Lesson> lessons = new ArrayList<>();
            
            //add the extramurals into an arraylist
            ResultSet extramuralRS = DatabaseManager.instance.query("Select `Name`, `Duration`, `DayOfWeek` from tblTeacherExtraMurals, tblExtramural WHERE tblTeacherExtraMurals.TeacherID = "+ curTeacherID+ " AND "
                    + "tblTeacherExtraMurals.ExtraMuralID = tblExtramural.ExtramuralID");
            while(extramuralRS.next())
            {
                extramurals.add(
                        new ExtraMural(
                                extramuralRS.getString(1), 
                                extramuralRS.getDouble(2), 
                                extramuralRS.getInt(3)
                        )
                );
                
            }
            //add lessons to the arraylist
            ResultSet lessonRS =  DatabaseManager.instance.query("Select `LessonID`, `Subject`, `Grade`, `SlotID`, `ClassOfGrade` from tblLessons, tblSubjects"
                                                                                                    + " WHERE tblLessons.SubjectID = tblSubjects.SubjectID"
                                                                                                    + " AND TeacherID = "+ curTeacherID +";");
            while (lessonRS.next())
            {
                lessons.add(
                        new Lesson(
                                lessonRS.getString(1), 
                                lessonRS.getString(2), 
                                lessonRS.getInt(3), 
                                lessonRS.getInt(4),
                                lessonRS.getString(5)
                        )
                );
            }
            
            Teacher temp = new Teacher(teacherName, extramurals, lessons, hasRegisterClass);
            boolean exists = false;
            for (Teacher t : teachers)
            {
                exists = (t.getFullName().equals(temp.getFullName()));
            }
            if(!exists) teachers.add(temp);
        }
    }

    public TreeModel getTreeModel(String teacherNameIn)
    {
        DefaultMutableTreeNode root  = new DefaultMutableTreeNode(teacherNameIn);
        DefaultMutableTreeNode extramuralsNode;
        DefaultMutableTreeNode classesTaught;
        
        Teacher matchingTeacher = null;
        for (Teacher t : teachers)
        {
            if (t.getFullName().equals(teacherNameIn)) matchingTeacher = t;
        }
        
        if(matchingTeacher != null) 
        {
            extramuralsNode = new DefaultMutableTreeNode("Extramurals");
            
            if(matchingTeacher.getExtraMuralsAsArrayList() != null)
            {
                for (ExtraMural em : matchingTeacher.getExtraMuralsAsArrayList())
                {
                    extramuralsNode.add(new DefaultMutableTreeNode(em.getExtraMuralName() + " - "+ em.getDuration() + " hrs"));
                }
            }
            
            classesTaught = new DefaultMutableTreeNode("Classes taught");
            if(matchingTeacher.getClassesTaught() != null)
            {
                for (String str : matchingTeacher.getClassesTaught())
                {
                    classesTaught.add(new DefaultMutableTreeNode(str));
                }
            }
            
            root.add(extramuralsNode);
            root.add(classesTaught);
            
            return new DefaultTreeModel(root);
        }
        return new DefaultTreeModel(new DefaultMutableTreeNode("No teacher in database with name: "+ teacherNameIn));
    }
    
    public Teacher getTeacher(String teacherNameIn)
    {
        for (Teacher t : teachers)
        {
            if (t.getFullName().equals(teacherNameIn)) return t;
        }
        
        return null;
    }

    public void addExtramural(Teacher teacherIn, ExtraMural extramuralIn) throws SQLException
    {
        for (Teacher t: teachers)
        {
            if (t.getFullName().equals(teacherIn.getFullName()))
            {
                teacherIn.addExtraMural(extramuralIn);
                t = teacherIn;
                //update database, insert for the teacher id and em. id in teacherExtramurals table
                DatabaseManager.instance.update("Insert into tblTeacherExtraMurals (`TeacherID`, `ExtraMuralID`) "
                +"Select `TeacherID`, `ExtramuralID` from tblTeachers, tblExtramural"
                        + " WHERE tblTeachers.FullName = \""+ t.getFullName() +"\""
                        + " AND tblExtramural.Name = \""+ extramuralIn.getExtraMuralName() +"\" AND tblExtramural.DayOfWeek = \""+ extramuralIn.getWeekday() +"\"");
                break;
            }
        }
        
    }

    public void updateTeacher(Teacher originalTeacher, Teacher updatedTeacher) throws SQLException
    {
        DatabaseManager.instance.update("Update tblTeachers Set `FullName` = \""+ updatedTeacher.getFullName() +"\" AND `HasRegisterClass` = "+ updatedTeacher.hasRegisterClass() 
        +" Where `FullName` = \""+ originalTeacher.getFullName() +"\"");
    }

    public void removeExtramural(Teacher teacher, ExtraMural extramural) throws SQLException
    {
        DatabaseManager.instance.update("Delete from tblTeacherExtramurals "
                + "Where `TeacherID` = (Select `TeacherID` from tblTeachers WHERE FullName = \""+ teacher.getFullName() +"\")"
                + " AND `ExtraMuralID` = (Select `ExtramuralID` from tblExtramural WHERE Name = \""+ extramural.getExtraMuralName() +"\"");
    }

    public void addLessonsFromCSV(File csvFile) throws FileNotFoundException, SQLException
    {
        String insertQueryStr = "Insert into tblLessons(`TeacherID`, `SubjectID`, `Grade`, `ClassOfGrade`, `SlotID`) Values ";
        
        //iterate through file rows
        Scanner filesc = new Scanner(csvFile);
        while(filesc.hasNextLine())
        {
            Scanner linesc = new Scanner(filesc.nextLine()).useDelimiter(",");
            insertQueryStr +="(";
            
            //store line data
            String teacherName = linesc.next();
            String subjectName = linesc.next();
            int grade = linesc.nextInt();
            String setCode = linesc.next();
            int slotID = linesc.nextInt();
            
            //transform line data
            ResultSet tID_RS = ( DatabaseManager.instance.query("Select `TeacherID` From tblTeachers WHERE `FullName` = \""+ teacherName +"\"") );
            tID_RS.next();
            String teacherID = tID_RS.getString(1);
            
            ResultSet sID_RS = (DatabaseManager.instance.query("Select `SubjectID` From tblSubjects WHERE `Subject` = \""+ subjectName +"\"") );
            sID_RS.next();
            String subjectID = sID_RS.getString(1);
            
            //for class of grade: if the setcode's last character is an alphabetic letter, then the class is that letter. If it is a number, then it is ácombined class, defined by a 'c'
            char classLetter = setCode.charAt( setCode.length() -1 );
            char classOfGrade = ( Character.isAlphabetic( classLetter ) )? Character.toUpperCase(classLetter) : 'C'; 
            
            insertQueryStr += teacherID + ", \""+ subjectID +"\", "+ grade +", \'"+ classOfGrade +"\', "+ slotID + ((filesc.hasNextLine())? "),": ")");
        }
        
        System.out.println(insertQueryStr);
        DatabaseManager.instance.update(insertQueryStr);
        
        teachers.clear();
        populateTeachersAL();
    }

    public ArrayList<Teacher> getTeachersFree(Lesson lesson)
    {
        ArrayList <Teacher> freeTeachers = new ArrayList<>();
        for (Teacher teacher : teachers)
        {
            if (teacher.hasLessonAt(lesson.getSlotNr()))
                freeTeachers.add(teacher);
        }
        
        return freeTeachers;
    }

    public DefaultComboBoxModel<String> getComboBoxModel()
    {
        DefaultComboBoxModel<String> cbModel = new DefaultComboBoxModel<String>();
        
        for (Teacher t : teachers)
        {
            cbModel.addElement(t.getFullName());
        }
        
        return cbModel;
    }
}
