package com.zobgo.web.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author zhangzongbo
 * @date 18-12-6 下午2:26
 */


@Data
@NoArgsConstructor
@AllArgsConstructor

public class PersonDTO implements Serializable {


//    private static final long serialVersionUID = -4921700376210707030L;

    private String name;

    private Integer age;

    private Long id;

}
