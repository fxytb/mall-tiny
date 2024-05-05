package com.fxytb.malltiny.controller;

import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.config.RedisConfig;
import com.fxytb.malltiny.model.param.PmsBrandAddParam;
import com.fxytb.malltiny.model.param.PmsBrandIdQueryParam;
import com.fxytb.malltiny.model.param.PmsBrandUpdateParam;
import com.fxytb.malltiny.model.po.mbg.PmsBrand;
import com.fxytb.malltiny.model.vo.PmsBrandVo;
import com.fxytb.malltiny.sevice.PmsBrandService;
import com.fxytb.malltiny.sevice.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/redis")
@Api(tags = "RedisController")
@Tag(name = "RedisController", description = "redis控制类")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private PmsBrandService pmsBrandService;


    @PostMapping("/testQuerySaveRedis")
    @Cacheable(value = RedisConfig.REDIS_KEY_DATABASE, key = "'pms:brand:'+#idQueryParam.id", unless = "#result.data==null")
    @ApiOperation(value = "测试查询存储redis", notes = "测试查询存储redis")
    @ApiImplicitParams({@ApiImplicitParam(name = "idQueryParam", value = "品牌ID查询参数", required = true)})
    @ApiResponses({@ApiResponse(responseCode = "100", description = "暂无数据"), @ApiResponse(responseCode = "200", description = "品牌数据查询成功")})
    public CommonResult<PmsBrandVo> testQuerySaveRedis(@RequestBody @Validated PmsBrandIdQueryParam idQueryParam) {
        return pmsBrandService.listPmsBrandById(idQueryParam);
    }

    @PostMapping("/testSaveSaveRedis")
    @CachePut(value = RedisConfig.REDIS_KEY_DATABASE, key = "'pms:brand:'+#result.data.id")
    @ApiOperation(value = "测试新增存储redis", notes = "测试新增存储redis")
    @ApiImplicitParams({@ApiImplicitParam(name = "addParam", value = "品牌新增参数", required = true)})
    @ApiResponses({@ApiResponse(responseCode = "100", description = "暂无数据"), @ApiResponse(responseCode = "200", description = "品牌数据新增成功")})
    public CommonResult<PmsBrandVo> testSaveSaveRedis(@RequestBody @Validated PmsBrandAddParam addParam) {
        return pmsBrandService.addPmsBrand(addParam);
    }

    @PostMapping("/testUpdateClearRedis")
    @CacheEvict(value = RedisConfig.REDIS_KEY_DATABASE, key = "'pms:brand:'+#updateParam.id",condition = "#updateParam.id!=null")
    @ApiOperation(value = "测试更新清除redis", notes = "测试更新清除redis")
    @ApiImplicitParams({@ApiImplicitParam(name = "updateParam", value = "品牌更新参数", required = true)})
    @ApiResponses({@ApiResponse(responseCode = "100", description = "暂无数据"), @ApiResponse(responseCode = "200", description = "品牌数据更新成功")})
    public CommonResult<String> testUpdateClearRedis(@RequestBody @Validated PmsBrandUpdateParam updateParam) {
        return pmsBrandService.updatePmsBrand(updateParam);
    }


}
