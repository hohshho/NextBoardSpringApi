package com.egis.prj.controller;

import com.egis.prj.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    // 댓글 작성 처리
    @PostMapping("/submitComment")
    @ResponseBody
    public Map<String, Object> submitComment(@RequestParam("postId") int postId, @RequestParam("content") String content) {
        commentService.addComment(postId, content);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("redirectUrl", "/detail?id=" + postId);
        return response;
    }

    // 댓글 삭제 처리
    @PostMapping(value = "/deleteComment", produces = "application/json")
    @ResponseBody
    public Map<String, Object> deleteComment(@RequestParam("commentId") int commentId,
                                             @RequestParam("postId") int postId) {
        commentService.deleteComment(commentId);
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("redirectUrl", "/detail?id=" + postId);
        return response;
    }
}
