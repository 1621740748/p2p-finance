package cn.itcast.jms.consumer.impl;

import javax.jms.MapMessage;

import org.springframework.stereotype.Component;

import cn.itcast.jms.consumer.IMessageSender;

//发送短信的具体操作类
@Component("msmSender")
public class MSMMessageSender implements IMessageSender {

	@Override
	public void sendMsg(MapMessage message) {

		
	}

}
