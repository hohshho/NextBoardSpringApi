package com.egis.prj.controller;

import com.egis.prj.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;

    // 댓글 작성 처리
    @PostMapping("/submitComment")
    public String submitComment(@RequestParam("postId") int postId, @RequestParam("content") String content) {
        commentService.addComment(postId, content);
        return "redirect:/detail?id=" + postId; // 게시글 상세 페이지로 리다이렉트
    }

    // 댓글 삭제 처리
    @PostMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") int commentId,
                                @RequestParam("postId") int postId) {
        commentService.deleteComment(commentId);
        return "redirect:/detail?id=" + postId; // 게시글 상세 페이지로 리다이렉트
    }
}
