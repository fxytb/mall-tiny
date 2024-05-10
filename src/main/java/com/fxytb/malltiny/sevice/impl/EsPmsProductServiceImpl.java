package com.fxytb.malltiny.sevice.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.stream.StreamUtil;
import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.dao.elasticsearch.EsProductRepository;
import com.fxytb.malltiny.dao.mbg.PmsProductMapper;
import com.fxytb.malltiny.model.param.EsPmsProductQueryParam;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsProduct;
import com.fxytb.malltiny.sevice.EsPmsProductService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EsPmsProductServiceImpl implements EsPmsProductService {

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private EsProductRepository esProductRepository;

    /**
     * @return
     */
    @Override
    public CommonResult<String> importAll() {
        try {
            List<EsPmsProduct> pmsProducts = productMapper.queryAllEsPmsProduct();
            esProductRepository.saveAll(pmsProducts);
            return CommonResult.success("商品数据存储elasticsearch成功");
        } catch (Exception e) {
            log.error("商品数据存储elasticsearch异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("商品数据存储elasticsearch异常,请联系管理员查看");
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CommonResult<String> delete(Long id) {
        try {
            esProductRepository.deleteById(id);
            return CommonResult.success("删除elasticsearch商品数据成功");
        } catch (Exception e) {
            log.error("删除elasticsearch商品数据异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("删除elasticsearch商品数据异常,请联系管理员查看");
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CommonResult<String> create(Long id) {
        try {
            EsPmsProduct pmsProduct = productMapper.queryEsPmsProductById(id);
            esProductRepository.save(pmsProduct);
            return CommonResult.success("新增elasticsearch商品数据成功");
        } catch (Exception e) {
            log.error("新增elasticsearch商品数据异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("新增elasticsearch商品数据异常,请联系管理员查看");
        }
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CommonResult<String> update(Long id) {
        try {
            EsPmsProduct pmsProduct = productMapper.queryEsPmsProductById(id);
            pmsProduct.setName("love songsong");
            pmsProduct.setAttributeValues(null);
            esProductRepository.save(pmsProduct);
            return CommonResult.success("新增elasticsearch商品数据成功");
        } catch (Exception e) {
            log.error("更新elasticsearch商品数据异常,异常信息:{}", e.getMessage(), e);
            return CommonResult.error("更新elasticsearch商品数据异常,请联系管理员查看");
        }
    }

    /**
     * @return
     */
    @Override
    public CommonResult<List<EsPmsProduct>> queryAll() {
        Iterable<EsPmsProduct> allProduct = esProductRepository.findAll();
        return CollUtil.isNotEmpty(allProduct) ?
                CommonResult.success("查询elasticsearch全部商品数据成功", StreamUtil.of(allProduct).collect(Collectors.toList())) :
                CommonResult.info("暂无数据");
    }

    /**
     * @param id
     * @return
     */
    @Override
    public CommonResult<EsPmsProduct> queryById(Long id) {
        Optional<EsPmsProduct> esPmsProduct = esProductRepository.findById(id);
        return esPmsProduct.isPresent() ? CommonResult.success("ID查询es中商品成功", esPmsProduct.get()) : CommonResult.info("暂无数据");
    }

    /**
     * @param pageQuery
     * @return
     */
    @Override
    public CommonResult<PageInfo<EsPmsProduct>> queryPage(PageQuery<EsPmsProductQueryParam> pageQuery) {
        Integer pageNum = pageQuery.getPageNum();
        Integer pageSize = pageQuery.getPageSize();
        EsPmsProductQueryParam data = pageQuery.getData();
        Page<EsPmsProduct> all = esProductRepository.findAll(PageRequest.of(pageNum - 1, pageSize));
        List<EsPmsProduct> esPmsProducts = all.get().collect(Collectors.toList());
        if (CollUtil.isNotEmpty(esPmsProducts)) {
            PageInfo<EsPmsProduct> pageInfo = PageInfo.of(esPmsProducts);
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(all.getTotalElements());
            return CommonResult.success("分页查询es中商品成功", pageInfo);
        } else {
            return CommonResult.info("暂无数据");
        }
    }

    /**
     * @param param
     * @return
     */
    @Override
    public CommonResult<List<EsPmsProduct>> findAllByKeywordsAndSubTitleAndNameOrderById(EsPmsProductQueryParam param) {
        String subTitle = param.getSubTitle();
        String name = param.getName();
        String keyword = param.getKeyword();
        List<EsPmsProduct> esPmsProducts = esProductRepository.findAllByKeywordsAndSubTitleAndNameOrderById(keyword, subTitle, name);
        return CollUtil.isNotEmpty(esPmsProducts) ? CommonResult.success("根据关键字、子标题、商品名称并按ID排序查询成功", esPmsProducts) : CommonResult.info("暂无数据");
    }

    /**
     * @param pageQuery
     * @return
     */
    @Override
    public CommonResult<PageInfo<EsPmsProduct>> queryPageByCondition(PageQuery<EsPmsProductQueryParam> pageQuery) {
        Integer pageNum = pageQuery.getPageNum();
        Integer pageSize = pageQuery.getPageSize();
        EsPmsProductQueryParam data = pageQuery.getData();


        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();


        List<EsPmsProduct> esPmsProducts = esProductRepository.queryPageByCondition(pageNum - 1, pageSize, data);
        if (CollUtil.isNotEmpty(esPmsProducts)) {
            PageInfo<EsPmsProduct> pageInfo = PageInfo.of(esPmsProducts);
            pageInfo.setPageNum(pageNum);
            pageInfo.setPageSize(pageSize);
            pageInfo.setTotal(esPmsProducts.size());
            return CommonResult.success("暂无数据");
        } else {
            return CommonResult.info("暂无数据");
        }
    }

}
