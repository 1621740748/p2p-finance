package cn.itcast.dao.creditor;

import java.util.List;
import java.util.Map;

import cn.itcast.domain.creditor.CreditorModel;

public interface ICreditorForSqlDAO {

	List<CreditorModel> findCreditorByCondition(Map<String, Object> map);

	List<Object[]> findCreditorBySum(Map<String, Object> map);

}
