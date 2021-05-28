package com.ssk.json.jackson;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/5/16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ClsCircle extends ClsShape{
    Integer radius;    //弧度
}
