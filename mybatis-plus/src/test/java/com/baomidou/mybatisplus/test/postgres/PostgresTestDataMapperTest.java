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
package com.baomidou.mybatisplus.test.postgres;

import javax.annotation.Resource;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.test.base.entity.CommonData;
import com.baomidou.mybatisplus.test.base.entity.CommonLogicData;
import com.baomidou.mybatisplus.test.base.enums.TestEnum;
import com.baomidou.mybatisplus.test.base.mapper.commons.CommonDataMapper;
import com.baomidou.mybatisplus.test.base.mapper.commons.CommonLogicDataMapper;
import com.baomidou.mybatisplus.test.postgres.entity.PgData;
import com.baomidou.mybatisplus.test.postgres.mapper.PgDataMapper;

/**
 * Mybatis Plus mysql Junit Test
 *
 * @author hubin
 * @since 2018-06-05
 */
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:postgres/spring-test-postgres.xml"})
class PostgresTestDataMapperTest {

    @Resource
    private CommonDataMapper commonMapper;
    @Resource
    private CommonLogicDataMapper commonLogicMapper;
    @Resource
    private PgDataMapper pgMapper;

    @Test
    void a_insertForeach() {
        for (int i = 1; i < 20; i++) {
            Long id = (long) i;
            commonMapper.insert(new CommonData().setTestInt(i).setTestStr(String.format("第%s条数据", i)).setId(id)
                .setTestEnum(TestEnum.ONE));
            commonLogicMapper.insert(new CommonLogicData().setTestInt(i).setTestStr(String.format("第%s条数据", i)).setId(id));
            pgMapper.insert(new PgData().setGroup(i).setId(id).setPgInt(i).setPgInt2(i));
        }
    }

    @Test
    void b1_deleteById() {
        Assertions.assertEquals(1, commonMapper.deleteById(1L));
        Assertions.assertEquals(1, commonLogicMapper.deleteById(1L));
        Assertions.assertEquals(1, pgMapper.deleteById(1L));
    }


    @Test
    void b3_delete() {
        Assertions.assertEquals(1, commonMapper.delete(new QueryWrapper<CommonData>().lambda()
            .eq(CommonData::getId, 2L)
            .eq(CommonData::getTestInt, 2)));
        Assertions.assertEquals(1, commonLogicMapper.delete(new QueryWrapper<CommonLogicData>().lambda()
            .eq(CommonLogicData::getId, 2L)
            .eq(CommonLogicData::getTestInt, 2)));
        Assertions.assertEquals(1, pgMapper.delete(new QueryWrapper<PgData>().lambda()
            .eq(PgData::getId, 2L)
            .eq(PgData::getGroup, 2).eq(PgData::getPgInt, 2)));
    }


    @Test
    void c1_updateById() {
        Assertions.assertEquals(1, commonMapper.updateById(new CommonData().setId(5L).setTestInt(555)));
        Assertions.assertEquals(1, commonLogicMapper.updateById(new CommonLogicData().setId(5L).setTestInt(555)));
        Assertions.assertEquals(1, pgMapper.updateById(new PgData().setId(5L).setGroup(555).setPgInt(555)));
    }

    @Test
    void c2_optimisticUpdateById() {
        Assertions.assertEquals(1, commonMapper.updateById(new CommonData().setId(5L).setTestInt(556)
            .setVersion(0)));
    }

    @Test
    void c3_update() {
        Assertions.assertEquals(1, commonMapper.update(
            new CommonData().setTestInt(666),
            new UpdateWrapper<CommonData>().lambda().eq(CommonData::getId, 6L)
                .eq(CommonData::getTestInt, 6)));
        Assertions.assertEquals(1, commonLogicMapper.update(
            new CommonLogicData().setTestInt(666),
            new UpdateWrapper<CommonLogicData>().lambda().eq(CommonLogicData::getId, 6L)
                .eq(CommonLogicData::getTestInt, 6)));
        Assertions.assertEquals(1, pgMapper.update(
            new PgData().setGroup(666).setPgInt(555),
            new UpdateWrapper<PgData>().lambda().eq(PgData::getId, 6L)
                .eq(PgData::getGroup, 6).eq(PgData::getPgInt, 6)));
    }

    @Test
    void d1_getAllNoTenant() {
        commonMapper.getAllNoTenant();
    }

    @Test
    void d2_selectById() {
        long id = 6L;
        Assertions.assertNotNull(commonMapper.selectById(id).getTestEnum());
        Assertions.assertNotNull(commonLogicMapper.selectById(id));
        Assertions.assertNotNull(pgMapper.selectById(id));
    }

    @Test
    void d6_selectList() {
        Assertions.assertTrue(CollectionUtils.isNotEmpty(commonMapper.selectList(new QueryWrapper<CommonData>()
            .lambda().eq(CommonData::getTestInt, 10))));
        Assertions.assertTrue(CollectionUtils.isNotEmpty(commonLogicMapper.selectList(new QueryWrapper<CommonLogicData>()
            .lambda().eq(CommonLogicData::getId, 10L).eq(CommonLogicData::getTestInt, 10))));
        Assertions.assertTrue(CollectionUtils.isNotEmpty(pgMapper.selectList(new QueryWrapper<PgData>()
            .lambda().eq(PgData::getId, 10L).eq(PgData::getGroup, 10).eq(PgData::getPgInt, 10))));
    }


    @Test
    void d8_testApply() {
        Assertions.assertTrue(CollectionUtils.isNotEmpty(commonMapper.selectList(new QueryWrapper<CommonData>()
            .apply("test_int = 12"))));
        Assertions.assertTrue(CollectionUtils.isNotEmpty(commonLogicMapper.selectList(new QueryWrapper<CommonLogicData>()
            .apply("test_int = 12"))));
        Assertions.assertTrue(CollectionUtils.isNotEmpty(pgMapper.selectList(new QueryWrapper<PgData>()
            .apply("\"group\" = 12").apply("\"pgInt\" = 12"))));
    }
}
