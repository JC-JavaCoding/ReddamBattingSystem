/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;

import java.util.ArrayList;

/**
 *
 * @author janch
 */
public class Teacher
{
    private String fullName;
    private ArrayList <ExtraMural> extraMurals;
    private ArrayList <Lesson> lessons;
    private ArrayList <String> classesTaught;
    private TimeTable tt;

    public Teacher(String fullName, ArrayList<ExtraMural> extraMurals, ArrayList<Lesson> lessons)
    {
        this.fullName = fullName;
        this.extraMurals = extraMurals;
        this.lessons = lessons;
        
        for (Lesson lesson : lessons)
        {
            if (! classesTaught.contains( lesson.getLessonID() ) ) classesTaught.add(lesson.getLessonID());
        }
    }

    @Override
    public String toString()
    {
        return "Teacher{" + "fullName=" + fullName + "\nEXTRAMURALS\n" + getExtraMuralsAsString() + "\nBattings\n" + getClassesAsString() + "\n tt=" + tt + '}';
    }
    public boolean addClass(Lesson e)
    {
        return lessons.add(e);
    }


    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public ArrayList<ExtraMural> getExtraMuralsAsArrayList()
    {
        return extraMurals;
    }
    private String getExtraMuralsAsString()
    {
        String extraMuralsStr = "";
        for (ExtraMural e : extraMurals)
        {
            extraMuralsStr += e.toString() + ",\t";
        }
        
        return extraMuralsStr;
    }

    public ArrayList<Lesson> getLessonsArrayList()
    {
        return lessons;
    }
    private String getClassesAsString()
    {
        String classesTaughtStr = "";
        for (DataTypes.Lesson currClass : lessons)
        {
            classesTaughtStr += currClass.toString() + ",\t";
        }
        
        return classesTaughtStr;
    }

    public TimeTable getTt()
    {
        return tt;
    }

    public boolean addExtraMural(ExtraMural e)
    {
        return extraMurals.add(e);
    }

    public ArrayList<String> getClassesTaught()
    {
        return classesTaught;
    } 
}
