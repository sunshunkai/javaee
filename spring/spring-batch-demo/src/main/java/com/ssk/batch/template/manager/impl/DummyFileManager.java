//package com.ssk.batch.template.manager.impl;
//
//import io.terminus.common.asyncexport.manager.FileManager;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//
///**
// * @author Hugh
// * @date 2018/08/14
// */
//@Slf4j
//public class DummyFileManager implements FileManager {
//
//    /**
//     * 上传文件
//     *
//     * @param fileName 文件名
//     * @param fileExt 文件扩展名
//     * @param data 文件数据
//     * @return 文件URL
//     */
//    @Override
//    public String upload(String fileName, String fileExt, byte[] data) {
//        try {
//            val file = new File(fileName + "." + fileExt);
//            val fos = new FileOutputStream(file);
//            fos.write(data);
//            fos.flush();
//            fos.close();
//            return file.getAbsolutePath();
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//        return "";
//    }
//}
