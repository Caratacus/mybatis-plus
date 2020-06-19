package com.baomidou.mybatisplus.extension.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nieqiurong 2020/2/28.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserBean implements Serializable {

    private Long id;

    private String name;

}
