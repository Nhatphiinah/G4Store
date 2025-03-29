<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<style>
    a {
        text-decoration: none !important;
    }
    .canvas_open a, .canvas_close a, .dropdown_links a, .top_links a, .cart_link a, .offcanvas_footer a, .header_top a, .header_middel a, .main_menu a {
        text-decoration: none !important;
    }
</style>

<div class="offcanvas_menu">
    <div class="canvas_open">
        <a href="javascript:void(0)"><i class="ion-navicon"></i></a>
    </div>
    <div class="offcanvas_menu_wrapper">
        <div class="canvas_close">
            <a href="javascript:void(0)"><i class="ion-android-close"></i></a>  
        </div>
        <div class="welcome_text">
            <ul>
                <li><span>Giao hàng miễn phí: </span>Hãy tận dụng thời gian của chúng tôi để lưu lại sự kiện </li>
                <li><span>Trả hàng miễn phí: </span> Đảm bảo sự hài lòng</li>
            </ul>
        </div>
        <div class="top_right">
            <ul>
                <li class="top_links"><a href="#">Tài Khoản của tôi <i class="ion-chevron-down"></i></a>
                    <ul class="dropdown_links">
                        <c:if test="${sessionScope.user.user_name!=null}">
                            <li><a href="my-account.html">${sessionScope.user.user_name}</a></li>
                            </c:if>

                        <c:if test="${sessionScope.user.user_name == null}">
                            <li><a href="user?action=myaccount">Tài khoản của tôi</a></li>
                            </c:if>

                        <c:if test="${sessionScope.user == null}">
                            <li><a href="user?action=login">Đăng nhập</a></li>
                            </c:if>

                        <c:if test="${sessionScope.user != null}">
                            <li><a href="user?action=login">Đăng xuất</a></li>
                            </c:if>

                        <c:if test="${fn:toUpperCase(sessionScope.user.isAdmin) == 'TRUE' || fn:toUpperCase(sessionScope.user.isStoreStaff) == 'TRUE'}">
                            <li><a href="dashboard">Quản lý</a></li>
                            </c:if>
                    </ul>
                </li> 
            </ul>
        </div> 
        <div class="search_bar">
            <form action="search?action=search" method="POST">
                <input name="text" placeholder="Tìm kiếm..." type="text">
                <button type="submit"><i class="ion-ios-search-strong"></i></button>
            </form>
        </div>
        <div class="cart_area">
            <div class="cart_link">
                <c:if test="${!user.isAdmin == 'True' && !user.isStoreStaff=='True'}">
                    <a href="cart?action=showcart"><i class="fa fa-shopping-basket">${sessionScope.size}</i>Giỏ Hàng</a>
                </c:if>
            </div>
        </div>
        <div id="menu" class="text-left ">
            <ul class="offcanvas_main_menu">
                <li class="active">
                    <a href="home" style="text-decoration: none">Trang chủ</a>
                </li>
                <li class="active">
                    <a href="search?action=view">Sản phẩm</a>

                </li>
                <li class="menu-item-has-children">
                    <a href="about">Chúng tôi</a>
                </li>
                <li class="menu-item-has-children">
                    <a href="contact">Liên hệ</a> 
                </li>
            </ul>
        </div>
        <div class="offcanvas_footer">
            <span><a href="#"><i class="fa fa-envelope-o"></i> </a></span>
            <ul>
                <li class="facebook"><a href="#"><i class="fa fa-facebook"></i></a></li>
                <li class="twitter"><a href="#"><i class="fa fa-twitter"></i></a></li>
                <li class="pinterest"><a href="#"><i class="fa fa-pinterest-p"></i></a></li>
                <li class="google-plus"><a href="#"><i class="fa fa-google-plus"></i></a></li>
                <li class="linkedin"><a href="#"><i class="fa fa-linkedin"></i></a></li>
            </ul>
        </div>
    </div>
</div>
<!--Offcanvas menu area end-->

<!--header area start-->
<header class="header_area header_three">
    <!--header top start-->
    <div class="header_top">
        <div class="container-fluid">   
            <div class="row align-items-center">
                <div class="col-lg-7 col-md-12">
                    <div class="welcome_text">
                        <ul>
                            <li><span>Giao hàng miễn phí:</span>Hãy tận dụng thời gian của chúng tôi để lưu lại sự kiện </li>
                            <li><span>Trả hàng miễn phí</span> Đảm bảo sự hài lòng</li>
                        </ul>
                    </div>
                </div>
                <div class="col-lg-5 col-md-12">
                    <div class="top_right text-right">
                        <ul>

                            <c:if test="${sessionScope.user != null}">
                                <li class="top_links"><a href="#">Xin chào ${sessionScope.user.user_name}<i class="ion-chevron-down"></i></a>
                                    </c:if>
                                    <c:if test="${sessionScope.user == null}">
                                <li class="top_links"><a href="#">Đăng nhập<i class="ion-chevron-down"></i></a>
                                    </c:if>
                                <ul class="dropdown_links">
                                    <c:if test="${sessionScope.user != null}">
                                        <li><a href="user?action=myaccount">Tài khoản của tôi</a></li>
                                        </c:if>

                                    <c:if test="${fn:toUpperCase(sessionScope.user.isAdmin) == 'TRUE' || fn:toUpperCase(sessionScope.user.isStoreStaff) == 'TRUE'}">
                                        <li><a href="dashboard">Quản lý</a></li>
                                        </c:if>

                                    <c:if test="${sessionScope.user == null}">
                                        <li><a href="user?action=login">Đăng nhập</a></li>
                                        </c:if>

                                    <c:if test="${sessionScope.user != null}">
                                        <li><a href="user?action=logout">Đăng xuất</a></li>
                                        </c:if>


                                </ul>
                            </li> 
                        </ul>
                    </div>   
                </div>
            </div>
        </div>
    </div>
    <!--header top start-->

    <!--header middel start-->
    <div class="header_middel">
        <div class="container-fluid">
            <div class="middel_inner">
                <div class="row align-items-center">
                    <div class="col-lg-4">
                        <div class="search_bar">
                            <form action="search?action=search" method="POST">
                                <input name="text" placeholder="Tìm kiếm..." type="text">
                                <button type="submit"><i class="ion-ios-search-strong"></i></button>
                            </form>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="logo">
                            <a "><img src="assets/img/logo/shopofDoanh.jpg" alt="" width="200px" ></a>
                        </div>
                    </div>
                    <div class="col-lg-4">
                        <div class="cart_area">
                            <div class="cart_link">
                                <c:if test="${!user.isAdmin == 'True' && !user.isStoreStaff=='True'}">
                                    <a href="cart?action=showcart"><i class="fa fa-shopping-basket"></i>${sessionScope.size} Giỏ hàng</a>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="horizontal_menu">
                <div class="left_menu">
                    <div class="main_menu"> 
                        <nav>  
                            <ul>
                                <li><a href="home">Trang chủ<i class="fa"></i></a>
                                </li>
                                <li class="mega_items"><a href="search?action=view">Sản phẩm</a>
                                </li>
                            </ul> 
                        </nav> 
                    </div>
                </div>
                <div class="right_menu">
                    <div class="main_menu"> 
                        <nav>  
                            <ul>
                                <li><a href="about">Chúng tôi</a></li>
                                <li><a href="contact">Liên hệ</a></li>
                            </ul> 
                        </nav> 
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--header middel end-->

    <!--header bottom satrt-->
    <div class="header_bottom sticky-header">
        <div class="container">
            <div class="row align-items-center">
                <div class="col-12">
                    <div class="main_menu_inner">
                        <div class="main_menu"> 
                            <nav>  
                                <ul>
                                    <ul class="menu">
                                        <li><a href="home">Trang chủ</a></li>
                                        <li><a href="search?action=view">Sản phẩm</a></li>
                                        <li><a href="about">Chúng tôi</a></li>
                                        <li><a href="contact">Liên hệ</a></li>
                                    </ul>

                                    <style>
                                        /* Định dạng menu */
                                        .menu {
                                            display: flex; /* Dàn đều các mục theo hàng ngang */
                                            justify-content: space-around; /* Canh đều khoảng cách giữa các mục */
                                            align-items: center; /* Căn giữa theo chiều dọc */
                                            list-style: none; /* Bỏ dấu chấm */
                                            padding: 0;
                                            margin: 0;
                                        }

                                        /* Định dạng chữ trong menu */
                                        .menu li a {
                                            font-size: 15px; /* Làm chữ to hơn */
                                            font-weight: bold; /* Chữ đậm hơn */
                                            text-decoration: none; /* Bỏ gạch chân */
                                            color: black; /* Màu chữ */
                                            padding: 15px 20px; /* Tạo khoảng cách giữa các mục */
                                            display: block; /* Đảm bảo toàn bộ vùng click được áp dụng */
                                            transition: color 0.3s ease-in-out;
                                        }

                                        /* Khi hover vào menu */
                                        .menu li a:hover {
                                            color: orange; /* Khi di chuột vào sẽ đổi màu cam */
                                        }
                                    </style>

                                </ul>   
                            </nav> 
                        </div>
                    </div> 
                </div>
            </div>
        </div>
    </div>

    <style>
        .header_bottom.sticky-header {
            background: rgba(255, 255, 255, 0.2);
            backdrop-filter: blur(5px);
            transition: background 0.3s ease-in-out;
        }
    </style>

    <!--header bottom end-->
</header>