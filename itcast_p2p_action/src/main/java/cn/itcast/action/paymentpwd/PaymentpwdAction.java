package cn.itcast.action.paymentpwd;

import java.io.IOException;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;
import cn.itcast.action.filter.GetHttpResponseHeader;
import cn.itcast.cache.BaseCacheService;
import cn.itcast.domain.user.UserModel;
import cn.itcast.service.user.IUserService;
import cn.itcast.utils.FrontStatusConstants;
import cn.itcast.utils.Response;

@Namespace("/paymentpwd")
@Controller
@Scope("prototype")
public class PaymentpwdAction extends BaseAction {

	@Autowired // 默认by type
	// @Resource(name="redisCache") // by name
	private BaseCacheService baseCacheService;

	@Autowired
	private IUserService userService;

	@Action("getPaymentPwd")
	public void getPaymentPwd() {
		String paypwd = getParameter("paypwd");
		// 1.得到token
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		try {
			// 判断token不为空
			if (StringUtils.isEmpty(token)) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_TOKEN).toJSON());
				return;
			}
			// 2.根据token从redis中获取用户信息，获取用户id
			Map<String, Object> hmap = baseCacheService.getHmap(token);
			int userId = (int) hmap.get("id");
			// 3.调用service从用户帐户表中获取用户帐户信息，根据用户id查询
			UserModel user = userService.findById(userId);
			user.setPayPassword(paypwd);
			user.setPayPwdStatus(1);
			userService.save(user);
			// 4.响应
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Action("updatePaymentPwd")
	public void updatePaymentPwd() {
		String payPassword = getParameter("payPassword");
		String newpaypwd = getParameter("newpaypwd");
		// 1.得到token
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		try {
			// 判断token不为空
			if (StringUtils.isEmpty(token)) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_TOKEN).toJSON());
				return;
			}
			// 2.根据token从redis中获取用户信息，获取用户id
			Map<String, Object> hmap = baseCacheService.getHmap(token);
			int userId = (int) hmap.get("id");
			// 3.调用service从用户帐户表中获取用户帐户信息，根据用户id查询
			UserModel user = userService.findById(userId);
			if (user != null && payPassword != null && payPassword.equals(user.getPayPassword())) {
				user.setPayPassword(newpaypwd);
				userService.save(user);
			} else {
				this.getResponse().getWriter().write(Response.build().setStatus("-999").toJSON());
				return;
			}
			// 4.响应
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
