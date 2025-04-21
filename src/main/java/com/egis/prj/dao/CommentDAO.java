package com.egis.prj.dao;

import com.egis.prj.model.CommentVO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("CommentDAO")
@RequiredArgsConstructor
public class CommentDAO {
    private final SqlSessionTemplate sqlSessionTemplate;

    // 댓글 추가
    public void insertComment(CommentVO commentVO) {
        sqlSessionTemplate.insert("CommentMapper.insertComment", commentVO);
    }

    // 댓글 목록 조회
    public List<CommentVO> getCommentsByPostId(int postId) {
        return sqlSessionTemplate.selectList("CommentMapper.getCommentsByPostId", postId);
    }

    // 댓글 삭제
    public void deleteComment(int commentId) {
        sqlSessionTemplate.delete("CommentMapper.deleteComment", commentId);
    }
    
    // 포스트 id로 댓글 전부 삭제
    public void deleteCommentByPostId(int postId) {
        sqlSessionTemplate.delete("CommentMapper.deleteCommentByPostId", postId);
    };
}
