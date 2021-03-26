//package com.ssk.batch.template.manager.impl;
//
//import com.aliyun.oss.OSSClient;
//import io.terminus.common.asyncexport.autoconfigure.AsyncExportProperties;
//import io.terminus.common.asyncexport.manager.FileManager;
//
//import javax.annotation.PreDestroy;
//import java.io.ByteArrayInputStream;
//
///**
// * @author Hugh
// * @date 2018/08/14
// */
//public class OssFileManagerImpl implements FileManager {
//
//    private final AsyncExportProperties properties;
//
//    private OSSClient ossClient;
//
//    public OssFileManagerImpl(AsyncExportProperties properties) {
//        this.properties = properties;
//    }
//
//    public void init() {
//        ossClient = new OSSClient(
//            properties.getOssEndPoint(),
//            properties.getOssAccessKeyId(),
//            properties.getOssAccessKeySecret());
//    }
//
//    @PreDestroy
//    public void preDestroy() {
//        ossClient.shutdown();
//    }
//
//    @Override
//    public String upload(String fileName, String fileExt, byte[] data) {
//        ossClient.putObject(
//            properties.getOssBucketName(),
//            buildObjKey(fileName, fileExt),
//            new ByteArrayInputStream(data));
//        return buildUrl(fileName, fileExt);
//    }
//
//    private String buildObjKey(String fileName, String fileExt) {
//        return fileName + "." + fileExt;
//    }
//
//    private String buildUrl(String fileName, String fileExt) {
//        return "https://" + properties.getOssBucketName() + "." + properties.getOssEndPoint()
//            + "/" + buildObjKey(fileName, fileExt);
//    }
//
//}
