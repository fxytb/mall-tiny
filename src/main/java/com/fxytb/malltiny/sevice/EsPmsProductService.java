package com.fxytb.malltiny.sevice;

import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.param.EsPmsProductQueryParam;
import com.fxytb.malltiny.model.po.elasticsearch.EsBlank;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsBrand;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsProduct;
import com.github.pagehelper.PageInfo;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.List;

public interface EsPmsProductService {


    CommonResult<String> importAll();


    CommonResult<String> delete(Long id);

    CommonResult<String> create(Long id);

    CommonResult<String> update(Long id);

    CommonResult<List<EsPmsProduct>> queryAll();

    CommonResult<EsPmsProduct> queryById(Long id);

    CommonResult<PageInfo<EsPmsProduct>> queryPage(PageQuery<EsPmsProductQueryParam> pageQuery);

    CommonResult<List<EsPmsProduct>> findAllByKeywordsAndSubTitleAndNameOrderById(EsPmsProductQueryParam param);

    CommonResult<SearchHits<EsBlank>> queryPageByCondition();
}
