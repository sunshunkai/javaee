package com.ssk.batch.template.manager;

/**
 * @author Hugh
 * @date 2018/08/14
 */
public interface FileManager {

    /**
     * 上传文件
     *
     * @param fileName 文件名
     * @param fileExt 文件扩展名
     * @param data 文件数据
     * @return 文件URL
     */
    String upload(String fileName, String fileExt, byte[] data);

}
