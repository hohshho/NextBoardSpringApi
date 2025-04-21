package com.egis.prj.service;

import com.egis.prj.model.Page;
import com.egis.prj.model.PostVO;
import com.egis.prj.model.PostWriteVO;

public interface PostService {
    Page<PostVO> getPosts(int page, int size);
    void insertPost(PostWriteVO postWriteVO);
    PostVO getPostById(int id);
    void deletePost(int id);
    void updatePost(int id, PostWriteVO postWriteVO);
    Page<PostVO> searchPosts(String searchKeyword, int currentPage, int pageSize);
}
