/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Home;

import dal.reportDAO;
import dal.userDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.net.HttpURLConnection;
import java.net.URL;

public class Contact extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    String action = request.getParameter("action");
    if(action != null) {
        HttpSession session = request.getSession();
        userDAO cdao = new userDAO();
        reportDAO dao = new reportDAO();
        String user_id = request.getParameter("user_id");
        String user_email = request.getParameter("user_email");
        String subject_report = request.getParameter("subject_report");
        String content_report = request.getParameter("content_report");
        dao.InsertReport(user_id, content_report, subject_report, user_email);
        session.setAttribute("messSuccess", "Gửi phản hồi thành công");
        request.setAttribute("MessSuccess", "Gửi phản hồi thành công");
    }
        request.getRequestDispatcher("contact.jsp").forward(request, response);

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
//        String email = request.getParameter("Email");
//        String subject = request.getParameter("Subject");
//        String message = request.getParameter("Message");
//
//        // Construct the URL of your Google Apps Script web app endpoint
//        String scriptUrl = "https://script.google.com/macros/s/AKfycby2HoMCJyTeclcs-4VlInS-NG_hs161qiMTiuzUOpNZp_YRsmk/exec";
//        String urlParameters = "Email=" + email + "&Subject=" + subject + "&Message=" + message;
//
//        // Send a POST request to the Google Apps Script web app endpoint
//        URL url = new URL(scriptUrl + "?" + urlParameters);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//
//        // Read the response
//        int responseCode = connection.getResponseCode();
//
//        // Send response back to the client
//        PrintWriter out = response.getWriter();
//        if (responseCode == HttpURLConnection.HTTP_OK) {
//            out.println("Success! Your message has been sent.");
//        } else {
//            out.println("Failed to send message. Please try again later.");
//        }
//        out.close();  
//        
//        
        
        
        processRequest(request, response);
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
