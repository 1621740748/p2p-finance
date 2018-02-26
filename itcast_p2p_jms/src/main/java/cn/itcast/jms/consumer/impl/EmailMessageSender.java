package cn.itcast.jms.consumer.impl;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import cn.itcast.jms.consumer.IMessageSender;

//发送邮件的具体操作类
@Component("emailSender") // 类似于@Service
public class EmailMessageSender implements IMessageSender {

	@Override
	public void sendMsg(MapMessage message) {

	

	}

}
