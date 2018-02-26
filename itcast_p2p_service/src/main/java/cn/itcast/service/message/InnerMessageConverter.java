package cn.itcast.service.message;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;

import cn.itcast.utils.IMessage;


public class InnerMessageConverter implements MessageConverter{
	@Override
	public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
		MapMessage message = session.createMapMessage();

		@SuppressWarnings("all")
		Map<String, Object> map = (Map) object;

		message.setObject("type", map.get(IMessage.MessageType)); //sms  email
		message.setObject("content", map.get(IMessage.MessageContent));//内容
		message.setObject("title", map.get(IMessage.EmailMessageTitle));
		message.setObject("to",map.get(IMessage.EmailMessageTo));
		message.setObject("number", map.get(IMessage.SMSNumbers));

		return message;
	}

	@Override
	public Object fromMessage(Message message) throws JMSException, MessageConversionException {
		return message;
	}
}
