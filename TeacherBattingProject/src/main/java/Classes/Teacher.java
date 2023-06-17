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

    public boolean add(Class e)
    {
        return classesTaught.add(e);
    }

    public boolean add(BattingLesson e)
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

    public ArrayList<ExtraMural> getExtraMurals()
    {
        return extraMurals;
    }

    public ArrayList<BattingLesson> getBattings()
    {
        return battings;
    }

    public ArrayList<Class> getClassesTaught()
    {
        return classesTaught;
    }

    public TimeTable getTt()
    {
        return tt;
    }

    public boolean add(ExtraMural e)
    {
        return extraMurals.add(e);
    }
    
    
}
