package com.fxytb.malltiny.controller;

import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.param.EsPmsProductQueryParam;
import com.fxytb.malltiny.model.po.elasticsearch.EsBlank;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsBrand;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsProduct;
import com.fxytb.malltiny.sevice.EsPmsProductService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/product")
@Api(tags = "PmsProductController")
@Tag(name = "PmsProductController", description = "商品请求控制类")
public class PmsProductController {


    @Autowired
    private EsPmsProductService esPmsProductService;


    @PostMapping("/import/all")
    @ApiOperation(value = "导入全部商品至es", notes = "导入全部商品至es")
    public CommonResult<String> importAll() {
        return esPmsProductService.importAll();
    }

    @PostMapping("/delete")
    @ApiOperation(value = "根据ID删除es中商品", notes = "根据ID删除es中商品")
    @ApiImplicitParam(name = "id", value = "商品ID")
    public CommonResult<String> delete(@RequestParam(name = "id") @Validated @NotNull Long id) {
        return esPmsProductService.delete(id);
    }

    @PostMapping("/create")
    @ApiOperation(value = "在es中创建商品", notes = "在es中创建商品")
    @ApiImplicitParam(name = "id", value = "商品ID")
    public CommonResult<String> create(@RequestParam(name = "id") @Validated @NotEmpty Long id) {
        return esPmsProductService.create(id);
    }

    @PostMapping("/update")
    @ApiOperation(value = "在es中更新商品", notes = "在es中更新商品")
    @ApiImplicitParam(name = "id", value = "商品ID")
    public CommonResult<String> update(@RequestParam(name = "id") @Validated @NotEmpty Long id) {
        return esPmsProductService.update(id);
    }

    @PostMapping("/query/all")
    @ApiOperation(value = "查询es中全部商品", notes = "查询es中全部商品")
    public CommonResult<List<EsPmsProduct>> queryAll() {
        return esPmsProductService.queryAll();
    }

    @PostMapping("/queryById")
    @ApiOperation(value = "ID查询es中商品", notes = "ID查询es中商品")
    public CommonResult<EsPmsProduct> queryById(@RequestParam(name = "id") @Validated @NotNull Long id) {
        return esPmsProductService.queryById(id);
    }

    @PostMapping("/queryPage")
    @ApiOperation(value = "分页查询es中商品", notes = "分页查询es中商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageQuery", value = "es商品分页查询参数")
    })
    public CommonResult<PageInfo<EsPmsProduct>> queryPage(@RequestBody @Validated PageQuery<EsPmsProductQueryParam> pageQuery) {
        return esPmsProductService.queryPage(pageQuery);
    }

    @PostMapping("/findAllByKeywordsAndSubTitleAndNameOrderById")
    @ApiOperation(value = "根据关键字、子标题、商品名称查询并按ID排序", notes = "根据关键字、子标题、商品名称查询并按ID排序")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "es商品查询参数")
    })
    public CommonResult<List<EsPmsProduct>> findAllByKeywordsAndSubTitleAndNameOrderById(@RequestBody @Validated EsPmsProductQueryParam param) {
        return esPmsProductService.findAllByKeywordsAndSubTitleAndNameOrderById(param);
    }

    @PostMapping("/queryPageByCondition")
    @ApiOperation(value = "es商品分页查询", notes = "es商品分页查询")
    public CommonResult<SearchHits<EsBlank>> queryPageByCondition() {
        return esPmsProductService.queryPageByCondition();
    }

}
