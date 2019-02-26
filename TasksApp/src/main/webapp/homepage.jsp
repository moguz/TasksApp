<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html>
    <h:head>
    <title>Mert's Tasks App Homepage</title>
</h:head> 
<h:body>
    <h1>Welcome to Mert's Tasks App</h1>
    <form action="AddTask" method="POST">
        <div><h3>Add a task:</h3></div>
        <div><span>Task name:</span>
        <input type="text" name="task_name" value="" /></div>
        <div><span>Task due date:</span>
        <input type="text" name="task_due_date" value="" /></div>
        <div><input type="submit" value="Submit" /><div/>
    </form>
    <form action="RemoveTask" method="POST">
        <div><h3>Remove a task:</h3></div>
        <div><span>Task ID</span>
        <input type="text" name="task_id" value="" /></div>
        <div><input type="submit" value="Submit" /></div>
    </form>
    <h3>All tasks:</h3>
    <TABLE id="results" border = "1" width = "70%">
        <tr>
            <th>Task ID</th>
            <th>Task Name</th>
            <th>Due Date</th>
            <th>Task Creation Date</th>
        </tr>
        <%
            List<String[]> results = (List<String[]>)request.getAttribute("resultList");
            for(String[] rowData: results){
                out.println("<TR>");
                for(String data: rowData){
                   out.println("<TD>"+data+"</TD>");
                }
                out.println("</TR>");
            }
        %>
    </TABLE>
    
</h:body>
</html>