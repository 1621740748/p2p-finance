package cn.itcast.jms.consumer;

import javax.jms.MapMessage;

public interface IMessageSender {

	public void sendMsg(MapMessage message);
}
