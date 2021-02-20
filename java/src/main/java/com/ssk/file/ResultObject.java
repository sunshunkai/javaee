package com.ssk.file;

import lombok.Builder;
import lombok.Data;

@Data
public class ResultObject {

    /**
     * 字段
     */
    private String field;

    /**
     * 类型
     */
    private String type;

    /**
     * 是否必填
     */
    private String fill;

    /**
     * 备注
     */
    private String remark;
}
