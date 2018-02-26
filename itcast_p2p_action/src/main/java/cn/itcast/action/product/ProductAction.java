package cn.itcast.action.product;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.action.common.BaseAction;
import cn.itcast.domain.product.Product;
import cn.itcast.domain.product.ProductEarningRate;
import cn.itcast.service.product.IProductService;
import cn.itcast.utils.FrontStatusConstants;
import cn.itcast.utils.JsonMapper;
import cn.itcast.utils.Response;

@Controller
@Scope("prototype")
@Namespace("/product")
public class ProductAction extends BaseAction implements ModelDriven<Product> {

	private Product product = new Product();
	
	
	@Override
	public Product getModel() {
		return product;
	}

	// 创建一个日志对象
	private Logger log = Logger.getLogger(ProductAction.class);

	@Autowired
	private IProductService productService;

	// 修改操作
	@Action("modifyProduct")
	public void modifyProduct() {
		// 得到请求参数,可以使用模型驱动，但是利率没有封装
		String proEarningRates = this.getRequest().getParameter("proEarningRates");// {12:3,15:3.2......}

		// 将proEarningRates转换成List<ProductEarningRate>
		Map map = new JsonMapper().fromJson(proEarningRates, Map.class);
		List<ProductEarningRate> pers = new ArrayList<ProductEarningRate>();
		for (Object key : map.keySet()) {
			// key就是月份 value就是利率值
			ProductEarningRate per = new ProductEarningRate();
			per.setMonth(Integer.parseInt(key.toString()));
			per.setIncomeRate(Double.parseDouble(map.get(key).toString()));
			per.setProductId((int) product.getProId()); // 封装理财产品的id到利率信息中
			pers.add(per);
		}
		product.setProEarningRate(pers);// 封装利率信息到理财产品

		// 调用service完成修改操作
		productService.update(product);

		// 响应状态
		try {
			this.getResponse().getWriter().write(Response.build().setStatus(FrontStatusConstants.SUCCESS).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 根据id查询利率
	@Action("findRates")
	public void findRates() {
		// 1.得到产品id
		String proId = this.getRequest().getParameter("proId");
		// 2.调用service查询利率
		List<ProductEarningRate> pers = productService.findRateByProId(proId);

		try {
			this.getResponse().getWriter()
					.write(Response.build().setStatus(FrontStatusConstants.SUCCESS).setData(pers).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 根据id查询操作
	@Action("findProductById")
	public void findById() {
		// 设置响应数据的编码
		this.getResponse().setCharacterEncoding("utf-8");
		// 得到要查询的理财产品的id
		String proId = this.getRequest().getParameter("proId");
		try {
			// 验证不能为空
			if (StringUtils.isEmpty(proId)) {
				log.error("产品id不存在");
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.PARAM_VALIDATE_FAILED).toJSON());
				return;
			}

			// 调用service根据id查询
			Product p = productService.findById(Long.parseLong(proId));
			if (p == null) {
				log.error("根据id查询产品失败");
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.NULL_RESULT).toJSON());
				return;
			}

			this.getResponse().getWriter()
					.write(Response.build().setStatus(FrontStatusConstants.SUCCESS).setData(p).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
			log.error(e.getMessage());
			try {
				this.getResponse().getWriter()
						.write(Response.build().setStatus(FrontStatusConstants.SYSTEM_ERROE).toJSON());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			return;
		}
	}

	// 查找所有理财产品操作
	@Action("findAllProduct")
	public void findAll() {

		// 设置响应数据的编码
		this.getResponse().setCharacterEncoding("utf-8");

		// 调用service后得到所有理财产品，应该是一个List<Product>
		List<Product> ps = productService.findAll();

		// 向浏览器响应json数据 {status:1,data:[{},{},{}]}

		try {
			this.getResponse().getWriter()
					.write(Response.build().setStatus(FrontStatusConstants.SUCCESS).setData(ps).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
