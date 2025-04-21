<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <!-- css -->
    <style>

    </style>

    <!-- script -->
    <!-- jQuery 라이브러리 -->
    <script type="text/javascript" src="./resources/js/lib/jquery-3.6.0.min.js"></script>
    <!-- jQuery UI 라이브러리 -->
    <script type="text/javascript" src="./resources/js/lib/jquery-ui.js"></script>
    <!-- Bootstrap JavaScript -->
    <script type="text/javascript" src="./resources/js/lib/bootstrap.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="./resources/css/lib/bootstrap.css">

    <title>Hello World</title>
</head>
<body>
<div class="container">
    <header class="d-flex flex-wrap justify-content-center py-3 mb-4 border-bottom">
        <a href="/" class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
            <span class="fs-4">EGIS</span>
        </a>

        <!-- 검색 폼 -->
        <form class="d-flex justify-content-center w-50" action="/search" method="get">
            <input class="form-control me-1" type="search" placeholder="Search" name="keyword" aria-label="Search" value="${keyword}">
            <button class="btn btn-outline-success me-4" type="submit">Search</button>
        </form>

        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link active">list 페이지</a></li>
            <li class="nav-item"><a href="/write" class="nav-link">write 페이지</a></li>
        </ul>
    </header>
</div>
<div class="container mt-5">
    <h2 class="mb-4">게시판</h2>

    <!-- 게시글 목록 -->
    <table class="table table-striped">
        <thead>
        <tr>
            <th scope="col">번호</th>
            <th scope="col">제목</th>
            <th scope="col">조회수</th>
            <th scope="col">작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="post" items="${boardPage.content}">
            <tr>
                <th scope="row">${post.id}</th>
                <td><a href="/detail?id=${post.id}">${post.title}</a></td>
                <td>${post.view_count}</td>
                <td>
                    <!-- fmt:formatDate를 사용하여 LocalDateTime 포맷팅 -->
                    <fmt:parseDate value="${post.created_date}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" type="both"/>
                    <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parseDateTime}"/>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <!-- 페이징 -->
    <nav aria-label="Page navigation">
        <ul class="pagination justify-content-center">
            <c:if test="${boardPage.currentPage > 1}">
                <li class="page-item">
                    <a class="page-link" href="?page=${boardPage.currentPage - 1}&size=${boardPage.pageSize}" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
            </c:if>
            <c:forEach var="i" begin="1" end="${boardPage.totalPages}">
                <li class="page-item ${i == boardPage.currentPage ? 'active' : ''}">
                    <a class="page-link" href="?page=${i}&size=${boardPage.pageSize}">${i}</a>
                </li>
            </c:forEach>
            <c:if test="${boardPage.currentPage < boardPage.totalPages}">
                <li class="page-item">
                    <a class="page-link" href="?page=${boardPage.currentPage + 1}&size=${boardPage.pageSize}" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</div>

</body>
</html>
