package com.egis.prj.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Page<PostVO> {
    private List<PostVO> content;
    private int currentPage;
    private int pageSize;
    private int totalItems;
    private int totalPages;

    public Page(List<PostVO> content, int currentPage, int pageSize, int totalItems) {
        this.content = content;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = (int) Math.ceil((double) totalItems / pageSize);
    }
}