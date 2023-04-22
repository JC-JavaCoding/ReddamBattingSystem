/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package testing;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author janch
 */
public class Email_JavaMail
{
    private final static String passkey = "kjhddpzfnogjscjz";
    private static Session session;
    public static void main(String[] args)
    {
        
        System.out.println();
        // Recipient's emal ID needs to be mentioned
        String to = "janchristiaan.jacobs@reddam.house" ;
        String from = "janchristiaan.jacobs@gmail.com" ;//securesally
        
        // Get system properties
        Properties properties = new Properties();
        // Setup mail server
        String host = "smtp.gmail.com";
        properties.put( "mail.smtp.auth" ,"true");
        properties.put( "mail.smtp.starttls.enable" ,"true");
        properties.put( "mail.smtp.host" ,host);
        properties.put("mail.smtp.port" , "587");
        
        //sending from gmail:
        // Get the default Session object.
        session = Session.getInstance(properties, new javax.mail.Authenticator()
            {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication()
                {
                    return new javax.mail.PasswordAuthentication(from, passkey);
                }
            }
        );
        
        //mailMessage(to, from, "This is the Subject Line!", "This is actual message");
        mailAttachment(to, from, "This is the Subject Line!", "This is actual message", "C:/Users/janch/OneDrive/Pictures/Artworks & their annotations 1.png");
    }
    
    public static void mailAttachment(String to, String from, String header, String text, String filepath)
    {
        try 
        {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message. setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(header);
            // Now set the actual message
            message.setText(text);
            
            //Attachment:
            
            MimeMultipart mimeMultipart = new MimeMultipart();
            // text
            MimeBodyPart textMime = new MimeBodyPart();
            // file
            MimeBodyPart fileMime = new MimeBodyPart();
            
            try {
                textMime.setText(text) ;
                File file = new File(filepath);
                fileMime.attachFile(file);
                
                //set the layout of the mimebodyparts in the mimemultipart
                mimeMultipart.addBodyPart(textMime);
                mimeMultipart.addBodyPart(fileMime);
                
                message.setContent(mimeMultipart);
                // Send message
                Transport.send(message) ;

                System. out. println("Sent message successfully...." );
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }
        catch (MessagingException mex) 
        {
            throw new RuntimeException(mex);
        }
    }
    
    private static void mailMessage(String to, String from, String header, String text)
    {
        try 
        {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);

            // Set From: header field of the header.
            message. setFrom(new InternetAddress(from));
            // Set To: header field of the header.
            message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            // Set Subject: header field
            message.setSubject(header);
            // Now set the actual message
            message.setText(text);
            
            // Send message
            Transport.send(message) ;
            
            System. out. println("Sent message successfully...." );
        }
        catch (MessagingException mex) 
        {
            throw new RuntimeException(mex);
        }
    }
}