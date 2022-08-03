package com.coral.base.common.param;

import com.coral.base.common.NameStyleUtil;
import lombok.Data;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.metadata.ConstraintDescriptor;
import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * @description: TODO
 * @author: huss
 * @time: 2021/1/11 11:44
 */
public class ParamValidationTest {

    public static void main(String[] args) {
        Person person = new Person();

        Validator validator = Validation.byProvider(HibernateValidator.class)//
                .configure()//
                .failFast(false)//
                .buildValidatorFactory()//
                .getValidator();

        Set<ConstraintViolation<Person>> results = validator.validate(person);

        results.forEach(violation -> {
            System.out.println(">>>>>>>" + getI18nKey(violation));

            System.out.println(violation.getPropertyPath() + " " + violation.getMessage());
        });

    }

    private static String getI18nKey(ConstraintViolation constraintViolation) {
        if (constraintViolation == null) {
            return "";
        }
        String className = NameStyleUtil.humpToLine(constraintViolation.getRootBeanClass().getName());

        ConstraintDescriptor<?> constraintDescriptor = constraintViolation.getConstraintDescriptor();
        Annotation annotation = constraintDescriptor.getAnnotation();

        String annotationStr = annotation.annotationType().getSimpleName();

        annotationStr = NameStyleUtil.humpToLine(annotationStr);

        String fileName = NameStyleUtil.humpToLine(constraintViolation.getPropertyPath().toString());
        return (className + "." + fileName + "." + annotationStr).toLowerCase();
    }

    @Valid
    @Data
    static class Person {

        // @NotBlank(message = "名称不能为空")
        @NotBlank
        private String personName;

        // @NotNull(message = "年龄不能为空")
        @NotNull
        @Max(30)
        @Min(18)
        private Integer age;
    }

}
