package cn.itcast.jms.consumer;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import cn.itcast.jms.consumer.impl.EmailMessageSender;
import cn.itcast.jms.consumer.impl.MSMMessageSender;

@Component
public class MessageFactory {

	// @Autowired
	// @Qualifier("msmSender")
	@Resource(name = "msmSender")
	private IMessageSender msmSender;

	@Autowired
	@Qualifier("emailSender")
	private IMessageSender emileSender;

	// 就是根据消息类型来创建一个具体的消息处理类对象
	public IMessageSender createMessageSender(String type) {
		if (type.equals("email")) {
			return emileSender;
		}
		if (type.equals("sms")) {
			return msmSender;
		}
		return null;

	}
}
