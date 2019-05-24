package com.zobgo.web.demo.dto;

import lombok.*;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;


/**
 *
 * @author zhangzongbo
 * @date 18-11-21 下午8:23
 */

@Data
public class DemoReqDto{

    @NotNull(message = "name不能为空")
    private String name;

    @NotNull(message = "id 不能为空")
    private Long id;

    @NotNull(message = "年龄不能为空")
    @Min(value = 3,message = "age 不能小于3")
    @Max(value = 10,message = "age 不能大于10")
    private Integer age;

    @NotNull(message = "邮箱不能为空")
    @Email(message = "邮箱格式不正确")
    private String mail;
}
