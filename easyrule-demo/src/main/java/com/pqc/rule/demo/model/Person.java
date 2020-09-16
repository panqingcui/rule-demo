package com.pqc.rule.demo.model;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.pqc.rule.demo.service.Producer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 功能描述,该部分必须以中文句号结尾。
 *
 * @author panqingcui
 * @create 2018-08-27 13:34
 */
@Getter
@Setter
@ToString
public class Person {
    private String name;
    private Integer age;
    private boolean isAdult;
    private String userName;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public void add(String s, Map<String, Object> map) {
        System.out.println("---------------------------" + s);
        Address address = new Address();
        address.setPostCode("123");
        System.out.println("---------------------------" + address);
        Producer producer = new Producer();
        try {
            producer.send(map);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
