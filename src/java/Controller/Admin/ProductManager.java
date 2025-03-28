/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller.Admin;

import dal.categoryDAO;
import dal.productDAO;
import model.Category;
import model.Color;
import model.Product;
import model.Size;
import model.User;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Objects;
import model.Product_Active;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author NhatPhi
 */
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 11
)
public class ProductManager extends HttpServlet {

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
        try {
            HttpSession session = request.getSession();
            model.User user = (User) session.getAttribute("user");

            if (user != null && user.getIsAdmin().equalsIgnoreCase("true") || user.getIsStoreStaff().equalsIgnoreCase("true")) {
                if (action == null || action.equals("")) {
                    productDAO c = new productDAO();
                    List<Product> product = c.getProductBySize();
                    List<Size> size = c.getSize();
                    List<Color> color = c.getColor();
                    List<Category> category = c.getCategory();
                    List<Product_Active> active = c.getActive();
                    request.setAttribute("ActiveData", c.getActive());
                    request.setAttribute("CategoryData", category);
                    request.setAttribute("ProductData", product);
                    request.setAttribute("SizeData", size);
                    request.setAttribute("ColorData", color);
                    page = "admin/product.jsp";
                } else if (action.equalsIgnoreCase("insert")) {
                    productDAO c = new productDAO();
                    List<Category> category = c.getCategory();
                    request.setAttribute("CategoryData", category);
                    page = "admin/productinsert.jsp";
                } else if (action.equalsIgnoreCase("insertcategory")) {
                    String name = request.getParameter("name");
                    if (name != null && !name.isEmpty()) {
                        productDAO dao = new productDAO();
                        Category c = dao.getCategoryByName(name);
                        if (c != null) {
                            String mess = "err";
                            session.setAttribute("cateStatus", mess);
                            response.sendRedirect("categorymanager");
                            return;
                            
                        } else {
                            dao.insertCategory(name);
                            String mess = "succ";
                            session.setAttribute("cateStatus", mess);
                            response.sendRedirect("categorymanager");
                            return;
                        }
                    } else {
                        request.setAttribute("error", "Category name cannot be empty.");
                        page = "admin/category.jsp";
                    }
                } else if (action.equalsIgnoreCase("insertproduct")) {
                    try {
                        String product_id = request.getParameter("product_id");

                        Product duplicate = new productDAO().getProductByID(product_id);

                        if (duplicate != null) {
                            String mess = "err";
                            session.setAttribute("insertStatus", mess);
                            response.sendRedirect("productmanager");
                            return;
                        } else {
                            String category_id = request.getParameter("category_id");
                            String product_name = request.getParameter("product_name");
                            String product_price = request.getParameter("price");
                            String product_size = request.getParameter("size");
                            String product_color = request.getParameter("color");
                            String product_quantity = request.getParameter("quantity");

                            String product_img = "images/" + request.getParameter("product_img");
                            String product_describe = request.getParameter("describe");
                            String active = request.getParameter("permission");
                            active = "True";
                            //int quantity = Integer.parseInt(product_quantity);
                            Float price = Float.parseFloat(product_price);
                            int cid = Integer.parseInt(category_id);
                            productDAO dao = new productDAO();
                            Category cate = new Category(cid);
                            String[] size_rw = product_size.split("\\s*,\\s*");
                            String[] color_rw = product_color.split("\\s*,\\s*");
                            String[] quantity_rw = product_quantity.split("\\s*,\\s*");
                            int quantity = 0;
                            List<Size> list = new ArrayList<>();
//                            for (String s : size_rw) {
//                                list.add(new Size(product_id, s));
//                            }

                            for (int i = 0; i < size_rw.length; i++) {
                                String size = size_rw[i];
                                if(i >= quantity_rw.length ) break;
                                int qua = Integer.parseInt(quantity_rw[i]);
                                quantity += qua;
                                list.add(new Size(product_id, size, qua));
                            }
                            
                            List<Color> list2 = new ArrayList<>();
                            for (String c : color_rw) {
                                list2.add(new Color(product_id, c));
                            }

                            Product product = new Product();
                            Product_Active Pa = new Product_Active(product_id, active);
                            product.setCate(cate);
                            product.setProduct_id(product_id);
                            product.setProduct_name(product_name);
                            product.setProduct_price(price);
                            product.setProduct_describe(product_describe);
                            product.setQuantity(quantity);
                            product.setImg(product_img);
                            product.setSize(list);
                            product.setColor(list2);
                            product.setActive(Pa);
                            dao.insertProduct(product);
                            String mess = "succ";
                            session.setAttribute("insertStatus", mess);
                            response.sendRedirect("productmanager");
                            return;
                        }

                    } catch (Exception ex) {
                        System.out.println("loi");
                    }

                    response.sendRedirect("productmanager");
                    return;
                } else if (action.equalsIgnoreCase("deleteproduct")) {
                    String product_id = request.getParameter("product_id");
                    productDAO dao = new productDAO();
                    dao.ProductDelete(product_id);
                    response.sendRedirect("productmanager");
                    return;

                } else if (action.equalsIgnoreCase("updateproduct")) {
                    String product_id = request.getParameter("product_id").replaceAll(" {2,}", " ");
                    String category_id = request.getParameter("category_id").replaceAll(" {2,}", " ");
                    String product_name = request.getParameter("product_name").replaceAll(" {2,}", " ");
                    String product_price = request.getParameter("product_price");
                    String product_size = request.getParameter("product_size");
                    String product_color = request.getParameter("product_color").replaceAll(" {2,}", " ");
                    String product_quantity = request.getParameter("product_quantity");
                    String product_img = "images/" + request.getParameter("product_img");
                    String product_describe = request.getParameter("product_describe").replaceAll(" {2,}", " ");
                    String active = request.getParameter("permission");
                    if (product_id.trim().isEmpty() || product_name.trim().isEmpty() || product_size.trim().isEmpty() || product_describe.trim().isEmpty() || product_quantity.trim().isEmpty()||product_color.trim().isEmpty()) {
                        String mess = "Không thể cập nhật khi tên, miêu tả, màu sắc, số lượng, kích thước của sản phẩm rỗng";
                        session.setAttribute("errorNull", mess);
                        response.sendRedirect("productmanager");
                        return;
                    }
                    else{
                    //lỗi do các căp key bị null
//                    int quantity = Integer.parseInt(product_quantity);
//                    if (quantity < 0) {
//                        quantity = 1;
//                    }
                    Float price = Float.parseFloat(product_price);
                    int cid = Integer.parseInt(category_id);
                    productDAO dao = new productDAO();

                    Category cate = new Category(cid);

                    String[] size_rw = product_size.split("\\s*,\\s*");
                    String[] color_rw = product_color.split("\\s*,\\s*");
                    String[] quantity_rw = product_quantity.split("\\s*,\\s*");
                    //size
                    List<Size> list = new ArrayList<>();

//                    for (String s : size_rw) {
//                        list.add(new Size(product_id, s));
//                    }
                    int quantity = 0;
                     for (int i = 0; i < size_rw.length; i++) {
                        String size = size_rw[i];
                        if(i >= quantity_rw.length ) break;
                        int qua = Integer.parseInt(quantity_rw[i]);
                        quantity += qua;
                        list.add(new Size(product_id, size, qua));
                    }
                    
                    
                    //color
                    List<Color> list2 = new ArrayList<>();
                    for (String c : color_rw) {
                        list2.add(new Color(product_id, c));
                    }
                    
                    
                    Product_Active activep = new Product_Active(product_id, active);
                    Product product = new Product();
                    product.setCate(cate);
                    product.setProduct_id(product_id);
                    product.setProduct_name(product_name);
                    product.setProduct_price(price);
                    product.setProduct_describe(product_describe);
                    product.setQuantity(quantity);
                    product.setImg(product_img);
                    product.setSize(list);
                    product.setColor(list2);
                    product.setActive(activep);
                    dao.updateProduct(product, cid, list2, list);
                    response.sendRedirect("productmanager");
                    return;
                    }
                } else if (action.equalsIgnoreCase("insertByExcel")) {

                    Part filePart = request.getPart("file");

                    InputStream fileContent = filePart.getInputStream();

                    Workbook workbook = new XSSFWorkbook(fileContent);
                    Sheet sheet = workbook.getSheetAt(0);
                    int numberOfRowAdded = 0;
                    int totalRow = sheet.getLastRowNum();
                    for (Row row : sheet) {
                        if (row.getRowNum() > 3) {
                            if (handleExcelData(row)) {
                                numberOfRowAdded++;
                            }
                        }
                    }

                    response.sendRedirect("productmanager?rowsAdded=" + numberOfRowAdded);
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

    private Boolean handleExcelData(Row row) {
//                    String name = row.getCell(0).getStringCellValue();
//                        int age = (int) row.getCell(1).getNumericCellValue();
        try{
            
            if(validateExcelData(row)){
                        String product_id = row.getCell(0).getStringCellValue();
                int category_id = (int) row.getCell(1).getNumericCellValue();
                String product_name = row.getCell(2).getStringCellValue();
                float product_price = (float) row.getCell(3).getNumericCellValue();
                String product_size = row.getCell(4).getStringCellValue();
                String product_color = row.getCell(5).getStringCellValue();
                int product_quantity = (int) row.getCell(6).getNumericCellValue();
                String product_img = row.getCell(7) != null && row.getCell(7).getStringCellValue().trim().length() > 0
                    ? "images/" + row.getCell(7).getStringCellValue()
                    : "default";
                String product_describe = row.getCell(8) != null && row.getCell(8).getStringCellValue().trim().length() > 0
                    ? row.getCell(8).getStringCellValue()
                    : "default";
                String active = "True";

                productDAO dao = new productDAO();
                Category cate = new Category(category_id);
                String[] size_rw = product_size.split("\\s*,\\s*");
                String[] color_rw = product_color.split("\\s*,\\s*");

                List<Size> list = new ArrayList<>();
                for (String s : size_rw) {
                    list.add(new Size(product_id, s));
                }

                List<Color> list2 = new ArrayList<>();
                for (String c : color_rw) {
                    list2.add(new Color(product_id, c));
                }

                Product product = new Product();
                Product_Active Pa = new Product_Active(product_id, active);
                product.setCate(cate);
                product.setProduct_id(product_id);
                product.setProduct_name(product_name);
                product.setProduct_price(product_price);
                product.setProduct_describe(product_describe);
                product.setQuantity(product_quantity);
                product.setImg(product_img);
                product.setSize(list);
                product.setColor(list2);
                product.setActive(Pa);

                Product find = dao.getProductByID(product.getProduct_id());
                //insert product by page
                if (find != null) {
                    return false;
                } else {
                    dao.insertProduct(product);
                    return true;
                }
                    }else{
                
            }
            
            
        }catch(Exception ex){
            return false;
        
        }
        
        return  false;
    }
    
    private Boolean validateExcelData(Row row) {
        boolean isValid = true;
        try {
            Cell cell0 = row.getCell(0);
            Cell cell1 = row.getCell(1);
            Cell cell2 = row.getCell(2);
            Cell cell3 = row.getCell(3);
            Cell cell4 = row.getCell(4);
            Cell cell5 = row.getCell(5);
            Cell cell6 = row.getCell(6);

            if (cell0 == null || cell0.getCellType() == CellType.BLANK || cell0.getStringCellValue().isEmpty()) {
                isValid = false;
            } else {
                String product_id = cell0.getStringCellValue();
            }

            if (isValid && (cell1 == null || cell1.getCellType() != CellType.NUMERIC)) {
                isValid = false;
            } else if (isValid) {
                int category_id = (int) cell1.getNumericCellValue();
                categoryDAO catDao = new categoryDAO();
                List<Category> list = catDao.getCategory();
                for (Category i : list) {
                    int id = i.getCategory_id();
                    if(id==category_id){
                        isValid = true;
                        break;
                    }else{
                        isValid = false;
                    }
                }
            }

            if (isValid && (cell2 == null || cell2.getCellType() == CellType.BLANK || cell2.getStringCellValue().isEmpty())) {
                isValid = false;
            } else if (isValid) {
                String product_name = cell2.getStringCellValue();
            }

            if (isValid && (cell3 == null || cell3.getCellType() != CellType.NUMERIC)) {
                isValid = false;
            } else if (isValid) {
                float product_price = (float) cell3.getNumericCellValue();
                if (product_price<0) {
                    isValid = false;
                }
            }

            if (isValid && (cell4 == null || cell4.getCellType() == CellType.BLANK || cell4.getStringCellValue().isEmpty())) {
                isValid = false;
            } else if (isValid) {
                String product_size = cell4.getStringCellValue();
            }

            if (isValid && (cell5 == null || cell5.getCellType() == CellType.BLANK || cell5.getStringCellValue().isEmpty())) {
                isValid = false;
            } else if (isValid) {
                String product_color = cell5.getStringCellValue();
            }

            if (isValid && (cell6 == null || cell6.getCellType() != CellType.NUMERIC)) {
                isValid = false;
            } else if (isValid) {
                int product_quantity = (int) cell6.getNumericCellValue();
                if (product_quantity<0) {
                    isValid = false;
                }
            }
        } catch (Exception e) {
            isValid = false;
        }

        return isValid;
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    public int BUFFER_SIZE = 1024 * 1000;

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("dowloadTemplate".equals(action)) {
            String path = getServletContext().getRealPath("") + "excel_template" + File.separator + "sample-xlsx-file.xlsx";

            System.out.println(path);

            File file = new File(path);
            OutputStream os = null;
            FileInputStream fis = null;

            response.setHeader("Content-Disposition", String.format("attachment;filename=\"%s\"", file.getName()));
            response.setContentType("application/octet-stream");
            if (file.exists()) {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                byte[] bf = new byte[BUFFER_SIZE];
                int byteRead = -1;
                while ((byteRead = fis.read(bf)) != -1) {
                    os.write(bf, 0, byteRead);
                }
            }
        } else {
            processRequest(request, response);
        }
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
