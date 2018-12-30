package Servlet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import DAO.LichThiDAO;
import controller.LoginControl;
import DAO.StudentDAO;
import DAO.TimeTableDAO;
import controller.SendMail;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Student;
import model.TimeTable;

/**
 *
 * @author Administrator
 */
public class LoginServlet extends HttpServlet {
    StudentDAO studentDAO = new StudentDAO();
    LichThiDAO lichThiDao = new LichThiDAO();
    TimeTableDAO timeTableDao = new TimeTableDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Để hiển thị Tiếng Việt
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        //-----------
        PrintWriter out1 = response.getWriter();
   
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String url = "";
        String thongBaoLichThi = "";
        String thongBaoLichHoc = "";
        HttpSession session = request.getSession();
        
        try {
            String mainQLDT = "http://qldt.ptit.edu.vn/";
            String defaultQLDT = "http://qldt.ptit.edu.vn/default.aspx";
            
            
            LoginControl http = new LoginControl();
            CookieHandler.setDefault(new CookieManager());
            http.GetCookie(mainQLDT);
            String postParams = http.getFormParams(username, password);
            http.sendPost(defaultQLDT, postParams);
            System.out.println(username + " " + password);
            
            if(http.checkLogin(username)) {
                studentDAO.updatePassword(username, password);
                Student student = studentDAO.getStudent(username);
                String email = student.getEmail();
                url = "./page/index.jsp";
                session.setAttribute("student", student);
                
                // Kiểm tra Remember Me. Nếu có lưu cookie
                if (request.getParameter("remember") != null) {
                    String remember = request.getParameter("remember").trim();
                    System.out.println("remember : " + remember);
                    Cookie cUserName = new Cookie("cookuser", username);
                    Cookie cPassword = new Cookie("cookpass", password);
                    Cookie cRemember = new Cookie("cookrem", remember);
                    cUserName.setMaxAge(60 * 60 * 24 * 15);     //15 days
                    cPassword.setMaxAge(60 * 60 * 24 * 15);
                    cRemember.setMaxAge(60 * 60 * 24 * 15);
                    response.addCookie(cUserName);
                    response.addCookie(cPassword);
                    response.addCookie(cRemember);
                }
                //Báo lịch thi    
                thongBaoLichThi = lichThiDao.thongBaoLichThi(username);
                session.setAttribute("thongBao", thongBaoLichThi);
                if(!thongBaoLichThi.equalsIgnoreCase("Chúc bạn một ngày tốt lành!") && email != null) {
                    SendMail sm = new SendMail();
                    sm.sendMail(email, "Test's Today", thongBaoLichThi);
                    System.out.println("Đã gửi mail lịch thi!");
                }
                //Báo TKB
                ArrayList<TimeTable> timeTableList = timeTableDao.getTimeTable(username);
                ArrayList<TimeTable> timeTableToday = timeTableDao.getTimeTableInToday(timeTableList, 20172);
                int j = 1;
                for (int i = 0; i < timeTableToday.size(); i++) {
                    TimeTable tt = timeTableToday.get(i);
                    thongBaoLichHoc += "<p>" +j + ". " + tt.getTenMH() + " - " + tt.getGiangVien() + " - Tiết bắt đầu: " + tt.getTietBD() + " - phòng: " + tt.getPhong() + " - " + tt.getNha() + "</p>";
                    j ++;
                } 
                System.out.println(thongBaoLichHoc);
                if(thongBaoLichHoc.equalsIgnoreCase("") != true && email != null){ // Vì email trả về giá trị null nên không so sánh bằng hàm equals được
                    SendMail sm = new SendMail();
                    sm.sendMail(email, "Class's Today", thongBaoLichHoc);
                    System.out.println("Đã gửi mail lịch học!");
                }
            }
            // Trường hợp web trường đang trong thời gian đăng ký tín chỉ (Có capcha) 
            else if(studentDAO.checkLoginDuPhong(username,password) == true){
                Student student = studentDAO.getStudent(username);
                url = "./page/index.jsp";
                session.setAttribute("student", student);
                String email = student.getEmail();
                thongBaoLichThi = lichThiDao.thongBaoLichThi(username);
                session.setAttribute("thongBao", thongBaoLichThi);
                if(!thongBaoLichThi.equalsIgnoreCase("Chúc bạn một ngày tốt lành!") && email != null) {
                    SendMail sm = new SendMail();
                    sm.sendMail(email, "Test's Today", thongBaoLichThi);
                    System.out.println("Đã gửi mail lịch thi!");
                }
                
                ArrayList<TimeTable> timeTableList = timeTableDao.getTimeTable(username);
                ArrayList<TimeTable> timeTableToday = timeTableDao.getTimeTableInToday(timeTableList, 20172);
                int j = 1;
                for (int i = 0; i < timeTableToday.size(); i++) {
                    TimeTable tt = timeTableToday.get(i);
                    thongBaoLichHoc += "<p>" + j + ". " + tt.getTenMH() + " - " + tt.getGiangVien() + " - Tiết bắt đầu: " + tt.getTietBD() + " - phòng: " + tt.getPhong() + " - " + tt.getNha() + "</p>";
                    j++;
                }
                if(thongBaoLichHoc.equalsIgnoreCase("") != true && email != null){
                    SendMail sm = new SendMail();
                    sm.sendMail(email, "Class's Today", thongBaoLichHoc);
                    System.out.println("Đã gửi mail lịch học!");
                }
            }
            else{
                System.out.println("false");
                url = "./page/login.jsp";
                String errorMessage = "Tên đăng nhập hoặc mật khẩu không chính xác";
                request.setAttribute("errorMessage", errorMessage);
            }
            
            RequestDispatcher dispatcher = request.getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } catch (Exception ex) {
            out1.println("<h1> WRONG " + request.getContextPath() + "</h1>");
            out1.println("<h1> WRONG " + ex.toString() + "</h1>");
        }
        
        
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
