package com.egis.prj.controller;

import com.egis.prj.model.CommentVO;
import com.egis.prj.model.FileVO;
import com.egis.prj.model.Page;
import com.egis.prj.model.PostVO;
import com.egis.prj.service.CommentService;
import com.egis.prj.service.FileService;
import com.egis.prj.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class HomeApiController {
    private final PostService postService;
    private final FileService fileService;
    private final CommentService commentService;

    @GetMapping("/list")
    public Page<PostVO> list(@RequestParam(defaultValue = "1") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        // 게시글 데이터와 페이징 정보 가져오기
        Page<PostVO> boardPage = postService.getPosts(page, size);

        return boardPage; // Page 객체를 JSON으로 직접 반환
    }

    @GetMapping("/detail")
    public Map<String, Object> detail(@RequestParam("id") int id) {

        // 게시글 조회
        PostVO post = postService.getPostById(id);

        // 게시글과 연관된 file조회
        List<FileVO> fileList = fileService.getFileByPostId(post.getId());

        // 게시글과 연관된 Comment조회
        List<CommentVO> commentList = commentService.getCommentsByPostId(post.getId());

        // content 객체에 모든 데이터 담기
        Map<String, Object> content = new HashMap<>();
        content.put("post", post);
        content.put("fileList", fileList);
        content.put("commentList", commentList);

        return content; // content 객체를 JSON으로 직접 반환
    }

    // 게시글 검색
    @GetMapping("/search")
    public Page<PostVO> search(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page) {

        int pageSize = 10;
        Page<PostVO> postPage = postService.searchPosts(keyword, page, pageSize);

        return postPage;
    }

    @GetMapping("/edit/{id}")
    public Map<String, Object> editPost(@PathVariable("id") int id) {
        // 해당 게시글 정보 가져오기
        PostVO post = postService.getPostById(id);

        // 해당 게시글 파일 리스트 가져오기
        List<FileVO> fileList = fileService.getFileByPostId(post.getId());

        // content 객체에 모든 데이터 담기
        Map<String, Object> content = new HashMap<>();
        content.put("post", post);
        content.put("postId", id);
        content.put("fileList", fileList);

        return content; // content 객체를 JSON으로 직접 반환
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") int id) {
        // 해당 게시글을 삭제
        postService.deletePost(id);

        // 삭제 후 목록 페이지로 리디렉션
        return "true";
    }
}