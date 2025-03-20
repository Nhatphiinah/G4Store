<%-- 
    Document   : verify.jsp
    Created on : May 28, 2024, 1:07:34 PM
    Author     : Phung An
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Verify Page</title>
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
    <div class="verify" style="width:50%;margin-left: 25% ">
        <div class="account_form">
            <h2>Xác minh</h2>
            <form action="verify" method="POST">
                <p style="color: red; align-content: center;">
                    ${sessionScope.msg2}
                </p>
                <p>   
                    <label>Nhập mã OTP <span>*</span></label>
                    <input name="authcode" type="text">
                </p>
                <div class="login_submit">
                    <button type="submit">Đăng Ký</button>

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



    <!-- Plugins JS -->
    <script src="assets/js/plugins.js"></script>

    <!-- Main JS -->
    <script src="assets/js/main.js"></script>
</body>
