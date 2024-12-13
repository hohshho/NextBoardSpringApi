package com.egis.prj.dao;

import com.egis.prj.model.FileVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("FileDAO")
@RequiredArgsConstructor
public class FileDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    public FileVO insertFile(FileVO file) {
        sqlSessionTemplate.insert("FileMapper.insertFile", file);
        return file;
    }

    public boolean deleteFile(int fileId) {
        // MyBatis에서 delete 실행 후, 삭제된 레코드 수를 반환
        int deletedRows = sqlSessionTemplate.delete("FileMapper.deleteFile", fileId);
        return deletedRows > 0;  // 삭제된 레코드 수가 1 이상이면 성공
    }

    public void updateFilePostId(int fileId, int postId) {
        Map<String, Integer> params = new HashMap<>();
        params.put("fileId", fileId);
        params.put("postId", postId);
        sqlSessionTemplate.update("FileMapper.updateFilePostId", params);
    }

    public FileVO getFileById(int id) {
        return sqlSessionTemplate.selectOne("FileMapper.getFileById", id);
    }

    public List<FileVO> getFilesByPostId(int boardId) {
        return sqlSessionTemplate.selectList("FileMapper.getFilesByPostId", boardId);
    }

    public void deleteFilesByPostId(int boardId) {
        sqlSessionTemplate.delete("FileMapper.deleteFilesByPostId", boardId);
    }
}
