package com.fxytb.malltiny;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.json.JSONUtil;
import com.fxytb.malltiny.dao.elasticsearch.EsPmsBrandRepository;
import com.fxytb.malltiny.model.po.elasticsearch.EsPmsBrand;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.SearchExecutionContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightParameters;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
class MallTinyApplicationTests {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Autowired
    private EsPmsBrandRepository esPmsBrandRepository;

    /**
     * 创建索引并映射字段
     */
    @Test
    void testInitIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(EsPmsBrand.class);
        indexOperations.delete();
    }


    /**
     * 测试新增数据至es
     */
    @Test
    void testInitInsert() {
        EsPmsBrand entity = new EsPmsBrand();
        entity.setId(1l);
        entity.setName("23123");
        entity.setFirstLetter("A");
        entity.setSort(1);
        entity.setFactoryStatus(1);
        entity.setShowStatus(1);
        entity.setProductCount(10);
        entity.setProductCommentCount(11);
        entity.setLogo("http://www.baidu.com");
        entity.setBigPic("http://www.baidu.com");
        entity.setBrandStory("464 [main] DEBUG org.springframework.test.context.support.AbstractContextLoader - Did not detect default resource location for test class [co");
        elasticsearchRestTemplate.save(entity);
    }

    /**
     * 测试批量新增数据至es
     */
    @Test
    void testBulkInitInsert() {
        List<IndexQuery> list = new ArrayList<>();
        for (int i = 1; i <= 20000; i++) {
            EsPmsBrand entity = new EsPmsBrand();
            entity.setId((long) i);
            entity.setName(String.valueOf(i));
            entity.setFirstLetter("A");
            entity.setSort(i);
            entity.setFactoryStatus(i);
            entity.setShowStatus(i);
            entity.setProductCount(i);
            entity.setProductCommentCount(i);
            entity.setLogo("http://www.baidu.com");
            entity.setBigPic("http://www.baidu.com");
            entity.setBrandStory("464 [main] DEBUG org.springframework.test.context.support.AbstractContextLoader - Did not detect default resource location for test class [co");
            list.add(new IndexQueryBuilder().withObject(entity).build());
        }
        elasticsearchRestTemplate.bulkIndex(list, EsPmsBrand.class);
    }

    /**
     * 测试删除数据从es
     */
    @Test
    void testDeleteDoc() {
        elasticsearchRestTemplate.delete("20000", EsPmsBrand.class);
    }

    /**
     * 测试条件删除数据
     */
    @Test
    void testDeleteDoc1() {
        NativeSearchQuery build = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.boolQuery().must(new BoolQueryBuilder()
                        .should(QueryBuilders.matchQuery("sort", "3"))
                        .should(QueryBuilders.matchQuery("sort", "4"))))
                .build();
        elasticsearchRestTemplate.delete(build, EsPmsBrand.class);
    }

    /**
     * 测试条件更新数据
     */
    @Test
    void testUpdateDoc() {
        EsPmsBrand entity = new EsPmsBrand();
        entity.setId(4l);
        entity.setSort(1231243123);
        elasticsearchRestTemplate.save(entity);
    }

    /**
     * 测试条件部分更新数据
     */
    @Test
    void testUpdateDoc2() {
        String script = "ctx._source.sort=30000;ctx._source.logo='https://www.baidu.com'";
        UpdateQuery build = UpdateQuery.builder("5").withScript(script).build();
        elasticsearchRestTemplate.update(build, IndexCoordinates.of("brand"));
    }

    /**
     * 测试条件部分更新数据
     */
    @Test
    void testUpdateDoc3() {
        //组装更新条件
        EsPmsBrand esPmsBrand = new EsPmsBrand();
        esPmsBrand.setName("123412412");
        UpdateQuery updateQuery = UpdateQuery
                .builder("6")
                .withDocument(Document.create().fromJson(JSONUtil.toJsonStr(esPmsBrand)))
                .build();
        elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("brand"));
    }

    /**
     * 测试条件部分更新数据
     */
    @Test
    void testQuery() {
        NativeSearchQuery query = new NativeSearchQueryBuilder().build();
        SearchHits<EsPmsBrand> search = elasticsearchRestTemplate.search(query, EsPmsBrand.class);
        List<EsPmsBrand> esPmsBrands = search.stream().map(SearchHit::getContent).collect(Collectors.toList());
        esPmsBrands.forEach(System.out::println);
    }

    /**
     * 测试条件部分更新数据
     */
    @Test
    void testQuery2() {


        System.out.println(JSONUtil.toJsonStr(elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders
                        .matchAllQuery())
                .build(), EsPmsBrand.class).getSearchHits()));


    }


}
