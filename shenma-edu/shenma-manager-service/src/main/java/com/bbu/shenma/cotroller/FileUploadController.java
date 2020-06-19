package com.bbu.shenma.cotroller;


import com.bbu.shenma.util.OSSUtil;
import com.bbu.shenma.util.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/shenma/oss")
@CrossOrigin
public class FileUploadController {

    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file,@RequestParam(value = "host",required = false)String host){
        String url =null;
        try{
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件流
            InputStream in = file.getInputStream();
            //床文件到oos中，并生成访问地址
            url = OSSUtil.getPicUrl(fileName,in,host);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.ok().data("imageUrl",url);
    }
}
