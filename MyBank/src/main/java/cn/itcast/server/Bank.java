package cn.itcast.server;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/bank")
public interface Bank {

	@Path("/recharge")
	@POST
	public boolean recharge(String bankCardId, double money);
}
