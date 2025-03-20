<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Đăng nhập  |  You&Me</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

        <!-- CSS 
        ========================= -->
        <!-- Plugins CSS -->
        <link rel="stylesheet" href="assets/css/plugins.css">
        <!-- Main Style CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <div class="off_canvars_overlay"></div>
        <jsp:include page="layout/menu.jsp"/>
        <!--breadcrumbs area start-->
        <div class="breadcrumbs_area other_bread">
            <div class="container">   
                <div class="row">
                    <div class="col-12">
                        <div class="breadcrumb_content">
                            <ul>
                                <li><a href="home">home</a></li>
                                <li>/</li>
                                <li>Đăng nhập</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>         
        </div>
        <!--breadcrumbs area end-->

        <!-- customer login start -->
        <div class="customer_login">
            <div class="container">
                <div class="row">
                    <!--login area start-->
                    <div class="col-lg-6 col-md-6">
                        <div class="account_form">
                            <h2>Đăng nhập</h2>
                            <c:set var="cookie" value="${pageContext.request.cookies}"/>
                                <form action="user?action=checkLogin" method="POST">
                                    <p style="color: red; align-content: center;">
                                        ${requestScope.error}
                                    </p>
                                    <p>   
                                        <label>Email <span></span></label>
                                        <input name="user_email" type="email" value="${cookie.email.value}">
                                    </p>
                                    <p>   
                                        <label>Mật khẩu <span></span></label>
                                        <input name="user_pass" type="password" value="${cookie.pass.value}">
                                    </p>   
                                    <div class="login_submit">
                                        <a href="forgotpass.jsp">Quên mật khẩu</a>
                                        <label for="remember">
                                            <input ${(cookie.rem.value eq 'ON')?"checked":""} id="remember" name="remember" value="ON" type="checkbox">
                                            Nhớ tài khoản
                                        </label>
                                        <button type="submit">Đăng nhập</button>

                                    </div>

                                </form>
                        </div>    
                    </div>
                    <!--login area start-->

                    <!--register area start-->
                    <div class="col-lg-6 col-md-6">
                        <div class="account_form register">
                            <h2>Đăng ký</h2>
                            <form action="user?action=signup" method="POST">
                                <p>   
                                    <label>Email <span></span></label>
                                    <input type="email" name="user_email" placeholder="Nhập email của bạn" required>
                                </p>                                                               
                                <p>   
                                    <label>Mật khẩu <span></span></label>
                                    <input type="password" name="user_pass" placeholder="Nhập mật khẩu dạng:a-zA-Z0-9..." required>
                                </p>
                                <p>   
                                    <label>Nhập lại mật khẩu <span></span></label>
                                    <input type="password" name="re_pass" placeholder="Nhập lại mật khẩu">
                                </p>
                                <button type="submit"  onclick="onSubmit()">Đăng Ký</button></div>
                            </form>
                        </div>    
                    </div>
                    <!--register area end-->
                </div>
            </div>    
        </div>
        <!-- customer login end -->

        <!--footer area start-->
        <jsp:include page="layout/footer.jsp"/>
        <!--footer area end-->

        <!-- JS
        ============================================ -->


        <!--map js code here-->
        <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdWLY_Y6FL7QGW5vcO3zajUEsrKfQPNzI"></script>
        
         <script src="https://www.google.com/recaptcha/api.js" async defer></script>
         
        <script  src="https://www.google.com/jsapi"></script>
        <script src="assets/js/map.js"></script>
        <script>
            function onSubmit() {
            var response = grecaptcha.getResponse();
            document.getElementById('g-recaptcha-response').value = response;
        }
        </script>

        <!-- Plugins JS -->
        <script src="assets/js/plugins.js"></script>

        <!-- Main JS -->
        <script src="assets/js/main.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
        function showNotification(message, isSuccess) {
            Swal.fire({
                title: isSuccess ? 'Thành công!' : 'Lỗi!',
                text: message,
                icon: isSuccess ? 'success' : 'error',
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
                didOpen: (toast) => {
                    toast.addEventListener('mouseenter', Swal.stopTimer)
                    toast.addEventListener('mouseleave', Swal.resumeTimer)
                }
            });
        }

// Kiểm tra và hiển thị thông báo khi trang được tải
        document.addEventListener('DOMContentLoaded', function () {
            var error_match = "${sessionScope.error_match}";
            var Recaptcha = "${sessionScope.Recaptcha}";
            var error_rePass = "${sessionScope.error_rePass}";
            var msg = "${sessionScope.msg}";
            var signupMessage = "${sessionScope.signupMessage}";
            var error_exist = "${sessionScope.error_exist}";
            var error_ban = "${sessionScope.error_ban}";

            if (error_match) {
                showNotification(error_match, false);
        <% session.removeAttribute("error_match"); %>
            }  if (Recaptcha) {
           
                showNotification(Recaptcha, false);
        <% session.removeAttribute("Recaptcha"); %>
            } else if (error_rePass) {
                showNotification(error_rePass, false);
        <% session.removeAttribute("error_rePass"); %>
            }
             else if (msg) {
                showNotification(msg, false);
        <% session.removeAttribute("msg"); %>
            }
             else if (error_exist) {
                showNotification(error_exist, false);
        <% session.removeAttribute("error_exist"); %>
            }
             else if (error_ban) {
                showNotification(error_ban, false);
        <% session.removeAttribute("error_ban"); %>
            }
             else if (signupMessage) {
                showNotification(signupMessage, true);
        <% session.removeAttribute("signupMessage"); %>
            }
        });
    </script>


    </body>

</html>
