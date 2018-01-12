package com.wong.queue;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * @Description: 消息监听
 * @Author: gang.wang
 * @Date: Created in 下午3:18 2018/1/12
 */
public class Listener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        try{
            System.out.println("收到的消息：" + ((TextMessage)message).getText());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
