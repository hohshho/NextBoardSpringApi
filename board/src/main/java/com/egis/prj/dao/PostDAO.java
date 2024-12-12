package com.egis.prj.dao;

import com.egis.prj.model.PostVO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository("PostDAO")
public class PostDAO {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    public void deletePostById(int id) {
        // MyBatis 매퍼의 deletePostById SQL 쿼리 호출
        sqlSessionTemplate.delete("PostMapper.deletePostById", id);
    }

    // 조회수 1 증가
    public void incrementViewCount(int id) {
        sqlSessionTemplate.update("PostMapper.incrementViewCount", id);
    }

    public PostVO getPostById(int id) {
        return sqlSessionTemplate.selectOne("PostMapper.getPostById", id);
    }

    public List<PostVO> getPosts(int offset, int pageSize) {
        Map<String, Integer> params = new HashMap<>();
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        return sqlSessionTemplate.selectList("PostMapper.getPosts", params);
    }

    // 게시글 검색 (검색어와 페이징 처리 포함)
    public List<PostVO> searchPosts(String searchKeyword, int offset, int pageSize) {
        Map<String, Object> params = new HashMap<>();
        params.put("searchKeyword", searchKeyword);
        params.put("offset", offset);
        params.put("pageSize", pageSize);
        return sqlSessionTemplate.selectList("PostMapper.searchPosts", params);
    }

    // 검색 결과의 총 개수 조회
    public int countSearchPosts(String searchKeyword) {
        return sqlSessionTemplate.selectOne("PostMapper.countSearchPosts", searchKeyword);
    }

    public int getTotalCount() {
        return sqlSessionTemplate.selectOne("PostMapper.getTotalCount");
    }

    public PostVO insertPost(PostVO post) {
        // MyBatis 매퍼의 insertPost SQL 쿼리 호출
        sqlSessionTemplate.insert("PostMapper.insertPost", post);
        return post;
    }

    // 게시글 수정 메서드
    public void updatePost(PostVO post) {
        sqlSessionTemplate.update("PostMapper.updatePost", post);
    }
}
