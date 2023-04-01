/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package testing;

import java.awt.Dimension;
import java.awt.Toolkit;

/**
 *
 * @author janch
 */
public class ResizingForm extends javax.swing.JFrame
{

    /**
     * Creates new form ResizingForm
     */
    public ResizingForm()
    {
        initComponents();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 10), new java.awt.Dimension(0, 10), new java.awt.Dimension(32767, 10));
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        rightArrowButton = new javax.swing.JButton();
        leftArrowButton = new javax.swing.JButton();
        displaySizeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(filler1, new org.netbeans.lib.awtextra.AbsoluteConstraints(126, 6, -1, 20));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Resizing Test");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jTextArea1.setText("Lets resize all this");
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, 230, 110));

        rightArrowButton.setText(">");
        rightArrowButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rightArrowButtonActionPerformed(evt);
            }
        });
        getContentPane().add(rightArrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 210, 50, 40));

        leftArrowButton.setText("<");
        leftArrowButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                leftArrowButtonActionPerformed(evt);
            }
        });
        getContentPane().add(leftArrowButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 213, 50, 40));

        displaySizeButton.setText("Display Size");
        displaySizeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                displaySizeButtonActionPerformed(evt);
            }
        });
        getContentPane().add(displaySizeButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 220, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void leftArrowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_leftArrowButtonActionPerformed
    {//GEN-HEADEREND:event_leftArrowButtonActionPerformed
        // Decrease the size of the form
        setSize(getWidth() - 25, getHeight() - 25);
    }//GEN-LAST:event_leftArrowButtonActionPerformed

    private void rightArrowButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rightArrowButtonActionPerformed
    {//GEN-HEADEREND:event_rightArrowButtonActionPerformed
        // Increase the size of the form
        setSize(getWidth() + 25, getHeight() + 25);
    }//GEN-LAST:event_rightArrowButtonActionPerformed

    private void displaySizeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_displaySizeButtonActionPerformed
    {//GEN-HEADEREND:event_displaySizeButtonActionPerformed
        // output the size of the form and the size of the system screen
        setLocationRelativeTo(null);
        Dimension formSize = getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        jTextArea1.setText("Form Height:\t"+ formSize.height +"\nForm Width:\t"+ formSize.width +"\nScreen Height:\t"+ screenSize.height +"\nScreen Width:\t"+ screenSize.width);
        
        //set to size of screen
        setLocation(0, 0);
        setSize(screenSize);
        formSize = screenSize;
        jTextArea1.setText("Form Height:\t"+ formSize.height +"\nForm Width:\t"+ formSize.width +"\nScreen Height:\t"+ screenSize.height +"\nScreen Width:\t"+ screenSize.width);
        
        //set component sizes
        packToRatio(formSize);
    }//GEN-LAST:event_displaySizeButtonActionPerformed

    private void packToRatio(Dimension formDimension)
    {
         int formWidth = formDimension.width;
        int formHeight = formDimension.height;
        //method to set the layout of all the components in a certain raitio:
        /*
        ***Placement***
        TextField:          X=(form-W)/2;                                   Y=Form/15 
        TextArea:           X=1/4 Form;                                     Y=1/5 Form
        ArrowLeft:          X=1/4 Form;                                     Y=TextArea+TextAreaH+Form/15
        ArrowRight:       X=Form - (1/4 Form+ArrowW);         Y=TextArea+TextAreaH+Form/15
        DisplayButton:  X=TextAX+(TextAW-DBW)/2  Y=TextArea+TextAreaH+Form/15        
        */
        //set placements
        jLabel1.setLocation( ((formWidth - jLabel1.getWidth()) / 2), (formHeight/15));
        jTextArea1.setLocation( ((formWidth) / 4), (formHeight/5));
        leftArrowButton.setLocation( (formWidth/4), (jTextArea1.getY() + jTextArea1.getHeight() + (formHeight/15)) );
        rightArrowButton.setLocation( (formWidth - ((formWidth + rightArrowButton.getWidth())/4)), (jTextArea1.getY() + jTextArea1.getHeight() + (formHeight/15)) );
        displaySizeButton.setLocation(jTextArea1.getX() + ((jTextArea1.getWidth() - displaySizeButton.getWidth())/2), (jTextArea1.getY() + jTextArea1.getHeight() + (formHeight/15)));
        getContentPane().validate();
        //doLayout();
        
        /*
        ***Size***
        TextField:          W=preferred;      H=preferred 
        TextArea:           W=3/5 Form;     H=1/2 Form
        ArrowButtons:   W=1/4 TextArea  H=preferred
        DisplayButton:  W=2/5 TextArea  H=preferred
        */
        //Set sizes
        jTextArea1.append("\nJTextArea Width (O) = "+ jTextArea1.getWidth() +", Height (O) = "+ jTextArea1.getHeight() +",\tWidth = "+ (3*formWidth/ 5) +",\tHeight = "+(formHeight/2));
        jTextArea1.append("\nLeftArrow Width = "+ (jTextArea1.getWidth() / 4)+ ",\t Height = "+ (int) leftArrowButton.getPreferredSize().getHeight());
        jTextArea1.append("\nRightArrow Width = "+ (jTextArea1.getWidth() / 4)+ ",\t Height = "+ (int) rightArrowButton.getPreferredSize().getHeight());
        jTextArea1.append("\nJTextArea Width = "+ (2*jTextArea1.getWidth() / 5) +",\tHeight = "+displaySizeButton.getHeight());
        
        jTextArea1.setPreferredSize(new Dimension( (3*formWidth / 5), (formHeight / 2) ));
        leftArrowButton.setPreferredSize(new Dimension( (jTextArea1.getWidth() / 4), (int) leftArrowButton.getPreferredSize().getHeight() ));
        rightArrowButton.setPreferredSize(new Dimension( (jTextArea1.getWidth() / 4), (int) rightArrowButton.getPreferredSize().getHeight() ));
        displaySizeButton.setPreferredSize( new Dimension( (2*jTextArea1.getWidth() / 5), displaySizeButton.getHeight() ));
        
        getContentPane().validate();
        setPreferredSize(formDimension);
        
        
        pack();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex)
        {
            java.util.logging.Logger.getLogger(ResizingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex)
        {
            java.util.logging.Logger.getLogger(ResizingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex)
        {
            java.util.logging.Logger.getLogger(ResizingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(ResizingForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                new ResizingForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton displaySizeButton;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JButton leftArrowButton;
    private javax.swing.JButton rightArrowButton;
    // End of variables declaration//GEN-END:variables
}
