<%@page import="java.io.File"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Uploaded Files</title></head>
<body>
  <h1>Uploaded Files</h1>
  <table border="1">
    <tr><th>File Name</th><th>File Size</th><th>Action</th></tr>
    <%
      File[] files = (File[]) request.getAttribute("files");
      if (files != null) {
        for (File f : files) {
    %>
    <tr>
      <td><%= f.getName() %></td>
      <td><%= f.length() %></td>
      <td><a href="downloadServlet?fileName=<%= f.getName() %>">Download</a></td>
    </tr>
    <%
        }
      }
    %>
  </table>
</body>
</html>
