package com.bbu.shenma.service;

import com.bbu.shenma.bean.Video;

import java.util.List;

public interface VideoService {

    public List<Video> getVideoByChapterId(String chapterId);

    public boolean addVideo(Video video);

    public boolean modifyVideo(Video video);

    public boolean removeVideoByCourseId(String courseId);

    public boolean removeVideoByPrimaryKey(String id);

    Video getVideoById(String id);
}
