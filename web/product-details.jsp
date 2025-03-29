<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!doctype html>
<html class="no-js" lang="en">

    <head>
        <meta charset="utf-8">
        <meta http-equiv="x-ua-compatible" content="ie=edge">
        <title>Thông Tin Sản Phẩm  |  You&Me</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Favicon -->
        <link rel="shortcut icon" type="image/x-icon" href="assets/img/favicon.ico">

        <!-- CSS -->
        <!-- Plugins CSS -->
        <link rel="stylesheet" href="assets/css/plugins.css">
        <!-- Main Style CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.min.css">
        <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11.0.19/dist/sweetalert2.all.min.js"></script>
    </head>
    <style>.product-buttons {
            display: flex;
            gap: 10px;
        }

        .button {
            display: inline-block;
            padding: 10px 20px;  /* Adjust padding as needed */
            font-size: 16px;
            font-weight: bold;
            text-align: center;
            text-decoration: none;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            white-space: nowrap;  /* Prevent text wrapping */
        }

        .button:hover {
            background-color: #0056b3;
        }

        .button:active {
            background-color: #003d82;
        }

        .button-secondary {
            background-color: #28a745;
        }

        .button-secondary:hover {
            background-color: #218838;
        }

        .button-secondary:active {
            background-color: #1e7e34;
        }
        /* General Styles */
        body {
            font-family: 'Arial', sans-serif;
            line-height: 1.6;
            color: #333;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }



        /* Product Name */
        h1 {
            font-size: 3em;
            margin-bottom: 20px;
            color: #2c3e50;
            font-weight: bold;
            text-align: center;
        }

        /* Product Price */
        .product_price {
            font-size: 1.5em;
            margin-bottom: 20px;
            color: #e74c3c;
            text-align: center;
            font-weight: bold;
        }
        .product_price label {
            font-weight: bold;
            color: #555;
        }
        .product_price .current_price {
            font-size: 2em;
            color: #e74c3c;
            font-weight: bold;
        }

        /* Product Description */
        .product_desc {
            margin-top: 20px;
            padding: 20px;
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .product_desc label {
            display: block;
            font-size: 1.5em;
            font-weight: bold;
            color: #555;
            margin-bottom: 10px;
        }
        .product_desc p {
            margin: 0;
            font-size: 1.2em;
            color: #34495e;
            font-weight: bold;
        }

        .product_reviews {
            margin-top: 30px;
            padding: 20px;
            background-color: #f9f9f9;
            border-radius: 5px;
        }
        .rating, .comment {
            margin-bottom: 15px;
            padding: 10px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0 1px 3px rgba(0,0,0,0.12), 0 1px 2px rgba(0,0,0,0.24);
        }
        .rating p, .comment p {
            margin: 5px 0;
        }
    </style>
    <style>
        /* Form container style */
        .form-container {
            margin-bottom: 20px;
            padding: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #f9f9f9;
        }

        /* Form heading style */
        .form-container h3 {
            font-size: 1.5em;
            margin-bottom: 10px;
            color: #333;
        }

        /* Select input style for rating */
        .form-container select {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }

        /* Textarea input style for comment */
        .form-container textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            font-size: 1em;
            border: 1px solid #ccc;
            border-radius: 3px;
            box-sizing: border-box;
        }

        /* Submit button style */
        .form-container button {
            padding: 10px 20px;
            font-size: 1em;
            font-weight: bold;
            color: #fff;
            background-color: #007bff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-container button:hover {
            background-color: #0056b3;
        }

        .form-container button:active {
            background-color: #003d82;
        }
        body {
            font-family: Arial, sans-serif;
        }

        .features-list {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            flex-wrap: wrap;
        }

        .features-list li {
            flex: 1 1 calc(33.333% - 20px);
            box-sizing: border-box;
            padding: 20px;
            margin: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            text-align: center;
        }

        .features-list .icon img {
            width: 50px;
            height: auto;
            margin-bottom: 10px;
        }

        .features-list .text {
            font-size: 14px;
        }

        .features-list .text strong {
            display: block;
            font-size: 16px;
            margin-bottom: 5px;
        }
        .size-selection-container {
            display: flex;
            align-items: center;
            gap:250px;
        }

        #size {
            margin-right: 200px; /* Add a right margin of 200px to the select element */
        }

        #size-guide-link {
            text-decoration: underline; /* Underline the text */
            color: black;
            font-weight: bold;
            transition: color 0.3s ease;
            display: flex;
            align-items: center;
        }

        #size-guide-link i {
            margin-right: 5px; /* Add some space between the icon and the text */
        }

        #size-guide-link:hover {
            color: orange;
        }
        #size-guide-popup {
            display: none;
            position: fixed;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            background-color: white;
            padding: 20px;
            border: 1px solid black;
            z-index: 1000;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); /* Add shadow for depth */
            width: 80%; /* Adjust width as needed */
            max-width: 600px; /* Set maximum width to avoid stretching on larger screens */
            text-align: center; /* Center align content */
        }

        #size-guide-popup h3 {
            font-size: 1.5em;
            margin-bottom: 10px;
            color: #333;
        }

        #size-guide-popup table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 10px;
        }

        #size-guide-popup table, #size-guide-popup th, #size-guide-popup td {
            border: 1px solid #ddd;
            padding: 8px;
        }

        #size-guide-popup th {
            background-color: #f2f2f2;
        }

        #size-guide-popup p {
            margin-bottom: 10px;
            font-style: italic;
            color: #666;
        }

        #size-guide-popup button {
            padding: 10px 20px;
            font-size: 1em;
            font-weight: bold;
            color: white;
            background-color: black;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        #size-guide-popup button:hover {
            background-color: orange;
        }
        .star {
            color: gold; /* Màu vàng */
        }
        .product_thumb {
            position: relative;
            overflow: hidden;
            aspect-ratio: 1 / 1; /* Tạo khung hình vuông */
        }

        .product_thumb img {
            width: 100%;
            height: 100%;
            object-fit: cover; /* Đảm bảo ảnh lấp đầy khung mà không bị méo */
            object-position: center; /* Căn giữa ảnh trong khung */
        }

        .single_product {
            margin-bottom: 20px; /* Tạo khoảng cách giữa các sản phẩm */
        }
        a, a:hover, a:focus {
            text-decoration: none;
        }

        .product_content h3 a,
        .banner_content a,
        .slider_content a {
            text-decoration: none;
        }



    </style>

    <body>


        <!-- Main Wrapper Start -->
        <div class="off_canvars_overlay"></div>
        <jsp:include page="layout/menu.jsp"/>

        <!--breadcrumbs area start-->
        <div class="breadcrumbs_area product_bread">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="breadcrumb_content">
                            <ul>
                                <li><a href="home">Trang chủ</a></li>
                                <li>/</li>
                                <li>Chi tiết sản phẩm</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--breadcrumbs area end-->

        <!--product details start-->
        <div class="product_details">
            <div class="container">
                <div class="row">
                    <div class="col-lg-5 col-md-5">
                        <div class="product-details-tab">
                            <div id="img-1" class="zoomWrapper single-zoom">
                                <a href="#">
                                    <img id="zoom1" src="${ProductData.img}" data-zoom-image="${ProductData.img}" alt="product">
                                </a>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-7 col-md-7">
                        <div class="product_d_right">
                            <form id="productForm" action="cart" method="POST">
                                <input type="hidden" name="action" id="action" value="addtocart">
                                <input type="hidden" name="product_id" value="${ProductData.product_id}">
                                <div class="product_container">                               
                                    <h1>${ProductData.product_name}</h1>
                                    <div class="product_price">
                                        <label >Giá tiền: </label>
                                        <span class="current_price">${ProductData.product_price} VNĐ</span>
                                    </div>
                                    <div class="product_desc">
                                        <h4>Đánh giá trung bình: 
                                            <fmt:formatNumber value="${averageRating}" maxFractionDigits="1"/> <span class="star">&#9733;</span>
                                        </h4>
                                        </h5>
                                        <label>Mô tả sản phẩm: </label>
                                        <p>${ProductData.product_describe}</p>
                                                 <label>Số lượng sản phẩm: </label>
                                        <p id="quantity">${ProductData.quantity}</p>
                                    </div>
                                </div>


                                <div class="product_variant color">
                                    <h3>Chọn màu sắc :</h3>
                                    <select class="niceselect_option" id="color" name="color">
                                        <c:forEach items="${ColorData}" var="c">
                                            <option value="${c.color}">${c.color}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="product_variant size">
                                    <h3>Chọn size :</h3>
                                    <div class="size-selection-container">
                                        <select class="niceselect_option" id="size" name="size" onchange="handleChange(this)" required="">
                                            <option disabled="true" selected="true" value="">Size</option>
                                                <c:forEach items="${SizeData}" var="s">
                                                    <option value="${s.size}" data-quantity="${s.quantity}">${s.size}</option>
                                                </c:forEach>
                                            </select>
                                        <a href="#" id="size-guide-link">
                                            <i class="fas fa-ruler"></i> Hướng dẫn chọn size
                                        </a>
                                    </div>
                                </div>


                                <div class="product_variant quantity">
                                    <label>Số lượng :</label>
                                    <input min="1" id="inputQuantity" name="quantity" type="number" value="1">
                                    <div class="product-buttons">
                                        <button class="button" type="submit">Thêm vào giỏ hàng</button>
                                    </div>
                                </div>


                            </form>
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <!--product details end-->
        <!-- Form for adding rating -->

        <div class="form-container">
            <form name="commentForm" onsubmit="return validateForm()" action="search" method="POST">
                <input type="hidden" name="action" value="addComment">
                <input type="hidden" name="product_id" value="${ProductData.product_id}">
                <input type="hidden" name="user_id" value="${user.user_id}">                
                <input type="hidden" name="user_name" value="${user.user_name}">  
                <h3>Đánh giá sản phẩm</h3>
                <select name="rating" id="star-rating">

                    <option value="1">1&#9733;</option>
                    <option value="2">2&#9733;&#9733;</option>
                    <option value="3">3&#9733;&#9733;&#9733;</option>
                    <option value="4">4&#9733;&#9733;&#9733;&#9733;</option>
                    <option value="5">5&#9733;&#9733;&#9733;&#9733;&#9733;</option>
                </select>
                <textarea name="comment" rows="4" cols="50" placeholder="Nhập bình luận của bạn"></textarea>
                <button type="submit">Gửi bình luận</button>
            </form>
        </div>



        <c:if test="${not empty comments}">
            <div class="product_reviews">
                <h3>Đánh giá và Bình luận</h3>
                <h5>Bình luận:</h5>
                <c:forEach items="${comments}" var="c">
                    <div class="comment">
                        <p>Bởi: ${c.user_name}</p>
                        <p>Đánh giá: ${c.rating}&#9733</p>
                        <p>Ngày: <fmt:formatDate value="${c.createdAt}" pattern="dd/MM/yyyy"/></p>
                        <p>${c.comment}</p>
                    </div>
                </c:forEach>
            </div>
        </c:if>



        <!--product section area start-->
        <section class="product_section related_product">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="section_title">
                            <h2>Sản phẩm tương tự</h2>
                        </div>
                    </div>
                </div>
                <div class="product_area">
                    <div class="row">
                        <div class="product_carousel product_three_column4 owl-carousel">
                            <c:forEach items="${ProductByCategory}" var="pc">
                                <div class="col-lg-3">
                                    <div class="single_product">
                                        <div class="product_thumb">
                                            <a class="primary_img" href="product?action=productdetail&product_id=${pc.product_id}"><img src="${pc.img}" alt=""></a>
                                        </div>
                                        <div class="product_content">
                                            <h3><a href="product?action=productdetail&product_id=${pc.product_id}">${pc.product_name}</a></h3>
                                            <span class="current_price">${pc.product_price} VNĐ</span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!--product section area end-->

        <!--footer area start-->
        <jsp:include page="layout/footer.jsp"/>
        <!--footer area end-->

        <!-- Plugins JS -->
        <script src="assets/js/plugins.js"></script>
        <script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
        <!-- Main JS -->
        <script src="assets/js/main.js"></script>
        <script>
            function setActionAndSubmit(action) {
                document.getElementById('action').value = action;
                document.getElementById('productForm').submit();
            }
        </script>
        <div id="size-guide-popup" style="display: none; position: fixed; left: 50%; top: 50%; transform: translate(-50%, -50%); background-color: white; padding: 20px; border: 1px solid black; z-index: 1000;">

            <h3>Bảng size</h3>
            <table border="1">
                <tr>
                    <th>SIZE</th>
                    <th>CHIỀU CAO (CM)</th>
                    <th>CÂN NẶNG (KG)</th>
                    <th>NGANG VAI</th>
                    <th>VÒNG NGỰC</th>
                    <th>VÒNG EO</th>
                    <th>DÀI TAY</th>
                    <th>DÀI ÁO</th>
                </tr>
                <tr>
                    <td>S</td>
                    <td>< 168</td>
                    <td>< 62</td>
                    <td>44.5</td>
                    <td>100</td>
                    <td>96</td>
                    <td>19.5</td>
                    <td>67.5</td>
                </tr>
                <tr>
                    <td>M</td>
                    <td>169 - 172</td>
                    <td>63 - 69</td>
                    <td>46</td>
                    <td>104</td>
                    <td>100</td>
                    <td>21</td>
                    <td>69</td>
                </tr>
                <tr>
                    <td>L</td>
                    <td>173 - 176</td>
                    <td>70 - 76</td>
                    <td>47.5</td>
                    <td>108</td>
                    <td>104</td>
                    <td>22.5</td>
                    <td>71</td>
                </tr>
                <tr>
                    <td>XL</td>
                    <td>177 - 180</td>
                    <td>77 - 83</td>
                    <td>49.5</td>
                    <td>114</td>
                    <td>110</td>
                    <td>24</td>
                    <td>73</td>
                </tr>
                <tr>
                    <td>XXL</td>
                    <td>> 180</td>
                    <td>> 83</td>
                    <td>51.5</td>
                    <td>120</td>
                    <td>116</td>
                    <td>25.5</td>
                    <td>75</td>
                </tr>
            </table>
            <p>97% khách hàng của chúng tôi đã chọn đúng size theo bảng này</p>
            <button id="close-popup">Đóng</button>
        </div>

        <script>
            
            function handleChange(select) {
                var selectedOption = select.options[select.selectedIndex]; 
                var quantity = selectedOption.getAttribute("data-quantity"); 
                console.log("Số lượng có sẵn:", quantity);
                document.getElementById('inputQuantity').max = quantity;
                document.getElementById('quantity').innerText = quantity;
            }
            
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
                var successMessage = "${sessionScope.successMessage}";
                var errorMessage = "${sessionScope.errorMessage}";
                var errorMessage1 = "${sessionScope.errorMessage1}";

                if (successMessage) {
                    showNotification(successMessage, true);
                    // Xóa thông báo khỏi session
            <% session.removeAttribute("successMessage"); %>
                } else if (errorMessage) {
                    showNotification(errorMessage, false);
                    // Xóa thông báo khỏi session
            <% session.removeAttribute("errorMessage"); %>
                } else if (errorMessage1) {
                    showNotification(errorMessage1, false);
                    // Xóa thông báo khỏi session
            <% session.removeAttribute("errorMessage1"); %>
                }
            });
        </script>
        <script>
                                                    function validateForm() {
                                                        var comment = document.forms["commentForm"]["comment"].value.trim();
                                                        
                                                        if (comment === "") {
                                                            Swal.fire({
                                                                icon: 'error',
                                                                title: 'Bình luận không cho phép khoảng trắng!',
                                                                text: 'Vui lòng nhập bình luận hợp lệ!',
                                                                confirmButtonText: 'Đóng'
                                                            });
                                                            return false;
                                                        }
                                                        return true;
                                                    }
                                                </script>
    </body>
</html>