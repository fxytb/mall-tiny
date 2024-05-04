package com.fxytb.malltiny.controller;

import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.param.*;
import com.fxytb.malltiny.model.vo.PmsBrandVo;
import com.fxytb.malltiny.sevice.PmsBrandService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/mall/tiny")
public class MallTinyController {

    @Autowired
    private PmsBrandService pmsBrandService;


    /**
     * 查询全部品牌
     *
     * @return 全部品牌
     */
    @GetMapping("/listAll")
    private CommonResult<List<PmsBrandVo>> listAllPmsBrand() {
        return pmsBrandService.listAllPmsBrand();
    }

    /**
     * 分页查询品牌
     *
     * @return 品牌信息
     */
    @PostMapping("/listPmsBrandByPage")
    private CommonResult<PageInfo<PmsBrandVo>> listPmsBrandByPage(@RequestBody @Validated PageQuery<PmsBrandQueryParam> pageQuery) {
        return pmsBrandService.listPmsBrandByPage(pageQuery);
    }


    /**
     * 根据ID查询品牌
     *
     * @return 品牌信息
     */
    @PostMapping("/listPmsBrandById")
    private CommonResult<PmsBrandVo> listPmsBrandById(@RequestBody @Validated PmsBrandIdQueryParam idQueryParam) {
        return pmsBrandService.listPmsBrandById(idQueryParam);
    }


    /**
     * 新增品牌
     *
     * @return 操作信息
     */
    @PostMapping("/addPmsBrand")
    private CommonResult<String> addPmsBrand(@RequestBody @Validated PmsBrandAddParam addParam) {
        return pmsBrandService.addPmsBrand(addParam);
    }

    /**
     * 删除品牌
     *
     * @return 操作信息
     */
    @PostMapping("/deletePmsBrand")
    private CommonResult<String> deletePmsBrand(@RequestBody @Validated PmsBrandDeleteParam deleteParam) {
        return pmsBrandService.deletePmsBrand(deleteParam);
    }

    /**
     * 更新品牌
     *
     * @return 操作信息
     */
    @PostMapping("/updatePmsBrand")
    private CommonResult<String> updatePmsBrand(@RequestBody @Validated PmsBrandUpdateParam updateParam) {
        return pmsBrandService.updatePmsBrand(updateParam);
    }

}
