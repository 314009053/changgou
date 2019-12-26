package com.changgou.file;

import java.io.Serializable;

public class FastDFSFile implements Serializable {

    private String name;       //文件名
    private byte[] content;    //文件内容
    private String ext;        //文件扩展名
    private String md5;        //文件MD5摘要
    private String auther;     //文件创建者


    public FastDFSFile(String name, byte[] content, String ext, String md5, String auther) {
        this.name = name;
        this.content = content;
        this.ext = ext;
        this.md5 = md5;
        this.auther = auther;
    }

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }

    public FastDFSFile() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getAuther() {
        return auther;
    }

    public void setAuther(String auther) {
        this.auther = auther;
    }
}
