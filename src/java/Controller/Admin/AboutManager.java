package Controller.Admin;

import dal.aboutDAO;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.About;
import model.User;

/**
 *
 * @author Phung An
 */
@WebServlet(name = "AboutManager", urlPatterns = {"/aboutmanager"})
public class AboutManager extends HttpServlet {

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
        PrintWriter out = response.getWriter();
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String action = request.getParameter("action");
        String page = "";
        String page1 = "";
        try {
            HttpSession session = request.getSession();
            model.User user = (User) session.getAttribute("user");

            if (user != null && user.getIsAdmin().equalsIgnoreCase("true") || user.getIsStoreStaff().equalsIgnoreCase("true")) {
                if (action == null || action.equals("")) {
                    aboutDAO dao = new aboutDAO();
                    List<About> list = dao.getAbout();
                    request.setAttribute("listabout", list);
                    page = "admin/about.jsp";
                    page1 = "about.jsp";
                } else if (action.equals("delete")) {
                    String aboutid = request.getParameter("aboutId");
                    aboutDAO dao = new aboutDAO();
                    dao.DeleteAbout(aboutid);
                    response.sendRedirect("aboutmanager");
                    return;
                } else if (action.equalsIgnoreCase("insert")) {
                    aboutDAO c = new aboutDAO();
                    List<About> about = c.getAbout();
                    request.setAttribute("About", about);
                    page = "admin/aboutInsert.jsp";
                } else if (action.equals("aboutinsert")) {
                    aboutDAO dao = new aboutDAO();
                    String abouttitle = request.getParameter("about_title");
                    String aboutcontent = request.getParameter("about_content");

                    String aboutimg = "images/" + request.getParameter("about_img");
                    dao.addAbout(abouttitle, aboutimg, aboutcontent);
                    session.setAttribute("successMessageInsert", "Thêm bài viết mới thành công!");
                    response.sendRedirect("aboutmanager");
                    return;
                } else if (action.equalsIgnoreCase("updateabout")) {
                    aboutDAO dao = new aboutDAO();
                    String aboutid = request.getParameter("aboutid");
                    String abouttitle = request.getParameter("title");
                    String abouttitle1 = abouttitle.replaceAll(" {2,}", " ");
                    String aboutcontent = request.getParameter("content");
                    String aboutcontent1 = aboutcontent.replaceAll(" {2,}", " ");
                    String aboutimg = "images/" + request.getParameter("about_img");
                    About about = new About();
                    about.setAboutId(aboutid);
                    about.setTitle(abouttitle1);
                    about.setContent(aboutcontent1);
                    about.setImg(aboutimg);
                    dao.UpdateAbout(about);
                    session.setAttribute("successMessageUpdate", "Cập nhật thông tin bài viết thành công!");
                    response.sendRedirect("aboutmanager");
                    return;
                }
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
