package com.fxytb.malltiny.dao.elasticsearch;

import com.fxytb.malltiny.model.po.elasticsearch.EsPmsBrand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsPmsBrandRepository extends ElasticsearchRepository<EsPmsBrand, Long> {
}
