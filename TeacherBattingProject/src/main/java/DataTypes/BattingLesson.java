/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;

import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author janch
 */
public class BattingLesson
{
    private Teacher selectedTeacher;
    private Lesson battingClass;
    private LocalDate date;
    private ArrayList<Teacher> availableTeachers;

    public BattingLesson(Teacher teacherOnDutyIN, Lesson battingClass, LocalDate date)
    {
        this.selectedTeacher = teacherOnDutyIN;
        this.battingClass = battingClass;
        this.date = date;
        
        availableTeachers = new ArrayList<>();
    }

    public Teacher getTeacherOnDuty()
    {
        return selectedTeacher;
    }

    public void setTeacherOnDuty(Teacher teacherOnDuty)
    {
        this.selectedTeacher = teacherOnDuty;
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
        return "BattingLesson{" + "teacherOnDuty=" + selectedTeacher + ", battingClass=" + battingClass + ",date=" + date + '}';
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }
    
    public void addAvailableTeacher(Teacher teacherIN)
    {
        availableTeachers.add(teacherIN);
    }
    
    public void setSelectedTeacher(String teacherName)
    {
        boolean teacherFound = false;
        for (Teacher teacher: availableTeachers)
        {
            if (teacher.getFullName().equals(teacherName)) 
            {
                selectedTeacher = teacher;
                teacherFound = true;
            }
        }
        if (!teacherFound) JOptionPane.showMessageDialog(null, "Teacher Not Found", "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
