package com.changgou.utils;

import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FastDFSUtils {
    public static TrackerClient trackerClient = null;
    public static TrackerServer trackerServer = null;
    public static StorageClient storageClient;
    static{
        //获取classpath下的文件路径
        String filename = new ClassPathResource("fdfs_client.conf").getFilename();
        try {
            //加载tracker链接信息
            ClientGlobal.init(filename);

            //初始化trackerClient，storageClient
            //创建一个tracker访问的客户端对象，用来访问trackerClinet
            trackerClient = new TrackerClient();
            //通过teackerClinet访问trackerServer服务，获取链接信息
            trackerServer = trackerClient.getConnection();
            //通过trackerServer的链接信息获取Storage的链接信息，创建storageClient对象存储 storage的链接信息
            storageClient =  new StorageClient(trackerServer,null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 文件上传
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception{

        //通过storageClient访问storage，实现文件上传，并获取文件上传后的存储信息
        //参数：上传文件的字节数组，文件扩展名，附加参数
        //附件参数：
        NameValuePair[] meta_list = {
                new NameValuePair("name",fastDFSFile.getName()),
                new NameValuePair("MD5",fastDFSFile.getMd5()),
                new NameValuePair("auther",fastDFSFile.getAuther())
        };
        return storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);
    }

    /**
     * 获取文件信息
     * @param groupNmae    文件组名
     * @param remoteName   文件路径
     * @return
     * @throws IOException
     * @throws MyException
     */
    public static FileInfo getFileInfo(String groupNmae,String remoteName) throws Exception {
        return storageClient.get_file_info(groupNmae, remoteName);
    }


    /**
     * 文件下载
     * @param groupNmae   文件组名
     * @param remoteName  文件路径
     * @return  InputStream     文件二进制输入流
     * @throws Exception
     */
    public InputStream fileDownload(String groupNmae,String remoteName) throws Exception{
        byte[] data = storageClient.download_file(groupNmae, remoteName);
        return new ByteArrayInputStream(data);
    }

    /**
     * 删除文件
     * @param groupNmae
     * @param remoteName
     * @return
     * @throws Exception
     *
     * 如果删除后还能显示，可能是缓存问题，在nginx中添加禁用缓存配置
     * #location ~/M00 {
     *     add_header Cache-Control no_store;      添加此行配置
     *     root /data/fast_data/data;
     *     ngx_fastdfs_module;
     * }
     *
     */
    public int deleteFile(String groupNmae,String remoteName) throws Exception{
       return storageClient.delete_file(groupNmae, remoteName);
    }

    /**
     * 获取storage信息，主要为storage Ip，下标
     * @return
     * @throws Exception
     */
    public static StorageServer getStorageInfo()  throws Exception{
        return  trackerClient.getStoreStorage(trackerServer);
    }

    public static String  getTrackerInfo(){
        //获取tracker的IP
        String ip = trackerServer.getInetSocketAddress().getHostString();
        //获取tracker的端口
        int port = ClientGlobal.getG_tracker_http_port();

        return "http://"+ip+":"+port;
    }
}
