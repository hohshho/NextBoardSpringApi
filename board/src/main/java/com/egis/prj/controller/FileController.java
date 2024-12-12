package com.egis.prj.controller;

import com.egis.prj.model.FileVO;
import com.egis.prj.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/deleteFile")
    @ResponseBody
    public ResponseEntity<String> deleteFile(@RequestBody Map<String, Integer> requestData) {
        try {
            int fileId = requestData.get("fileId");  // JSON에서 fileId 추출
            boolean isDeleted = fileService.deleteFile(fileId); // 파일 삭제 서비스 호출

            if (isDeleted) {
                return ResponseEntity.ok("파일 삭제 성공");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("파일 삭제 중 오류 발생");
        }
    }

    @PostMapping("/fileUpload")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("uploadFile") MultipartFile uploadFile) {
        Map<String, Object> response = new HashMap<>();

        if (uploadFile.isEmpty()) {
            response.put("success", false);
            response.put("message", "파일을 선택하세요.");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            FileVO fileVO = fileService.insertFile(uploadFile); // 자동 생성된 ID가 fileVO에 설정됨

            // 업로드 성공
            response.put("success", true);
            response.put("fileId", fileVO.getId()); // 반환된 FileVO에서 ID를 추출
            response.put("fileName", fileVO.getFileName());
            response.put("message", "파일 업로드 성공!");

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "파일 업로드 실패: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/fileDownload/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("id") int id) {
        FileVO fileVO = fileService.getFileById(id);

        if (fileVO == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            Path filePath = Paths.get(fileVO.getFilePath());
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            String contentDisposition = "attachment; filename=\"" +
                    URLEncoder.encode(fileVO.getFileName(), "UTF-8").replace("+", "%20") + "\"";
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
