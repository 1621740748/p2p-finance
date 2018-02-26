package cn.itcast.dao.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.domain.message.UserMessage;

public interface IMessageDao extends JpaRepository<UserMessage, Integer>, JpaSpecificationExecutor<UserMessage> {

}
