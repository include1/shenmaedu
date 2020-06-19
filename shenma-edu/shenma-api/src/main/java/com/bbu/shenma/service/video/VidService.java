package com.bbu.shenma.service.video;


import org.springframework.web.multipart.MultipartFile;


public interface VidService {


    public String uploadAliyunVideo(MultipartFile file);

    public void removeAliyunVideo(String aliyunVideoId);

    public void removeAliyunVideoMany(String [] aliyunVideoIds);

    String getVideoPlayAuth(String videoId);
}
