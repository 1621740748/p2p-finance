package cn.itcast.action.verification;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
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
import cn.itcast.service.email.IEmailService;
import cn.itcast.service.msm.IMSMService;
import cn.itcast.service.user.IUserService;
import cn.itcast.utils.EmailUtils;
import cn.itcast.utils.FrontStatusConstants;
import cn.itcast.utils.MSMUtils;
import cn.itcast.utils.Response;
import cn.itcast.utils.SecretUtil;

@Namespace("/verification")
@Controller
@Scope("prototype")
public class VerificationAction extends BaseAction {

	@Autowired
	private BaseCacheService baseCacheService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IEmailService emailService;

	// 验证短信是否正确
	@Action("validateSMS")
	public void validateSMS() {
		// phone : $scope.bankUserObj.authPhone,
		// code : $scope.phonecaptcha
		String phone = this.getRequest().getParameter("phone");
		String code = this.getRequest().getParameter("code");
		String _code = baseCacheService.get(phone);

		try {
			if (code.equals(_code)) {
				this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
			} else {
				this.getResponse().getWriter().write(Response.build().setStatus("0").toJSON());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 绑定邮箱操作
	@Action("emailactivation")
	public void emailactivation() {
		this.getResponse().setContentType("text/html;charset=utf-8");

		// 1.得到us
		String us = this.getRequest().getParameter("us");
		// 2.解密
		try {
			String userId = SecretUtil.decode(us);

			// 3.校验

			// 4.修改邮箱状态
			userService.updateEmailStatus(Integer.parseInt(userId));

			this.getResponse().getWriter().write("邮箱认证成功");

			// 发送邮件

		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.getResponse().getWriter().write("邮箱认证失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

	}

	// 主要是发送验证邮件
	@Action("auth")
	public void auth() {
		// 1. 得到请求参数
		String userId = this.getRequest().getParameter("userId");
		String username = this.getRequest().getParameter("username");
		String email = this.getRequest().getParameter("email");
		// 2. 调用service完成发送邮件操作
		String title = "P2P邮箱认证激活";
		try {
			String enc = SecretUtil.encrypt(userId);// 加密后的id
			String content = EmailUtils.getMailCapacity(email, enc, username);
			emailService.sendEmail(email, title, content); // 发送邮件

			// UserModel user = userService.findById(Integer.parseInt(userId));
			// if (user.getEmail() == null&&user.getEmailStatus()==0) {
			// 绑定邮箱--校验
			userService.addEmail(email, Integer.parseInt(userId));
			// }
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 实名认证
	@Action("verifiRealName")
	public void verifiRealName() {
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		try {
			if (StringUtils.isEmpty(token)) {
				// token过期
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_TOKEN).toJSON());
				return;
			}
			Map<String, Object> hmap = baseCacheService.getHmap(token);
			if (hmap == null || hmap.size() == 0) {
				// 用户未登录
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NOT_LOGGED_IN).toJSON());
				return;

			}
			// 1.得到实名与身份证号
			String realName = this.getRequest().getParameter("realName");
			String identity = this.getRequest().getParameter("identity");
			// 2.对比
			// ##################################
			// 3.修改实名状态 身份证号 实名
			userService.updateRealName(realName, identity, (int) hmap.get("id"));
			this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 绑定认证
	@Action("addPhone")
	public void addPhone() {
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		try {
			if (StringUtils.isEmpty(token)) {
				// token过期
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_TOKEN).toJSON());
				return;
			}
			Map<String, Object> hmap = baseCacheService.getHmap(token);
			if (hmap == null || hmap.size() == 0) {
				// 用户未登录
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NOT_LOGGED_IN).toJSON());
				return;

			}
			// 1.获取请求参数
			String phone = this.getRequest().getParameter("phone");
			String phoneCode = this.getRequest().getParameter("phoneCode");
			// 2.判断验证码是否正确
			String _phoneCode = baseCacheService.get(phone);

			if (!phoneCode.equals(_phoneCode)) {
				// 说明不正确
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.INPUT_ERROR_OF_VALIDATE_CARD).toJSON());
				return;
			}
			// 3.得到用户，判断用户是否已经绑定手机
			UserModel user = userService.findById((int) (hmap.get("id")));
			if (user.getPhoneStatus() == 1) {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.MOBILE_ALREADY_REGISTER).toJSON());
				return;
			}

			// 4.绑定手机
			userService.updatePhoneStatus(phone, (int) (hmap.get("id")));
			this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS).toJSON());

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Autowired
	private IMSMService msmService;

	// 发送短信验证码操作
	@Action("sendMessage")
	public void sendMessage() {
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());

		if (StringUtils.isEmpty(token)) {
			// token过期
		}
		Map<String, Object> hmap = baseCacheService.getHmap(token);
		if (hmap == null || hmap.size() == 0) {
			// 用户未登录
		}
		// 1.得到电话 号
		String phone = this.getRequest().getParameter("phone");

		// 2.调用发送短信的工具，发送短信
		String randomNumeric = RandomStringUtils.randomNumeric(6); // 随机得到6位数字
		String content = "P2P短信认证:" + randomNumeric;
		// try {
		// MSMUtils.SendSms(phone, content);
		// } catch (UnsupportedEncodingException e) {
		// e.printStackTrace();
		// }
		// System.out.println("向" + phone + "发送验证信息:" + content);
		System.out.println(content);

		// msmService.sendMsm(phone, content);
		// 3.存储到redis中
		baseCacheService.set(phone, randomNumeric);
		baseCacheService.expire(phone, 3 * 60);

		try {
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
