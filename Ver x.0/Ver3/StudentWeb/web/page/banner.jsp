<%-- 
    Document   : banner
    Created on : Dec 4, 2018, 9:18:49 AM
    Author     : admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

   <body>
        <div class="page-header">
            <div class="container">
                <div class="row">
                    <div class="col-md-10">
                        <ul class="page-header-breadcrumb">
                            <li><a href="index.html">Home</a></li>
                            <% 
                                String url[] = request.getRequestURL().toString().split("/");
                                String jsp = url[url.length-1];
                                if (jsp.equals("timeTable.jsp")){
                                %>
                                    <li>Thời Khóa Biểu</li>
                                <%} else if (jsp.equals("mark.jsp")){%>
                                    <li>Điểm</li>
                                <%} else if (jsp.equals("studentInfo.jsp")) {%>
                                    <li>Thông tin sinh viên</li>
                                <%} else if (jsp.equals("lichthi.jsp")) {%>
                                    <li>Lịch thi</li>
                                <%} else if (jsp.equals("login.jsp")) {%>
                                    <li>Đăng nhập</li>    
                                <%} else if (jsp.equals("registerEmail.jsp")) {%>
                                    <li>Đăng kí Email</li>    
                                <%}%>    
                        </ul>
                        <h1>
                            <%if (jsp.equals("timeTable.jsp")) {%>
                                Thời Khóa Biểu
                            <%} else if (jsp.equals("mark.jsp")) {%> 
                                Điểm
                            <%} else if (jsp.equals("studentInfo.jsp")) {%>
                                Thông tin sinh viên
                            <%} else if (jsp.equals("lichthi.jsp")) {%>
                                Lịch thi
                            <%} else if (jsp.equals("login.jsp")) {%>
                                Đăng nhập 
                            <%}else if (jsp.equals("registerEmail.jsp")) {%>
                                Đăng kí Email
                            <%}%> 
                        </h1>
                    </div>
                </div>
            </div>
        </div>
 
