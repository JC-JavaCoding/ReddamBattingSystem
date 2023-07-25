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
    private Teacher teacherOnDuty;
    private Lesson battingClass;
    private LocalDate date;

    public BattingLesson(Teacher teacherOnDuty, Lesson battingClass, LocalDate date)
    {
        this.teacherOnDuty = teacherOnDuty;
        this.battingClass = battingClass;
        this.date = date;
    }

    public Teacher getTeacherOnDuty()
    {
        return teacherOnDuty;
    }

    public void setTeacherOnDuty(Teacher teacherOnDuty)
    {
        this.teacherOnDuty = teacherOnDuty;
    }

    public Lesson getBattingClass()
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
        return "BattingLesson{" + "teacherOnDuty=" + teacherOnDuty + ", battingClass=" + battingClass + ",date=" + date + '}';
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }
    
}
