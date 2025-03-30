<%-- 
    Document   : category
    Created on : Jun 9, 2024, 3:37:12 PM
    Author     : ZZ
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách danh mục sản phẩm | Quản trị Admin</title>
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
    </head>
    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
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
        <!-- Sidebar menu-->
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

                            </c:if>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách danh mục</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
<!--                            <div class="row element-button"
                                 <div class="col-sm-2">
                                <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                        class="fas fa-print"></i> In dữ liệu</a>
                            </div>-->
                            <div>
                                <a class="btn btn-add btn-sm" data-toggle="modal" data-target="#adddanhmuc"><i
                                        class="fas fa-folder-plus"></i> Thêm danh mục</a>
                            </div>
                        </div>
                        <table class="table table-hover table-bordered" id="sampleTable">
                            <thead>
                                <tr>
                                    <th>ID danh mục</th>
                                    <th>Tên danh mục</th>
                                    <th>Trạng thái</th>
                                    <th>Chức năng</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${category}" var="c">
                                    <tr>
                                        <td>${c.category_id}</td>
                                        <td>${c.category_name}</td>
                                        <td>${c.status == true ? 'Active' : 'InActive'}</td>
                                        <td>
                                            <button class="btn btn-primary btn-sm edit" type="button" data-toggle="modal" data-target="#ModalEditCategory${c.category_id}" title="Sửa"><i class="fas fa-edit"></i></button>
                                            <c:if test="${c.status == true}">
<!--                                            <button class="btn btn-primary btn-sm trash" type="button" title="xóa" value="${c.category_id}">
                                                <i class="fas fa-trash"></i></button></c:if></td>-->

                                    </tr>
                                <div class="modal fade" id="ModalEditCategory${c.category_id}" tabindex="-1" role="dialog" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered" role="document">
                                        <div class="modal-content">
                                            <div class="modal-body">
                                                <form name="editCate" onsubmit="return validateForm2()" action="categorymanager" method="POST">
                                                    <input type="hidden" name="action" value="updatecategory">
                                                    <div class="row">
                                                        <div class="form-group col-md-12">
                                                            <span class="thong-tin-thanh-toan">
                                                                <h5>Chỉnh sửa danh mục</h5>
                                                            </span>
                                                        </div>
                                                    </div>
                                                    <div class="row">
                                                        <div class="form-group col-md-12">
                                                            <label class="control-label">ID</label>
                                                            <input class="form-control" type="text" readonly name="category_id" value="${c.category_id}">
                                                        </div>
                                                        <div class="form-group col-md-12">
                                                            <label class="control-label">Tên danh mục</label>
                                                            <input class="form-control" type="text" name="category_name" value="${c.category_name}">
                                                        </div>
                                                        <div class="form-group col-md-12">
                                                            <label class="control-label">Trạng thái</label>
                                                            <select class="form-control" name="status">
                                                                <option ${c.status == true ? 'selected' : ''} value="1">Active</option>
                                                                <option ${c.status == false ? 'selected' : ''} value="0">InActive</option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                                    <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <script>
                                    document.addEventListener('DOMContentLoaded', function () {
                                        var errorNull = "<%= (session.getAttribute("errorNull") != null) ? session.getAttribute("errorNull") : "" %>";
                                        if (errorNull) {
                                            swal({
                                                title: 'Lỗi!',
                                                text: errorNull,
                                                icon: 'error',
                                                confirmButtonText: 'OK'
                                            });
                                    <% session.removeAttribute("errorNull"); %>
                                        }
                                    });
                                </script>
                            </c:forEach>
                            </tbody>


                        </table>
                        <script>
                            function validateForm() {
                                let name = document.forms["editCate"]["category_name"].value.trim();
                                let name2 = document.forms["insertCate"]["name"].value.trim();
                                if (name === "") {
                                    alert("Tên danh mục không chấp nhận khoảng trắng!");
                                    return false;
                                }
                                if (name2 === "") {
                                    alert("Tên danh mục không chấp nhận khoảng trắng!");
                                    return false;
                                }
                                return true;
                            }
                            function validateForm2() {
                                let name = document.forms["editCate"]["category_name"].value.trim();

                                if (name === "") {
                                    alert("Tên danh mục không chấp nhận khoảng trắng!");
                                    return false;
                                }
                                return true;
                            }
                        </script>
                    </div>
                </div>
            </div>
        </div>
    </main>




    <div class="modal fade" id="adddanhmuc" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         data-backdrop="static" data-keyboard="false">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">

                <div class="modal-body">
                    <div class="row">
                        <div class="form-group  col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Thêm mới danh mục </h5>
                            </span>
                        </div>

                        <div class="form-group col-md-12" >

                            <h2 style="color: red; padding-left: 10px">
                                ${error}</h2>
                            <label class="control-label">Nhập tên danh mục mới</label>
                            <form name="insertCate" onsubmit="return validateForm()" action="productmanager?action=insertcategory" method="post"> 
                                <input class="form-control" type="text" name="name" required>
                                <br>
                                <button class="btn btn-save" type="submit">Lưu lại</button>
                                <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                            </form>
                        </div>
                        <div class="form-group col-md-12">
                            <label class="control-label">Danh mục sản phẩm hiện đang có</label>
                            <ul style="padding-left: 20px;">
                                <c:forEach items="${CategoryData1}" var="cat">
                                    <li>${cat.category_name}</li>
                                    </c:forEach>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Essential javascripts for application to work-->
    <script src="admin/js/jquery-3.2.1.min.js"></script>
    <script src="admin/js/popper.min.js"></script>
    <script src="admin/js/bootstrap.min.js"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="admin/js/main.js"></script>
    <!-- The javascript plugin to display page loading on top-->
    <script src="admin/js/plugins/pace.min.js"></script>
    <!-- Page specific javascripts-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
    <!-- Data table plugin-->
    <script type="text/javascript" src="admin/js/plugins/jquery.dataTables.min.js"></script>
    <script type="text/javascript" src="admin/js/plugins/dataTables.bootstrap.min.js"></script>
    <script type="text/javascript">$('#sampleTable').DataTable();</script>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            var cateStatus = "<%= (session.getAttribute("cateStatus") != null) ? session.getAttribute("cateStatus") : "default" %>";
            if (cateStatus === "succ") {
                swal({
                    title: 'Thành công!',
                    text: 'Thêm thành công!',
                    icon: 'success',
                    confirmButtonText: 'OK'
                });
            }
            if (cateStatus === "err") {
                swal({
                    title: 'Lỗi!',
                    text: 'Thêm thất bại',
                    icon: 'error',
                    confirmButtonText: 'OK'
                });
            }
        });
    </script>
    <%
        if (session.getAttribute("cateStatus") != null) {
            session.removeAttribute("cateStatus");
        }
    %>
    <script>

        jQuery(document).ready(function () {
            // Event delegation for delete buttons
            jQuery(document).on('click', '.trash', function () {
                var categoryId = $(this).attr("value");
                swal({
                    title: "Cảnh báo",
                    text: "Bạn có chắc chắn là muốn xóa danh mục này?",
                    buttons: ["Hủy bỏ", "Đồng ý"],
                }).then((willDelete) => {
                    if (willDelete) {
                        window.location = "categorymanager?action=delete&category_id=" + categoryId;
//                        swal("Đã xóa thành công!", {
//                            icon: "success",
//                        });
                    }
                });
            });
        });
        //Thời Gian
        function time() {
            var today = new Date();
            var weekday = new Array(7);
            weekday[0] = "Chủ Nhật";
            weekday[1] = "Thứ Hai";
            weekday[2] = "Thứ Ba";
            weekday[3] = "Thứ Tư";
            weekday[4] = "Thứ Năm";
            weekday[5] = "Thứ Sáu";
            weekday[6] = "Thứ Bảy";
            var day = weekday[today.getDay()];
            var dd = today.getDate();
            var mm = today.getMonth() + 1;
            var yyyy = today.getFullYear();
            var h = today.getHours();
            var m = today.getMinutes();
            var s = today.getSeconds();
            m = checkTime(m);
            s = checkTime(s);
            nowTime = h + " giờ " + m + " phút " + s + " giây";
            if (dd < 10) {
                dd = '0' + dd
            }
            if (mm < 10) {
                mm = '0' + mm
            }
            today = day + ', ' + dd + '/' + mm + '/' + yyyy;
            tmp = '<span class="date"> ' + today + ' - ' + nowTime +
                    '</span>';
            document.getElementById("clock").innerHTML = tmp;
            clocktime = setTimeout("time()", "1000", "Javascript");

            function checkTime(i) {
                if (i < 10) {
                    i = "0" + i;
                }
                return i;
            }
        }
        //In dữ liệu
        var myApp = new function () {
            this.printTable = function () {
                var tab = document.getElementById('sampleTable');
                var win = window.open('', '', 'height=700,width=700');
                win.document.write(tab.outerHTML);
                win.document.close();
                win.print();
            }
        }
        
       
    </script>
    
</body>

</html>
