<%-- 
    Document   : timeTable
    Created on : Dec 22, 2018, 10:28:19 PM
    Author     : Nam Anh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Thời Khóa Biểu</title>
        <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:700%7CNunito:300,600" rel="stylesheet"> 

        <!-- Bootstrap -->
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/page/css/bootstrap.min.css"/>

        <!-- Font Awesome Icon -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/page/css/font-awesome.min.css">

        <!-- Custom stlylesheet -->
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/page/css/style.css"/>

        <!-- Custom CSS -->
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/page/css/timeTable.css"/>
        
        <!-- Custom Js -->
        <script src="${pageContext.request.contextPath}/page/js/jquery.min.js"></script>
        <script src="${pageContext.request.contextPath}/page/js/bootstrap.min.js"></script>
        <script>var contextPath = "${pageContext.request.contextPath}";</script>
        <script src="${pageContext.request.contextPath}/page/js/timeTable.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"></jsp:include>
        <jsp:include page="banner.jsp"></jsp:include>
        <jsp:include page="contentTimeTable.jsp"></jsp:include>
        <jsp:include page="footer.jsp"></jsp:include>
        
        <script src="${pageContext.request.contextPath}/page/js/main.js"></script>
    </body>
</html>
