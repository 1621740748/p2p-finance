package cn.itcast.service.msm.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import cn.itcast.service.msm.IMSMService;
import cn.itcast.utils.IMessage;

@Service
public class MSMServiceImpl implements IMSMService {

	@Autowired
	private JmsTemplate jmsTemplate;

	// 完成向activemq发送要发送的短信的结构信息
	@Override
	public void sendMsm(String phone, String content) {
		Map<String, Object> map = new HashMap<String, Object>();

		map.put(IMessage.MessageType, IMessage.SMSMessage); // 存储消息类型
		map.put(IMessage.MessageContent, content);// 存储消息内容
		map.put(IMessage.SMSNumbers, phone);// 存储电话号码
		
		jmsTemplate.convertAndSend(map);
	}

}
