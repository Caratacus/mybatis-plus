package com.baomidou.mybatisplus.test.h2.tenant.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nieqiuqiu 2019/12/8
 */
@Data
@TableName(value = "student")
@NoArgsConstructor
public class Student implements Serializable {

    private Long id;

    private String tenantId;

    private String name;

    public Student(String name) {
        this.name = name;
    }
}
