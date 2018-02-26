package cn.itcast.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.jms.consumer.IMessageSender;
import cn.itcast.jms.consumer.MessageFactory;

public class MessageReceiver implements MessageListener {
	@Autowired
	private MessageFactory factory;

	@Override
	public void onMessage(Message msg) {
		// 1.将Message强制转换成MapMessage
		if (msg instanceof MapMessage) {
			MapMessage message = (MapMessage) msg;

			try {
				// 2.调用本类的方法来处理具体要执行的消息操作
				processMsg(message);
				// 3.设置手动响应
				message.acknowledge();
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

	// 处理具体的消息操作
	private void processMsg(MapMessage message) {
		try {
			// 从消息中获取type值，工厂根据type值来创建具体的消息处理对象
			String type = message.getString("type");
			IMessageSender messageSender = factory.createMessageSender(type);
			// 消息处理对象来完成发送消息操作
			messageSender.sendMsg(message);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
