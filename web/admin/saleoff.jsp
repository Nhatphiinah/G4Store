<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Sale Off Management</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="admin/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">

        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://cdn.ckeditor.com/4.16.2/standard/ckeditor.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/sweetalert"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="dashboard"><i class='bx bx-log-out bx-rotate-180'></i> </a>

                </li>
            </ul>
        </header>
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="admin/images/user.png" width="50px"
                                                alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b>${sessionScope.user.user_name}</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item" href="dashboard"><i class='app-menu__icon bx bx-tachometer'></i><span class="app-menu__label">Bảng thống kê</span></a></li>
                <li><a class="app-menu__item" href="categorymanager"><i class='app-menu__icon bx bxs-category'></i><span class="app-menu__label">Quản lý danh mục</span></a></li>
                <li><a class="app-menu__item" href="productmanager"><i class='app-menu__icon bx bx-purchase-tag-alt'></i><span class="app-menu__label">Quản lý sản phẩm</span></a></li>
                <li><a class="app-menu__item" href="ordermanager"><i class='app-menu__icon bx bx-task'></i><span class="app-menu__label">Quản lý đơn hàng</span></a></li>
                <li><a class="app-menu__item" href="saleoff"><i class='app-menu__icon bx bxs-discount'></i><span class="app-menu__label">Quản lý khuyến mãi</span></a></li>
               
                <!-- Conditionally Display Menu Items -->
                <c:if test="${sessionScope.user.isAdmin}">
                    <li><a class="app-menu__item" href="customermanager"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Quản lý người dùng</span></a></li>
                    <li><a class="app-menu__item" href="reportmanager"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý phản hồi</span></a></li>
                    <li><a class="app-menu__item" href="aboutmanager"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý trang giới thiệu</span></a></li>
                   
                            </c:if>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Sale Off Management</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <h2>Current Sale Offs</h2>
            <table border="1">
                <tr>
                    <th>Sale ID</th>
                    <th>Product ID</th>
                    <th>Discount Percentage</th>
                    <th>Start Date</th>
                    <th>End Date</th>
                    <th>Action</th>
                </tr>
                <c:forEach var="saleOff" items="${saleOffs}">
                    <tr>
                        <td>${saleOff.sale_id}</td>
                        <td>${saleOff.product_id}</td>
                        <td>${saleOff.discount_percentage}</td>
                        <td>${saleOff.start_date}</td>
                        <td>${saleOff.end_date}</td>
                        <td>
                            <form action="saleoff" method="post">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="saleId" value="${saleOff.sale_id}">
                                <input type="number" name="discountPercentage" value="${saleOff.discount_percentage}" step="0.01" required>
                                <input type="date" name="startDate" value="${saleOff.start_date}" required>
                                <input type="date" name="endDate" value="${saleOff.end_date}" required>
                                <input type="submit" value="Update">
                                <a href="saleoff?saleId=${saleOff.sale_id}&action=delete" ><input type="button" value="Delete"></a>
                            </form>
                                
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <br/>
            <h2>Add New Sale Off</h2>
            <form action="saleoff" method="post">
                <input type="hidden" name="action" value="insert">
                Sale ID: <input type="text" name="saleId" required><br>
                Product 
                <select name="productId" required >
                    <c:forEach var="p" items="${products}">
                        <option value="${p.product_id}">${p.product_name}</option>
                    </c:forEach>
                </select>
                <br>
                Discount Percentage: <input type="number" name="discountPercentage" step="0.01" required><br>
                Start Date: <input type="date" name="startDate" required><br>
                End Date: <input type="date" name="endDate" required><br>
                <input type="submit" value="Add Sale Off">
            </form>
        </main>



    </body>
</html>