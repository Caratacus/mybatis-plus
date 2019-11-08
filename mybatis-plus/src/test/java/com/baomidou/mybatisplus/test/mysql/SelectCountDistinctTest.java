/*
 * Copyright (c) 2011-2019, hubin (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.baomidou.mybatisplus.test.mysql;

import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.test.base.entity.CommonData;
import com.baomidou.mybatisplus.test.base.entity.CommonLogicData;
import com.baomidou.mybatisplus.test.base.mapper.commons.CommonDataMapper;
import com.baomidou.mybatisplus.test.base.mapper.commons.CommonLogicDataMapper;

@DirtiesContext
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:mysql/spring-test-mysql.xml"})
class SelectCountDistinctTest {

    @Resource
    private CommonLogicDataMapper commonLogicMapper;
    @Resource
    private CommonDataMapper commonDataMapper;

    @Test
    @Order(1)
    void testCountDistinct() {
        QueryWrapper<CommonData> distinct = new QueryWrapper<>();
        distinct.select("distinct test_int");
        distinct.eq("test_int", 25).or().eq("test_str", "test");
        int count = commonDataMapper.selectCount(distinct);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(2)
    void testCountDistinctTwoColumn() {
        QueryWrapper<CommonData> distinct = new QueryWrapper<>();
        distinct.select("distinct test_int, test_str");
        distinct.eq("test_int", 25).or().eq("test_str", "test");
        int count = commonDataMapper.selectCount(distinct);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(3)
    void testLogicCountDistinct() {
        QueryWrapper<CommonLogicData> distinct = new QueryWrapper<>();
        distinct.select("distinct test_int");
        distinct.eq("test_int", 25).or().eq("test_str", "test");
        int count = commonLogicMapper.selectCount(distinct);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(4)
    void testLogicSelectList() {
        QueryWrapper<CommonLogicData> commonQuery = new QueryWrapper<>();
        List<CommonLogicData> commonLogicDataList = commonLogicMapper.selectList(commonQuery);
        CommonLogicData commonLogicData = commonLogicDataList.get(0);
        Assertions.assertEquals(25, commonLogicData.getTestInt().intValue());
        Assertions.assertEquals("test", commonLogicData.getTestStr());
    }

    @Test
    @Order(5)
    void testLogicCountDistinctUseLambda() {
        LambdaQueryWrapper<CommonLogicData> lambdaQueryWrapper =
            new QueryWrapper<CommonLogicData>().select("distinct test_int").lambda();
        int count = commonLogicMapper.selectCount(lambdaQueryWrapper);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(6)
    void testCountDistinctUseLambda() {
        LambdaQueryWrapper<CommonData> lambdaQueryWrapper =
            new QueryWrapper<CommonData>().select("distinct test_int, test_str").lambda();
        int count = commonDataMapper.selectCount(lambdaQueryWrapper);
        Assertions.assertEquals(1, count);
    }

    @Test
    @Order(7)
    void testLogicSelectCountWithoutDistinct() {
        QueryWrapper<CommonLogicData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_int", 25).or().eq("test_str", "test");
        int count = commonLogicMapper.selectCount(queryWrapper);
        Assertions.assertEquals(2, count);
    }

    @Test
    @Order(8)
    void testCountDistinctWithoutDistinct() {
        QueryWrapper<CommonData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_int", 25).or().eq("test_str", "test");
        int count = commonDataMapper.selectCount(queryWrapper);
        Assertions.assertEquals(2, count);
    }

    @Test
    @Order(9)
    void testSelectPageWithoutDistinct() {
        QueryWrapper<CommonData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("test_int", 25).or().eq("test_str", "test");
        IPage<CommonData> page = commonDataMapper.selectPage(new Page<>(1, 10), queryWrapper);
        Assertions.assertEquals(2, page.getTotal());
        Assertions.assertNotNull(page.getRecords().get(0));
        Assertions.assertNotNull(page.getRecords().get(1));
    }

    @Test
    @Order(10)
    void testSelectPageWithDistinct() {
        QueryWrapper<CommonData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("distinct test_int, test_str");
        queryWrapper.eq("test_int", 25).or().eq("test_str", "test");
        IPage<CommonData> page = commonDataMapper.selectPage(new Page<>(1, 10), queryWrapper);
        Assertions.assertEquals(1, page.getTotal());
        Assertions.assertNotNull(page.getRecords().get(0));
    }

}
