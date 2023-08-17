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
    private boolean hasRegisterClass;

    public Teacher(String fullName, ArrayList<ExtraMural> extraMurals, ArrayList<Lesson> lessons, boolean hasRegisterclass)
    {
        this.fullName = fullName;
        this.extraMurals = extraMurals;
        this.lessons = lessons;
        this.hasRegisterClass = hasRegisterclass;
        classesTaught = new ArrayList<String>();
        
        for (Lesson lesson : lessons)
        {
            if (classesTaught.isEmpty() || ! classesTaught.contains( lesson.getLessonID() ) ) classesTaught.add(lesson.toString());
        }
    }

    @Override
    public String toString()
    {
        return "Teacher{" + "fullName=" + fullName + "\nEXTRAMURALS\n" + getExtraMuralsAsString() + "\nBattings\n" + getClassesAsString();
    }
    public boolean addClass(Lesson e)
    {
        return lessons.add(e);
    }

    public boolean hasLessonAt(int inLessonSlot)
    {
        for (Lesson lesson : lessons)
        {
            if (lesson.getSlotNr() == inLessonSlot) return true;
        }
        
        return false;
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

    public boolean addExtraMural(ExtraMural e)
    {
        return extraMurals.add(e);
    }

    public ArrayList<String> getClassesTaught()
    {
        return classesTaught;
    } 

    public boolean hasRegisterClass()
    {
        return hasRegisterClass;
    }
}
