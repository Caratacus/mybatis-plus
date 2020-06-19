package com.baomidou.mybatisplus.test.h2.issues.genericid.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
class SuperEntity<ID extends Serializable> {

    @TableId(type = IdType.ASSIGN_ID)
    private ID id;

}
