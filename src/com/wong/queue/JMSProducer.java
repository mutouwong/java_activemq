package com.wong.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * @Description: 消息提供着
 * @Author: gang.wang
 * @Date: Created in 下午2:42 2018/1/12
 */
public class JMSProducer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;


    public static void main(String[] args){

        ConnectionFactory connectionFactory;//连接工厂
        Connection connection = null; //连接
        Session session; //会话 - 接收/发送消息的线程
        Destination destination; //消息目的地
        MessageProducer messageProducer; //消息提供者

        try{
            //实例化连接工厂
            connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME,JMSProducer.PASSWORD,JMSProducer.BROKERURL);
            //通过连接工厂新建连接
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue("java-queue"); //创建消息队列
            messageProducer = session.createProducer(destination);//创建消息生产者
            //发送消息
            sendMessage(session,messageProducer);

            session.commit();

        }catch (Exception e){
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

    public static void  sendMessage(Session session,MessageProducer messageProducer) throws Exception {
        for(int i =0; i < 10; i++) {
            TextMessage textMessage = session.createTextMessage("ActiveMQ Send Message" + i);
            System.out.println("发送消息：ActiveMQ Send Message" + i);
            messageProducer.send(textMessage);
        }

    }

}
