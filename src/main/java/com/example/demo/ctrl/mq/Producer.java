package com.example.demo.ctrl.mq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
/**生产者*/
public class Producer {
    public static void main(String[] args) throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer("producer1");
        producer.setNamesrvAddr("localhost:9876");
        producer.setInstanceName("rmq-instance");
        producer.start();
        try {
            Message message = new Message("demo-topic", "demo-tag", "这是一条测试消息".getBytes());
            SendResult sendResult =producer.send(message);

//            while (true) {
//                String text = new Scanner(System.in).next();
//                Message msg = new Message("demo-topic",// topic
//                        "demo-tag",// tag
//                        text.getBytes() // body
//                );
//                SendResult sendResult = producer.send(msg);
//            }

            System.out.println("发送的消息ID:" + sendResult.getMsgId() +"--- 发送消息的状态：" + sendResult.getSendStatus());

        } catch (Exception e) {
            e.printStackTrace();
        }
        producer.shutdown();
    }
}
