/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Home;

import dal.userDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;

/**
 *
 * @author 
 */
@WebServlet(name = "ForgotServlet", urlPatterns = {"/fpass"})
public class ForgotServlet extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ForgotServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ForgotServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        userDAO dao = new userDAO();
        String email = request.getParameter("email");
        HttpSession session = request.getSession();
        User a = dao.checkAcc(email);
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        System.out.println(gRecaptchaResponse);
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        if (!verify) {
            request.setAttribute("Recaptcha", "Vui lòng xác nhận mã captcha");
            request.getRequestDispatcher("forgotpass.jsp").forward(request, response);
            return;
        }
        if (a != null) {
            SendEmail sm = new SendEmail();
            String code = sm.getRandom();
            UserC userc = new UserC(code, email);
            boolean test = sm.sendEmail2(userc);
            if (test && userc != null) {
                User ac = new User(a.getUser_id(), a.getUser_name(), email, userc.getCode(), a.getIsAdmin(), a.getDateOfBirth(), a.getAddress(), a.getPhoneNumber(),a.isBanned(),a.getAdminReason(),a.getIsStoreStaff());
                dao.change(ac);
                session.setAttribute("user", ac);
                response.sendRedirect("login.jsp");
            }

        } else {
            String msg = "Email không tồn tại!";
            session.setAttribute("msg", msg);
            response.sendRedirect("forgotpass.jsp");
        }
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
