<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <!-- css -->
    <style>
        /* 댓글 목록 스타일 */
        #commentList {
            margin-top: 30px;
        }

        .comment-item {
            background-color: #f9f9f9;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            position: relative; /* X 버튼을 댓글 아이템 안에서 위치시키기 위한 설정 */
        }

        .comment-item:hover {
            background-color: #f1f1f1;
            transform: translateY(-2px);
        }

        .comment-item p {
            margin: 0;
            font-size: 14px;
        }

        .comment-item .comment-meta {
            font-size: 12px;
            color: #888;
            margin-bottom: 10px;
        }

        .comment-item .comment-meta strong {
            color: #333;
        }

        .comment-item .comment-content {
            font-size: 16px;
            color: #333;
            line-height: 1.6;
        }

        /* X 버튼 스타일 */
        .comment-item .delete-btn {
            position: absolute;
            top: 10px;
            right: 10px;
            background: none;
            border: none;
            color: #ff0000;
            font-size: 18px;
            cursor: pointer;
        }

        .comment-item .delete-btn:hover {
            color: #cc0000;
        }
        
        .card-body img {
            width: 100%;
            height: auto; /* 이미지 비율 유지 */
            max-width: 100%; /* 이미지가 카드 너비를 초과하지 않도록 보장 */
            display: block; /* 이미지 아래 여백 제거 */
            margin: 0 auto; /* 가운데 정렬 */
        }
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
        <ul class="nav nav-pills">
            <li class="nav-item"><a href="/" class="nav-link" aria-current="page">list 페이지</a></li>
            <li class="nav-item"><a href="/write" class="nav-link active">write 페이지</a></li>
        </ul>
    </header>
</div>
<div class="container mt-5">
    <h2 class="mb-4">게시글 상세보기</h2>
    <div class="card">
        <div class="card-header">
            <h4>${post.title}</h4>
        </div>
        <div class="card-body">
            <p class="card-text">${post.content}</p>
        </div>
        <div class="card-footer text-muted">
            <fmt:parseDate value="${post.created_date}" pattern="yyyy-MM-dd'T'HH:mm" var="parseDateTime" type="both"/>
            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${parseDateTime}"/>
            <span class="float-end">조회수: ${post.view_count}</span>
        </div>
    </div>

    <!-- 파일 목록 -->
    <div class="mt-4">
        <h5>첨부된 파일</h5>
        <ul class="list-group">
            <c:forEach var="file" items="${fileList}">
                <li class="list-group-item">
                    <!-- 파일 이름을 다운로드 링크로 표시 -->
                    파일 ID: ${file.id},
                    <a href="/fileDownload/${file.id}" class="text-primary" download>
                            ${file.fileName}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div class="d-flex gap-3 mt-3">
        <a href="/edit/${post.id}" class="btn btn-info">글 수정하기</a>
        <a href="/delete/${post.id}" class="btn btn-danger">글 삭제하기</a>
    </div>

    <!-- 댓글 작성 폼 -->
    <div class="mt-4">
        <h5>댓글</h5>
        <form action="/submitComment" method="post">
            <input type="hidden" name="postId" value="${post.id}" />
            <div class="mb-3">
                <label for="commentContent" class="form-label">댓글 내용</label>
                <textarea class="form-control" id="commentContent" name="content" rows="3" required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">댓글 작성</button>
        </form>
    </div>

    <div id="commentList">
        <c:forEach var="comment" items="${commentList}">
            <div class="comment-item">
                <!-- 댓글 삭제 버튼 -->
                <form action="/deleteComment" method="post" style="display:inline;">
                    <input type="hidden" name="commentId" value="${comment.id}" />
                    <input type="hidden" name="postId" value="${post.id}" />  <!-- 게시글 ID 추가 -->
                    <button type="submit" class="delete-btn" title="댓글 삭제">×</button>
                </form>

                <div class="comment-meta">
                    <fmt:parseDate value="${comment.createdDate}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="commentDateTime" type="both"/>
                    <strong><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${commentDateTime}"/></strong>
                </div>
                <div class="comment-content">
                    <p>${comment.content}</p>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
