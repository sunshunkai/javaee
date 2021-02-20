package com.ssk.jpa.mode.dao;

import com.ssk.jpa.mode.HouseIndexTemplate;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @author ssk
 * @date 2020/12/31
 */
@Repository
public interface HouseRepository extends ElasticsearchRepository<HouseIndexTemplate,Long> {
}
