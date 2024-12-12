package com.egis.prj.controller;

import com.egis.prj.model.PostWriteVO;
import com.egis.prj.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping(value = "/writeBoard", consumes = "application/json")
    public Map<String, String> submitPost(@RequestBody PostWriteVO postWriteVO) {
        // 받은 데이터를 로그에 출력
        postService.insertPost(postWriteVO);

        // 리다이렉트 URL을 JSON 형식으로 반환
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/");  // 게시글 목록 페이지 URL
        return response;
    }

    @PostMapping(value = "/editBoard/{id}", consumes = "application/json")
    public Map<String, String> updatePost(@PathVariable("id") int id, @RequestBody PostWriteVO postWriteVO) {
        // 수정된 게시글 정보를 처리
        postService.updatePost(id, postWriteVO);

        // 리다이렉트 URL을 JSON 형식으로 반환
        Map<String, String> response = new HashMap<>();
        response.put("redirectUrl", "/");  // 수정 후 게시글 목록 페이지로 리다이렉트
        return response;
    }
}
