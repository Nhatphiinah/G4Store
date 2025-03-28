<%-- 
    Document   : product-list
    Created on : Mar 22, 2025, 4:48:24 PM
    Author     : Admin
--%><%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
                                    <p class="card-text product-price">
                                        Giá gốc: <del><fmt:formatNumber value="${p.product_price}" pattern="#,##0" /> VNĐ</del>
                                    </p>
                                    <p class="card-text product-final-price">
                                        Giá sau giảm: <strong>
                                            <c:set var="discountedPrice" value="${p.product_price - (p.product_price * p.discount / 100)}" />
                                            <fmt:formatNumber value="${discountedPrice}" pattern="#,##0" /> VNĐ
                                        </strong>
                                    </p>
                                </c:when>
                                <c:otherwise>
                                    <p class="card-text product-final-price">
                                        Giá gốc: <strong><fmt:formatNumber value="${p.product_price}" pattern="#,##0" /> VNĐ</strong>
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