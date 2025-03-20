<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Xác Minh || You&Me</title>
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
    </div>
    <div class="customer_login">
        <div class="container">
            <div class="row">
                <div class="forgot" style="width:50%;margin-left: 25% ">
                    <div class="account_form">
                        <h2>Quên Mật Khẩu</h2>
                        <form action="fpass" method="POST">
                            <p style="color: red; align-content: center;">
                                <b> ${sessionScope.msg}</b>
                            </p>
                            <p>
                            <p style="color:red" ><b>${requestScope.Recaptcha}</b></p>
                            <label>Nhập Email của bạn <span>*</span></label>
                            <input name="email" type="text">
                            </p>
                            <input type="hidden" name="g-recaptcha-response" id="g-recaptcha-response">
                            <div class="g-recaptcha" data-sitekey="6LefS_ApAAAAAG7d6P0zRbGoed8oK2dwNnez6agN"></div>
                            <div class="login_submit">
                                <button type="submit">Đặt lại mật khẩu</button>
                            </div>
                        </form>
                    </div>    
                </div>
            </div>
        </div>
    </div>


    <!-- JS
    ============================================ -->


    <!--map js code here-->
    <script src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAdWLY_Y6FL7QGW5vcO3zajUEsrKfQPNzI"></script>
    <script  src="https://www.google.com/jsapi"></script>
    <script src="assets/js/map.js"></script>


    <!-- Plugins JS -->
    <script src="assets/js/plugins.js"></script>

    <!-- Main JS -->
    <script src="assets/js/main.js"></script>

    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    <script>
        function onSubmit() {
            var response = grecaptcha.getResponse();
            document.getElementById('g-recaptcha-response').value = response;
        }
    </script>
</body>

</html>
