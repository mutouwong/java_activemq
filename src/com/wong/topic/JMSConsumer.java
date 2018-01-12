package com.wong.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: 消息订阅者一
 * @Author: gang.wang
 * @Date: Created in 下午3:12 2018/1/12
 */
public class JMSConsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args){

        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        MessageConsumer messageConsumer;

        //实例化连接工厂
        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.USERNAME, JMSConsumer.PASSWORD, JMSConsumer.BROKERURL);

        try{
            //通过工厂获取连接
            connection = connectionFactory.createConnection();
            connection.start();//启动连接
            session = connection.createSession(Boolean.FALSE,Session.AUTO_ACKNOWLEDGE);
            destination = session.createTopic("java-topic");//创建连接的消息队列
            messageConsumer = session.createConsumer(destination);//创建消息消费者

            /*messageConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try{
                        System.out.println("收到的消息：" + ((TextMessage)message).getText());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });*/
            messageConsumer.setMessageListener(new Listener());//注册消息监听

            Thread.sleep(1000*50);

            /*while(true){
                TextMessage message = (TextMessage) messageConsumer.receive(100000);
                if(message != null) {
                    System.out.println("收到的消息：" + message.getText());
                }else{
                    break;
                }
            }*/
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection != null) {
                try{
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }



    }

}
