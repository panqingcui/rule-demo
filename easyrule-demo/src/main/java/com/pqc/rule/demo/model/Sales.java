package com.pqc.rule.demo.model;

import lombok.Data;

/**
 * <p>功能描述,该部分必须以中文句号结尾 .</p>
 * <p>
 * 创建日期 2019/12/13
 *
 * @author panqingcui
 * @since 1.0.0
 */
@Data
public class Sales {
    private double value;
    private double salary;

    @Override public String toString() {
        return "Sales{" +
               "value=" + value +
               ", salary=" + salary +
               '}';
    }
}
