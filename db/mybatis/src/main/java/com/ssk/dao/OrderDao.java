package com.ssk.dao;

import com.ssk.mode.OrderDO;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author 孙顺凯（惊云）
 * @date 2021/4/9
 */
@Repository
public interface OrderDao extends Mapper<OrderDO> {
}
