package com.ssk.batch.template.manager.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Hugh
 * @date 2018/08/14
 */
@Data
public class TaskDetail implements Serializable {

    private static final long serialVersionUID = -1853461139036508700L;

    private String jobName;
    private String id;
    private TaskStatus status;
    private Object[] param;
    private Object result;
}
