/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author janch
 */
public class Class
{
    private String classID, subject, grade;

    public Class(String classID, String subject, String grade)
    {
        this.classID = classID;
        this.subject = subject;
        this.grade = grade;
    }

    public String getClassID()
    {
        return classID;
    }

    @Override
    public String toString()
    {
        return "Class{" + "classID=" + classID + ", subject=" + subject + ", grade=" + grade + '}';
    }

    public void setClassID(String classID)
    {
        this.classID = classID;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public String getGrade()
    {
        return grade;
    }

    public void setGrade(String grade)
    {
        this.grade = grade;
    }
    
}
