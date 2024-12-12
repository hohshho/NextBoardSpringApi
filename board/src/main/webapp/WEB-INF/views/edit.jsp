<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ page session="false" %>
<html>
<head>
    <!-- css -->
    <style>

    </style>

    <!-- script -->
    <!-- jQuery 라이브러리 -->
    <script type="text/javascript" src="/resources/js/lib/jquery-3.6.0.min.js"></script>
    <!-- jQuery UI 라이브러리 -->
    <script type="text/javascript" src="/resources/js/lib/jquery-ui.js"></script>
    <!-- Bootstrap JavaScript -->
    <script type="text/javascript" src="/resources/js/lib/bootstrap.js"></script>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" type="text/css" href="/resources/css/lib/bootstrap.css">
    <!-- editor -->
    <script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
    <link rel="stylesheet" href="https://uicdn.toast.com/editor/latest/toastui-editor.min.css" />

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
    <h2 class="mb-4">작성한 글 수정</h2>

    <!-- 파일 업로드 -->
    <form id="fileUploadForm" enctype="multipart/form-data">
        <input type="file" name="uploadFile" id="uploadFile"/>
        <button type="button" id="uploadBtn">파일 업로드</button>
    </form>

    <!-- 글 작성 폼 -->
    <div id="postId" data-post-id="${postId}"></div>
    <form id="postUpdateForm">
        <div class="mb-3">
            <label for="title" class="form-label">제목</label>
            <input type="text" class="form-control" id="title" name="title" value="${post.title}" required>
        </div>
<%--        <div class="mb-3">--%>
<%--            <label for="content" class="form-label">내용</label>--%>
<%--            <textarea class="form-control" id="content" name="content" rows="5" required>${post.content}</textarea>--%>
<%--        </div>--%>
        <div id="editor"></div>
        <button type="submit" class="btn btn-info mt-4">게시글 수정</button>
    </form>

    <span>첨부된 파일 리스트</span>
    <div id="uploadFileList">
        <c:forEach var="file" items="${fileList}">
            <div id="file${file.id}" class="file-item">
                <span class="file-name">${file.fileName}</span>
                <button class="delete-btn" onclick="removeFile(${file.id})">x</button>
            </div>
        </c:forEach>
    </div>
</div>
<script>
    // 파일 ID를 저장할 배열 초기화
    var uploadedFileIds = [];
    var content = '${post.content}';

    <c:forEach var="file" items="${fileList}">
    // 배열에 파일 ID 추가
    uploadedFileIds.push(${file.id});
    </c:forEach>

    console.log(uploadedFileIds);  // 파일 ID 배열 확인 (디버깅용)
</script>
<script type="text/javascript" src="/resources/js/use/edit.js"></script>
</body>
</html>
