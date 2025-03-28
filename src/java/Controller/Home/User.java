/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Home;

import dal.billDAO;
import dal.cartDAO;
import dal.userDAO;
import model.BillDetail;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.Item;

public class User extends HttpServlet {

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
        String action = request.getParameter("action");
        if (action.equals("login")) {
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
        if (action.equals("checkLogin")) {
            String user_email = request.getParameter("user_email");
            String user_pass = request.getParameter("user_pass");
            String remember = request.getParameter("remember");
            userDAO dao = new userDAO();
            model.User user = dao.checkUser(user_email, user_pass);
            if (user == null) {
                HttpSession session = request.getSession();
                session.setAttribute("error_exist", "Tài khoản không tồn tại !");
                request.getRequestDispatcher("user?action=login").forward(request, response);
            } else if (user.isBanned() == true) {
                HttpSession session = request.getSession();
                session.setAttribute("error_ban", "Tài khoản của bạn đã bị khóa. Vui lòng liên hệ quản trị viên.");
                request.getRequestDispatcher("user?action=login").forward(request, response);
            } else {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                session.setAttribute("loginMessage", "Đăng nhập thành công!");
                Cookie email = new Cookie("email", user_email);
                Cookie pass = new Cookie("pass", user_pass);
                Cookie rem = new Cookie("remember", remember);
                if (remember != null) {
                    email.setMaxAge(60 * 60 * 24 * 30);
                    pass.setMaxAge(60 * 60 * 24 * 30);
                    rem.setMaxAge(60 * 60 * 24 * 30);
                } else {
                    email.setMaxAge(0);
                    pass.setMaxAge(0);
                    rem.setMaxAge(0);
                }
                response.addCookie(email);
                response.addCookie(pass);
                response.addCookie(rem);

                cartDAO cDao = new cartDAO();
                model.Cart cart = cDao.GetCartByUser(user.getUser_id());
                List<Item> list = cart.getItems();
                session.setAttribute("cart", cart);
                session.setAttribute("total", cart.getTotalMoney());
                session.setAttribute("size", list.size());
                response.sendRedirect("home");
            }
        }

        if (action.equals("logout")) {
            HttpSession session = request.getSession();
            session.removeAttribute("user");
            session.removeAttribute("cart");
            session.removeAttribute("total");
            session.removeAttribute("size");
            session.setAttribute("logoutMessage", "Đăng xuất thành công!");
            response.sendRedirect("home");
        }

        if (action.equals("myaccount")) {
            try {
                HttpSession session = request.getSession();
                model.User user = (model.User) session.getAttribute("user");
                if (user != null) {
                    int user_id = user.getUser_id();
                    billDAO dao = new billDAO();
                    List<model.Bill> bill = dao.getBillByID(user_id);
                    request.setAttribute("bill", bill);
                    request.getRequestDispatcher("my-account.jsp").forward(request, response);
                } else {
                    response.sendRedirect("user?action=login");
                }
            } catch (Exception e) {
                response.sendRedirect("user?action=login");
            }
        }
        if (action.equals("showdetail")) {
            String bill_id = request.getParameter("bill_id");
            int id = Integer.parseInt(bill_id);
            billDAO dao = new billDAO();
            List<BillDetail> detail = dao.getDetail(id);
            request.setAttribute("detail", detail);
            request.getRequestDispatcher("billdetail.jsp").forward(request, response);
        }

        if (action.equals("updateinfo")) {
            try {
                HttpSession session = request.getSession();
                model.User user = (model.User) session.getAttribute("user");
                if (user != null) {
                    String user_name = request.getParameter("user_name").replaceAll(" {2,}", " ");
                    String dateOfBirth = request.getParameter("dateOfBirth");
                    String address = request.getParameter("address").replaceAll(" {2,}", " ");
                    String phoneNumber = request.getParameter("phoneNumber");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date dob = sdf.parse(dateOfBirth);
                        Date currentDate = new Date();
                        if (dob.after(currentDate)) {
                            session.setAttribute("error_dob", "Ngày sinh không được lớn hơn ngày hiện tại");
                            request.getRequestDispatcher("user?action=myaccount").forward(request, response);
                            return;
                        }
                    } catch (NumberFormatException e) {

                    }

                    int user_id = user.getUser_id();
                    if (user_name.trim().isEmpty() || address.trim().isEmpty()) {
                        session.setAttribute("errorNull", "Tên và thông tin không thể rỗng");
                        response.sendRedirect("user?action=myaccount");
                        return;
                    } else if (!phoneNumber.matches("^\\d{9,11}$")) {
                        session.setAttribute("errorPhone", "Vui lòng nhập đúng điện thoại");
                        response.sendRedirect("user?action=myaccount");
                        return;
                    }
                    userDAO dao = new userDAO();
                    dao.updateUser(user_id, user_name, dateOfBirth, address, phoneNumber);
                    model.User user1 = new model.User(user.getUser_id(), user_name, user.getUser_email(), user.getUser_pass(), user.getIsAdmin(), dateOfBirth, address, phoneNumber, user.isBanned(), user.getAdminReason(), user.getIsStoreStaff());
                    session.setAttribute("user", user1);
                    session.setAttribute("updateMessage", "Cập nhật thông tin thành công!");
                    response.sendRedirect("user?action=myaccount");
                } else {

                    response.sendRedirect("user?action=login");
                }
            } catch (Exception e) {

                response.sendRedirect("user?action=login");
            }
        }

        if (action.equals("changepass")) {
            try {
                HttpSession session = request.getSession();
                model.User user = (model.User) session.getAttribute("user");
                if (user != null) {
                    String user_pass = request.getParameter("new_pass");
                    String old_pass = request.getParameter("old_pass");
                    String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$";
                    if (user_pass.matches(passwordRegex) && old_pass.matches(passwordRegex)) {
                        userDAO dao = new userDAO();

                        int user_id = user.getUser_id();
                        dao.changePass(user_id, user_pass);
                        model.User user1 = new model.User(user.getUser_id(), user.getUser_name(), user.getUser_email(), user_pass, user.getIsAdmin(), user.getDateOfBirth(), user.getAddress(), user.getPhoneNumber(), user.isBanned(), user.getAdminReason(), user.getIsStoreStaff());
                        session.setAttribute("user", user1);
                        session.setAttribute("updateMessage", "Cập nhật thông tin thành công!");
                        response.sendRedirect("user?action=myaccount");
                    } else {
                        session.setAttribute("error_pass", "Mật khẩu chữ cái viết hoa và có 1 ký tự số với độ dài từ 6 trở lên");
                        request.getRequestDispatcher("user?action=myaccount").forward(request, response);
                    }
                } else {

                    response.sendRedirect("user?action=login");
                }
            } catch (Exception e) {

                response.sendRedirect("user?action=login");
            }
        }

        if (action.equals("signup")) {
            HttpSession session = request.getSession();
            userDAO da = new userDAO();
            String email = request.getParameter("user_email");
            String pass = request.getParameter("user_pass");
            String repass = request.getParameter("re_pass");
//            String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
//            System.out.println(gRecaptchaResponse);
//            boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
            String passwordRegex = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{6,}$";
            if (!pass.matches(passwordRegex)) {
                session.setAttribute("error_match", "Mật khẩu phải có ít nhất 6 ký tự, bao gồm ít nhất một chữ cái viết hoa và một chữ số");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }
//            if (!verify) {
//                session.setAttribute("Recaptcha", "Vui lòng xác nhận mã captcha");
//                request.getRequestDispatcher("login.jsp").forward(request, response);
//                return;
//            }

            if (!pass.equals(repass)) {
                session.setAttribute("error_rePass", "Vui lòng nhập lại mật khẩu cho đúng");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            } else {
                model.User a = da.checkAcc(email);
                if (a == null) {
                    // Check verify
                    SendEmail sm = new SendEmail();
                    String code = sm.getRandom();
                    UserC userc = new UserC(code, email);
                    boolean test = sm.sendEmail1(userc);
                    if (test == true && userc != null) {
                        session.setAttribute("userc", userc);
                        session.setAttribute("email", email);
                        session.setAttribute("pass", pass);
                        response.sendRedirect("verify.jsp");
                        return;
                    }
                } else {
                    session.setAttribute("msg", "Email đã tồn tại");
                    response.sendRedirect("user?action=login");
                }
            }
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
