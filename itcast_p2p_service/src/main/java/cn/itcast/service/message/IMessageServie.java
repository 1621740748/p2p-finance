package cn.itcast.service.message;

import org.springframework.data.domain.Page;

import cn.itcast.domain.message.UserMessage;

public interface IMessageServie {

	Page<UserMessage> findAllByPage(int id, int currpage, int rows);

}
