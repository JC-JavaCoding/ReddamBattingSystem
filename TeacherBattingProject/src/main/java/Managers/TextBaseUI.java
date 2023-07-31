/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.BattingLesson;
import DataTypes.TimeTable;
import DataTypes.Teacher;
import DataTypes.ExtraMural;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author janch
 */
public class TextBaseUI
{
    public static ArrayList<ExtraMural> extraMurals = new ArrayList<>(); 
    public static ArrayList<BattingLesson> battings = new ArrayList<>(); 
    public static ArrayList<DataTypes.Lesson> classes = new ArrayList<>(); 
    public static ArrayList <Teacher> teachers = new ArrayList<>();
    
    public static void main(String[] args)
    {
        //instantiate arrayLists:
        loadArrayLists();
        
        for (int i = 0; i < 10; i++)
        {
            //teachers.add(new Teacher("Bob"+ i, "R"+ i, extraMurals, battings, classes, new TimeTable()));
        }
        for (Teacher t : teachers)
        {
            System.out.println(t.toString());
        }
    }

    private static void loadArrayLists()
    {
        for (int i = 0; i < 5; i++)
        {
//            extraMurals.add(new ExtraMural("ExtraMural"+ i, (int)(Math.random() * 9 + 1)));
//            battings.add(new BattingLesson(null, 
//                    new DataTypes.Lesson("Bobb"+i,
//                            "Subject" +i,
//                            "Grade"+ i), 
//                    getRandomLessons(), 
//                    LocalDate.now()));
//            classes.add(new DataTypes.Lesson("Bob"+i,
//                            "Subject" +i,
//                            "Grade"+ i));
        }
    }

    private static ArrayList<Object> getRandomLessons()
    {
        ArrayList<Object> randLessons = new ArrayList<>();
        
        for (int i = 0; i < 3; i++)
        {
            randLessons.add("L"+ (int)(Math.random() * 8 + 1));
            
        }
        
        return randLessons;
    }
}
