package com.egis.prj.service.impl;

import com.egis.prj.dao.CommentDAO;
import com.egis.prj.model.CommentVO;
import com.egis.prj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDAO commentDAO;


    @Override
    public void addComment(int postId, String content) {
        CommentVO commentVO = new CommentVO();
        commentVO.setPostId(postId);
        commentVO.setContent(content);
        commentVO.setCreatedDate(LocalDateTime.now());
        commentDAO.insertComment(commentVO);
    }

    @Override
    public List<CommentVO> getCommentsByPostId(int postId) {
        return commentDAO.getCommentsByPostId(postId);
    }

    @Override
    public void deleteComment(int commentId) {
        commentDAO.deleteComment(commentId);
    }

    public void deleteCommentByPostId(int postId) {
        commentDAO.deleteCommentByPostId(postId);
    }
}
