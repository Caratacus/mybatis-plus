package com.baomidou.mybatisplus.test.h2.tenant.mapper;

import org.apache.ibatis.annotations.CacheNamespace;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.test.h2.tenant.model.Student;

/**
 * @author nieqiuqiu 2019/12/8
 */
@CacheNamespace
public interface StudentMapper extends BaseMapper<Student> {

}
