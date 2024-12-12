package com.egis.prj.service;

import com.egis.prj.model.FileVO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {
    void updateFilePostId(int fileId, int postId);
    List<FileVO> getFileByPostId(int postId);
    boolean deleteFile(int fileId);
    void deleteFileByPostId(int postId);
    FileVO insertFile(MultipartFile uploadFile) throws IOException;
    FileVO getFileById(int id);
}
