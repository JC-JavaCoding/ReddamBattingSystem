/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author janch
 */
public class SubjectModel implements ListModel<String>
{
    ArrayList<String> subjects;
    public SubjectModel () throws SQLException
    {
        subjects = new ArrayList<>();
        ResultSet subjectsRS = DatabaseManager.instance.query("SELECT `Subject` FROM tblSubjects;");
        
        while (subjectsRS.next())
        {
            subjects.add(subjectsRS.getNString(1));
        }
    }
    @Override
    public int getSize()
    {
        return subjects.size();
    }

    @Override
    public String getElementAt(int index)
    {
        return subjects.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l)
    {
        
    }

    @Override
    public void removeListDataListener(ListDataListener l)
    {
        
    }

    public void deleteSubject(String subjectName) throws SQLException
    {   
        DatabaseManager.instance.update("Delete from tblSubjects Where `Subject` = "+ subjectName);
        
        for (String s : subjects)
        {
            if (s.equals(subjectName)) subjects.remove(s);
        }
    }

    public void addSubject(String subjectName) throws SQLException
    {
        DatabaseManager.instance.update("Insert into tblSubjects(`Subject`) Values(\""+ subjectName +"\")");
        subjects.add(subjectName);
    }

    public void updateSubject(int selectedIndex, String subjectName) throws SQLException
    {
        DatabaseManager.instance.update("Update tblSubjects Set `Subject` = \""+ subjectName +"\" Where `Subject` = \""+ subjects.get(selectedIndex) +"\"");
    }
    
}
