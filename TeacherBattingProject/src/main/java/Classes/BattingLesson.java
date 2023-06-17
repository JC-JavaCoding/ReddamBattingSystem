/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author janch
 */
public class BattingLesson
{
    private Teacher teacherOnDuty;
    private Class battingClass;
    private ArrayList<Object> lessonNumbers;
    private LocalDate date;

    public BattingLesson(Teacher teacherOnDuty, Class battingClass, ArrayList<Object> lessonNumbers, LocalDate date)
    {
        this.teacherOnDuty = teacherOnDuty;
        this.battingClass = battingClass;
        this.lessonNumbers = lessonNumbers;
        this.date = date;
    }

    public boolean add(Object e)
    {
        return lessonNumbers.add(e);
    }

    public Teacher getTeacherOnDuty()
    {
        return teacherOnDuty;
    }

    public void setTeacherOnDuty(Teacher teacherOnDuty)
    {
        this.teacherOnDuty = teacherOnDuty;
    }

    public Class getBattingClass()
    {
        return battingClass;
    }

    public void setBattingClass(Class battingClass)
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
        return "BattingLesson{" + "teacherOnDuty=" + teacherOnDuty + ", battingClass=" + battingClass + ", lessonNumbers=" + lessonNumbers + ", date=" + date + '}';
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }
    
}
