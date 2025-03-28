

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Danh sách nhân viên | Quản trị Admin</title>
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
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách người dùng</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>

            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">

                            <div class="row element-button">
                                <c:if test="${sessionScope.user.isAdmin.equalsIgnoreCase('true')}"><div class="col-sm-2">
                                        <a class="btn btn-add btn-sm" data-toggle="modal" data-target="#adddanhmuc"><i
                                                class="fas fa-folder-plus"></i> Thêm nhân viên</a></div></c:if>
                                    <div class="col-sm-2">
                                        <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                                class="fas fa-print"></i> In dữ liệu</a>
                                    </div>
                                </div>
                            <c:if test="${sessionScope.user.isAdmin.equalsIgnoreCase('true')}">
                                <div class="form-group">
                                    <label for="userRoleFilter"></label>
                                    <select class="form-control" id="userRoleFilter">
                                        <option value="">Tất cả</option>
                                        <option value="ADMIN" <c:if test="${param.userRoleFilter == 'ADMIN'}">selected</c:if>>Quản trị viên</option>
                                        <option value="STORE_STAFF" <c:if test="${param.userRoleFilter == 'STORE_STAFF'}">selected</c:if>>Nhân viên cửa hàng</option>
                                        <option value="USER" <c:if test="${param.userRoleFilter == 'USER'}">selected</c:if>>Khách hàng</option>
                                        </select>
                                    </div>
                            </c:if>

                            <table class="table table-hover table-bordered js-copytextarea" cellpadding="0" cellspacing="0" border="0"
                                   id="sampleTable">
                                <thead>
                                    <tr>
                                        <th>ID khách hàng</th>
                                        <th>Tên khách hàng</th>
                                        <th>Email</th>
                                        <th>Quản trị viên</th>
                                        <th>Nhân viên cửa hàng</th>
                                        <th>Ngày sinh</th>
                                        <th>Địa chỉ</th>                                      
                                        <th>Số điện thoại</th>
                                        <th>Trạng thái</th>
                                        <th>Lý do</th>
                                        <th width="70">Tính năng</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${user}" var="u">
                                        <tr>
                                            <td>${u.user_id}</td>
                                            <td>${u.user_name}</td>
                                            <td>${u.user_email}</td>
                                            <td>${u.isAdmin}</td>
                                            <td>${u.isStoreStaff}</td>
                                            <td>${u.dateOfBirth}</td>
                                            <td>${u.address}</td>
                                            <td>${u.phoneNumber}</td>
                                            <td>${u.banned ? 'Bị cấm' : 'Hoạt động'}</td>
                                            <td>${u.adminReason}</td>
                                            <td class="text-center">
    <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp" data-toggle="modal"
        data-target="#ModalUP${u.user_id}">
        <i class="fas fa-edit"></i>
    </button>
</td>


                                            <td>

                                                <c:if test="${!u.banned}">
                                                    <button class="btn btn-warning btn-sm ban" type="button" title="Cấm" value="${u.user_id}"><i class="fas fa-ban"></i></button>
                                                    </c:if>
                                                    <c:if test="${u.banned}">
                                                    <button class="btn btn-success btn-sm unban" type="button" title="Bỏ cấm" value="${u.user_id}"><i class="fas fa-check"></i></button>
                                                    </c:if>
                                            </td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </div>
        </main>
        <c:forEach items="${user}" var="u">           
            <div class="modal fade" id="ModalUP${u.user_id}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                 data-keyboard="false">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <form method="POST" action="customermanager?action=update">
                            <div class="modal-body">
                                <div class="row">
                                    <div class="form-group  col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Cập nhật quyền người dùng</h5>
                                        </span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="form-group col-md-6">
                                        <label for="exampleSelect1" class="control-label">Quyền quản trị</label>
                                        <input hidden name="user_id" value="${u.user_id}">
                                        <select name="permission" class="form-control" id="exampleSelect1">
                                            <option value="True">Cho phép</option>
                                            <option value="False">Hủy bỏ</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="exampleSelect2" class="control-label">Quyền nhân viên cửa hàng</label>
                                        <select name="storeStaffPermission" class="form-control" id="exampleSelect2">
                                            <option value="TRUE">Cho phép</option>
                                            <option value="FALSE">Hủy bỏ</option>
                                        </select>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="adminReason" class="control-label">Lý do</label>
                                        <input name="adminReason" class="form-control" id="adminReason" required>
                                    </div>
                                </div>
                                <BR>
                                <button class="btn btn-save" type="submit">Lưu lại</button>
                                <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                                <BR>
                            </div>
                        </form>
                        <div class="modal-footer">
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="modal fade" id="adddanhmuc" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
             data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">

                    <div class="modal-body">
                        <div class="row">
                            <div class="form-group  col-md-12">
                                <span class="thong-tin-thanh-toan">
                                    <h5>Thêm mới nhân viên </h5>
                                </span>
                            </div>

                            <div class="form-group col-md-12" >

                                <h2 style="color: red; padding-left: 10px">
                                    ${error}</h2>
                               
                                <form name="insertCate" action="customermanager?action=insertStaff" method="post"> 
                                     <label class="control-label">Tên</label>
                                    <input class="form-control" type="text" name="name" required>
                                    <label class="control-label">Email</label>
                                    <input class="form-control" type="email" name="email" required>
                                    <br>
                                    <button class="btn btn-save" type="submit">Lưu lại</button>
                                    <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                                </form>
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

            $(document).ready(function () {
                $('#sampleTable').DataTable();

                // Event delegation for delete button
                jQuery(document).on("click", ".trash", function () {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn muốn xóa nhân viên này?",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    }).then((willDelete) => {
                        if (willDelete) {
                            var userId = $(this).val();
                            window.location = "customermanager?action=delete&user_id=" + userId;
                            // Optionally show a success message after deletion
                            swal("Đã xóa thành công!", {
                                icon: "success",
                            });
                        }
                    });
                });

                // Event delegation for ban button
                jQuery(document).on("click", ".ban", function () {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn muốn cấm nhân viên này?",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    }).then((willBan) => {
                        if (willBan) {
                            var userId = $(this).val();
                            window.location = "customermanager?action=ban&user_id=" + userId;
                            // Optionally show a success message after banning
                            swal("Nhân viên đã bị cấm!", {
                                icon: "success",
                            });
                        }
                    });
                });

                // Event delegation for unban button
                jQuery(document).on("click", ".unban", function () {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn muốn bỏ cấm nhân viên này?",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    }).then((willUnban) => {
                        if (willUnban) {
                            var userId = $(this).val();
                            window.location = "customermanager?action=unban&user_id=" + userId;
                            // Optionally show a success message after unbanning
                            swal("Nhân viên đã được bỏ cấm!", {
                                icon: "success",
                            });
                        }
                    });
                });
            });

        </script>
        <script>
            document.getElementById('roleFilter').addEventListener('change', function () {
                const selectedRole = this.value;
                const tableRows = document.querySelectorAll('#sampleTable tbody tr');

                tableRows.forEach(row => {
                    const userRoleCell = row.querySelector('td:nth-child(4)');
                    const userRole = userRoleCell.textContent.trim();

                    if (selectedRole === '' || userRole === selectedRole) {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            });
        </script>
        <script>
            document.getElementById('userRoleFilter').addEventListener('change', filterTable);

            function filterTable() {
                const selectedRole = document.getElementById('userRoleFilter').value;
                const tableRows = document.querySelectorAll('#sampleTable tbody tr');

                tableRows.forEach(row => {
                    const userRoleCell = row.querySelector('td:nth-child(4)');
                    const userRole = userRoleCell.textContent.trim() === 'TRUE' ? 'ADMIN' : '';

                    const userStoreStaffCell = row.querySelector('td:nth-child(5)');
                    const userStoreStaff = userStoreStaffCell.textContent.trim() === 'TRUE' ? 'STORE_STAFF' : '';

                    const actualRole = userRole || userStoreStaff || 'USER';

                    if (selectedRole === '' || actualRole === selectedRole) {
                        row.style.display = '';
                    } else {
                        row.style.display = 'none';
                    }
                });
            }

            document.getElementById('userStatusFilter').addEventListener('change', function () {
                var statusFilter = this.value.toLowerCase();
                var table = document.getElementById('sampleTable');
                var rows = table.getElementsByTagName('tr');
                for (var i = 1; i < rows.length; i++) {
                    var cells = rows[i].getElementsByTagName('td');
                    var statusCell = cells[8]; // Adjust index as per your table structure
                    var userStatus = statusCell.textContent.trim().toLowerCase();
                    if (statusFilter === '' || userStatus === statusFilter) {
                        rows[i].style.display = '';
                    } else {
                        rows[i].style.display = 'none';
                    }
                }
            });
        </script>
    </body>

</html>
