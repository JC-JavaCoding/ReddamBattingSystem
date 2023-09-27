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
    private String lessonID, subject, classOfGrade;
    private int slotNr, grade;

    public Lesson(String lessonID, String subject, int grade, int inSlotNr, String classOfGrade)
    {
        this.lessonID = lessonID;
        this.subject = subject;
        this.grade = grade;
        this.slotNr = inSlotNr;
        this.classOfGrade = classOfGrade;
    }

    public String getClassOfGrade()
    {
        return classOfGrade;
    }
    
    public String getLessonID()
    {
        return lessonID;
    }

    public int getSlotNr()
    {
        return slotNr;
    }

    public void setSlotNr(int slotNr)
    {
        this.slotNr = slotNr;
    }

    @Override
    public String toString()
    {
        return subject + "\n" + grade + ""+ classOfGrade;
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

    int getLessonNumber()
    {
        if (slotNr <= 56)
        {
            return (slotNr)%14;
        }
        return (slotNr-56)%12;
    }
    
    int getDayOfWeek()
    {
        if (slotNr <= 56)
        {
            return (slotNr)/14;
        }
        return 5;
    }
    
}
