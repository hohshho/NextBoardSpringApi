package com.egis.prj.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class PostWriteVO {
    private List<Integer> fileIds;  // 파일 ID 목록
    private PostVO post;
}
