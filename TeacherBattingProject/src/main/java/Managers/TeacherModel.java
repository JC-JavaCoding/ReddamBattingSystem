/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Managers;

import DataTypes.ExtraMural;
import DataTypes.Lesson;
import DataTypes.Teacher;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.table.TableModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

/**
 *
 * @author janch
 */
public class TeacherModel implements ListModel, TreeModel
{
    private ArrayList<Teacher> teachers = new ArrayList<>();
    private int selectedTeacherIndex, treeSelectionInt;

    public void setSelectedTeacherIndex(int selectedTeacherIndex)
    {
        this.selectedTeacherIndex = selectedTeacherIndex;
    }

    public void setTreeSelectionInt(int treeSelectionInt)
    {
        this.treeSelectionInt = treeSelectionInt;
    }
    
    public TeacherModel() throws SQLException 
    {
        selectedTeacherIndex = 0;
        treeSelectionInt = 0;
        
        //insert all the teachers into an arrayList from the DB
        ResultSet rs = DatabaseManager.instance.query("Select * From tblTeachers");//need to select multiple tables to get all relevant information for teacher  
        
        while (rs.next())
        {
            int curTeacherID = rs.getInt(1);
            String teacherName = rs.getString(2);
            ArrayList <ExtraMural> extramurals = new ArrayList<>();
            ArrayList <Lesson> lessons = new ArrayList<>();
            
            
            //add the extramurals into an arraylist
            ResultSet extramuralRS = DatabaseManager.instance.query("Select `Name`, `Duration`, `DayOfWeek` from tblTeacherExtraMurals, tblExtramural WHERE tblTeacherExtraMurals.TeacherID = "+ curTeacherID+ " AND "
                    + "tblTeacherExtraMurals.ExtraMuralID = tblExtramural.ExtramuralID");
            while(extramuralRS.next())
            {
                extramurals.add(
                        new ExtraMural(
                                extramuralRS.getString(1), 
                                extramuralRS.getDouble(2), 
                                extramuralRS.getInt(3)
                        )
                );
                
                //add lessons to the arraylist
                ResultSet lessonRS =  DatabaseManager.instance.query("Select `LessonID`, `Subject`, `Grade`, `SlotID` from tblLessons, tblSubjects"
                                                                                                        + " WHERE tblLessons.SubjectID = tblSubjects.SubjectID"
                                                                                                        + " AND TeacherID = "+ curTeacherID +";");
                while (lessonRS.next())
                {
                    lessons.add(
                            new Lesson(
                                    lessonRS.getString(1), 
                                    lessonRS.getString(2), 
                                    lessonRS.getInt(3), 
                                    lessonRS.getInt(4)
                            )
                    );
                }
            }
            
            teachers.add(new Teacher(teacherName, extramurals, lessons));
        }
    }
    
// end of Table model implementation
    @Override
    public Object getRoot()
    {
        return teachers.get(selectedTeacherIndex).getFullName();
    }

    @Override
    public Object getChild(Object parent, int index)
    {
        Teacher temp = teachers.get(selectedTeacherIndex);
        return switch (treeSelectionInt)
        {
            case 0 -> temp.getExtraMuralsAsArrayList().size();
            case 1 -> temp.getClassesTaught().size();
            default -> -1;
        };
    }

    @Override
    public int getChildCount(Object parent)
    {
        Teacher temp = teachers.get(selectedTeacherIndex);
        return switch (treeSelectionInt)
        {
            case 0 -> temp.getExtraMuralsAsArrayList().size();
            case 1 -> temp.getClassesTaught().size();
            default -> -1;
        };
    }

    @Override
    public boolean isLeaf(Object node)
    {
        return (node instanceof ExtraMural || node instanceof String);
    }

    @Override
    public void valueForPathChanged(TreePath path, Object newValue)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getIndexOfChild(Object parent, Object child)
    {
        if (child instanceof ExtraMural) 
        {
            return teachers.get(
                    teachers.indexOf(parent)
            )
                    .getExtraMuralsAsArrayList()
                    .indexOf(child);
        }
        return teachers.get(
                teachers.indexOf(parent)
        )
                .getClassesTaught()
                .indexOf(child);
    }

    @Override
    public void addTreeModelListener(TreeModelListener l)
    {
        new TreeModelListener()
        {
            @Override
            public void treeNodesChanged(TreeModelEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void treeStructureChanged(TreeModelEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    @Override
    public void removeTreeModelListener(TreeModelListener l)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getSize()
    {
        return teachers.size();
    }

   @Override
    public Object getElementAt(int index)
    {
        return teachers.get(index).getFullName();
    }

    @Override
    public void addListDataListener(ListDataListener l)
    {
        new ListDataListener()
        {
            @Override
            public void intervalAdded(ListDataEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void intervalRemoved(ListDataEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }

            @Override
            public void contentsChanged(ListDataEvent e)
            {
                throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
            }
        };
    }

    @Override
    public void removeListDataListener(ListDataListener l)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
