package com.fxytb.malltiny.sevice.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.dao.mbg.PmsBrandMapper;
import com.fxytb.malltiny.model.param.*;
import com.fxytb.malltiny.model.po.mbg.PmsBrand;
import com.fxytb.malltiny.model.po.mbg.PmsBrandExample;
import com.fxytb.malltiny.model.vo.PmsBrandVo;
import com.fxytb.malltiny.sevice.PmsBrandService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

@Service
@Slf4j
public class PmsBrandServiceImpl implements PmsBrandService {

    @Autowired
    private PmsBrandMapper pmsBrandMapper;

    @Override
    public CommonResult<List<PmsBrandVo>> listAllPmsBrand() {
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectByExample(new PmsBrandExample());
        if (CollUtil.isNotEmpty(pmsBrands)) {
            return CommonResult.success("品牌数据查询成功", BeanUtil.copyToList(pmsBrands, PmsBrandVo.class));
        } else {
            return CommonResult.info("暂无数据");
        }
    }

    @Override
    public CommonResult<PageInfo<PmsBrandVo>> listPmsBrandByPage(PageQuery<PmsBrandQueryParam> pageQuery) {
        Integer pageNum = pageQuery.getPageNum();
        Integer pageSize = pageQuery.getPageSize();
        PageHelper.startPage(pageNum, pageSize);
        PmsBrandExample example = new PmsBrandExample();
        example.setOrderByClause("id");
        List<PmsBrand> pmsBrands = pmsBrandMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(pmsBrands)) {
            PageInfo<PmsBrand> pmsBrandPageInfo = PageInfo.of(pmsBrands);
            long total = pmsBrandPageInfo.getTotal();
            List<PmsBrand> list = pmsBrandPageInfo.getList();
            List<PmsBrandVo> pmsBrandVos = BeanUtil.copyToList(list, PmsBrandVo.class);
            PageInfo<PmsBrandVo> pmsBrandVoPageInfo = PageInfo.of(pmsBrandVos);
            pmsBrandVoPageInfo.setPageNum(pageNum);
            pmsBrandVoPageInfo.setPageSize(pageSize);
            pmsBrandVoPageInfo.setTotal(total);
            return CommonResult.success("品牌数据分页查询成功", pmsBrandVoPageInfo);
        } else {
            return CommonResult.info("暂无数据");
        }
    }


    @Override
    public CommonResult<PmsBrandVo> listPmsBrandById(PmsBrandIdQueryParam idQueryParam) {
        PmsBrand pmsBrand = pmsBrandMapper.selectByPrimaryKey(idQueryParam.getId());
        return ObjectUtil.isEmpty(pmsBrand) ? CommonResult.info("暂无数据") :
                CommonResult.success("品牌数据查询成功", BeanUtil.copyProperties(pmsBrand, PmsBrandVo.class));
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<PmsBrandVo> addPmsBrand(PmsBrandAddParam addParam) {
        try {
            PmsBrand build = PmsBrand.builder().name(addParam.getName()).firstLetter(addParam.getFirstLetter()).sort(addParam.getSort()).factoryStatus(addParam.getFactoryStatus()).showStatus(addParam.getShowStatus()).productCount(0).productCommentCount(0).logo(addParam.getLogo()).bigPic(addParam.getBigPic()).brandStory(addParam.getBrandStory()).build();
            return pmsBrandMapper.insertSelective(build) == 1 ? CommonResult.success("品牌新增成功", BeanUtil.copyProperties(build, PmsBrandVo.class)) : CommonResult.warn("品牌新增失败");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("品牌新增异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("品牌新增异常,请联系管理员查看");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> deletePmsBrand(PmsBrandDeleteParam deleteParam) {
        try {
            return pmsBrandMapper.deleteByPrimaryKey(deleteParam.getId()) == 1 ? CommonResult.success("品牌删除成功") : CommonResult.warn("品牌删除失败");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("品牌删除异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("品牌删除异常,请联系管理员查看");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult<String> updatePmsBrand(PmsBrandUpdateParam updateParam) {
        try {
            PmsBrandExample example = new PmsBrandExample();
            PmsBrandExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(updateParam.getId());
            return pmsBrandMapper.updateByExampleSelective(PmsBrand.builder().name(updateParam.getName()).firstLetter(updateParam.getFirstLetter()).sort(updateParam.getSort()).factoryStatus(updateParam.getFactoryStatus()).showStatus(updateParam.getShowStatus()).logo(updateParam.getLogo()).bigPic(updateParam.getBigPic()).brandStory(updateParam.getBrandStory()).build(), example) == 1 ? CommonResult.success("品牌更新成功") : CommonResult.warn("品牌更新失败");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            log.error("品牌更新异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("品牌更新异常,请联系管理员查看");
        }
    }


}
