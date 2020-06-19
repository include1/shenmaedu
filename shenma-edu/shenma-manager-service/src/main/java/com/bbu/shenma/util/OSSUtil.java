package com.bbu.shenma.util;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.bbu.shenma.config.ConstantPropertiesUtil;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

public class OSSUtil {


    /**
     * //获取图片的请求地址
     * @param fileName:文件名
     * @param inputStream:文件流
     * @param host:目录名
     * @return
     */
    public static String getPicUrl(String fileName, InputStream inputStream,String host) {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = ConstantPropertiesUtil.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.KEY_SECRET;
        String yourBucketName = ConstantPropertiesUtil.BUCKET_NAME;
        String hostName = ConstantPropertiesUtil.HOST;
        if(!StringUtils.isBlank(host)){
            hostName = host;
        }
        //生成uuid
        String randomStr = UUID.randomUUID().toString();
        Format formatter = new SimpleDateFormat("yyyy/MM/dd");
        String format = formatter.format(new Date());
        fileName =  format+"/"+hostName+"/"+ randomStr + fileName.substring(fileName.indexOf("."));

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件流。
        ossClient.putObject(yourBucketName, fileName, inputStream);

        // 关闭OSSClient。
        ossClient.shutdown();
        String path = "https://"+yourBucketName+"."+endpoint+"/"+fileName;
        return path;

    }

}
