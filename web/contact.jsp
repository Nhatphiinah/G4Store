<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Liên hệ  |  You&Me</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <link rel="stylesheet" href="assets/css/plugins.css">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>

    <body>
        <div class="off_canvars_overlay"></div>
        <jsp:include page="layout/menu.jsp"/>

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

        <div class="contact_area">
            <div class="container">
                <div class="row">
                    <div class="col-lg-6 col-md-12">
                        <div class="contact_message content">
                            <h3>Liên hệ</h3>
                            <ul>
                                <li><i class="fa fa-fax"></i> Đại học FPT</li>
                                <li><i class="fa fa-envelope-o"></i> <a href="mailto:phinvnce181599@fpt.edu.vn">phinvnce181599@fpt.edu.vn</a></li>
                                <li><i class="fa fa-phone"></i> 0909090909</li>
                            </ul>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-12">
                        <div class="contact_message form">
                            <h3>Gửi thông tin phản hồi</h3>
                            <form name="contactForm" onsubmit="return validateFormAndNotify()" id="contact-form" method="POST" action="contact?action=insert">
                                <p>
                                    <label>Địa chỉ email</label>
                                    <span style="font-weight: bolder">${user.user_email}</span>
                                    <input type="hidden" name="user_email" value="${user.user_email}">
                                </p>
                                <p>
                                    <label>Tiêu đề</label>
                                    <input name="subject_report" placeholder="Nhập tiêu đề ..." required type="text">
                                </p>
                                <div class="contact_textarea">
                                    <label>Nội dung</label>
                                    <input placeholder="Nhập nội dung của phản hồi ..." name="content_report" required/>
                                </div>
                                <input hidden name="user_id" required type="text" value="${user.user_id}">
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

        <jsp:include page="layout/footer.jsp"/>

        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="assets/js/plugins.js"></script>
        <script src="assets/js/main.js"></script>
        <script>
                                function validateFormAndNotify() {
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
                                            title: 'Nội dung không cho phép khoảng trắng!',
                                            text: 'Vui lòng nhập nội dung hợp lệ!',
                                            confirmButtonText: 'Đóng'
                                        });
                                        return false;
                                    }
                                    Swal.fire({
                                        icon: 'success',
                                        title: 'Gửi phản hồi thành công!',
                                        text: 'Cảm ơn bạn đã gửi phản hồi.',
                                        confirmButtonText: 'Đóng'
                                    }).then((result) => {
                                        if (result.isConfirmed) {
                                            document.getElementById("contact-form").submit();
                                        }
                                    });
                                    return false;
                                }
        </script>
    </body>
</html>
