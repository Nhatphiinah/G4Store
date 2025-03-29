<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
        <!-- jQuery -->
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <!-- Popper -->
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <!-- Bootstrap JS -->
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

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
                    <li><a class="app-menu__item" href="commentmanager"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý bình luận</span></a></li>
                    <li><a class="app-menu__item" href="saleoff"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý sale</span></a></li>
                            </c:if>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Quản lý giảm giá</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <button type="button" class="btn btn-primary mb-3" data-toggle="modal" data-target="#addModal">
                Thêm khuyến mại mới
            </button>
            <table border="1">
                <thead>
                    <tr>
                        <th>Sale ID</th>
                        <th>Product Name</th>
                        <th>Discount Percentage</th>
                        <th>Before Sale Price</th>
                        <th>After Sale Price</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${saleOffs}" var="sale">
                        <tr>
                            <td>${sale.sale_id}</td>
                            <td>${sale.product_name}</td>
                            <td>${sale.discount_percentage} %</td>
                            <td>${sale.beforeSalePrice}</td>
                            <td>${sale.afterSalePrice}</td>
                            <td><fmt:formatDate value="${sale.start_date}" pattern="yyyy-MM-dd" /></td>
                            <td><fmt:formatDate value="${sale.end_date}" pattern="yyyy-MM-dd" /></td>
                            <td>
                                <!-- Update Button -->
                                <button type="button" class="btn btn-warning btn-sm" 
                                        data-toggle="modal" 
                                        data-target="#updateModal" 
                                        onclick="showUpdateModal('${sale.sale_id}', '${sale.discount_percentage}', '${sale.start_date}', '${sale.end_date}')">
                                    Sửa
                                </button>

                                <!-- Update Modal -->
                                <div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog" role="document">
                                        <div class="modal-content">

                                            <form action="saleoff" method="post">
                                                <input type="hidden" name="action" value="update">
                                                <input type="hidden" name="saleId" id="update-saleId">

                                                <div class="modal-header">
                                                    <h5 class="modal-title">Update Sale Off</h5>
                                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                        <span aria-hidden="true">&times;</span>
                                                    </button>
                                                </div>

                                                <div class="modal-body">

                                                    <label>Discount Percentage:</label>
                                                    <input type="number" name="discountPercentage" id="update-discountPercentage" step="0.01" max="99.99" class="form-control" required>

                                                    <label>Start Date:</label>
                                                    <input type="date" name="startDate" id="update-startDate" class="form-control" required>

                                                    <label>End Date:</label>
                                                    <input type="date" name="endDate" id="update-endDate" class="form-control" required>

                                                </div>

                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                    <input type="submit" class="btn btn-success" value="Update">
                                                </div>

                                            </form>

                                        </div>
                                    </div>
                                </div>

                                <script>
                                    function showUpdateModal(saleId, discountPercentage, startDate, endDate) {
                                        // Gán giá trị vào form trong modal
                                        document.getElementById('update-saleId').value = saleId;
                                        document.getElementById('update-discountPercentage').value = discountPercentage;
                                        document.getElementById('update-startDate').value = startDate;
                                        document.getElementById('update-endDate').value = endDate;
                                    }
                                </script>

                                <form action="saleoff" method="get">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="saleId" value="${sale.sale_id}">
                                    <input type="submit" value="Xóa" onclick="return confirm('Are you sure you want to delete this sale off?')" style="background-color: red; color: white; border: none; padding: 5px 10px; border-radius: 5px; cursor: pointer;">
                                </form>

                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

            <br/>
            <!-- Add Modal -->
            <div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="addModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">

                        <form action="saleoff" method="post">
                            <input type="hidden" name="action" value="insert">

                            <div class="modal-header">
                                <h5 class="modal-title">Add New Sale Off</h5>
                                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                    <span aria-hidden="true">&times;</span>
                                </button>
                            </div>

                            <div class="modal-body">

                                <div class="form-group">
                                    <label>Sale ID:</label>
                                    <input type="text" name="saleId" class="form-control" required>
                                </div>

                                <div class="form-group">
                                    <label>Product:</label>
                                    <select name="productId" class="form-control" required>
                                        <c:forEach var="p" items="${products}">
                                            <option value="${p.product_id}">${p.product_name}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group">
                                    <label>Discount Percentage:</label>
                                    <input type="number" name="discountPercentage" step="0.01" max="99.99" class="form-control" required>
                                </div>

                                <div class="form-group">
                                    <label>Start Date:</label>
                                    <input type="date" name="startDate" id="add-startDate" class="form-control" required>
                                </div>

                                <div class="form-group">
                                    <label>End Date:</label>
                                    <input type="date" name="endDate" class="form-control" required>
                                </div>

                            </div>

                            <div class="modal-footer">
                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                <input type="submit" class="btn btn-success" value="Add Sale Off">
                            </div>

                        </form>

                    </div>
                </div>
            </div>

            <c:if test="${not empty msg}">
                <p style="color:red">${msg}</p>
            </c:if>

        </main>


    </body>
</html>