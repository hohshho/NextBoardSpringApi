package com.egis.prj.service.impl;

import com.egis.prj.dao.FileDAO;
import com.egis.prj.model.FileVO;
import com.egis.prj.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final FileDAO fileDAO;

    @Override
    public void updateFilePostId(int fileId, int postId) {
        fileDAO.updateFilePostId(fileId, postId);
    }

    @Override
    public List<FileVO> getFileByPostId(int postId) {
        return fileDAO.getFilesByPostId(postId);
    }

    @Override
    public boolean deleteFile(int fileId) {
        return fileDAO.deleteFile(fileId);
    }

    @Override
    public void deleteFileByPostId(int postId) {
        // 1. DB에서 삭제: 해당 게시글의 모든 파일을 DB에서 삭제
        List<FileVO> files = fileDAO.getFilesByPostId(postId);
        if (files != null && !files.isEmpty()) {
            // 2. 파일 삭제: 서버에서 파일을 삭제
            for (FileVO file : files) {
                File serverFile = new File(file.getFilePath());
                if (serverFile.exists()) {
                    boolean deleted = serverFile.delete(); // 파일 삭제
                    if (!deleted) {
                        // 파일 삭제 실패 처리
                        System.out.println("Failed to delete file: " + file.getFilePath());
                    }
                }
            }

            // DB에서 파일 삭제
            fileDAO.deleteFilesByPostId(postId);
        }
    }

    @Override
    public FileVO insertFile(MultipartFile uploadFile) throws IOException {
        // 파일 이름 생성 로직
        String originalFileName = uploadFile.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

        // 업로드 디렉토리 확인 및 생성
        File uploadDir = new File("C:/upload");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 파일 저장 경로
        File destFile = new File(uploadDir, fileName);

        // 파일 저장
        uploadFile.transferTo(destFile);

        // DB에 파일 정보 저장
        FileVO fileVO = new FileVO();
        fileVO.setFileName(originalFileName);
        fileVO.setFilePath(destFile.getAbsolutePath());
        fileVO.setFileSize((int) uploadFile.getSize());

        return fileDAO.insertFile(fileVO);
    }

    @Override
    public FileVO getFileById(int id) {
        return fileDAO.getFileById(id);
    }
}
