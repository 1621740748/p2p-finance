package cn.itcast.service.message.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.message.IMessageDao;
import cn.itcast.domain.message.UserMessage;
import cn.itcast.service.message.IMessageServie;

@Service
@Transactional
public class MessageServieImpl implements IMessageServie {

	@Autowired
	private IMessageDao messageDao;

	@Override
	public Page<UserMessage> findAllByPage(final int id, int currpage, int rows) {
		Pageable pageable = new PageRequest(currpage - 1, rows);
		Page<UserMessage> page = messageDao.findAll(new Specification<UserMessage>() {

			@Override
			public Predicate toPredicate(Root<UserMessage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Path<Object> s_receive_user_id = root.get("s_receive_user_id");
				List<Predicate> list = new ArrayList<>();
				Predicate p1 = cb.equal(s_receive_user_id, id);
				list.add(p1);
				query.where(list.toArray(new Predicate[list.size()]));
				return null;
			}
		}, pageable);
		return page;
	}

}
