package com.baomidou.mybatisplus.core;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.builder.StaticSqlSource;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.incrementer.SnowflakeIdGenerator;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

class MybatisDefaultParameterHandlerTest {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Model {

        private Long id;

        private String name;

        private String insertOperator;

        private String updateOperator;

        Model(String name) {
            this.name = name;
        }
    }

    @Test
    void test() {
        MappedStatement mappedStatement;
        Configuration configuration = new MybatisConfiguration();
        StaticSqlSource staticSqlSource = new StaticSqlSource(configuration, " ***********");
        GlobalConfigUtils.getGlobalConfig(configuration).setIdGenerator(new SnowflakeIdGenerator()).setMetaObjectHandler(new MetaObjectHandler() {
            @Override
            public void insertFill(MetaObject metaObject) {
                setFieldValByName("insertOperator", "咩咩", metaObject);
            }

            @Override
            public void updateFill(MetaObject metaObject) {
                setFieldValByName("updateOperator", "逗号", metaObject);
            }
        });
        Model model = new Model("坦克");
        TableInfoHelper.initTableInfo(new MapperBuilderAssistant(configuration, ""), Model.class);
        //查询
        mappedStatement = new MappedStatement.Builder(configuration, "***", staticSqlSource, SqlCommandType.SELECT).build();
        MybatisDefaultParameterHandler.processBatch(mappedStatement, model);
        Assertions.assertNull(model.getId());
        Assertions.assertNull(model.getInsertOperator());
        Assertions.assertNull(model.getUpdateOperator());
        // 普通插入
        mappedStatement = new MappedStatement.Builder(configuration, "***", staticSqlSource, SqlCommandType.INSERT).build();
        MybatisDefaultParameterHandler.processBatch(mappedStatement, model);
        Assertions.assertNotNull(model.getId());
        Assertions.assertNotNull(model.getInsertOperator());
        Assertions.assertNull(model.getUpdateOperator());
        //普通更新
        mappedStatement = new MappedStatement.Builder(configuration, "***", staticSqlSource, SqlCommandType.UPDATE).build();
        MybatisDefaultParameterHandler.processBatch(mappedStatement, model);
        Assertions.assertNotNull(model.getId());
        Assertions.assertNotNull(model.getInsertOperator());
        Assertions.assertNotNull(model.getUpdateOperator());
        //批量插入
        List<Model> list = Arrays.asList(new Model("坦克一号"), new Model("坦克二号"));
        mappedStatement = new MappedStatement.Builder(configuration, "***", staticSqlSource, SqlCommandType.INSERT).build();
        MybatisDefaultParameterHandler.processBatch(mappedStatement, list);
        list.forEach(m -> {
            Assertions.assertNotNull(m.getId());
            Assertions.assertNotNull(m.getInsertOperator());
            Assertions.assertNull(m.getUpdateOperator());
        });
        //批量更新
        mappedStatement = new MappedStatement.Builder(configuration, "***", staticSqlSource, SqlCommandType.UPDATE).build();
        MybatisDefaultParameterHandler.processBatch(mappedStatement, list);
        list.forEach(m -> {
            Assertions.assertNotNull(m.getId());
            Assertions.assertNotNull(m.getInsertOperator());
            Assertions.assertNotNull(m.getUpdateOperator());
        });
    }

}
