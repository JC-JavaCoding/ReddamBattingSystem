/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

/**
 *
 * @author janch
 */
public class ExtraMural
{
    private String extraMuralName;
    private double weeklyTime;

    public ExtraMural(String extraMuralName, double weeklyTime)
    {
        this.extraMuralName = extraMuralName;
        this.weeklyTime = weeklyTime;
    }

    @Override
    public String toString()
    {
        return "ExtraMural{" + "extraMuralName=" + extraMuralName + ", weeklyTime=" + weeklyTime + '}';
    }
    
}
