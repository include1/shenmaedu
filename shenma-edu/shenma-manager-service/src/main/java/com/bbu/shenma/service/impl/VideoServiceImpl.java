package com.bbu.shenma.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bbu.shenma.bean.CourseChapter;
import com.bbu.shenma.bean.Video;
import com.bbu.shenma.client.VidClient;
import com.bbu.shenma.handle.EduException;
import com.bbu.shenma.mapper.VideoMapper;
import com.bbu.shenma.service.VideoService;
import com.bbu.shenma.util.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    private VidClient vidClient;

    //获取视频信息
    @Override
    public List<Video> getVideoByChapterId(String chapterId) {
        if(!StringUtils.isBlank(chapterId)){
            QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("chapter_id",chapterId);
            List<Video> videos = videoMapper.selectList(queryWrapper);
            return videos;
        }
        return null;
    }
    //添加
    @Override
    public boolean addVideo(Video video) {
        boolean flag = validateVideo(video);
        if(flag){
            int result = videoMapper.insert(video);
            return result > 0;
        }
        return false;
    }
    //对视频对象字段进行验证
    private boolean validateVideo(Video video) {
        if(StringUtils.isBlank(video.getCoverImg())||"".equals(video.getCoverImg())){
            return false;
        }else if(StringUtils.isBlank(video.getTime())||"".equals(video.getTime())){
            return false;
        }else if(StringUtils.isBlank(video.getTitle())||"".equals(video.getTitle())){
            return false;
        }else if(StringUtils.isBlank(video.getChapterId())||"".equals(video.getChapterId())){
            return false;
        }else if(StringUtils.isBlank(video.getCourseId())||"".equals(video.getChapterId())){
            return false;
        } else if(StringUtils.isBlank(video.getVideoSourseId())||"".equals(video.getVideoSourseId())){
            return false;
        }
        return true;
    }
    //更新视频
    @Override
    public boolean modifyVideo(Video video) {
        boolean flag = validateVideo(video);
        if(flag && !StringUtils.isBlank(video.getId())){
            int result = videoMapper.updateById(video);
            return result > 0;
        }
        return false;
    }
    //通过课程id删除视频章节
    @Override
    public boolean removeVideoByCourseId(String courseId) {
        int result = 0;
        QueryWrapper<Video> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("course_id", courseId);
        //设置指定查询的参数值,使用，分割查询的字段
        queryWrapper.select("video_sourse_id");
        List<Video> videoList = videoMapper.selectList(queryWrapper);
        //判读是否有子章节，有则删除，没有则不删
        if(videoList.size() != 0) {
            //把videoSourceId从集合中提取出来
            String [] str = new String[videoList.size()];
            for (int i = 0; i < videoList.size(); i++){
                str[i] = videoList.get(i).getVideoSourseId();
            }
            //批量删除阿里云视频
            vidClient.removeAliyunVideoMany(str);
            //删除数据库中的数据
            QueryWrapper<Video> wrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId);
            result = videoMapper.delete(wrapper);
        }
        return result > 0;
    }
    //通过视频主键删除
    @Transactional
    @Override
    public boolean removeVideoByPrimaryKey(String id) {

        int result = 0;
        try{
            //删除阿里云中的视频,通过调用videoService服务进行删除
            Video video = videoMapper.selectById(id);
            if(video != null&&StringUtils.isNotBlank(video.getVideoSourseId())) {
                vidClient.removeAliyunVideo(video.getVideoSourseId());
            }
            //删除数据库中视频信息
            result = videoMapper.deleteById(id);
        }catch (Exception e){
            throw new EduException(40000,"删除失败");
        }
        return result > 0;
    }
    //通过主键获取视频信息
    @Override
    public Video getVideoById(String id) {
        //判断id是否为空
        if(!StringUtils.isBlank(id)){
            Video video = videoMapper.selectById(id);
            return video;
        }
        return null;
    }
}
