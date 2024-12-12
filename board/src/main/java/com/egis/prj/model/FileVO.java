package com.egis.prj.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class FileVO {
    private int id;
    private int postId;
    private String fileName;
    private String filePath;
    private int fileSize;
    private LocalDateTime uploadedDate;
}
