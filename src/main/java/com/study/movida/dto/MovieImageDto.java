package com.study.movida.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieImageDto {

    private String uuid;
    private String imgName;;
    private String path;

    public String getImageURL() {
        return URLEncoder.encode(path + "/" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }

    public String getThumbnailURL() {
        return URLEncoder.encode(path + "/s_" + uuid + "_" + imgName, StandardCharsets.UTF_8);
    }
}

