package com.egis.prj.service;

import com.egis.prj.model.CommentVO;

import java.util.List;

public interface CommentService {
    void addComment(int postId, String content);
    List<CommentVO> getCommentsByPostId(int postId);
    void deleteComment(int commentId);
    void deleteCommentByPostId(int postId);
}
