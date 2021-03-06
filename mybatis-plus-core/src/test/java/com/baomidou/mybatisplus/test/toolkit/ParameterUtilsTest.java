package com.baomidou.mybatisplus.test.toolkit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.binding.MapperMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ParameterUtils;


class ParameterUtilsTest {

    static class Page<T> implements IPage<T> {
        private int current = 1;
        private int total = 0;
        private int size = 10;
        private List<T> records = Collections.emptyList();
        private List<OrderItem> orders = new ArrayList<>();

        @Override
        public List<OrderItem> orders() {
            return orders;
        }

        @Override
        public List<T> getRecords() {
            return records;
        }

        @Override
        public Page<T> setRecords(List<T> records) {
            this.records = records;
            return this;
        }

        @Override
        public int getTotal() {
            return this.total;
        }

        @Override
        public Page<T> setTotal(int total) {
            this.total = total;
            return this;
        }

        @Override
        public int getSize() {
            return this.size;
        }

        @Override
        public Page<T> setSize(int size) {
            this.size = size;
            return this;
        }

        @Override
        public int getCurrent() {
            return this.current;
        }

        @Override
        public Page<T> setCurrent(int current) {
            this.current = current;
            return this;
        }
    }

    @Test
    void testFindPage() {
        Assertions.assertFalse(ParameterUtils.findPage(null).isPresent());
        MapperMethod.ParamMap<Object> param = new MapperMethod.ParamMap<>();
        param.put(Constants.ENTITY, null);
        Assertions.assertFalse(ParameterUtils.findPage(param).isPresent());
        param.put("page", new Page());
        Assertions.assertTrue(ParameterUtils.findPage(param).isPresent());
        Assertions.assertTrue(ParameterUtils.findPage(new Page<>()).isPresent());
        Assertions.assertFalse(ParameterUtils.findPage(new HashMap<>()).isPresent());
    }
}
