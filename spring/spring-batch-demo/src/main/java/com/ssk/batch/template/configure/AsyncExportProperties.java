package com.ssk.batch.template.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ssk
 * @date 2021/3/25
 */
@ConfigurationProperties("async-export")
@Data
public class AsyncExportProperties {

    private String jobName = "ej-export-job";

    private String cron = "0/1 * * * * ?";

    private Integer shardingCount = 10;

    private Boolean logToDb = Boolean.FALSE;

    private String ossEndPoint = "";

    private String ossAccessKeyId = "";

    private String ossAccessKeySecret = "";

    private String ossBucketName = "";

    private Zk zk = new Zk();

    @SuppressWarnings("WeakerAccess")
    @Data
    public static class Zk {
        private String serverLists = "127.0.0.1:2181";
        private String namespace = "async-export";
    }
}
