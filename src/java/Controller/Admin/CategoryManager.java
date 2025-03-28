/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Admin;

import dal.categoryDAO;
import dal.productDAO;
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
import model.Category;
import model.User;

/**
 *
 * @author
 */
@WebServlet(name = "CategoryManager", urlPatterns = {"/categorymanager"})
public class CategoryManager extends HttpServlet {

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
            if (user.getIsAdmin().equalsIgnoreCase("true") || user.getIsStoreStaff().equalsIgnoreCase("true")) {
                if (action == null) {
                    categoryDAO cdao = new categoryDAO();
                    List<Category> category = cdao.getCategory();
                    request.setAttribute("category", category);
                    page = "admin/category.jsp";
                } else if (action.equalsIgnoreCase("insertcategory")) {
                    categoryDAO c = new categoryDAO();
                    List<Category> categories = c.getCategory();
                    request.setAttribute("CategoryData1", categories);
                    String name = request.getParameter("name");
                    if (name != null && !name.isEmpty()) {
                        productDAO dao = new productDAO();
                        Category d = dao.getCategoryByName(name);
                        if (d != null) {
                            request.setAttribute("error", name + " already exists.");
                            page = "admin/category.jsp";
                        } else {
                            dao.insertCategory(name);
                            response.sendRedirect("categorymanager");
                            return;
                        }
                    } else {
                        request.setAttribute("error", "Category name cannot be empty.");
                        page = "admin/category.jsp";
                    }
                } else if (action.equalsIgnoreCase("updatecategory")) {
                    String category_id = request.getParameter("category_id");
                    String category_name = request.getParameter("category_name");
                    boolean status = request.getParameter("status").equals("1");
                    if (category_name.trim().isEmpty()) {
                        session.setAttribute("errorNull", "Vui lòng không nhập khoảng trắng");
                        response.sendRedirect("categorymanager");
                        return;
                    } else {
                        categoryDAO cateDao = new categoryDAO();
                        List<Category> list = cateDao.getCategory();
                        for (Category i : list) {
                            if (i.getCategory_id() != Integer.parseInt(category_id) && category_name.trim().equalsIgnoreCase(i.getCategory_name())) {
                                session.setAttribute("errorNull", "Tên danh mục đã tồn tại!");
                                response.sendRedirect("categorymanager");
                                return;
                            }
                        }
                    }
                    if (category_id != null && category_name != null && !category_id.isEmpty() && !category_name.isEmpty()) {
                        try {
                            int categoryId = Integer.parseInt(category_id);
                            categoryDAO dao = new categoryDAO();
                            dao.updateCategory(categoryId, category_name, status);
                            response.sendRedirect("categorymanager");
                            return;
                        } catch (NumberFormatException e) {
                            request.setAttribute("error", "Invalid category ID format.");
                        } catch (Exception e) {
                            request.setAttribute("error", "An error occurred while updating the category.");
                        }
                    } else {
                        // Handle validation errors or missing parameters
                        request.setAttribute("error", "Category ID and Name cannot be empty.");
                    }
                    // Forward back to the category manager page with an error message
                    page = "admin/category.jsp"; // Set the destination page

                    RequestDispatcher dd = request.getRequestDispatcher(page);
                    dd.forward(request, response);

                } else if (action.equalsIgnoreCase("delete")) {
                    String category_id = request.getParameter("category_id");
                    int id = Integer.parseInt(category_id);
                    categoryDAO dao = new categoryDAO();
                    dao.deleteCategory(id);
                    response.sendRedirect("categorymanager");
                    return;
                }
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
