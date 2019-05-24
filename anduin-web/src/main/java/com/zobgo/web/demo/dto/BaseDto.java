package com.zobgo.web.demo.dto;

import javax.validation.constraints.NotNull;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author zhangzongbo
 * @date 18-11-28 上午10:20
 */
public class BaseDto {
    public void nullFieldValidate() throws IllegalAccessException, InvocationTargetException {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldValue = runGetter(field, this);

            boolean isAnnotationNotNull = field.isAnnotationPresent(NotNull.class);
            if (isAnnotationNotNull && fieldValue == null) {
                System.out.println(fieldName + " can't be null");
            }
        }
    }

    // 由于所有子类的属性都是private的，所以必须要找到属性的getter方法
    //  以下代码借鉴[stackoverflow的文章](https://stackoverflow.com/questions/13400075/reflection-generic-get-field-value)
    public Object runGetter(Field field, Object instance) {
        // MZ: Find the correct method
        for (Method method : instance.getClass().getDeclaredMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        return method.invoke(instance);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        System.out.println("Could not determine method: " + method.getName());
                    }
                }
            }
        }
        return null;
    }

}
