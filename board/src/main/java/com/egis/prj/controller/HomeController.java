package com.egis.prj.controller;

import com.egis.prj.model.CommentVO;
import com.egis.prj.model.FileVO;
import com.egis.prj.model.Page;
import com.egis.prj.model.PostVO;
import com.egis.prj.service.CommentService;
import com.egis.prj.service.FileService;
import com.egis.prj.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public String listBoard(@RequestParam(defaultValue = "1") int page,
                            @RequestParam(defaultValue = "10") int size,
                            Model model) {
        // 게시글 데이터와 페이징 정보 가져오기
        Page<PostVO> boardPage = postService.getPosts(page, size);

        // Model에 데이터 추가
        model.addAttribute("boardPage", boardPage);

        return "list"; // list.jsp 반환
    }

    @GetMapping("/write")
    public String write(Model model) {
        return "write";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam("id") int id, Model model) {
        // 게시글 조회
        PostVO post = postService.getPostById(id);
        
        // 게시글과 연관된 file조회
        List<FileVO> fileList = fileService.getFileByPostId(post.getId());
        
        // 게시글과 연관된 Comment조회
        List<CommentVO> commentList = commentService.getCommentsByPostId(post.getId());

        // Model에 게시글 추가
        model.addAttribute("post", post);
        model.addAttribute("fileList", fileList);
        model.addAttribute("commentList", commentList);

        return "detail";
    }

    @GetMapping("/delete/{id}")
    public String deletePost(@PathVariable("id") int id) {
        // 해당 게시글을 삭제
        postService.deletePost(id);

        // 삭제 후 목록 페이지로 리디렉션
        return "redirect:/";  // 리다이렉트가 정상적으로 작동합니다.
    }

    @GetMapping("/edit/{id}")
    public String editPost(@PathVariable("id") int id, Model model) {
        // 해당 게시글 정보 가져오기
        PostVO post = postService.getPostById(id);

        // 해당 게시글 파일 리스트 가져오기
        List<FileVO> fileList = fileService.getFileByPostId(post.getId());

        // Model에 게시글 정보 추가
        model.addAttribute("post", post);
        model.addAttribute("postId", id);
        model.addAttribute("fileList", fileList);

        // edit.jsp 페이지로 이동
        return "edit";
    }

    // 게시글 검색
    @GetMapping("/search")
    public String search(
            @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
            @RequestParam(value = "page", defaultValue = "1") int page,
            Model model) {

        int pageSize = 10;
        Page<PostVO> postPage = postService.searchPosts(keyword, page, pageSize);

        model.addAttribute("boardPage", postPage);
        model.addAttribute("keyword", keyword);

        return "search";
    }
}
