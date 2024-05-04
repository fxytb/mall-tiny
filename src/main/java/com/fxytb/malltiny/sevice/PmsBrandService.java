package com.fxytb.malltiny.sevice;

import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.param.PmsBrandAddParam;
import com.fxytb.malltiny.model.param.PmsBrandDeleteParam;
import com.fxytb.malltiny.model.param.PmsBrandQueryParam;
import com.fxytb.malltiny.model.param.PmsBrandUpdateParam;
import com.fxytb.malltiny.model.vo.PmsBrandVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface PmsBrandService {
    CommonResult<List<PmsBrandVo>> listAllPmsBrand();

    CommonResult<String> addPmsBrand(PmsBrandAddParam addParam);

    CommonResult<String> deletePmsBrand(PmsBrandDeleteParam deleteParam);

    CommonResult<String> updatePmsBrand(PmsBrandUpdateParam updateParam);

    CommonResult<PageInfo<PmsBrandVo>> listPmsBrandByPage(PageQuery<PmsBrandQueryParam> pageQuery);
}
