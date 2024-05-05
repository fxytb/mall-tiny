package com.fxytb.malltiny.controller;

import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.param.*;
import com.fxytb.malltiny.model.po.mbg.PmsBrand;
import com.fxytb.malltiny.model.vo.PmsBrandVo;
import com.fxytb.malltiny.sevice.PmsBrandService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "PmsBrandController")
@Tag(name = "PmsBrandController", description = "PmsBrand请求控制类")
@RequestMapping("/pmsBrand")
public class PmsBrandController {

    @Autowired
    private PmsBrandService pmsBrandService;

    /**
     * 查询全部品牌
     *
     * @return 全部品牌
     */
    @GetMapping("/listAll")
    @ApiOperation(value = "查询全部品牌", notes = "查询全部品牌")
    @ApiResponses({
            @ApiResponse(responseCode = "100", description = "暂无数据"),
            @ApiResponse(responseCode = "200", description = "品牌数据查询成功")
    })
    private CommonResult<List<PmsBrandVo>> listAllPmsBrand() {
        return pmsBrandService.listAllPmsBrand();
    }

    /**
     * 分页查询品牌
     *
     * @return 品牌信息
     */
    @PostMapping("/listPmsBrandByPage")
    @ApiOperation(value = "分页查询品牌", notes = "分页查询品牌")
    @ApiResponses({
            @ApiResponse(responseCode = "100", description = "暂无数据"),
            @ApiResponse(responseCode = "200", description = "品牌数据分页查询成功")
    })
    private CommonResult<PageInfo<PmsBrandVo>> listPmsBrandByPage(@RequestBody @Validated @ApiParam(
            value = "分页参数",
            required = true
    ) PageQuery<PmsBrandQueryParam> pageQuery) {
        return pmsBrandService.listPmsBrandByPage(pageQuery);
    }


    /**
     * 根据ID查询品牌
     *
     * @return 品牌信息
     */
    @PostMapping("/listPmsBrandById")
    @ApiOperation(value = "根据ID查询品牌", notes = "根据ID查询品牌")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "idQueryParam",
                    value = "品牌ID查询参数",
                    required = true)
    })
    @ApiResponses({
            @ApiResponse(responseCode = "100", description = "暂无数据"),
            @ApiResponse(responseCode = "200", description = "品牌数据查询成功")
    })
    private CommonResult<PmsBrandVo> listPmsBrandById(@RequestBody @Validated PmsBrandIdQueryParam idQueryParam) {
        return pmsBrandService.listPmsBrandById(idQueryParam);
    }


    /**
     * 新增品牌
     *
     * @return 操作信息
     */
    @PostMapping("/addPmsBrand")
    private CommonResult<PmsBrandVo> addPmsBrand(@RequestBody @Validated PmsBrandAddParam addParam) {
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
