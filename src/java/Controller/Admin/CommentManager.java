/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import dal.commentRatingDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Comment;
import model.User;

/**
 *
 * @author admin
 */
@WebServlet(name = "CommentManager", urlPatterns = {"/commentmanager"})
public class CommentManager extends HttpServlet {

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
             String ratingFilter = request.getParameter("ratingFilter");
            if (user != null && user.getIsAdmin().equalsIgnoreCase("true")) {               
                if (action == null) {
                    commentRatingDAO dao = new commentRatingDAO();
                    List<Comment> comments;
                    if (ratingFilter != null && !ratingFilter.isEmpty()) {
                        int rating = Integer.parseInt(ratingFilter);
                        comments = dao.getCommentsByRating(rating);
                    } else {
                        comments = dao.getComment();
                    }
                    request.setAttribute("comment", comments);
                    page = "admin/comment.jsp";
                } else if (action.equals("delete")) {
                    String comment_id = request.getParameter("comment_id");
                    if (comment_id != null) {
                        try {
                            int id = Integer.parseInt(comment_id);
                            commentRatingDAO dao = new commentRatingDAO();
                            dao.deleteComment(id);
                            response.sendRedirect("commentmanager");
                            return;
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                            response.sendRedirect("error.jsp"); // chuyển hướng đến trang lỗi nếu comment_id không hợp lệ
                        }
                    }
                }
            }else {
                    response.sendRedirect("user?action=login");
                    return;
                }
                
            }catch (Exception e) {
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
        protected void doGet
        (HttpServletRequest request, HttpServletResponse response)
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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
