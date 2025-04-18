package Controller.Home;

import dal.cartDAO;
import dal.productDAO;
import model.Item;
import model.Product;
import model.User;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Cart extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");
        model.User user = (model.User) session.getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("user?action=login").forward(request, response);
            return;
        }
        if (user != null && ("TRUE".equalsIgnoreCase(user.getIsAdmin()) || "TRUE".equalsIgnoreCase(user.getIsStoreStaff()))) {
            session.setAttribute("errorMessage", "Quản trị viên và nhân viên cửa hàng không thể thêm hoặc mua sản phẩm.");
            request.getRequestDispatcher("search?action=productdetail&product_id=" + request.getParameter("product_id")).forward(request, response);
            return;
        }
        cartDAO cartDao = new cartDAO();
        model.Cart cart = null;
        Object o = session.getAttribute("cart");

        if (o != null) {
            cart = (model.Cart) o;
        } else {
            cart = new model.Cart();
        }
        if (user != null) {
            cart.setUserId(user.getUser_id() + "");
        }

        if (action == null || action.equalsIgnoreCase("addtocart")) {
            addToCart(request, cart);
            List<Item> list = cart.getItems();
            session.setAttribute("cart", cart);
            session.setAttribute("total", cart.getTotalMoney());
            session.setAttribute("size", list.size());
            session.setAttribute("successMessage", "Sản phẩm đã được thêm vào giỏ hàng thành công.");
            request.getRequestDispatcher("search?action=productdetail&product_id=" + request.getParameter("product_id")).forward(request, response);
        } else if (action.equalsIgnoreCase("buynow")) {
            addToCart(request, cart);
            List<Item> list = cart.getItems();
            session.setAttribute("cart", cart);
            session.setAttribute("total", cart.getTotalMoney());
            session.setAttribute("size", list.size());
            response.sendRedirect("cart?action=showcart");
        } else if (action.equalsIgnoreCase("showcart")) {
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        } else if (action.equals("deletecart")) {
            String product_id = request.getParameter("product_id");
            cart.removeItem(product_id);
            cartDao.DeleteProductInCart(product_id, user.getUser_id() + "");
            List<Item> list = cart.getItems();
            session.setAttribute("cart", cart);
            session.setAttribute("total", cart.getTotalMoney());
            session.setAttribute("size", list.size());
            session.setAttribute("successMessageDelete", "Sản phẩm đã được xóa khỏi giỏ hàng thành công");
            response.sendRedirect("cart.jsp");
        } else if (action.equalsIgnoreCase("update")) {
            String productId = request.getParameter("product_id");
            int quantity = Integer.parseInt(request.getParameter("quantity"));

            cart.updateQuantity(productId, quantity);

            cartDao.UpdateQuantity(cart);
            session.setAttribute("cart", cart);
            double total = cart.getTotalMoney();
            session.setAttribute("total", total);

            // Trả về JSON response
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("{\"total\":" + total + "}");
            return;
        }
    }

    private void addToCart(HttpServletRequest request, model.Cart cart) {
        cartDAO cd = new cartDAO();
         HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
        String Squantity = request.getParameter("quantity");
        String product_id = request.getParameter("product_id");
        String size = request.getParameter("size");
        String color = request.getParameter("color");
        try {
            int quantity = Integer.parseInt(Squantity);
            productDAO pdao = new productDAO();
            Product product = pdao.getProductByID(product_id);
            Item item = new Item(product, quantity, size, color);
//            cart.addItem(item);
//            lỗi logic khi add sản phẩm của vòng else
            if (cart.getUserId() != null) {              
                if (cd.GetUserProductInCart(product.getProduct_id(), user.getUser_id(),  size) == null) {
                    cart.addItem(item);
                    cd.AddCart(item, cart.getUserId());
                } else {
                    for (Item i : cart.getItems()) {
                        if (i.getProduct().getProduct_id().equals(product_id) && i.getSize().equals(size)) {
                            i.setQuantity(quantity + i.getQuantity());
                            break;
                        }
                    }
                    cd.UpdateQuantity(cart);
                }
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
