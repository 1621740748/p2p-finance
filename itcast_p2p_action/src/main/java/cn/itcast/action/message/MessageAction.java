package cn.itcast.action.message;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;
import cn.itcast.action.filter.GetHttpResponseHeader;
import cn.itcast.cache.BaseCacheService;
import cn.itcast.domain.message.UserMessage;
import cn.itcast.domain.user.UserModel;
import cn.itcast.service.message.IMessageServie;
import cn.itcast.service.user.IUserService;
import cn.itcast.utils.FrontStatusConstants;
import cn.itcast.utils.Response;

@Namespace("/api")
@Controller
@Scope("prototype")
public class MessageAction extends BaseAction {

	@Autowired
	private IUserService userService;
	@Autowired
	private BaseCacheService baseCacheService;
	@Autowired
	private IMessageServie messageServie;

	@Action("list")
	public void list() {
		this.getResponse().setCharacterEncoding("utf-8");
		// 1.得到token
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		try {
			// 判断token不为空
			if (StringUtils.isEmpty(token)) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_TOKEN).toJSON());
				return;
			}
			// 2.从redis中根据token获取信息，可以获取到用户id
			Map<String, Object> hmap = baseCacheService.getHmap(token);
			// 判断hmap不为空
			if (hmap == null || hmap.size() == 0) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NOT_LOGGED_IN).toJSON());
				return;
			}
			// 3.根据用户id，调用service，获取用户对象
			int userId = (int) hmap.get("id");
			UserModel u = userService.findById(userId);
			if (u == null) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NOT_LOGGED_IN).toJSON());
				return;
			}
			// 接收参数
			String Sid = this.getRequest().getParameter("userId");
			int id = Integer.parseInt(Sid);
			String Soffset = this.getRequest().getParameter("offset");
			int offset = Integer.parseInt(Soffset);
			// 判断是否为空
			if (StringUtils.isEmpty(Sid)) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_RESULT).toJSON());
				return;
			}
			if (StringUtils.isEmpty(Soffset)) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_RESULT).toJSON());
				return;
			}
			// 调用serive
			int rows = 3;
			Page<UserMessage> msgList = messageServie.findAllByPage(id, offset, rows);
			if (msgList == null) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_RESULT).toJSON());
				return;
			} else {

				Map<String, Object> map = new HashMap<String, Object>();
				List<UserMessage> list = msgList.getContent();
				int totalNumber = (int) msgList.getTotalElements();
				map.put("list", list);
				map.put("totalNumber", totalNumber);
				this.getResponse().getWriter().write(Response.build().setStatus("1").setData(map).toJSON());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
