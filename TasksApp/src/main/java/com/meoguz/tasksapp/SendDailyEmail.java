package com.meoguz.tasksapp;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Servlet implementation class SendDailyEmail
 */
@WebServlet("/SendDailyEmail")
public class SendDailyEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SendDailyEmail() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	String sender = Configuration.SENDER_EMAIL;
    	String recipient = Configuration.RECIPIENT_EMAIL;
    	String subject = "Your "+new SimpleDateFormat("d MMM yyyy").format(new Date())+" daily tasks notification";
    	StringBuilder bodySB = new StringBuilder();
    	Connection conn = null;
        try {
            Class.forName(Configuration.JDBC_DRIVER);
            conn = DriverManager.getConnection(Configuration.DB_URL, Configuration.USER, Configuration.PASS);
            String sql = "SELECT * FROM Tasks WHERE dueDate <= str_to_date(?, '%Y-%m-%d')";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            preparedStatement.setString(1, today);
            ResultSet rs = preparedStatement.executeQuery();
            int taskNo = 1;
            while(rs.next()){
            	bodySB.append(""+taskNo+" - "+rs.getString("taskName")+" (Due Date:"+rs.getString("dueDate")+")\n");
            	taskNo++;
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetrieveTasks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RetrieveTasks.class.getName()).log(Level.SEVERE, null, ex);
        }
        String body = bodySB.toString();
        if(body.length()>0)
        	sendEmail(sender,recipient,subject,body);
    }
    
    protected void sendEmail(String sender, String recipient, String subject, String body) {
    	Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        try {
          Message msg = new MimeMessage(session);
          msg.setFrom(new InternetAddress(sender, "TasksApp Admin"));
          msg.addRecipient(Message.RecipientType.TO,
                           new InternetAddress(recipient, "Mr. User"));
          msg.setSubject(subject);
          msg.setText(body);
          Transport.send(msg);
        } catch (AddressException e) {
          // ...
        } catch (MessagingException e) {
          // ...
        } catch (UnsupportedEncodingException e) {
          // ...
        }
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
