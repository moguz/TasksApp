package com.meoguz.tasksapp;



import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RetrieveTasks
 */
@WebServlet("/RetrieveTasks")
public class RetrieveTasks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RetrieveTasks() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();
        Connection conn = null;
        Statement stmt = null;
        try {
            Class.forName(Configuration.JDBC_DRIVER);
            conn = DriverManager.getConnection(Configuration.DB_URL, Configuration.USER, Configuration.PASS);
            stmt = conn.createStatement();
            String sql = "SELECT * from Tasks";
            ResultSet rs = stmt.executeQuery(sql);
            List<String[]> resultList = new ArrayList<String[]>();
            while(rs.next()){
                String[] row = {rs.getString("taskId"),rs.getString("taskName"),rs.getString("dueDate"),rs.getString("creationDate")};
                resultList.add(row);
            }
            RequestDispatcher dispatcher = request.getRequestDispatcher("/homepage.jsp");
            request.setAttribute("resultList", resultList);
            dispatcher.forward(request,  response);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RetrieveTasks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RetrieveTasks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
