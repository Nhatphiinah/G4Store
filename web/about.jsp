<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html class="no-js" lang="en">
    <head>
        <!-- Meta Tags -->
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Giới Thiệu | You&Me</title>
        <meta name="description" content="Giới thiệu về You&Me Shop - Thương hiệu thời trang Việt Nam với sản phẩm chất lượng cao và thiết kế độc đáo.">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">
        <!-- CSS -->
        <link rel="stylesheet" href="assets/css/plugins.css">
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <!-- Header & Menu -->
        <jsp:include page="layout/menu.jsp"/>

        <!-- Breadcrumbs -->
        <div class="breadcrumbs_area other_bread">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="breadcrumb_content">
                            <ul>
                                <li><a href="home">Trang chủ</a></li>
                                <li>/</li>
                                <li>Giới thiệu</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- About Section -->
        <div class="about_section">
           <div class="container">
                <c:forEach items="${listabout}" var="c">
                <div class="row">
                    <div class="col-lg-6 col-md-12">
                        <div class="about_content">
                             <h1>${c.title}</h1>
                            <p>${c.content}</p>
                        </div>
                    </div>
                    <div class="col-lg-6 col-md-12">
                        <div class="about_thumb">
                            <img src=${c.img} alt="Hình ảnh về You&Me Shop">
                        </div>
                    </div>
                </div>
                </c:forEach>
            </div>
        </div>



        <!-- Footer -->
        <jsp:include page="layout/footer.jsp"/>

        <!-- JS -->
        <script src="assets/js/plugins.js"></script>
        <script src="assets/js/main.js"></script>
    </body>
</html>