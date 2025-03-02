/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import dal.billDAO;
import dal.categoryDAO;
import dal.productDAO;
import model.Bill;
import model.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import model.Category;

public class Dashboard extends HttpServlet {

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
            HttpSession session = request.getSession();
            model.User user = (User) session.getAttribute("user");
            if (user.getIsAdmin().equalsIgnoreCase("true") || user.getIsStoreStaff().equalsIgnoreCase("true")) {
                productDAO dao = new productDAO();
                billDAO bdao = new billDAO();
                categoryDAO cdao = new categoryDAO();
                //pie chart
                List<Category> categoryList = cdao.getCategoryCounts();
                request.setAttribute("categoryList", categoryList);
                List<Bill> billbyday = bdao.getBillByDay();
                List<Double> paidList = new ArrayList<>();
                List<Double> unpaidList = new ArrayList<>();
                List<Date> dateList = new ArrayList<>();

                for (Bill bill : billbyday) {
                    double totalPaid = bdao.getTotalPaidByDate(bill.getDate());
                    double totalUnpaid = bdao.getTotalUnpaidByDate(bill.getDate());
                    paidList.add(totalPaid);
                    unpaidList.add(totalUnpaid);
                    dateList.add(bill.getDate());
                }
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                String message1 = null;
                String message2 = null;
                if (startDate != null && endDate != null) {  // Chỉ xử lý khi có tìm kiếm
    if (startDate.isEmpty()) {
        startDate = "default_start_date";
    }
    if (endDate.isEmpty()) {
        endDate = "default_end_date";
    }
    List<Bill> bills = bdao.getBillBetweenDates(startDate, endDate);
    if (bills != null && !bills.isEmpty()) {
        message1 = "Tìm kiếm thành công từ " + startDate + " đến " + endDate;
        session.setAttribute("message1", message1);
    } else {
        message2 = "Không tìm thấy kết quả từ " + startDate + " đến " + endDate;
        session.setAttribute("message2", message2);
    }
    request.setAttribute("bills", bills);
}

                int count = dao.CountProduct();
                int countuser = dao.CountUser();
                int countbill = dao.CountBill();
                int countproductlow = dao.CountProductLow();
//            List<Bill> billbyday = bdao.getBillByDay();
                request.setAttribute("product", count);
                request.setAttribute("user", countuser);
                request.setAttribute("bill", countbill);
                request.setAttribute("low", countproductlow);
                request.setAttribute("billbyday", billbyday);
                

                List<Object[]> monthlyTotals = bdao.getTotalBillAmountByMonth();
                request.setAttribute("monthlyTotals", monthlyTotals);
                request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
            } else {
                response.sendRedirect("login");
            }
        } catch (Exception e) {
            response.sendRedirect("404.jsp");
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
