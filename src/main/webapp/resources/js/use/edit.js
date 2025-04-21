$(document).ready(function() {
    const editor = new toastui.Editor({
        el: document.querySelector('#editor'),      // 에디터를 적용할 요소 (컨테이너)
        height: '500px',                                    // 에디터 영역의 높이 값 (OOOpx || auto)
        initialEditType: 'wysiwyg',                         // 최초로 보여줄 에디터 타입 (markdown || wysiwyg)
        initialValue: content,                                   // 내용의 초기 값으로, 반드시 마크다운 문자열 형태여야 함
        previewStyle: 'vertical',                           // 마크다운 프리뷰 스타일 (tab || vertical)
        hideModeSwitch: true,                               // 모드 변경 차단
    });

    // 파일 업로드
    $('#uploadBtn').click(function() {
        var formData = new FormData();
        var fileInput = $("#uploadFile")[0];

        // 파일 선택 여부 확인
        if (fileInput.files.length === 0) {
            alert('파일을 선택해주세요.');
            return;
        }

        formData.append("uploadFile", fileInput.files[0]);

        $.ajax({
            url: '/fileUpload',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            timeout: 10000, // 10초 타임아웃 설정
            success: function(response) {
                if (response.success) {
                    alert(response.message);
                    uploadedFileIds.push(response.fileId);
                    console.log("업로드된 파일 ID 배열:", uploadedFileIds);

                    // #uploadFileList에 파일명 추가
                    var fileName = response.fileName; // 서버에서 파일명도 반환하도록 수정 필요
                    var fileId = response.fileId; // 파일 ID

                    // 파일 항목 추가 (파일 ID를 동적으로 id로 추가)
                    $('#uploadFileList').append(
                        '<div id="file' + fileId + '" class="file-item">' +
                        '<span class="file-name">' + fileName + '</span>' +
                        '<button class="delete-btn" onclick="removeFile(' + fileId + ')">x</button>' +
                        '</div>'
                    );
                } else {
                    alert(response.message);
                }
            },
            error: function(xhr, status, error) {
                console.error("xhr:", xhr);
                console.error("status:", status);
                console.error("error:", error);

                let errorMessage = "파일 업로드 실패: ";
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    errorMessage += xhr.responseJSON.message;
                } else {
                    errorMessage += error;
                }
                alert(errorMessage);
            }
        });
    });
    // 수정 폼 제출 시
    $('#postUpdateForm').submit(function(event) {
        event.preventDefault();  // 폼 기본 제출 방지

        const title = $('#title').val();

        if (editor.getMarkdown().length < 1) {
            alert("에디터 내용을 입력해주세요");
            return;
        }

        // 제목의 길이를 검사하여 3자 이상, 50자 미만인지 확인
        if (title.length < 3 || title.length >= 50) {
            alert("제목은 5자 이상, 50자 미만으로 입력해주세요.");
            return;
        }

        // 폼 데이터를 JSON 형식으로 준비
        const postData = {
            post: {
                title: title,
                content: editor.getHTML()
            },
            fileIds: uploadedFileIds  // 파일 ID 목록 추가
        };

        const postId = $('#postId').data('post-id');

        // AJAX 요청 보내기
        $.ajax({
            url: `/editBoard/${postId}`,  // 수정 요청 URL
            type: 'POST',                 // 요청 방식 (POST)
            contentType: 'application/json', // 요청 헤더에 contentType 지정
            data: JSON.stringify(postData), // 데이터는 JSON 문자열로 변환하여 보내기
            success: function(response) {
                console.log('성공:', response);
                // 수정 완료 후 결과 페이지로 리다이렉트
                window.location.href = response.redirectUrl || '/';
            },
            error: function(xhr, status, error) {
                console.error('실패:', error);
            }
        });
    });
});

function removeFile(fileId) {
    const postData = {
        fileId: fileId  // 파일 ID만 전송
    };

    // AJAX 요청 보내기
    $.ajax({
        url: '/deleteFile',   // 요청 URL
        type: 'POST',         // 요청 방식 (POST)
        contentType: 'application/json',  // 전송할 데이터 타입
        data: JSON.stringify(postData),  // JSON 형식으로 데이터 전송
        success: function(response) {
            // 삭제하려는 파일 항목을 찾아서 제거
            $('#file' + fileId).remove();

            // 삭제한 파일 ID를 uploadedFileIds 배열에서 제거
            const index = uploadedFileIds.indexOf(fileId);
            if (index > -1) {
                uploadedFileIds.splice(index, 1);
            }
        },
        error: function(xhr, status, error) {
            console.error('실패:', error);
        }
    });
}