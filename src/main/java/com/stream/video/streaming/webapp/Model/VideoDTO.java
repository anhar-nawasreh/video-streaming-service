package com.stream.video.streaming.webapp.Model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class VideoDTO {
    private String name;

    private String path;

    private LocalDateTime createdTime;

    private String publisherName;
}
