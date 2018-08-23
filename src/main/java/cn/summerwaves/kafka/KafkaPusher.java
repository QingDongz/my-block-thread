package cn.summerwaves.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaPusher implements PythonTaskPusher{
    private static final Logger logger = LoggerFactory.getLogger(KafkaPusher.class);


    // 创建一个Producer
    private final Producer<String, String> kafkaProducer;

    // 指定Topic名称
    private String topic;

    public void setTopic(String topic) {
        this.topic = topic;
    }


    public KafkaPusher() {
        kafkaProducer = createKafkaProducer() ;
    }

    private Producer<String, String> createKafkaProducer() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "193.112.139.251:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 0);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return new KafkaProducer<>(props);
    }

    // 生产者
    private boolean producer(String produce_json_ms) {
        logger.info("发送KAFKA消息开始");

        final boolean[] success = {false};

        // 发送数据
        kafkaProducer.send(
                new ProducerRecord<>(topic, String.valueOf(new Date().getTime()), produce_json_ms), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e != null) {
                            success[0] = false;
                        } else {
                            success[0] = true;
                        }
                    }
                });
        // 发送单条的时候就不会掉数据
        kafkaProducer.flush();
        // 测试输出
//        System.out.println(produce_json_ms);
        logger.info("发送KAFKA消息成功：{}", success[0]);

        return success[0];
    }

    @Override
    public boolean pushTask(Integer command, String bankName, String account, String password,
                            Integer bankId, Integer sessionTimeout, Integer bankCardId) {
        logger.info("推送信息   command:{},bankName:{},account:{},password:{},bankId:{},sessionTimeout:{}," +
                "bankCardId:{}", command, bankName, account, password, bankId, sessionTimeout, bankCardId);
        Map<String,Object> map = new HashMap<>();
        map.put("command", command);
        map.put("bank_name", bankName);
        map.put("account", account);
        map.put("password", password);
        map.put("bank_id", bankId);
        map.put("session_timeout", sessionTimeout);
        map.put("bank_card_id", bankCardId);
        String json_msg = JSON.toJSONString(map, false);

        return producer(json_msg);
    }

    public static void main(String[] args) {
        Map<String,Object> map = new HashMap<>();
        map.put("key001", "value001");
        String json_msg = JSON.toJSONString(map, false);
        KafkaPusher pusher = new KafkaPusher();
        pusher.setTopic("192.168.100.245");
        pusher.producer(json_msg);
    }
}
