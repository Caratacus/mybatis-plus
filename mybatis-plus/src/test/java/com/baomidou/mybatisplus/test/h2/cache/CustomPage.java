package com.baomidou.mybatisplus.test.h2.cache;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author nieqiurong 2020/4/27.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CustomPage<T> extends Page<T> {

    private int offset;

    public CustomPage() {
    }

    public CustomPage(int current, int size) {
        super(current, size);
    }

    public CustomPage(int current, int size, int total) {
        super(current, size, total);
    }

    public CustomPage(int current, int size, boolean isSearchCount) {
        super(current, size, isSearchCount);
    }

    public CustomPage(int current, int size, int total, boolean isSearchCount) {
        super(current, size, total, isSearchCount);
    }

    @Override
    public int offset() {
        return offset == 0 ? super.offset() : offset;
    }

}
