/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.Home;

import dal.billDAO;
import dal.commentRatingDAO;
import dal.productDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.Category;

/**
 *
 * @author admin
 */
@WebServlet(name = "Product_Search", urlPatterns = {"/search"})
public class Product_Search extends HttpServlet {

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

        if (action.equalsIgnoreCase("productdetail")) {
            String product_id = request.getParameter("product_id");
            productDAO c = new productDAO();
            List<model.Size> sizeList = c.getSizeByID(product_id);
            List<model.Color> colorList = c.getColorByID(product_id);
            model.Product product = c.getProductByID(product_id);
            int category_id = product.getCate().getCategory_id();
            List<model.Product> productByCategory = c.getProductByCategory(category_id);
            commentRatingDAO crDAO = new commentRatingDAO();
            List<model.Comment> comments = crDAO.getCommentsByProductId(product_id);
            double averageRating = crDAO.getAverageRatingForProduct(product_id);
            request.setAttribute("ProductData", product);
            request.setAttribute("SizeData", sizeList);
            request.setAttribute("ColorData", colorList);
            request.setAttribute("ProductByCategory", productByCategory);
            request.setAttribute("comments", comments);
            request.setAttribute("averageRating", averageRating);
            request.getRequestDispatcher("product-details.jsp").forward(request, response);
        } else if (action.equalsIgnoreCase("addComment")) {
            billDAO bill = new billDAO();
            String productId = request.getParameter("product_id");
            String userId = request.getParameter("user_id");  // Retrieve userId from session
            String userName = request.getParameter("user_name");  // Retrieve userId from session
            int rating = Integer.parseInt(request.getParameter("rating"));
            String commentText = request.getParameter("comment");

            
            commentRatingDAO dao = new commentRatingDAO();
            if (dao.hasUserCommented(productId, userId)) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage", "Bạn đã đánh giá và bình luận cho sản phẩm này rồi.");
            }
            if (bill.hasUserBuyItem(productId, userId) == false) {
                HttpSession session = request.getSession();
                session.setAttribute("errorMessage1", "Bạn phải nhận hàng mới được bình luận");
            } else {
                dao.addComment(productId, userId, commentText, rating, userName);
                HttpSession session = request.getSession();
                session.setAttribute("successMessage", "Đánh giá bình luận thành công");
            }
            response.sendRedirect("search?action=productdetail&product_id=" + productId);
        }

        if (action.equals("view")) {
            try { int numberProductPerPage = 9;
                productDAO c = new productDAO();
                List<Category> category = c.getCategoryActive();
                request.setAttribute("CategoryData", category);

                String txtSearch = request.getParameter("search");
                String cID = request.getParameter("category");
                String sort = request.getParameter("sort");
                sort = (sort == null || sort.equals("")) ? "asc" : sort;
                String priceRange = request.getParameter("price");
                String saleOff = request.getParameter("saleOff");
                priceRange = (priceRange == null || priceRange.equals("")) ? "5" : priceRange;

                cID = (cID == null || cID.equals("all")) ? null : Integer.parseInt(cID) + "";
                int pageSize = getPageSize(numberProductPerPage,  c.searchWithPaging(txtSearch, cID, sort, priceRange, saleOff, null, numberProductPerPage).size());
                String index = request.getParameter("pageIndex");
                int pageIndex = 0;
                if (index == null) {
                    pageIndex = 1;
                } else {
                    pageIndex = Integer.parseInt(index);
                }

                List<model.Product> ls =  c.searchWithPaging(txtSearch, cID, sort, priceRange, saleOff, pageIndex, numberProductPerPage);
                request.setAttribute("ProductData", ls);

                request.setAttribute("totalPage", pageSize);
                request.setAttribute("numberProduct", 9);
                request.setAttribute("pageIndex", pageIndex);
                request.setAttribute("searchValue", txtSearch);
                request.setAttribute("categoryId", cID);
                request.setAttribute("sort", sort);
                request.setAttribute("priceRange", priceRange);

                request.getRequestDispatcher("shop_category.jsp").forward(request, response);
            } catch (Exception e) {
                request.getRequestDispatcher("shop_category.jsp").forward(request, response);
            }
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
