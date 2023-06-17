/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import java.util.ArrayList;

/**
 *
 * @author janch
 */
public class Teacher
{
    private String fullName, classID;
    private ArrayList <ExtraMural> extraMurals;
    private ArrayList <BattingLesson> battings;
    private ArrayList <Class> classesTaught;
    private TimeTable tt;

    public Teacher(String fullName, String classID, ArrayList<ExtraMural> extraMurals, ArrayList<BattingLesson> battings, ArrayList<Class> classesTaught, TimeTable tt)
    {
        this.fullName = fullName;
        this.classID = classID;
        this.extraMurals = extraMurals;
        this.battings = battings;
        this.classesTaught = classesTaught;
        this.tt = tt;
    }

    @Override
    public String toString()
    {
        return "Teacher{" + "fullName=" + fullName + ", classID=" + classID + "\nEXTRAMURALS\n" + getExtraMuralsAsString() + "\nBATTINGS:\n" + getBattingsAsString() + "\nBattings\n" + getClassesAsString() + ",\n tt=" + tt + '}';
    }
    public boolean addClass(Class e)
    {
        return classesTaught.add(e);
    }

    public boolean addBattingLesson(BattingLesson e)
    {
        return battings.add(e);
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getClassID()
    {
        return classID;
    }

    public void setClassID(String classID)
    {
        this.classID = classID;
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

    public ArrayList<BattingLesson> getBattingsAsArrayList()
    {
        return battings;
    }
    private String getBattingsAsString()
    {
        String battingsStr = "";
        for (BattingLesson b : battings)
        {
            battingsStr += b.toString() + ",\t";
        }
        
        return battingsStr;
    }
    public ArrayList<Class> getClassesTaughtAsArrayList()
    {
        return classesTaught;
    }
    private String getClassesAsString()
    {
        String classesTaughtStr = "";
        for (Classes.Class currClass : classesTaught)
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
    
    
}
