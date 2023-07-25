/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;

/**
 *
 * @author janch
 */
public class Lesson
{
    private String lessonID, subject;
    private int slotNr, grade;

    public Lesson(String lessonID, String subject, int grade, int inSlotNr)
    {
        this.lessonID = lessonID;
        this.subject = subject;
        this.grade = grade;
        this.slotNr = inSlotNr;
    }

    public String getLessonID()
    {
        return lessonID;
    }

    @Override
    public String toString()
    {
        return "Class{" + "classID=" + lessonID + ", subject=" + subject + ", grade=" + grade + '}';
    }

    public void setLessonID(String lessonID)
    {
        this.lessonID = lessonID;
    }

    public String getSubject()
    {
        return subject;
    }

    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    public int getGrade()
    {
        return grade;
    }

    public void setGrade(int grade)
    {
        this.grade = grade;
    }
    
}
