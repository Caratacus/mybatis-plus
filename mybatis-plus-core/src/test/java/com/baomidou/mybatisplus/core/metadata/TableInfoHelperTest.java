package com.baomidou.mybatisplus.core.metadata;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

class TableInfoHelperTest {

    @Data
    private static class ModelOne {

        @TableId
        private Long id;

        private String name;
    }

    @Data
    private static class ModelTwo {

        private Long id;

        private String name;
    }

    @Test
    void testIsExistTableId() {
        Assertions.assertTrue(TableInfoHelper.isExistTableId(Arrays.asList(ModelOne.class.getDeclaredFields())));
        Assertions.assertFalse(TableInfoHelper.isExistTableId(Arrays.asList(ModelTwo.class.getDeclaredFields())));
    }

}
