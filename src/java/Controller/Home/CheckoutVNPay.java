package Controller.Home;

import dal.billDAO;
import dal.cartDAO;
import model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CheckoutVNPay extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            HttpSession session = request.getSession();
            User acc = (User) session.getAttribute("user"); // Lấy user từ session
            String responseCode = request.getParameter("vnp_ResponseCode");

            if ("00".equals(responseCode)) {
                // Thanh toán thành công
                String billId = request.getParameter("billId");
                billDAO bAO = new billDAO();
                bAO.updateBill(Integer.parseInt(billId), 2);
                
                if (acc != null) {
                    new cartDAO().ClearCartByUser(acc.getUser_id());
                    session.removeAttribute("cart");
                    session.setAttribute("size", 0);
                }
                
                session.setAttribute("orderSuccessMessage", "Đơn hàng của bạn đã được đặt thành công!");
                response.sendRedirect("home");
            } else {
                // Thanh toán thất bại
                session.setAttribute("orderErrorMessage", "Đơn hàng của bạn đã thanh toán không thành công!");
                response.sendRedirect("home");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    }
}
