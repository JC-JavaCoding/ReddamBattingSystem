/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;

import Managers.BattingModel;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author janch
 */
public class Teacher
{
    private String fullName;
    private int numFrees, numBattings;
    private ArrayList <ExtraMural> extraMurals;
    private ArrayList <Lesson> lessons;
    private ArrayList <String> classesTaught;
    private boolean hasRegisterClass;

    public Teacher(String fullName, ArrayList<ExtraMural> extraMurals, ArrayList<Lesson> lessons, boolean hasRegisterclass) throws SQLException
    {
        this.fullName = fullName;
        this.extraMurals = extraMurals;
        this.lessons = lessons;
        this.hasRegisterClass = hasRegisterclass;
        classesTaught = new ArrayList<String>();

        BattingModel.init();
        numBattings = BattingModel.instance.getNumBattings(fullName);
        numFrees = 0;
        
        for (Lesson lesson : lessons)//*********************************************************
        {
            if(classesTaught.isEmpty()) classesTaught.add(lesson.toString());
            else 
            {
                boolean lessonExists = false;
                for(String lessonInfo : classesTaught)
                {
                    if(lessonInfo.equals(lesson.toString()))
                    {
                        lessonExists = true;
                        break;
                    }
                }
                if(!lessonExists) 
                    classesTaught.add(lesson.toString());
            }
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

    public double getBattingWeight(int dayofWeek) throws SQLException
    {
        calculateNumFrees(dayofWeek);
        
        double battingWeight = (numFrees * 0.3) + (numBattings * 0.5) + getExtramuralHours() * 0.3 + (hasRegisterClass? 1: 0 )*0.1;
        
        numFrees --;
        return battingWeight;
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

    public Lesson getLessonAt(int dayOfWeek, int lessonNumber)
    {
        int inSlotNr = (dayOfWeek == 5?(dayOfWeek-1)*12: (dayOfWeek-1)*14 ) + lessonNumber;
        for (Lesson lesson : lessons)
        {
            if (lesson.getSlotNr() == inSlotNr)return lesson;
        }
        return null;
    }

    private double getExtramuralHours()
    {
        double emHours = 0;
        for (ExtraMural em : extraMurals)
        {
            emHours += em.getDuration();
        }
        return emHours;
    }
    
    public void setNumFrees(int numFrees)
    {
        this.numFrees = numFrees;
    }
    
    public int calculateNumFrees(int dayofWeek)
    {
        //get number of free lessons for the day
        int numFrees = 0;
        boolean firstLessonofDay = true;
        for(int i = 0; i < lessons.size(); i++)
        {
            Lesson lesson = lessons.get(i);
            if(lesson.getDayOfWeek() == dayofWeek)
            {
                if(firstLessonofDay) 
                {
                    numFrees += lesson.getLessonNumber();
                    firstLessonofDay = false;
                }
                else numFrees += (lesson.getLessonNumber() - lessons.get(i-1).getLessonNumber() - 1);

            }else if (lesson.getDayOfWeek() > dayofWeek) break;
        }
        return numFrees;
    }
}
