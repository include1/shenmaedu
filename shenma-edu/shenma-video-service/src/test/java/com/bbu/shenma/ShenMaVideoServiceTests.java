package com.bbu.shenma;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.vod.upload.impl.*;
import com.aliyun.vod.upload.req.*;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.*;
import com.bbu.shenma.config.AliyunPropertyConfig;
import com.bbu.shenma.service.VideoService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShenMaVideoServiceTests {


    private static String accessKeyId = "LTAI4FnWuYwZpiaRiDh3kSes";
    private static String accessKeySecret = "WH3keVwl7tqf19LmfoAQfqy4XrWQiL";
    //aliyun.vod.file.keyid=LTAI4FnWuYwZpiaRiDh3kSes
    //aliyun.vod.file.keysecret=WH3keVwl7tqf19LmfoAQfqy4XrWQiL
    @Test
    public void test1(){
        String [] aliyunVideoIds = {"dd","dsffdf","dsfsfewg"};
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < aliyunVideoIds.length; i++){
            if(i < aliyunVideoIds.length - 1){
                sb.append(aliyunVideoIds[i]+",");
            }else{
                sb.append(aliyunVideoIds[i]);
            }
        }
        System.out.println(sb);
    }

    @Test
    public void test(){

        try {
            //创建对象
            DefaultAcsClient client = com.bbu.shenma.util.AliyunVodSDKUtils.initVodClient(AliyunPropertyConfig.KEY_ID, AliyunPropertyConfig.KEY_SECRET);
            //创建请求对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //设置传入的视频id
            request.setVideoIds("b2342331bd8b457d9551857d9ab7beeb");
            //创建响应对象
            //返回结果
            DeleteVideoResponse response = client.getAcsResponse(request);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //获取CreateUploadVideoResponse对象,用于上传
    public CreateUploadVideoResponse createUploadVideo(DefaultAcsClient client) throws Exception {
        CreateUploadVideoRequest request = new CreateUploadVideoRequest();
        request.setTitle("this is a sample");
        request.setFileName("filename.mp4");
        return client.getAcsResponse(request);
    }
    //上传视频
    @Test
    public void uploadVideo(){
        //上传文件名
        String fileName = "D:\\新建文件夹\\新建文件夹\\新建文件夹\\20200212_141446.mp4";
        //文件标题
        String title = "测试视频";
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        //设置分片上传

        //设置上传并发线程数

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        System.out.print("RequestId=" + response.getRequestId() + "\n"); //请求视频点播服务的请求ID
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
    //视频点播获取视频凭证测试
    @Test
    public void videoTest() throws ClientException {

        DefaultAcsClient client = AliyunVodSDKUtils.initVodClient(accessKeyId,accessKeySecret);
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        request.setVideoId("c3e8c563d351458aae0867e3f06819f5");
        request.setAuthInfoTimeout(1000L);
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            response = client.getAcsResponse(request);
            System.out.print("VideoMeta = " + response.getVideoMeta().getTitle() + "\n");
            System.out.print("UploadAuth = " + response.getPlayAuth() + "\n");
        } catch (Exception e) {
            System.out.print("ErrorMessage = " + e.getLocalizedMessage());
        }
        System.out.print("RequestId = " + response.getRequestId() + "\n");
    }
    //上传到阿里云的图片测试
    @Test
    public void contextLoads() {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "LTAI4FnWuYwZpiaRiDh3kSes";
        String accessKeySecret = "WH3keVwl7tqf19LmfoAQfqy4XrWQiL";
        String bucketName = "shenma11122-bucket";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 创建存储空间。
        ossClient.createBucket(bucketName);

// 关闭OSSClient。
        ossClient.shutdown();
    }
    @Test
    public void poiWriteTest() throws IOException {
            //1.创建workbook
        Workbook workbook =  new XSSFWorkbook();
            //根据workbooked创建sheet
        Sheet sheet = workbook.createSheet("会员列表");
        //根据sheet创建row
        Row row1 = sheet.createRow(0);
        //根据行创建cell
        Cell cell1 = row1.createCell(0);
        //向cell里面设置值
        cell1.setCellValue("jack");
        //使用输出流写入的文件中
        OutputStream os = new FileOutputStream("F:\\test.xlsx");
        //写入输入的内容
        workbook.write(os);
        os.close();
    }
    @Test
    public void poiReadTest() throws IOException {
        //创建输入流，进行读
        InputStream is = new FileInputStream("F:\\test1.xlsx");
        //创建工作簿
        Workbook workbook = new XSSFWorkbook(is);
        //读取第一行
        Sheet sheet = workbook.getSheetAt(0);
        //读取全部数据
        int lastRowNum = sheet.getLastRowNum();

        for(int i = 0; i < lastRowNum; i++){
            Row row = sheet.getRow(i);
            int col = row.getLastCellNum();
            for(int j = 0; j < col; j++){
                Cell cell = row.getCell(j);
                System.out.println(cell.getStringCellValue());
            }

        }
//        Row row = sheet.getRow(0);
//        Cell cell = row.getCell(0);
//        System.out.println(cell.getStringCellValue());
        //关闭资源
        is.close();
    }
}