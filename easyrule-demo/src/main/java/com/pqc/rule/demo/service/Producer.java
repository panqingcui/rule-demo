package com.pqc.rule.demo.service;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

/**
 * 功能描述,该部分必须以中文句号结尾。
 *
 * @author panqingcui
 * @create 2018-09-14 11:28
 */
public class Producer {
    private static final Logger LOGGER = LogManager.getLogger(Producer.class);

    public void send(Map<String, Object> content) throws ExecutionException, InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.2.176:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 10);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        org.apache.kafka.clients.producer.Producer<String, String> producer = new KafkaProducer<>(props);
        LOGGER.info("------------>>>>>>>{}", content);
        String topic = "test";
        Future future = producer
            .send(new ProducerRecord<String, String>(topic, "test", JSONObject.toJSONString(content)));
        LOGGER.info("------>{}", future.get().toString());
        Thread.sleep(100000);
        producer.close();
    }
}
