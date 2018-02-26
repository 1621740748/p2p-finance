package cn.itcast.action.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.action.common.BaseAction;
import cn.itcast.action.filter.GetHttpResponseHeader;
import cn.itcast.cache.BaseCacheService;
import cn.itcast.domain.user.UserModel;
import cn.itcast.domain.userAccount.UserAccountModel;
import cn.itcast.service.user.IUserService;
import cn.itcast.service.userAccount.IUserAccountService;
import cn.itcast.utils.CommomUtil;
import cn.itcast.utils.ConfigurableConstants;
import cn.itcast.utils.FrontStatusConstants;
import cn.itcast.utils.ImageUtil;
import cn.itcast.utils.MD5Util;
import cn.itcast.utils.Response;
import cn.itcast.utils.TokenUtil;

@Namespace("/user")
@Controller
@Scope("prototype")
public class UserAction extends BaseAction implements ModelDriven<UserModel> {
	private UserModel user = new UserModel();

	@Override
	public UserModel getModel() {

		return user;
	}

	@Autowired // 默认by type
	// @Resource(name="redisCache") // by name
	private BaseCacheService baseCacheService;

	@Autowired
	private IUserService userService;

	@Autowired
	private IUserAccountService userAccountService;

	public String generateUserToken(String userName) {

		try {
			// 生成令牌
			String token = TokenUtil.generateUserToken(userName);

			// 根据用户名获取用户
			UserModel user = userService.findByUsername(userName);
			// 将用户信息存储到map中。
			Map<String, Object> tokenMap = new HashMap<String, Object>();
			tokenMap.put("id", user.getId());
			tokenMap.put("userName", user.getUsername());
			tokenMap.put("phone", user.getPhone());
			tokenMap.put("userType", user.getUserType());
			tokenMap.put("payPwdStatus", user.getPayPwdStatus());
			tokenMap.put("emailStatus", user.getEmailStatus());
			tokenMap.put("realName", user.getRealName());
			tokenMap.put("identity", user.getIdentity());
			tokenMap.put("realNameStatus", user.getRealNameStatus());
			tokenMap.put("payPhoneStatus", user.getPhoneStatus());

			baseCacheService.del(token);
			baseCacheService.setHmap(token, tokenMap); // 将信息存储到redis中

			// 获取配置文件中用户的生命周期，如果没有，默认是30分钟
			String tokenValid = ConfigurableConstants.getProperty("token.validity", "30");
			tokenValid = tokenValid.trim();
			baseCacheService.expire(token, Long.valueOf(tokenValid) * 60);

			return token;
		} catch (Exception e) {
			e.printStackTrace();
			return Response.build().setStatus("-9999").toJSON();
		}
	}

	// 获取用户帐户中信息，在帐户中心显示
	@Action("accountHomepage")
	public void accountHomepage() {
		// 1.得到token
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		// 2.根据token从redis中获取用户信息，获取用户id
		Map<String, Object> hmap = baseCacheService.getHmap(token);
		int userId = (int) hmap.get("id");
		// 3.调用service从用户帐户表中获取用户帐户信息，根据用户id查询
		UserAccountModel account = userAccountService.findByUserId(userId);
		// System.out.println(account.getTotal());
		// 4.将数据封装，响应到浏览器
		List<JSONObject> list = new ArrayList<JSONObject>();

		JSONObject jsonObject = new JSONObject();

		jsonObject.put("u_total", account.getTotal());// 帐户总额
		jsonObject.put("u_balance", account.getBalance()); // 余额
		jsonObject.put("u_interest_a", account.getInterestA()); // 收益
		list.add(jsonObject);

		try {
			this.getResponse().getWriter().write(Response.build().setStatus("1").setData(list).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取用户安全级别
	@Action("userSecureDetailed")
	public void userSecureDetailed() {
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
			// 4.封装响应到浏览器的数据，响应到浏览器。
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("phoneStatus", u.getPhoneStatus());
			data.put("realNameStatus", u.getRealNameStatus());
			data.put("payPwdStatus", u.getPayPwdStatus());
			data.put("emailStatus", u.getEmailStatus());
			data.put("passwordstatus", u.getPassword());
			data.put("username", u.getUsername());
			data.put("phone", u.getPhone());

			list.add(data);

			this.getResponse().getWriter().write(Response.build().setStatus("1").setData(list).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取用户安全级别
	@Action("userSecure")
	public void userSecure() {
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
			// 4.封装响应到浏览器的数据，响应到浏览器。
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("phoneStatus", u.getPhoneStatus());
			data.put("realNameStatus", u.getRealNameStatus());
			data.put("payPwdStatus", u.getPayPwdStatus());
			data.put("emailStatus", u.getEmailStatus());
			list.add(data);

			this.getResponse().getWriter().write(Response.build().setStatus("1").setData(list).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 退出操作
	@Action("logout")
	public void logout() {
		// 1.得到token
		String token = this.getRequest().getHeader("token");
		// 2.从redis中根据token删除
		Map<String, Object> map = baseCacheService.getHmap(token);
		try {
			if (map == null || map.size() == 0) {
				// 为空
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_TOKEN).toJSON());
				return;

			}
			baseCacheService.del(token);
			// 3.响应状态码1

			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 登录操作
	@Action("login")
	public void login() {
		// 1.得到请求参数
		String signUuid = this.getRequest().getParameter("signUuid");
		String signCode = this.getRequest().getParameter("signCode");

		// 2.验证
		// 2.1非空判断
		// 2.2判断验证码
		try {
			String _signCode = baseCacheService.get(signUuid);
			if (!_signCode.equalsIgnoreCase(signCode)) {
				// 验证码不正确
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.INPUT_ERROR_OF_VALIDATE_CARD).toJSON());
				return;
			}

			// 3.调用service完成登录操作
			// 3.0判断是否是手机号，可以使用手机号登录
			String str = user.getUsername();// 得到页面上录入的用户帐户名，有可能是用户名，也有可能是手机号
			if (CommomUtil.isMobile(str)) { // 是手机号
				UserModel um = userService.findByPhone(str);
				str = um.getUsername();
			}

			// 3.1调用service的根据username,password查询用户操作
			// 注意:处理密码
			String pwd = MD5Util.md5(str + user.getPassword().toLowerCase());
			UserModel u = userService.login(str, pwd);
			// 3.2判断是否登录成功
			if (u != null) {
				// 成功,要将用户存储到redis中
				String token = generateUserToken(u.getUsername());
				// 处理数据
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("userName", u.getUsername());
				data.put("id", u.getId());
				// 4.响应数据到浏览器
				this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS)
						.setData(data).setToken(token).toJSON());
			} else {
				// 失败
				// 4.响应数据到浏览器
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.ERROR_OF_USERNAME_PASSWORD).toJSON()); // 用户名与密码不正确

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 注册功能
	@Action("signup")
	public void regist() {
		// 1.封装请求参数 可以使用模型驱动
		// 2.调用userService完成用户添加操作
		// 密码加密 处理
		String pwdMd5 = MD5Util.md5(user.getUsername() + user.getPassword().toLowerCase());// 将其进行md5加密
		user.setPassword(pwdMd5);
		boolean flag = userService.addUser(user);
		try {
			if (flag) {
				// 注册成功
				// 3.开帐户
				userAccountService.add(user.getId());
				// 4.将用户存储到redis中，有效时间30分钟
				String token = generateUserToken(user.getUsername());
				// 响应数据要返回status data token
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", user.getId());
				this.getResponse().getWriter().write(
						Response.build().setStatus(FrontStatusConstants.SUCCESS).setData(map).setToken(token).toJSON());
			} else {
				// 注册失败
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.BREAK_DOWN).toJSON());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 验证录入的验证码是否正确
	@Action("codeValidate")
	public void codeValidate() {
		// 1.得到请求参数
		String signUuid = this.getRequest().getParameter("signUuid"); // uuid
		String signCode = this.getRequest().getParameter("signCode"); // 录入的验证码

		// 2.从redis中根据uuid获取
		String _signCode = baseCacheService.get(signUuid);
		// 3.判断验证码是否正确
		try {
			if (signCode.equalsIgnoreCase(_signCode)) {
				// 正确
				this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS).toJSON());
				return;
			} else {
				// 不正确
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.INPUT_ERROR_OF_VALIDATE_CARD).toJSON());
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 校验手机号码是否存在
	@Action("validatePhone")
	public void validatePhone() {
		// 1.得到请求参数--电话
		String phone = this.getRequest().getParameter("phone");
		// 2.根据用户名在数据库中查询，是否存在
		UserModel user = userService.findByPhone(phone);

		try {
			if (user == null) {
				// 说明用户名没有被占用
				this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS).toJSON());
				return;
			} else {
				// 被占用
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.MOBILE_ALREADY_REGISTER).toJSON());
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 校验用户名是否存在操作
	@Action("validateUserName")
	public void validateUserName() {
		// 1.得到请求参数--用户名
		String username = this.getRequest().getParameter("username");
		// 2.根据用户名在数据库中查询，是否存在
		UserModel user = userService.findByUsername(username);

		try {
			if (user == null) {
				// 说明用户名没有被占用
				this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS).toJSON());
				return;
			} else {
				// 被占用
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.ALREADY_EXIST_OF_USERNAME).toJSON());
				return;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 得到一个图片验证码
	@Action("validateCode")
	public void validateCode() {
		// 1.得到请求参数tokenUuid
		String tokenUuid = this.getRequest().getParameter("tokenUuid");
		// 2.判断它在redis中是否存在
		String uuid = baseCacheService.get(tokenUuid);
		try {
			if (StringUtils.isEmpty(uuid)) {
				// 为空，说明可能存在风险
				this.getResponse().getWriter().write(Response.build().setStatus("-999").toJSON());
				return;
			}
			// 3.产生图片验证码
			// 3.1 通过工具获取验证码信息
			String str = ImageUtil.getRundomStr();
			// 3.2将信息存储到redis中
			baseCacheService.del(uuid);
			baseCacheService.set(tokenUuid, str);
			baseCacheService.expire(tokenUuid, 3 * 60);
			// 3.将验证码图片响应到浏览器
			ImageUtil.getImage(str, this.getResponse().getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 得到一个uuid
	@Action("uuid")
	public void uuid() {
		// 1.随机产生一个uuid
		String uuid = UUID.randomUUID().toString();
		// 2.存储到redis中
		baseCacheService.set(uuid, uuid);
		baseCacheService.expire(uuid, 3 * 60); // uuid三分钟有效
		// 3.响应到浏览器--响应一个status 还要一个uuid
		try {
			this.getResponse().getWriter().write(Response.build().setStatus("1").setUuid(uuid).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 修改密码
	@Action("updatePassword")
	public void updatePassword() {
		// 获取参数
		String oldpw = getParameter("oldpw");
		String newpw = getParameter("newpw");
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
			// 4.校验旧密码
			String oldpwMd5 = MD5Util.md5(user.getUsername() + oldpw.toLowerCase());// 将其进行md5加密
			if (user != null && oldpwMd5.equals(user.getPassword())) {
				String pwdMd5 = MD5Util.md5(user.getUsername() + newpw.toLowerCase());// 将其进行md5加密
				user.setPassword(pwdMd5);
				userService.save(user);
			} else {
				this.getResponse().getWriter().write(Response.build().setStatus("-999").toJSON());
				return;
			}
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
