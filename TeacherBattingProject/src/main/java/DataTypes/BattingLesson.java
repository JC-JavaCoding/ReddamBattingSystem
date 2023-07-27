/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author janch
 */
public class BattingLesson
{
    private String teacherName;
    private Lesson battingClass;
    private LocalDate date;

    public BattingLesson(String teacherOnDuty, Lesson battingClass, LocalDate date)
    {
        this.teacherName = teacherOnDuty;
        this.battingClass = battingClass;
        this.date = date;
    }

    public String getTeacherOnDuty()
    {
        return teacherName;
    }

    public void setTeacherOnDuty(String teacherOnDuty)
    {
        this.teacherName = teacherOnDuty;
    }

    public Lesson getLesson()
    {
        return battingClass;
    }

    public void setBattingClass(Lesson battingClass)
    {
        this.battingClass = battingClass;
    }

    public LocalDate getDate()
    {
        return date;
    }

    @Override
    public String toString()
    {
        return "BattingLesson{" + "teacherOnDuty=" + teacherName + ", battingClass=" + battingClass + ",date=" + date + '}';
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }
    
}
