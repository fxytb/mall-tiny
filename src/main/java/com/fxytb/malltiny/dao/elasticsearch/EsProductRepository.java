package com.fxytb.malltiny.dao.elasticsearch;

import com.fxytb.malltiny.model.po.elasticsearch.EsPmsProduct;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EsProductRepository extends ElasticsearchRepository<EsPmsProduct, Long>{

    List<EsPmsProduct> findAllByKeywordsAndSubTitleAndNameOrderById(String keywords, String subTitle, String name);

}
