package com.pqc.rule.demo.service;

/**
 * 功能描述,该部分必须以中文句号结尾。
 *
 * @author panqingcui
 * @create 2018-09-14 09:25
 */
public class Consumer {
    // private static final Logger LOGGER = LogManager.getLogger();
    //
    // public void send(String topic, Map<String, String> content) {
    // Properties props = new Properties();
    // props.put("bootstrap.servers", "192.168.2.176:9092");
    // props.put("acks", "all");
    // props.put("retries", 0);
    // props.put("batch.size", 16384);
    // props.put("linger.ms", 10);
    // props.put("buffer.memory", 33554432);
    // props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    // props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
    // Producer<String, String> producer = new KafkaProducer<>(props);
    // for (int i = 0; i < 1; i++) {
    // JSONObject jsonObject = new JSONObject();
    // jsonObject.put("new_user_id", UUID.randomUUID());
    // jsonObject.put("user_id", UUID.randomUUID());
    // Future future = producer.send(new ProducerRecord<String, String>(topic, "test", jsonObject.toJSONString()));
    // LOGGER.info(future.get().toString());
    // }
    // producer.close();
    // }
}
