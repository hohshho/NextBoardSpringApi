package com.egis.prj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostVO {
    private int id;
    private String title;
    private String content;
    private LocalDateTime created_date;
    private int view_count;

    PostVO(String post_title, String post_content) {
        this.title = post_title;
        this.content = post_content;
    }
}
