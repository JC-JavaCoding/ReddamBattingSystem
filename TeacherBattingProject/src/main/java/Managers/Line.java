/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

/**
 *
 * @author janch
 */
public class Line
{
    private int periodId, year;
    private String setCode, subjectCode, teacherCode;

    public Line(int periodId, int year, String setCode, String subjectCode, String teacherCode)
    {
        this.periodId = periodId;
        this.year = year;
        this.setCode = setCode;
        this.subjectCode = subjectCode;
        this.teacherCode = teacherCode;
    }

    public int getPeriodId()
    {
        return periodId;
    }

    public void setPeriodId(int periodId)
    {
        this.periodId = periodId;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public String getSetCode()
    {
        return setCode;
    }

    public void setSetCode(String setCode)
    {
        this.setCode = setCode;
    }

    public String getSubjectCode()
    {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode)
    {
        this.subjectCode = subjectCode;
    }

    public String getTeacherCode()
    {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode)
    {
        this.teacherCode = teacherCode;
    }
    
}
