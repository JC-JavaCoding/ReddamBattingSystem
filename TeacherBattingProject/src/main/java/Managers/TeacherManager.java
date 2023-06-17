/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author janch
 */
public class TeacherManager
{
    private static ArrayList<Line> csvArrList;
    public static void sortByAge()
    {
        for (int i = csvArrList.size()-1; i > 0; i--)
        {
            boolean sorted = false;
            for (int j = 1; j < i; j++)
            {
                if (csvArrList.get(i).getYear() < csvArrList.get(j).getYear()) 
                {
                    Line temp = csvArrList.get(i);
                    csvArrList.set(i, csvArrList.get(j));
                    csvArrList.set(j, temp);
                    
                }
            }
            if (sorted) break;
        }
    }

    private static ArrayList<Line> fileToArray() throws FileNotFoundException
    {
        csvArrList = new ArrayList<>();
        Scanner sc = new Scanner(new File("data/2023v3TimetableData.csv"));
        
        while (sc.hasNextLine())
        {
            int periodId = sc.nextInt();
            String setCode = sc.next();
            int year = sc.nextInt();
            String subjectCode = sc.next(), teacherCode = sc.next();
            csvArrList.add(new Line(periodId, year, setCode, subjectCode, teacherCode));
        }
        return csvArrList;
    }
    private static String getVals() throws FileNotFoundException
    {
        String output = "";
        
        
        
        return output;
    }
}
