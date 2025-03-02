

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="model.Product" %>
<%@page import="model.Product_Active" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh sách sản phẩm | Quản trị Admin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="admin/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
       <script src="https://cdn.ckeditor.com/4.25.1-lts/standard/ckeditor.js"></script>



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
                    <li><a class="app-menu__item" href="customermanager"><i class='app-menu__icon bx bx-user-voice'></i><span class="app-menu__label">Quản lý khách hàng</span></a></li>
                    <li><a class="app-menu__item" href="reportmanager"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý phản hồi</span></a></li>
                    <li><a class="app-menu__item" href="aboutmanager"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý trang giới thiệu</span></a></li>
                    <li><a class="app-menu__item" href="commentmanager"><i class='app-menu__icon bx bx-receipt'></i><span class="app-menu__label">Quản lý bình luận</span></a></li>
                            </c:if>
            </ul>
        </aside>
        <main class="app-content">
            <div class="app-title">
                <ul class="app-breadcrumb breadcrumb side">
                    <li class="breadcrumb-item active"><a href="#"><b>Danh sách sản phẩm</b></a></li>
                </ul>
                <div id="clock"></div>
            </div>
            <div class="row">
                <div class="col-md-12">
                    <div class="tile">
                        <div class="tile-body">
                            <div class="row element-button">
                                <div class="col-sm-2">
                                    <a class="btn btn-add btn-sm" href="productmanager?action=insert" title="Thêm"><i class="fas fa-plus"></i>
                                        Tạo mới sản phẩm</a>
                                </div>

                                <!-- UploadExcelBtn -->
                                <!-- UploadExcelBtn -->
                                <!-- UploadExcelBtn -->
                                <div class="col-sm-2">
                                    <a class="btn btn-add btn-sm"  title="ThêmExcel"
                                       data-toggle="modal" data-target="#uploadModal"><i class="fas fa-plus"></i>
                                        Tạo mới sản phẩm bằng Excel(chỉ xlsx)</a>
                                </div>
                                <!-- UploadExcelBtn -->
                                <!-- uploadModal -->
                                <div class="modal fade" id="uploadModal" tabindex="-1" aria-labelledby="uploadModalLabel" aria-hidden="true">
                                    <div class="modal-dialog modal-dialog-centered">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" style="color: green" id="uploadModalLabel">Upload Excel File</h5>
                                                <button type="button" class="btn btn-cancel btn-cancel" data-dismiss="modal" aria-label="Close">
                                                    <span aria-hidden="true">&times;</span>
                                                </button>
                                            </div>
                                            <div class="modal-body">
                                                <form action="productmanager?action=insertByExcel" method="POST" enctype="multipart/form-data">
                                                    <div class="form-group">
                                                        <label for="fileInput">Choose file</label>
                                                        <input type="file"  accept=".xlsx" "class="form-control-file" id="fileInput" name="file" required>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary">Upload</button>
                                                    <a style="margin: 10px" href="productmanager?action=dowloadTemplate" class="btn btn-primary">Download Template File</a>
                                                </form>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- uploadModal -->   
                                <!-- UploadExcelBtn -->
                                <!-- UploadExcelBtn -->


                                <div class="col-sm-2">
                                    <a class="btn btn-delete btn-sm print-file" type="button" title="In" onclick="myApp.printTable()"><i
                                            class="fas fa-print"></i> In dữ liệu</a>
                                </div>
                            </div>
                            <form action="productmanager" method="POST" enctype="multipart/form-data">
                                <input type="hidden" name="action" value="updateproduct">
                                <table class="table table-hover table-bordered" id="sampleTable">
                                    <thead>
                                        <tr>
                                            <th>Mã sản phẩm</th>
                                            <th>Danh mục</th>
                                            <th>Tên sản phẩm</th>
                                            <th>Giá</th>
                                            <th>Size</th>
                                            <th>Màu</th>
                                            <th>Thông tin</th>
                                            <th>Số lượng</th>
                                            <th>Ảnh</th>
                                            <th>Trạng thái</th>
                                            <th>Chức năng</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${ProductData}" var="p">
                                            <tr>
                                                <td>${p.product_id}</td>
                                                <td>${p.cate.category_name}</td>
                                                <td>${p.product_name}</td>
                                                <td>${p.product_price}</td>
                                                <td>
                                                    <c:forEach items="${SizeData}" var="s">
                                                        <c:if test="${p.product_id==s.product_id}">
                                                            ${s.size}   
                                                        </c:if>
                                                    </c:forEach>
                                                </td>

                                                <td>
                                                    <c:forEach items="${ColorData}" var="c">
                                                        <c:if test="${p.product_id==c.product_id}">
                                                            ${c.color}   
                                                        </c:if>
                                                    </c:forEach>
                                                </td>
                                                <td>${p.product_describe}</td>
                                                <td>${p.quantity}</td>
                                                <td><img src="${p.img}" alt="" width="100px;"></td>
                                                <td>
                                                    <c:forEach items="${ActiveData}" var="s">
                                                        <c:if test="${p.product_id==s.product_id}">
                                                            ${s.acitve}   
                                                        </c:if>
                                                    </c:forEach>
                                                </td>

                                                <td>
                                                    <button class="btn btn-primary btn-sm trash" type="button" title="Xóa" value="${p.product_id}"><i
                                                            class="fas fa-trash-alt"></i>
                                                    </button>
                                                    <button class="btn btn-primary btn-sm edit" type="button" title="Sửa" id="show-emp"
                                                            data-toggle="modal" data-target="#ModalUP${p.product_id}"><i class="fas fa-edit"></i>
                                                    </button>
                                                </td>
                                            </tr>

                                            <!--
                                            MODAL
                                            -->

                                        <div class="modal fade" id="ModalUP${p.product_id}" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                                             data-keyboard="false">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <form name="editProduct" onsubmit="return validateForm()" action="productmanager" method="POST">
                                                            <input type="hidden" name="action" value="updateproduct">
                                                            <div class="row">
                                                                <div class="form-group  col-md-12">
                                                                    <span class="thong-tin-thanh-toan">
                                                                        <h5>Chỉnh sửa thông tin sản phẩm</h5>
                                                                    </span>
                                                                </div>
                                                            </div>
                                                            <div class="row">
                                                                <div class="form-group col-md-6">
                                                                    <label class="control-label">Mã sản phẩm </label>
                                                                    <input class="form-control" type="text" readonly name="product_id" value="${p.product_id}">
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label for="exampleSelect1" class="control-label">Danh mục</label>
                                                                    <select name="category_id" class="form-control"  id="exampleSelect1">
                                                                        <option value="">-- Chọn danh mục --</option>
                                                                        <c:forEach items="${CategoryData}" var="cat">
                                                                            <option value="${cat.category_id}" <c:if test="${cat.category_id == p.cate.category_id}">selected</c:if>>${cat.category_name}</option>
                                                                        </c:forEach>
                                                                    </select>
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label class="control-label">Tên sản phẩm</label>
                                                                    <input class="form-control" type="text" name="product_name" required value="${p.product_name}">
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label class="control-label">Giá</label>
                                                                    <input class="form-control" type="number" name="product_price" min="1000" required value="${p.product_price}">
                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label class="control-label">Màu</label>
                                                                    <input class="form-control" name="product_color" type="text" value="<c:forEach items="${ColorData}" var="c"><c:if test="${p.product_id==c.product_id}">${c.color},</c:if></c:forEach>">
                                                                        </div>

                                                                        <div class="form-group col-md-6">
                                                                            <label class="control-label">Size</label>
                                                                            <input class="form-control" name="product_size" type="text" value="<c:forEach items="${SizeData}" var="s"><c:if test="${p.product_id==s.product_id}">${s.size},</c:if></c:forEach>">
                                                                        </div>

                                                                        <div class="form-group col-md-6">
                                                                            <label class="control-label">Thông tin</label>
                                                                            <textarea class="ckeditor" name="product_describe">${p.product_describe}</textarea>
                                                                    <script>
                                                                        CKEDITOR.replace('product_describe');
                                                                    </script>

                                                                </div>
                                                                <div class="form-group col-md-6">
                                                                    <label class="control-label">Trạng thái</label>
                                                                    <select name="permission" class="form-control" id="exampleSelect1">
                                                                        <option value="True">Bật</option>
                                                                        <option value="False">Tắt</option>
                                                                    </select>
                                                                </div>

                                                                <div class="form-group col-md-6">
                                                                    <label class="control-label">Số lượng</label>
                                                                    <input class="form-control" type="number" min="1" name="product_quantity" value="${p.quantity}">
                                                                </div>
                                                                <!--anh san pham-->
                                                                <div class="form-group col-md-12">
                                                                    <label class="control-label">Ảnh sản phẩm</label>
                                                                    <div id="myfileupload">
                                                                        <input type="file" id="uploadfile" name="product_img" value="${p.img}" onchange="readURL(this);" />
                                                                    </div>
                                                                    <div id="thumbbox">
                                                                        <img height="450" width="400" alt="Thumb image" id="thumbimage" style="display: none" />
                                                                        <a class="removeimg" href="javascript:"></a>
                                                                    </div>
                                                                    <div id="boxchoice">
                                                                        <a href="javascript:" class="Choicefile"><i class="fas fa-cloud-upload-alt"></i> Chọn ảnh</a>
                                                                        <p style="clear:both"></p>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <BR>
                                                            <button class="btn btn-save" type="submit">Lưu lại</button>
                                                            <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                                                            <BR>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!--
                                      MODAL
                                        -->

                                        <script>
                                            document.addEventListener('DOMContentLoaded', function () {
                                                var errorNull = "<%= (session.getAttribute("errorNull") != null) ? session.getAttribute("errorNull") : "" %>";
                                                if (errorNull) {
                                                    Swal.fire({
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
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>



        <!-- Essential javascripts for application to work-->
        <script src="admin/js/jquery-3.2.1.min.js"></script>
        <script src="admin/js/popper.min.js"></script>
        <script src="admin/js/bootstrap.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <script src="admin/js/main.js"></script>
        <!-- The javascript plugin to display page loading on top-->
        <script src="admin/js/plugins/pace.min.js"></script>
        <!-- Page specific javascripts-->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        <!-- Data table plugin-->
        <script type="text/javascript" src="admin/js/plugins/jquery.dataTables.min.js"></script>
        <script type="text/javascript" src="admin/js/plugins/dataTables.bootstrap.min.js"></script>
         <script>
    document.addEventListener('DOMContentLoaded', function () {
        var insertStatus = "<%= (session.getAttribute("insertStatus") != null) ? session.getAttribute("insertStatus") : "default" %>";
        var insertStatus = "<%= (session.getAttribute("insertStatus") != null) ? session.getAttribute("insertStatus") : "default" %>";
        if (insertStatus==="succ") {
            swal({
                title: 'Thành công!',
                text: 'Thêm thành công!',
                icon: 'success',
                confirmButtonText: 'OK'
            });
        }
        if (insertStatus==="err") {
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
                                            if (session.getAttribute("insertStatus") != null) {
                                                session.removeAttribute("insertStatus");
                                            }
                                        %>
        <script type="text/javascript">
                                          $('#sampleTable').DataTable();
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
        </script>
        <script>

            $(document).ready(jQuery(function () {
                jQuery(document).on('click', '.trash', function () {
                    swal({
                        title: "Cảnh báo",
                        text: "Bạn có chắc chắn là muốn xóa sản phẩm này?",
                        buttons: ["Hủy bỏ", "Đồng ý"],
                    })
                            .then((willDelete) => {
                                if (willDelete) {
                                    window.location = "productmanager?action=deleteproduct&product_id=" + $(this).attr("value");
                                    swal("Đã xóa thành công.!", {
                                        icon: "success",
                                    });
                                }
                            });
                });
            }));
        </script>
        <script>
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
        <%
        // Retrieve the 'rowsAdded' parameter from the request
        String rowsAddedParam = request.getParameter("rowsAdded");
        int rowsAdded = -2;
        if (rowsAddedParam != null) {
            try {
                rowsAdded = Integer.parseInt(rowsAddedParam);
            } catch (NumberFormatException e) {
                // Handle exception if the parameter is not a valid integer
                rowsAdded = -1;
            }
        }
    %>
        <script>
        $(document).ready(function () {
            // Use Java code to embed the number of rows added
            var rowsAdded = <%= rowsAdded %>;

            // If 'rowsAdded' is greater than zero, show the notification
            if (rowsAdded > 0) {
                swal({
                    title: "Thành công",
                    text: rowsAdded + " sản phẩm đã được thêm mới!",
                    icon: "success"
                });
            }if(rowsAdded === 0){
                swal({
                    title: "Thất bại",
                    text: rowsAdded + " 0 sản phẩm đã được thêm mới!",
                    icon: "error"
                });
            }
        });
    </script>

    </body>

</html>