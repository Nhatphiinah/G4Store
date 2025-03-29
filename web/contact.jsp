<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Liên hệ  |  G4Store</title>
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

        <!--Offcanvas menu area start-->
        <div class="off_canvars_overlay"></div>
        <jsp:include page="layout/menu.jsp"/>
        <!--breadcrumbs area start-->
        <div class="breadcrumbs_area other_bread">
            <div class="container">   
                <div class="row">
                    <div class="col-12">
                        <div class="breadcrumb_content">
                            <ul>
                                <li><a href="home">Trang chủ</a></li>
                                <li>/</li>
                                <li>Liên hệ</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>         
        </div>
        <!--breadcrumbs area end-->


        <!--contact area start-->
        <div class="contact_area">
            <div class="container">   
                <div class="row">
                    <div class="col-lg-6 col-md-12">
                        <div class="contact_message content">
                            <h3>Liên hệ</h3>
                            <ul>
                                <li><i class="fa fa-fax"></i>  Đại học FPT</li>
                                <li><i class="fa fa-envelope-o"> </i> <a href="mailto:phinvnce181599@fpt.edu.vn">phinvnce181599@fpt.edu.vn</a></li>
                                <li><i class="fa fa-phone"></i> 0348956373</li>                        
                            </ul>             
                        </div> 
                    </div>
                    <div class="col-lg-6 col-md-12">
                        <div class="contact_message form">
                            <h3>Gửi thông tin phản hồi</h3> 

                            <form name="contactForm" onsubmit="return validateForm()" id="contact-form" method="POST" action="contact?action=insert">

                                <p>      
                                    <label>  Địa chỉ email</label>
                                    <input style="font-weight: bolder" name="user_email" readonly type="text" value="${user.user_email}">
                                </p>

                                <p>          
                                    <label>  Tiêu đề</label>
                                    <input name="subject_report" placeholder="Nhập tiêu đề ..." required type="text">
                                </p>    
                                <div class="contact_textarea">
                                    <label>  Nội dung</label>
                                    <input placeholder="Nhập nội dung của phản hồi ..." name="content_report" required/>  
                                </div>
                                <input hidden name="user_id" require type="text" value="${user.user_id}">

                                <c:if test="${not empty requestScope.msgc}">
                                    <label style="color: green; font-size: 15px">${msgc}</label>
                                </c:if>
                                <br>
                                <c:if test="${!user.isAdmin == 'True' && !user.isStoreStaff=='True'}">
                                    <button type="submit">Gửi</button>
                                </c:if>

                            </form>    


                        </div> 
                    </div>
                </div>
            </div>    
        </div>
        <!--contact area end-->

        <!--contact map start-->
        <!--contact map end-->

        <jsp:include page="layout/footer.jsp"/>

        <!-- JS
        ============================================ -->
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <!-- Plugins JS -->
        <script src="assets/js/plugins.js"></script>

        <!-- Main JS -->
        <script src="assets/js/main.js"></script>
        <script>
                                function doGet(e) {
                                    var email = e.parameter.Email;
                                    var subject = e.parameter.Subject;
                                    var message = e.parameter.Message;

                                    // Tạo nội dung email
                                    var emailContent = 'Email: ' + email + '\n\n' + 'Subject: ' + subject + '\n\n' + 'Message: ' + message;

                                    // Gửi email
                                    MailApp.sendEmail({
                                        to: 'dtran4673@gmail.com', // Thay bằng địa chỉ email của bạn
                                        subject: subject,
                                        body: emailContent
                                    });

                                    // Trả về phản hồi
                                    return ContentService.createTextOutput('Success');
                                }

        </script>
        <script>
            function validateForm() {
                var subject = document.forms["contactForm"]["subject_report"].value.trim();
                var content = document.forms["contactForm"]["content_report"].value.trim();
                if (subject === "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Tiêu đề không cho phép khoảng trắng!',
                        text: 'Vui lòng nhập tiêu đề hợp lệ!',
                        confirmButtonText: 'Đóng'
                    });
                    return false;
                }
                if (content === "") {
                    Swal.fire({
                        icon: 'error',
                        title: 'Nội dung dùng không cho phép khoảng trắng!',
                        text: 'Vui lòng nhập nội dung hợp lệ!',
                        confirmButtonText: 'Đóng'
                    });
                    return false;
                }
                return true;
            }
        </script>

    </body>

</html>