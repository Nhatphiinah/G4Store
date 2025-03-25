package Controller.Admin;

import dal.SaleOffDAO;
import dal.productDAO;
import model.SaleOff;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Product;

@WebServlet(name = "SaleOffServlet", urlPatterns = {"/saleoff"})
public class SaleOffServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String action = request.getParameter("action");
        SaleOffDAO saleOffDAO = new SaleOffDAO();
        productDAO PdAao = new productDAO();
        if (action == null) {
            List<SaleOff> saleOffs = saleOffDAO.getAllSaleOffs();
            List<Product> products = PdAao.getProduct();
            request.setAttribute("products", products);
            request.setAttribute("saleOffs", saleOffs);
            request.getRequestDispatcher("admin/saleoff.jsp").forward(request, response);
        } else if (action.equals("update")) {
            String saleId = request.getParameter("saleId");
            float discountPercentage = Float.parseFloat(request.getParameter("discountPercentage"));
            Date startDate = parseDate(request.getParameter("startDate"));
            Date endDate = parseDate(request.getParameter("endDate"));
            SaleOff saleOff = new SaleOff(saleId, null, discountPercentage, startDate, endDate);
            saleOffDAO.updateSaleOff(saleOff);
            response.sendRedirect("saleoff");
        } else if (action.equals("insert")) {
            String saleId = request.getParameter("saleId");
            String productId = request.getParameter("productId");
            float discountPercentage = Float.parseFloat(request.getParameter("discountPercentage"));
            Date startDate = parseDate(request.getParameter("startDate"));
            Date endDate = parseDate(request.getParameter("endDate"));

            SaleOff saleOff = new SaleOff(saleId, productId, discountPercentage, startDate, endDate);
            saleOffDAO.insertSaleOff(saleOff);

            response.sendRedirect("saleoff");
        } else if (action.equals("delete")) {
            String saleId = request.getParameter("saleId");
            saleOffDAO.deleteSaleOff(saleId);
            response.sendRedirect("saleoff");
        }
    }

    private Date parseDate(String dateStr) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// 
}
