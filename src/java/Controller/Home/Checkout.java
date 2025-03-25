package Controller.Home;

import dal.billDAO;
import dal.productDAO;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

public class Checkout extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        HttpSession session = request.getSession();
        model.Cart cart = (model.Cart) session.getAttribute("cart");
        model.User acc = (model.User) session.getAttribute("user");
        System.out.println("Session ID: " + session.getId());
        System.out.println("Cart Object: " + session.getAttribute("cart"));

        if (cart == null) {
            request.setAttribute("error", "Null");
            request.getRequestDispatcher("404.jsp").forward(request, response);
            return;
        }
        if (acc == null) {
            response.sendRedirect("user?action=login");
            return;
        }

        productDAO proDAO = new productDAO();
        for (Item i : cart.getItems()) {
            Size productSize = proDAO.getQuatityBySize(i.getProduct().getProduct_id(), i.getSize());
            System.out.println(productSize);
            if (productSize == null || i.getQuantity() > productSize.getQuantity()) {
                session.setAttribute("orderfailedMessage", "Sản phẩm " + i.getProduct().getProduct_id()
                        + " chỉ còn " + (productSize != null ? productSize.getQuantity() : 0) + " chiếc.");
                response.sendRedirect("cart?action=showcart");
            }
        }

        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String payment_method = request.getParameter("payment_method");

        billDAO dao = new billDAO();
        BillRubish bill = new BillRubish(acc, cart, payment_method.toUpperCase(), address, phone);

        switch (payment_method) {
            case "cod" ->
                handleCODPayment(acc, cart, address, phone, proDAO, dao, session, response);
            case "vnpay" ->
                handleVNPayPayment(request, response, bill, dao);
            case "momo" -> {
                request.setAttribute("total", Math.round(dao.getBill().getTotal()));
                request.setAttribute("bill", dao.getBill());
                request.getRequestDispatcher("qrcode.jsp").forward(request, response);
            }
            default -> response.sendRedirect("cart.jsp");
        }
    }

    private void handleCODPayment(model.User acc, model.Cart cart, String address, String phone, productDAO proDAO, billDAO dao, HttpSession session, HttpServletResponse response) throws IOException {
        for (Item item : cart.getItems()) {
            Size s = proDAO.getQuatityBySize(item.getProduct().getProduct_id(), item.getSize());
            proDAO.UpdateQuatityBySize(item.getProduct().getProduct_id(), item.getSize(), s.getQuantity() - item.getQuantity());
            proDAO.UpdateQuantity(item.getProduct().getProduct_id(), item.getProduct().getQuantity() - item.getQuantity());
        }
        dao.addOrder1(acc, cart, "COD", address, Integer.parseInt(phone), 1);
//        new cartDAO().DeleteProductInCart(cart.getId(),acc.getUser_id());
        session.removeAttribute("cart");
        session.setAttribute("size", 0);
        session.setAttribute("orderSuccessMessage", "Đơn hàng của bạn đã được đặt thành công!");
        response.sendRedirect("home");
    }
//update vnpay
    private void handleVNPayPayment(HttpServletRequest request, HttpServletResponse response, BillRubish bill, billDAO dao) throws ServletException, IOException {
        request.getSession().setAttribute("pendingBill", bill);
        request.setAttribute("total", (int) Math.round(bill.getCart().getTotalMoney()));
        request.setAttribute("bill", bill);
        request.setAttribute("billId", dao.GetLastId() + 1);
        request.getRequestDispatcher("VN_Pay/vnpay_pay.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession().getAttribute("user") != null) {
            request.getRequestDispatcher("checkout.jsp").forward(request, response);
        } else {
            response.sendRedirect("user?action=login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
