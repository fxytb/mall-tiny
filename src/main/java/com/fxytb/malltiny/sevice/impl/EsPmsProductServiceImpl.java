package com.fxytb.malltiny.sevice.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.stream.StreamUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONUtil;
import co.elastic.clients.elasticsearch._types.aggregations.MaxAggregation;
import com.fxytb.malltiny.common.page.PageQuery;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.dao.elasticsearch.EsProductRepository;
import com.fxytb.malltiny.dao.mbg.PmsProductMapper;
import com.fxytb.malltiny.model.param.EsPmsProductQueryParam;
import com.fxytb.malltiny.model.po.elasticsearch.EsBlank;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsBrand;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsProduct;
import com.fxytb.malltiny.sevice.EsPmsProductService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.parsson.JsonUtil;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.MaxAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.NumericMetricsAggregator;
import org.elasticsearch.search.aggregations.metrics.ValueCountAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentParser;
import org.elasticsearch.xcontent.smile.SmileXContentParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.*;
import org.springframework.data.elasticsearch.core.query.*;
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
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

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
     * @return
     */
    @Override
    public CommonResult<SearchHits<EsBlank>> queryPageByCondition() {

        //查询最大balance
//        MaxAggregationBuilder maxAggregationBuilder = AggregationBuilders.max("maxBalance").field("balance");
//        TermsAggregationBuilder genderCount = AggregationBuilders.terms("genderCount")
//                .field("gender.keyword").subAggregation(AggregationBuilders.max("age").field("age"));
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                //全部查询
                .withQuery(QueryBuilders.matchAllQuery())
                //精确查询
                //.withQuery(QueryBuilders.termsQuery("firstname.keyword", "West"))
                //精确匹配in
                //.withQuery(QueryBuilders.termsQuery("firstname.keyword", "West","Oconnor"))
                //match匹配
                //.withQuery(QueryBuilders.matchQuery("firstname","Oconnor"))
                //match模糊匹配
                //.withQuery(QueryBuilders.matchQuery("firstname","Oconnor").analyzer("ik_max_word"))
                //match多字段匹配
                //.withQuery(QueryBuilders.multiMatchQuery("F","gender","firstname"))
                //模糊匹配
                //.withQuery(QueryBuilders.fuzzyQuery("address", "659"))
                //前缀匹配
                //.withQuery(QueryBuilders.prefixQuery("address", "659"))
                //通配符匹配
                //.withQuery(QueryBuilders.wildcardQuery("firstname.keyword","O*"))
                //正则匹配
                //.withQuery(QueryBuilders.regexpQuery("firstname.keyword","(Oconnor)|(West)").boost(1))
                //多条件匹配
                //.withQuery(QueryBuilders.boolQuery()
                //.should(QueryBuilders.termsQuery("firstname.keyword","Oconnor"))
                //.should(QueryBuilders.termsQuery("firstname.keyword","West"))
                //.mustNot(QueryBuilders.termsQuery("firstname.keyword","West"))
                //)
                //过滤数据
                //.withFilter(QueryBuilders.termQuery("firstname.keyword","Oconnor"))
                //范围查询
                //.withFilter(QueryBuilders.rangeQuery("balance").lt(20000).gt(10000))
                //聚合查询
//                .withAggregations(genderCount)
                //分页
//                .withPageable(PageRequest.of(0, 3))
                //排序
//                .withSorts(SortBuilders.fieldSort("balance").order(SortOrder.DESC))
                .build();
        SearchHits<EsBlank> search = elasticsearchRestTemplate.search(build, EsBlank.class);


        //查询全部

        SearchHits<EsBlank> search1 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build(), EsBlank.class);

        log.info("{}", JSONUtil.toJsonStr(search1));


        //分页查询
        SearchHits<EsBlank> search2 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withPageable(PageRequest.of(0, 5))
                .build(), EsBlank.class);

        log.info("{}", JSONUtil.toJsonStr(search2));


        //排序查询
        SearchHits<EsBlank> search3 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withSorts(SortBuilders.fieldSort("balance").order(SortOrder.DESC))
                .build(), EsBlank.class);

        log.info("{}", JSONUtil.toJsonStr(search3.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList())));

        //搜索并返回指定字段内容
        SearchHits<EsBlank> search4 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withSourceFilter(new FetchSourceFilterBuilder()
                        .withIncludes("account_number", "balance")
                        .build())
                .build(), EsBlank.class);

        log.info("{}", JSONUtil.toJsonStr(search4));

        //条件查询

        SearchHits<EsBlank> search5 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("account_number", 20))
                .build(), EsBlank.class);

        log.info("{}", JSONUtil.toJsonStr(search5));


        //文本条件查询

        SearchHits<EsBlank> search6 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("address", "mill"))
                .withSourceFilter(new FetchSourceFilterBuilder()
                        .withIncludes("account_number", "address")
                        .build())
                .build(), EsBlank.class);
        log.info("{}", JSONUtil.toJsonStr(search6));


        //短语匹配
        SearchHits<EsBlank> search7 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("address", "mill lane"))
                .build(), EsBlank.class);

        log.info("{}", JSONUtil.toJsonStr(search7));

        //组合搜索
        SearchHits<EsBlank> search8 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.boolQuery()
                                .must(QueryBuilders.termsQuery("address", "mill"))
                                .must(QueryBuilders.termsQuery("address", "lane"))
                        )
                        .build()
                , EsBlank.class);
        log.info("{}", JSONUtil.toJsonStr(search8));

        //组合搜索2
        SearchHits<EsBlank> search9 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                        .withQuery(QueryBuilders.boolQuery()
                                .should(QueryBuilders.termsQuery("address", "mill"))
                                .must(QueryBuilders.termsQuery("address", "lane"))
                        )
                        .build()
                , EsBlank.class);
        log.info("{}", JSONUtil.toJsonStr(search9));

        //组合搜索3
        SearchHits<EsBlank> search10 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.termsQuery("address", "mill"))
                        .mustNot(QueryBuilders.termsQuery("address", "lane"))
                )
                .build(), EsBlank.class);
        log.info("{}", JSONUtil.toJsonStr(search10));

        //组合搜索4-过滤
        SearchHits<EsBlank> search11 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchAllQuery())
                        .filter(QueryBuilders.rangeQuery("balance")
                                .gte(30000)
                                .lte(40000))
                )
                .build(), EsBlank.class);
        log.info("{}", JSONUtil.toJsonStr(search11));

        //搜索聚合
        SearchHits<EsBlank> search12 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withAggregations(AggregationBuilders
                        .terms("group_by_state")
                        .field("state.keyword")
                )
                .build(), EsBlank.class);
        ElasticsearchAggregations aggregations = (ElasticsearchAggregations) search12.getAggregations();
        List<Aggregation> list = aggregations.aggregations().asList();
        ParsedStringTerms obj = (ParsedStringTerms) list.get(0);
        List<ParsedStringTerms.ParsedBucket> buckets = (List<ParsedStringTerms.ParsedBucket>) obj.getBuckets();
        log.info("{}", JSONUtil.toJsonStr(buckets.stream().map(item -> item.getKey() + "-" + item.getDocCount()).collect(Collectors.toList())));

        //搜索聚合2
        SearchHits<EsBlank> search13 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withAggregations(AggregationBuilders
                        .terms("group_by_state")
                        .field("state.keyword")
                        .subAggregation(AggregationBuilders
                                .avg("average_balance")
                                .field("balance")
                                .format("0.00")))
                .build(), EsBlank.class);
        log.info("{}", search13);

        //搜索聚合3 聚合降序平均
        SearchHits<EsBlank> search14 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withAggregations(AggregationBuilders
                        .terms("group_by_state")
                        .field("state.keyword")
                        .subAggregation(
                                AggregationBuilders
                                        .avg("average_balance")
                                        .field("balance"))
                        .order(BucketOrder.aggregation("average_balance", false))
                )
                .build(), EsBlank.class);
        log.info("{}", search14);


        //搜索聚合4 分段个数平均

        SearchHits<EsBlank> search15 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withAggregations(
                        AggregationBuilders
                                .range("group_by_age")
                                .field("age")
                                .addRange(20, 30)
                                .addRange(30, 40)
                                .addRange(40, 50)
                                .subAggregation(AggregationBuilders
                                        .terms("group_by_gender")
                                        .field("gender.keyword")
                                        .subAggregation(AggregationBuilders.avg("average_balance")
                                                .field("balance"))))
                .build(), EsBlank.class);
        log.info("{}", search15);


        //高亮查询
        HighlightBuilder.Field field = new HighlightBuilder.Field("address");
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .preTags("<span>")
                .postTags("</span>")
                .field(field);
        SearchHits<EsBlank> search16 = elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchPhraseQuery("address","Mill lane"))
                .withHighlightBuilder(highlightBuilder)
                .build(), EsBlank.class);
        log.info("{}", search16);

        return CommonResult.success("查询成功", search);


    }

}
