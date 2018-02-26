package com.heima.crm.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.heima.crm.domain.Customer;

@Produces("*/*")
public interface CustomerService {
	@GET
	@Path("/customer/noassociation")
	@Produces({ "application/xml", "application/json" })
	public List<Customer> getNoAssociations();

	@GET
	// 客户端 查询请求
	@Path("/customer/findcustomerbydecidedzoneid/{decidezoneId}")
	// http://xxxx/user/1
	@Consumes({ "application/xml", "application/json" })
	// 客户端 只能发送 xml 数据类型
	@Produces({ "application/xml", "application/json" })
	public List<Customer> getInUserAssociations(@PathParam("decidezoneId") String decidezone_id);

	@PUT
	@Path("/customer/assigencusotmertodecidedzone/{decidezoneId}/{cids}")
	// /customer/dq001/1,2,3
	@Consumes({ "application/xml", "application/json" })
	public void assignedCustomerToDecidedZone(@PathParam("cids") String customerids,
			@PathParam("decidezoneId") String decidedZone_id);

	@GET
	// 客户端 查询请求
	@Path("/customer/findcustomerbytelephone/{telephone}")
	// http://xxxx/user/1
	@Consumes({ "application/xml", "application/json" })
	// 客户端 只能发送 xml 数据类型
	@Produces({ "application/xml", "application/json" })
	public Customer findCustomerByTelephone(@PathParam("telephone") String telephone);

	@GET
	// 客户端 查询请求
	@Path("/customer/findcustomerbyaddress/{address}")
	// http://xxxx/user/1
	@Consumes({ "application/xml", "application/json" })
	// 客户端 只能发送 xml 数据类型
	@Produces({ "application/xml", "application/json" })
	public Customer findCustomerByAddress(@PathParam("address") String address);

	@POST
	// 客户端 查询请求
	@Path("/customer/save")
	// http://xxxx/user/1
	@Consumes({ "application/xml", "application/json" })
	// 客户端 只能发送 xml 数据类型
	@Produces({ "application/xml", "application/json" })
	public Customer save(Customer customer);

	@PUT
	@Path("/customer/updateadressbyid/{customerid}/{address}")
	@Consumes({ "application/xml", "application/json" })
	public void updateadressbyid(@PathParam("customerid") String customerid, @PathParam("address") String address);

}
