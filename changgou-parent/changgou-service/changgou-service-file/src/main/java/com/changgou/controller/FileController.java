package com.changgou.controller;

import com.changgou.file.FastDFSFile;
import com.changgou.utils.FastDFSUtils;
import entity.Result;
import entity.StatusCode;
import org.csource.fastdfs.FileInfo;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
@CrossOrigin
public class FileController {

    @PostMapping("/upload")
    public Result upload(@RequestBody MultipartFile file) throws Exception {

        FastDFSFile fastDFSFile = new FastDFSFile(file.getOriginalFilename(),file.getBytes(), StringUtils.getFilenameExtension(file.getOriginalFilename()));
        String[] result = FastDFSUtils.upload(fastDFSFile);
        return new Result(true, StatusCode.OK,"文件上传成功！path="+result[0]+"/"+result[1]);

    }

    @GetMapping("info/{groupName}/{remoteName}")
    public Result fileInfo(@PathVariable("groupName") String groupName,@PathVariable("remoteName") String remoteName) throws Exception {
        FileInfo fileInfo = FastDFSUtils.getFileInfo(groupName,remoteName);
        return new Result(true, StatusCode.OK,"文件信息获取成功！"+fileInfo);

    }
}
