/*
 * Created on 29-giu-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.ips.mail;
 
/**
 * @author pasquale
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.sql.*;
import java.util.*;
import javax.activation.*;
import javax.mail.*;
import javax.mail.util.*;
import javax.mail.Message;
import javax.mail.event.*;
import javax.mail.internet.*;
import javax.mail.Session;
import java.io.*;


import com.ips.log.LogBook;

public class MailManager {

    private String mailHost;  // host service di posta
    private String Destination;  // indirizzo di posta elettronica del destinatario
    private String Source;    // indirizzo di posta elettronica del mittente
    private String Subject; // oggetto del messaggio di posta
    private String Content;      // contenuto del messaggio
    public String WebMaster;
    private String MsgCC;
    public String[] MailServiceAdministratorsList = null;
    private String MailStyle;
    private String HtmlHeader;
    private String HtmlFooter;
    private java.util.Vector BinaryAttachments;
    private final int version = 10;
    public MailManager()
    {
        HtmlHeader = new String("<html><head><style>{style}</style></head><body>");
        HtmlFooter = new String("</body></html>");
        MailStyle = new String("");
        BinaryAttachments = new java.util.Vector();
    }


    public void readStyle(String FileNameParam) throws IOException
    {

        try
        {
                BufferedReader myBuf = new BufferedReader(new FileReader(FileNameParam));
                String myLine;
                while((myLine = myBuf.readLine())!= null)
                    this.MailStyle += myLine;
                myBuf.close();

        }
        catch(IOException ioe)
        {
            throw  ioe;
        }

    }



    public void setProperties(ResultSet SYSTEM_CONFIG) throws SQLException {
        try {

            mailHost = SYSTEM_CONFIG.getString("MailServer");
            Source = SYSTEM_CONFIG.getString("SenderMailNameProgram");

            WebMaster = SYSTEM_CONFIG.getString("MailWebMaster");

            /* Legge la lista amministratori dal file di proprieta */
            StringTokenizer myStrTok = getTokenizer(SYSTEM_CONFIG.getString("CarbonCopyMailRecipients"));
            StringTokenizer myStrTokName = getTokenizer(SYSTEM_CONFIG.getString("NameServiceAdministratorsList"));

            /* crea un array per la lista degli amministratori */
            MailServiceAdministratorsList = new String[myStrTok.countTokens()];

            int i = 0;
            while (myStrTok.hasMoreTokens()) {
                MailServiceAdministratorsList[i++] = myStrTok.nextToken();
            }




        } catch (SQLException sqle) {
            throw sqle;

        }
    }

    public void setMsgCC(String MsgCCParam) {
        this.MsgCC = MsgCCParam;

    }

    public void setMailHost(String par1) {
        mailHost = par1;
    }

    public void setDestination(String par1) {
        Destination = par1;
    }

    public void setSource(String par1) {
        Source = par1;
    }

    public void setSubject(String par1) {
        Subject = par1;
    }

    public void setContent(String par1) {
        Content = par1;
    }

    private java.util.StringTokenizer getTokenizer(String Property) {
        java.util.StringTokenizer Tokenizer = new java.util.StringTokenizer(Property, ";");
        return Tokenizer;
    }

    public boolean sendMessage()
    {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", mailHost);
        properties.put("mail.from", Source);
        Session session = Session.getInstance(properties, null);
        try {
            Message message = new MimeMessage(session);
            InternetAddress[] addressTO = null;
            if(this.Destination !=null && this.Destination.length() != 0)
            {
                StringTokenizer TOList = getTokenizer(this.Destination);
                addressTO = new InternetAddress[TOList.countTokens()];
                for (int i = 0; i < addressTO.length; i++) {
                    addressTO[i] = new InternetAddress(TOList.nextToken());
                    message.addRecipient(Message.RecipientType.TO, addressTO[i]);
                }

            }


            if (this.MsgCC != null && this.MsgCC.length() != 0) {
                StringTokenizer CCList = getTokenizer(this.MsgCC);
                InternetAddress[] addressCC = new InternetAddress[CCList.countTokens()];
                for (int i = 0; i < addressCC.length; i++) {
                    addressCC[i] = new InternetAddress(CCList.nextToken());
                    message.addRecipient(Message.RecipientType.CC, addressCC[i]);
                }
            }
            message.setFrom(new InternetAddress(Source));
            message.setSubject(Subject);
            Content = getHtmlHeader() + Content+ getHtmlFooter();
            Content = Content.replaceAll("\\{style\\}", MailStyle);


            BodyPart messageBodyPart = new MimeBodyPart();

            messageBodyPart.setText(Content);
            messageBodyPart.setContent(Content,"text/html");


            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            Iterator it = this.BinaryAttachments.iterator ();
              while (it.hasNext ()) {
                ByteArrayDataSource bads  = (ByteArrayDataSource) it.next ();

                messageBodyPart = new MimeBodyPart();
                //messageBodyPart.setDataHandler(new DataHandler(new FileDataSource("c:/test/tom.jpg")));
                 messageBodyPart.setDataHandler(new DataHandler(bads));
                 messageBodyPart.setFileName(bads.getName());

                multipart.addBodyPart(messageBodyPart);


            }
            message.setContent(multipart);

            Transport transport = session.getTransport(addressTO[0]);
            transport.addConnectionListener(new ConnectionHandler());
            transport.addTransportListener(new TransportHandler());
            transport.connect();
            transport.send(message);
            return true;
        } catch (Exception e) {
                e.printStackTrace();
                return false;

        }
    }



    public void addBinaryAttachment(ByteArrayDataSource badsParam)
    {
        this.BinaryAttachments.add(badsParam);
    }

    /**
     * @return the MailStyle
     */
    public String getMailStyle() {
        return MailStyle;
    }

    /**
     * @param MailStyle the MailStyle to set
     */
    public void setMailStyle(String MailStyle) {
        this.MailStyle = MailStyle;
    }

    /**
     * @return the HtmlHeader
     */
    public String getHtmlHeader() {
        return HtmlHeader;
    }

    /**
     * @param HtmlHeader the HtmlHeader to set
     */
    public void setHtmlHeader(String HtmlHeader) {
        this.HtmlHeader = HtmlHeader;
    }

    /**
     * @return the HtmlFooter
     */
    public String getHtmlFooter() {
        return HtmlFooter;
    }

    /**
     * @param HtmlFooter the HtmlFooter to set
     */
    public void setHtmlFooter(String HtmlFooter) {
        this.HtmlFooter = HtmlFooter;
    }

    class ConnectionHandler extends ConnectionAdapter {

        public void opened(ConnectionEvent e) {
            LogBook.record("Mailing to: " + Destination);
            LogBook.record("Connection opened.");
        }

        public void disconnected(ConnectionEvent e) {
            LogBook.record("Connection disconnected.");
        }

        public void closed(ConnectionEvent e) {
            LogBook.record("Connection closed.");
        }
    }

    class TransportHandler extends TransportAdapter {

        public void messageDelivered(TransportEvent e) {
            LogBook.record("Message delivered.");
        }

        public void messageNotDelivered(TransportEvent e) {
            LogBook.record("Message NOT delivered.");
        }

        public void messagePartiallyDelivered(TransportEvent e) {
            LogBook.record("Message partially delivered.");
        }
    }
}
