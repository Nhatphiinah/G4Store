package Controller.Home;

import VnPayCommon.Config;
import dal.billDAO;
import dal.cartDAO;
import dal.productDAO;
import model.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
                session.setAttribute("orderErrorMessage", "Sản phẩm " + i.getProduct().getProduct_id()
                        + " chỉ còn " + (productSize != null ? productSize.getQuantity() : 0) + " chiếc.");
                response.sendRedirect("cart?action=showcart");
                return;
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
            case "vnpay" -> {
//                handleVNPayPayment(request, response, bill, dao);
                for (Item item : cart.getItems()) {
                    Size s = proDAO.getQuatityBySize(item.getProduct().getProduct_id(), item.getSize());
                    proDAO.UpdateQuatityBySize(item.getProduct().getProduct_id(), item.getSize(), s.getQuantity() - item.getQuantity());
                    proDAO.UpdateQuantity(item.getProduct().getProduct_id(), item.getProduct().getQuantity() - item.getQuantity());
                }
                int billId = dao.addOrder1(acc, cart, "VNPay", address, Integer.parseInt(phone), 1);
                checkoutVNPay(request, response, billId, cart.getTotalMoney());
            }
            case "momo" -> {
                request.setAttribute("total", Math.round(dao.getBill().getTotal()));
                request.setAttribute("bill", dao.getBill());
                request.getRequestDispatcher("qrcode.jsp").forward(request, response);
            }
            default ->
                response.sendRedirect("cart.jsp");
        }
    }
    
    private void checkoutVNPay(HttpServletRequest request, HttpServletResponse response, int billId, double totalPrice){
        try {
            //vnpay
                String vnp_Version = "2.1.0";
                String vnp_Command = "pay";
                String orderType = "other";
                long amount = (long) (totalPrice * 100);
                String bankCode = "NCB";
                
                String vnp_TxnRef = Config.getRandomNumber(8);
                String vnp_IpAddr = Config.getIpAddress(request);
                
                String vnp_TmnCode = Config.vnp_TmnCode;
                
                Map<String, String> vnp_Params = new HashMap<>();
                vnp_Params.put("vnp_Version", vnp_Version);
                vnp_Params.put("vnp_Command", vnp_Command);
                vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                vnp_Params.put("vnp_Amount", String.valueOf(amount));
                vnp_Params.put("vnp_CurrCode", "VND");
                
                vnp_Params.put("vnp_BankCode", bankCode);
                vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
                vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
                vnp_Params.put("vnp_OrderType", orderType);
                
                String locate = request.getParameter("language");
                if (locate != null && !locate.isEmpty()) {
                    vnp_Params.put("vnp_Locale", locate);
                } else {
                    vnp_Params.put("vnp_Locale", "vn");
                }
                String returnURL = Config.vnp_ReturnUrl + "?billId=" + billId;
                vnp_Params.put("vnp_ReturnUrl", returnURL);
                vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
                
                Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                String vnp_CreateDate = formatter.format(cld.getTime());
                vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
                
                cld.add(Calendar.MINUTE, 15);
                String vnp_ExpireDate = formatter.format(cld.getTime());
                vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
                
                List fieldNames = new ArrayList(vnp_Params.keySet());
                Collections.sort(fieldNames);
                StringBuilder hashData = new StringBuilder();
                StringBuilder query = new StringBuilder();
                Iterator itr = fieldNames.iterator();
                while (itr.hasNext()) {
                    String fieldName = (String) itr.next();
                    String fieldValue = (String) vnp_Params.get(fieldName);
                    if ((fieldValue != null) && (fieldValue.length() > 0)) {
                        //Build hash data
                        hashData.append(fieldName);
                        hashData.append('=');
                        hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                        //Build query
                        query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                        query.append('=');
                        query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                        if (itr.hasNext()) {
                            query.append('&');
                            hashData.append('&');
                        }
                    }
                }
                String queryUrl = query.toString();
                
                String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
                queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
                String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;
                
                response.sendRedirect(paymentUrl);
        } catch (Exception e) {
            e.printStackTrace();
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
        new cartDAO().ClearCartByUser(acc.getUser_id());
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
