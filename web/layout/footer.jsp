<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Online Shopping Footer</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <style>
            footer {
                font-family: 'Arial', sans-serif;
            }

            footer h5 {
                font-weight: bold;
                border-bottom: 2px solid #fff;
                padding-bottom: 10px;
                margin-bottom: 20px;
            }

            footer a {
                text-decoration: none;
                transition: color 0.3s ease;
            }

            footer a:hover {
                color: #ffc107 !important;
            }

            .social-icon {
                font-size: 1.2rem;
            }

            @media (max-width: 768px) {
                footer .col-md-3 {
                    text-align: center;
                }
            }
        </style>
    </head>
    <body>
<!--        <div class="map-area">                         
            <iframe id="googleMap" src="https://www.google.com/maps/place/%C4%90%E1%BA%A1i+H%E1%BB%8Dc+FPT/@10.0125653,105.7300141,17z/data=!4m14!1m7!3m6!1s0x31a08900318d0db3:0x7aa54681cde47b0b!2zxJDhuqFpIEjhu41jIEZQVA!8m2!3d10.01256!4d105.732589!16s%2Fg%2F11qsmk_wjx!3m5!1s0x31a08900318d0db3:0x7aa54681cde47b0b!8m2!3d10.01256!4d105.732589!16s%2Fg%2F11qsmk_wjx?entry=ttu&g_ep=EgoyMDI1MDIyNi4xIKXMDSoASAFQAw%3D%3D" width="600" height="450" style="border:0;" allowfullscreen="" loading="lazy" referrerpolicy="no-referrer-when-downgrade"></iframe>
        </div>-->
        <footer class="bg-dark text-light py-5">
            <div class="container">
                <div class="row">
                    <!-- Company Info -->
                    <div class="col-md-3 mb-4">
                        <h5 class="text-uppercase mb-4">Cửa hàng Thể Thao G4-Store</h5>
                        <p>Văn phòng: Cần Thơ</p>
                    </div>

                    <!-- Newsletter -->
                    <div class="col-md-3 mb-4">
                        <h5 class="text-uppercase mb-4">Bản tin</h5>
                        <form>
<!--                            <div class="input-group mb-3">
                                <input type="email" class="form-control" placeholder="Nhập email của bạn" aria-label="Email" aria-describedby="button-addon2">
                                <button class="btn btn-outline-light" type="submit" id="button-addon2">Đăng ký</button>
                            </div>-->
                        </form>
                    </div>

                    <!-- Company Links -->
                    <div class="col-md-3 mb-4">
                        <h5 class="text-uppercase mb-4">Công ty</h5>
                        <ul class="list-unstyled">
                            <li><a href="about.jsp" class="text-light">Giới thiệu về G4</a></li>
<!--                            <li><a href="" class="text-light">THE 31</a></li>-->
                            <li><a href="" class="text-light">Tuyển dụng</a></li>
                            <li><a href="/tin-thoi-trang" class="text-light">Tin thời trang</a></li>
                            <li><a href="" class="text-light">Hợp tác nhượng quyền</a></li>
                            <li><a href="/thong-tin-lien-he" class="text-light">Liên hệ</a></li>
                        </ul>
                    </div>

                    <!-- Social Media -->
                    <div class="col-md-3 mb-4">
                        <h5 class="text-uppercase mb-4">Kết nối với chúng tôi</h5>
                        <div class="d-flex flex-wrap">
                            <a href="https://www.facebook.com/daihocfptcantho" class="btn btn-outline-light me-2 mb-2" target="_blank" rel="nofollow noopener">
                                <i class="fab fa-facebook-f"></i>
                            </a>
                            <a href="https://www.instagram.com/nike/" class="btn btn-outline-light me-2 mb-2" target="_blank" rel="nofollow noopener">
                                <i class="fab fa-instagram"></i>
                            </a>
                            <a href="https://zalo.me/1391225272460633719" class="btn btn-outline-light me-2 mb-2" target="_blank" rel="nofollow noopener">
                                Zalo
                            </a>
                            <a href="https://www.youtube.com/@nike" class="btn btn-outline-light me-2 mb-2" target="_blank" rel="nofollow noopener">
                                <i class="fab fa-youtube"></i>
                            </a>
                            <a href="https://www.tiktok.com/@nike" class="btn btn-outline-light me-2 mb-2" target="_blank" rel="nofollow noopener">
                                <i class="fab fa-tiktok"></i>
                            </a>
                        </div>
                    </div>
                </div>

                <hr class="my-4">

                <!-- Bottom Footer -->
                <div class="row align-items-center">
                    <div class="col-md-6 text-center text-md-start">
                        <p>&copy; 2024 You&Me Vietnam. All rights reserved.</p>
                    </div>
                    <div class="col-md-6 text-center text-md-end">
                        <img src="path_to_certification_image.png" alt="Chứng nhận đã khai báo với Bộ Công Thương" width="153" height="58">
                    </div>
                </div>
            </div>
        </footer>

    </body>
</html>