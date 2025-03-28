/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Home;

import com.google.gson.Gson;
import dal.productDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.Category;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ProductFilter", urlPatterns = {"/products"})
public class ProductFilter extends HttpServlet {

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
        try {
            int numberProductPerPage = 9;
            productDAO dao = new productDAO();
            List<Category> category = dao.getCategoryActive();
            request.setAttribute("CategoryData", category);

            String txtSearch = request.getParameter("search");
            String cID = request.getParameter("category");
            String sort = request.getParameter("sort");
            sort = (sort == null || sort.equals("")) ? "asc" : sort;
            String priceRange = request.getParameter("price");
            String saleOff = request.getParameter("saleOff");
            priceRange = (priceRange == null || priceRange.equals("")) ? "5" : priceRange;

            cID = (cID == null || cID.equals("all")) ? null : Integer.parseInt(cID) + "";
            int pageSize = getPageSize(numberProductPerPage, dao.searchWithPaging(txtSearch, cID, sort, priceRange, saleOff, null, numberProductPerPage).size());
            String index = request.getParameter("pageIndex");
            int pageIndex = 0;
            if (index == null) {
                pageIndex = 1;
            } else {
                pageIndex = Integer.parseInt(index);
            }

            List<model.Product> ls = dao.searchWithPaging(txtSearch, cID, sort, priceRange, saleOff, pageIndex, numberProductPerPage);
            request.setAttribute("ProductData", ls);

            request.setAttribute("totalPage", pageSize);
            request.setAttribute("numberProduct", 9);
            request.setAttribute("pageIndex", pageIndex);
            request.setAttribute("searchValue", txtSearch);
            request.setAttribute("categoryId", cID);
            request.setAttribute("sort", sort);
            request.setAttribute("priceRange", priceRange);
            request.getRequestDispatcher("product-list.jsp").forward(request, response);
        } catch (Exception e) {
            request.getRequestDispatcher("product-list.jsp").forward(request, response);
        }
    }

    public int getPageSize(int numberProduct, int allProduct) {
        int pageSize = allProduct / numberProduct;
        if (allProduct % numberProduct != 0) {
            pageSize = (allProduct / numberProduct) + 1;
        }
        return pageSize;

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
