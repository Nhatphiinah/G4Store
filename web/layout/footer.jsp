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
<div class="map-area">                         
    <iframe 
        id="googleMap" 
        src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3929.0519803167535!2d105.73001407450853!3d10.01256527281942!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x31a08900318d0db3%3A0x7aa54681cde47b0b!2zxJDhuqFpIEjhu41jIEZQVA!5e0!3m2!1sen!2s!4v1743228770865!5m2!1sen!2s" 
        width="600" 
        height="450" 
        style="border:0;" 
        allowfullscreen="" 
        loading="lazy" 
        referrerpolicy="no-referrer-when-downgrade">
    </iframe>
</div>

        <footer class="bg-dark text-light py-5">
            <div class="container">
                <div class="row justify-content-between">
    <!-- Company Info -->
    <div class="col-md-3 mb-4 text-center">
        <h5 class="text-uppercase mb-4 border-bottom pb-2">G4-Store</h5>
        <p>Văn phòng: Cần Thơ</p>
    </div>

    <!-- Company Links -->
    <div class="col-md-3 mb-4 text-center">
    <h5 class="text-uppercase mb-4 border-bottom pb-2">Công ty</h5>
    <ul class="list-unstyled text-start">
        <li><a href="about.jsp" class="text-light">Giới thiệu về G4-Store</a></li>
        <li><a href="" class="text-light">Tuyển dụng</a></li>
        <li><a href="/tin-thoi-trang" class="text-light">Tin thời trang</a></li>
        <li><a href="" class="text-light">Hợp tác nhượng quyền</a></li>
        <li><a href="/thong-tin-lien-he" class="text-light">Liên hệ</a></li>
    </ul>
</div>



    <!-- Social Media -->
    <div class="col-md-3 mb-4 text-center">
        <h5 class="text-uppercase mb-4 border-bottom pb-2">Kết nối với chúng tôi</h5>
        <div class="d-flex flex-wrap justify-content-center">
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
                </div>
            </div>
        </footer>

    </body>
</html>