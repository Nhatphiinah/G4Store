/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import dal.billDAO;
import model.Bill;
import model.BillDetail;
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
//NhuanPPCE180878- OrderManager
public class OrderManager extends HttpServlet {

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
            billDAO dao = new billDAO();

            if (user.getIsAdmin().equalsIgnoreCase("true") || user.getIsStoreStaff().equalsIgnoreCase("true")) {
                if (action == null) {
                    String paymentFilter = request.getParameter("paymentFilter");
                    List<Bill> bill = dao.getBillInfo1(paymentFilter);
                    request.setAttribute("bill", bill);
                    page = "admin/order.jsp";

                } else if (action.equals("showdetail")) {
                    String bill_id = request.getParameter("bill_id");
                    int id = Integer.parseInt(bill_id);
                    List<BillDetail> detail = dao.getDetail(id);
                    request.setAttribute("detail", detail);
                    page = "admin/orderdetail.jsp";

                }
                else if(action.equals("updatebill")){                    
                    int Id = Integer.parseInt(request.getParameter("bill_id"));
                    int status = Integer.parseInt(request.getParameter("status"));
                    dao.updateBill(Id, status);
                    session.setAttribute("mess", "Thay đổi trạng thái đơn hàng thành công");
                      response.sendRedirect("ordermanager");
                      return;
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
