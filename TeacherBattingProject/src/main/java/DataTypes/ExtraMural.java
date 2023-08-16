/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataTypes;



/**
 *
 * @author janch
 */
public class ExtraMural
{
    private String extraMuralName;
    private double duration;
    private int weekday;

    public ExtraMural(String extraMuralName, double duration, int dayOfWeek)
    {
        this.extraMuralName = extraMuralName;
        this.duration = duration;
        this.weekday = dayOfWeek;
    }

    public String getExtraMuralName()
    {
        return extraMuralName;
    }

    public void setExtraMuralName(String extraMuralName)
    {
        this.extraMuralName = extraMuralName;
    }

    public double getDuration()
    {
        return duration;
    }

    public void setDuration(double duration)
    {
        this.duration = duration;
    }

    public int getWeekday()
    {
        return weekday;
    }

    public void setWeekday(int weekday)
    {
        this.weekday = weekday;
    }

    @Override
    public String toString()
    {
        return extraMuralName + " | " + weekday +" | "+ duration;
    }
    
}
