package com.egis.prj.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CommentVO {
    private int id;
    private String content;
    private int postId;
    private LocalDateTime createdDate;
}
