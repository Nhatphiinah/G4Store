/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import Controller.Home.SendEmail;
import dal.userDAO;
import model.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomerManager extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        String page = "";
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            String action = request.getParameter("action");
            if (user.getIsAdmin().equalsIgnoreCase("true")) {
                if (action == null) {
                    userDAO dao = new userDAO();
                    List<User> user1 = dao.getUser();
                    request.setAttribute("user", user1);
                    page = "admin/customer.jsp";
                } else if (action.equals("update")) {
                    String user_id = request.getParameter("user_id");
                    String isAdmin = request.getParameter("permission");
                    String isStoreStaff = request.getParameter("storeStaffPermission");
                    String adminReason = request.getParameter("adminReason");
                    int id = Integer.parseInt(user_id);
                    userDAO dao = new userDAO();
                    dao.setAdmin(id, isAdmin, isStoreStaff, adminReason);
                    response.sendRedirect("customermanager");
                    return;
                } else if (action.equals("ban")) {
                    int banUserId = Integer.parseInt(request.getParameter("user_id"));
                    User currentUser = (User) session.getAttribute("user");

                    if (currentUser.getUser_id() == banUserId) {
                        request.setAttribute("error", "Bạn không thể tự ban chính mình!");
                        userDAO dao = new userDAO();
                        List<User> user1 = dao.getUser();
                        request.setAttribute("user", user1);
                        page = "admin/customer.jsp";
                    } else {
                        userDAO dao = new userDAO();
                        dao.banUser(banUserId);
                        response.sendRedirect("customermanager");
                        return;
                    }
                } else if (action.equals("unban")) {
                    String user_id = request.getParameter("user_id");
                    int id = Integer.parseInt(user_id);
                    userDAO dao = new userDAO();
                    dao.unbanUser(id);
                    response.sendRedirect("customermanager");
                    return;
                } else if (action.equals("insertStaff")) {
                    userDAO da = new userDAO();
                    String name = request.getParameter("name");
                    String email = request.getParameter("email");
                    String dob = request.getParameter("dob");
                    String phone = request.getParameter("phone");
                    String address = request.getParameter("address");
                    String defaultPass = "Abc123";
                    model.User a = da.checkAcc(email);
                    if (a == null) {
                        User u = new User(name, email, defaultPass, "FALSE", false, "TRUE");
                        u.setDateOfBirth(dob);
                        u.setPhoneNumber(phone);
                        u.setAddress(address);
                        da.addStaff(u);
                        SendEmail sm = new SendEmail();
                        sm.sendEmail3(email, "Staff Account", "Admin create a account for you. Your password is: " + defaultPass);
                        response.sendRedirect("customermanager");
                        return;
                    } else {
                        userDAO dao = new userDAO();
                        List<User> user1 = dao.getUser();
                        request.setAttribute("error", "Email đã tồn tại");
                        request.setAttribute("user", user1);
                        page = "admin/customer.jsp";

                    }
                }
            } else {
                response.sendRedirect("user?action=login");
                return;
            }
        } catch (Exception e) {
            page = "404.jsp";
        }
        RequestDispatcher dd = request.getRequestDispatcher(page);
        dd.forward(request, response);

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
