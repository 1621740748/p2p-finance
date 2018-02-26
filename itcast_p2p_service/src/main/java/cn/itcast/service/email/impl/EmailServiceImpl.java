package cn.itcast.service.email.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import cn.itcast.service.email.IEmailService;
import cn.itcast.utils.IMessage;

@Service
public class EmailServiceImpl implements IEmailService {

	@Autowired
	private JmsTemplate jmsTemplate;

	// email 接收者 title 邮件标题 content 邮件内容
	public void sendEmail(String email, String title, String content) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put(IMessage.MessageType, IMessage.EmailMessage);// 存储类型
		map.put(IMessage.MessageContent, content);
		map.put(IMessage.EmailMessageTo, email);
		
		jmsTemplate.convertAndSend(map);

		// // 创建一个邮件信息
		// MimeMessage mm = mailSender.createMimeMessage();
		//
		// //
		// MimeMessageHelper helper = new MimeMessageHelper(mm);
		// try {
		// helper.setFrom(from);
		// helper.setSubject(title);
		// helper.setText(content, true); //代表内容如果有html代码可以解析
		// helper.setTo(email);
		// } catch (MessagingException e) {
		// e.printStackTrace();
		// }
		//
		//
		// // 发送邮件
		// mailSender.send(mm);
	}
}
