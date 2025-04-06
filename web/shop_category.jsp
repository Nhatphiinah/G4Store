<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Sản Phẩm  | G4Store</title>
        <link rel="shortcut icon" href="assets/img/favicon.ico" type="image/x-icon">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link rel="stylesheet" href="assets/css/plugins.css">
        <link rel="stylesheet" href="assets/css/style.css">
        <style>
            body {
                font-family: 'Roboto', sans-serif;
                background-color: #f8f9fa;
            }
            .navbar {
                box-shadow: 0 2px 4px rgba(0,0,0,.1);
            }
            .sidebar {
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,.05);
                padding: 20px;
            }
            .sidebar h2 {
                font-size: 1.2rem;
                color: #333;
                margin-bottom: 15px;
                border-bottom: 2px solid #007bff;
                padding-bottom: 10px;
            }
            .sidebar ul {
                list-style-type: none;
                padding-left: 0;
            }
            .sidebar ul li {
                margin-bottom: 10px;
                text-decoration: none;
                background-color: #f1f1f1;
                padding: 10px;
                border-radius: 8px;
                transition: background-color 0.3s ease;
            }
            .sidebar ul li a {
                color: #555;
                text-decoration: none;
                display: block;
                width: 100%;
                font-family: serif;
            }
            .sidebar ul li:hover {
                background-color: #007bff;
            }
            .sidebar ul li:hover a {
                color: #fff;
            }
            .form-check {
                margin-bottom: 10px;
                background-color: #f1f1f1;
                padding: 10px;
                border-radius: 8px;
                transition: background-color 0.3s ease;
            }
            .form-check:hover {
                background-color: #007bff;
            }
            .form-check-label {
                color: #555;
            }
            .form-check:hover .form-check-label {
                color: #fff;
            }
            .product-card {
                background-color: #fff;
                border-radius: 8px;
                box-shadow: 0 0 10px rgba(0,0,0,.05);
                transition: transform 0.3s ease;
            }
            .product-card:hover {
                transform: translateY(-5px);
            }
            .product-img {
                height: 200px;
                object-fit: cover;
                border-top-left-radius: 8px;
                border-top-right-radius: 8px;
            }
            .product-title {
                font-size: 1rem;
                margin-top: 15px;
            }
            .product-price {
                font-weight: bold;
                color: #007bff;
            }
            .pagination .page-item.active .page-link {
                background-color: #007bff;
                border-color: #007bff;
            }
            .breadcrumb_content ul li:last-child {
                margin-right: 0;
            }
            .breadcrumb_content ul li a {
                color: #999999;
            }
            .breadcrumb_content ul li a:hover {
                color: #ff6a28;
            }
            .category.active{
                background-color: #007bff;
            }
        </style>


    </head>
    <body>
        <jsp:include page="layout/menu.jsp"/>
        <!--Offcanvas menu area start-->
        <div class="off_canvars_overlay"></div>


        <div class="container my-5">
            <nav aria-label="breadcrumb">
                <ol class="breadcrumb bg-white">
                    <li class="breadcrumb-item"><a href="home">Trang chủ</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Cửa hàng</li>
                </ol>
            </nav>

            <div class="row">
                <div class="col-lg-3">
                    <div class="sidebar mb-4">
                        <h2><i class="fas fa-th-list mr-2"></i>Danh mục</h2>
                        <ul>
                            <li class="category active"><a href="#" data-category="all">TẤT CẢ</a></li>
                                <c:forEach items="${CategoryData}" var="c">
                                <li class="category ${categoryId == c.category_id ? 'active' : ''}"><a href="#" data-category="${c.category_id}">${c.category_name}</a></li>
                                </c:forEach>
                        </ul>

                        <h2 class="mt-4"><i class="fas fa-money-bill mr-2"></i>Giá</h2>
                        <div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="price" value="5" id="price5" ${priceRange eq '5' ? 'checked' : ''}>
                                <label class="form-check-label" for="price1">Tất cả</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="price" value="0" id="price1" ${priceRange eq '0' ? 'checked' : ''}>
                                <label class="form-check-label" for="price1">Dưới 50.000Vnđ</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="price" value="1" id="price2" ${priceRange eq '1' ? 'checked' : ''}>
                                <label class="form-check-label" for="price2">Từ 50.000Vnđ - 200.000Vnđ</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="price" value="2" id="price3" ${priceRange eq '2' ? 'checked' : ''}>
                                <label class="form-check-label" for="price3">Từ 200.000Vnđ - 500.000Vnđ</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="price" value="3" id="price4" ${priceRange eq '3' ? 'checked' : ''}>
                                <label class="form-check-label" for="price4">Từ 500.000 Vnđ - 1.000.000 Vnđ</label>
                            </div>
                            <div class="form-check">
                                <input class="form-check-input" type="radio" name="price" value="4" id="price5" ${priceRange eq '4' ? 'checked' : ''}>
                                <label class="form-check-label" for="price5">1 triệu Vnđ trở lên</label>
                            </div>
                        </div>

                        <h2 class="mt-4"><i class="fas fa-money-bill mr-2"></i>Trạng thái</h2>
                        <div class="status-menu">
                            <!--                            <ul>
                                                            <li><a href="search?action=NoSoldOut">Sản phẩm còn hàng</a></li>
                                                            <li><a href="search?action=SoldOut">Sản phẩm hết hàng</a></li>
                                                            <li><a href="search?action=SaleOff">Sản phẩm Sale off</a></li>
                                                        </ul>-->
                            <div class="form-check">
                                <input class="form-check-input" type="checkbox" name="saleOff" id="saleOff">
                                <label class="form-check-label" for="saleOff">Sale off</label>
                            </div>
                        </div>

                        <!--                        <h2 class="mt-4"><i class="fas fa-palette mr-2"></i>Màu Sắc</h2>
                                                <form action="search?action=SearchByColor" method="POST">
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" name="colors" value="0" id="color1">
                                                        <label class="form-check-label" for="color1">Đỏ</label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" name="colors" value="1" id="color2">
                                                        <label class="form-check-label" for="color2">Xanh</label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" name="colors" value="2" id="color3">
                                                        <label class="form-check-label" for="color3">Trắng</label>
                                                    </div>
                                                    <div class="form-check">
                                                        <input class="form-check-input" type="checkbox" name="colors" value="3" id="color4">
                                                        <label class="form-check-label" for="color4">Đen</label>
                                                    </div>
                                                    <button type="submit" class="btn btn-primary btn-sm mt-3">Lọc</button>
                                                </form>-->
                    </div>
                </div>

                <div class="col-lg-9">
                    <h1 class="mb-4">Sản phẩm</h1>
                    <div class="d-flex justify-content-between align-items-center mb-4">
<!--                        <p class="mb-0">Hiển thị ${ProductData.size()} sản phẩm</p>-->
                        <div class="dropdown">
                            <!--                            <button class="btn btn-outline-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                            Sắp xếp
                                                        </button>-->
                            <select id="dropdownMenuButton" class="form-control">
                                <!--<option>Mặc định</option>-->
                                <option value="asc" ${sort eq 'asc' ? 'selected' : ''}>Giá từ thấp đến cao</option>
                                <option value="desc" ${sort eq 'desc' ? 'selected' : ''}>Giá từ cao đến thấp</option>
                            </select>
                        </div>
                    </div>



                    <div class="row" id="product-list">
                        <c:choose>
                            <c:when test="${empty ProductData}">
                                <div class="col-12 text-center">
                                    <img src="assets/img/logo/no-products-found.jpg" alt="No products found" class="img-fluid mb-3" style="max-width: 200px;">
                                    <p class="lead">Không có sản phẩm nào được tìm thấy</p>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${ProductData}" var="p">
                                    <div class="col-md-4 mb-4">
                                        <div class="card product-card">
                                            <img src="${p.img}" class="card-img-top product-img" alt="${p.product_name}">
                                            <div class="card-body">
                                                <h5 class="card-title product-title">${p.product_name}</h5>
                                                <c:choose>
                                                    <c:when test="${p.discount > 0}">
<!--                                                        <p class="card-text product-price">
                                                            Giá gốc: <del>
<%--<fmt:formatNumber value="${p.product_price}" pattern="#,##0" />--%>
VNĐ</del>
                                                        </p>-->
                                                        <p class="card-text product-final-price">
                                                            Giá: <strong>
                                                                <c:set var="discountedPrice" value="${p.product_price - (p.product_price * p.discount / 100)}" />
                                                                <del style="color: red"><fmt:formatNumber value="${p.product_price}" pattern="#,##0" /> </del>
                                                                <fmt:formatNumber value="${discountedPrice}" pattern="#,##0" /> VNĐ
                                                            </strong>
                                                        </p>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <p class="card-text product-final-price">
                                                            Giá: <strong><fmt:formatNumber value="${p.product_price}" pattern="#,##0" /> VNĐ</strong>
                                                        </p>
                                                    </c:otherwise>
                                                </c:choose>
                                                <a href="search?action=productdetail&product_id=${p.product_id}" class="btn btn-primary btn-sm">Xem chi tiết</a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                                <nav aria-label="navigation">
                                    <ul class="pagination justify-content-end mt-50" id="paging">
                                        <c:forEach begin="1" end="${totalPage}" var="i">
                                            <li class="page-item ${pageIndex == i ? "active":""}"><a
                                                    class="page-link"
                                                    href="#" data-page="${i}">${i}</a></li>
                                            </c:forEach>
                                    </ul>
                                </nav>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
        </div>

        <jsp:include page="layout/footer.jsp"/>

        <script src="assets/js/plugins.js"></script>

        <!-- Main JS -->
        <script src="assets/js/main.js"></script>
        <script>
            $(document).ready(function () {
                function loadProducts(page = 1, category = null) {
                    category = category || $(".category.active a").data("category") || "all";
                    let search = $("#search-input").val();
                    let sort = $("#dropdownMenuButton").val();
                    let price = $("input[name='price']:checked").val();
                    let saleOff = $("#saleOff").is(":checked") ? "true" : "false";

                    $.ajax({
                        url: "products",
                        method: "GET",
                        data: {category, search, sort, price, saleOff, pageIndex: page},
                        success: function (response) {
                            console.log("success");
                            $("#product-list").html(response);
                        },
                        error: function (error) {
                            console.error("AJAX Error:", error);
                        }
                    });
                }

                $(document).on("click", ".category a", function (e) {
                    e.preventDefault(); // Ngăn chặn chuyển hướng trang

                    $(".category").removeClass("active"); // Xóa class active cũ
                    $(this).parent().addClass("active"); // Gán class active cho danh mục mới

                    let category = $(this).data("category"); // Lấy category từ phần tử vừa click
                    loadProducts(1, category); // Gọi hàm với category mới
                });


                $("input[name='price'], #saleOff").on("click change", function () {
                    loadProducts();
                });
                $("#dropdownMenuButton").on("change", function () {
                    loadProducts();
                });

                $("#search-input").on("keyup", function () {
                    loadProducts();
                });

                $(document).on("click", ".pagination a", function (e) {
                    e.preventDefault();
                    let page = $(this).data("page");
                    loadProducts(page);
                });
            });


        </script>
    </body>
</html>