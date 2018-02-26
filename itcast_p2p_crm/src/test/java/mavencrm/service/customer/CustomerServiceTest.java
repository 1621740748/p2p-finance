package mavencrm.service.customer;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.client.WebClient;

import com.heima.crm.domain.Customer;

/**
 * 前台系统 会更新crm客户表信息
 * 
 * @author tps 模拟前台系统 自己编写sql脚本 插入数据库表customers即可
 */
@SuppressWarnings("all")
public class CustomerServiceTest {

	public static void main(String[] args) {
		// WebClient
		String url = "http://localhost:9999/bos-crm/rs/customerService/customer/";
	 //test2(url + "noassociation");
	test3(url + "findcustomerbydecidedzoneid/123");
		// test4(url + "assigencusotmertodecidedzone/DQ001/2,3");
	}

	private static void test4(String url) {
		// 修改 直接使用请求参数 完成 部分字段修改
		WebClient.create(url).put(null);

	}

	private static void test3(String url) {
		// 测试查询 未被定区关联的客户信息
		List<Customer> list = (List<Customer>) WebClient.create(url).accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		System.out.println(list);
	}

	private static void test2(String url) {
		// 测试查询 未被定区关联的客户信息
		List<Customer> list = (List<Customer>) WebClient.create(url).accept(MediaType.APPLICATION_JSON)
				.getCollection(Customer.class);
		System.out.println(list);
	}

}
