package com.egis.prj.service.impl;

import com.egis.prj.dao.PostDAO;
import com.egis.prj.model.Page;
import com.egis.prj.model.PostVO;
import com.egis.prj.model.PostWriteVO;
import com.egis.prj.service.CommentService;
import com.egis.prj.service.FileService;
import com.egis.prj.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDAO postDAO;
    @Autowired
    private FileService fileService;
    @Autowired
    private CommentService commentService;

    @Override
    public PostVO getPostById(int id) {
        // 조회수 1 증가
        postDAO.incrementViewCount(id);

        return postDAO.getPostById(id);
    }

    @Override
    public Page<PostVO> getPosts(int page, int size) {
        int offset = (page - 1) * size; // 데이터베이스에서 가져올 시작점 계산
        List<PostVO> posts = postDAO.getPosts(offset, size);
        int totalCount = postDAO.getTotalCount(); // 총 게시글 수

        return new Page<>(posts, page, size, totalCount);
    }

    @Override
    public void insertPost(PostWriteVO postWriteVO) {
        // post 내부 값 추가
        // date, viewCount생성
        PostVO postVO = postWriteVO.getPost();
        postVO.setCreated_date(LocalDateTime.now());
        postVO.setView_count(0);
        postVO = postDAO.insertPost(postVO);

        for(int fileId : postWriteVO.getFileIds()) {
            fileService.updateFilePostId(fileId, postVO.getId()); // 파일의 boardId를 업데이트
        }
    }

    @Override
    public void deletePost(int id) {
        // 해당 게시글 댓글 삭제
        commentService.deleteCommentByPostId(id);

        // 해당 게시글 첨부파일 삭제
        fileService.deleteFileByPostId(id);

        // 해당 id의 게시글 삭제
        postDAO.deletePostById(id);
    }

    @Override
    public void updatePost(int id, PostWriteVO postWriteVO) {
        String title = postWriteVO.getPost().getTitle();
        String content = postWriteVO.getPost().getContent();

        // 게시글을 가져옴
        PostVO post = postDAO.getPostById(id);
        // 제목과 내용 수정
        post.setTitle(title);
        post.setContent(content);
        // 수정일자 갱신
        post.setCreated_date(LocalDateTime.now());

        // 수정된 게시글을 데이터베이스에 업데이트
        postDAO.updatePost(post);

        for(int fileId : postWriteVO.getFileIds()) {
            fileService.updateFilePostId(fileId, post.getId()); // 파일의 boardId를 업데이트
        }
    }

    // 게시글 검색 및 페이징 처리
    @Override
    public Page<PostVO> searchPosts(String searchKeyword, int currentPage, int pageSize) {
        int offset = (currentPage - 1) * pageSize;
        List<PostVO> posts = postDAO.searchPosts(searchKeyword, offset, pageSize);
        int totalItems = postDAO.countSearchPosts(searchKeyword);
        return new Page<>(posts, currentPage, pageSize, totalItems);
    }
}
