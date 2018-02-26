package cn.itcast.service.creditor;

import java.util.List;
import java.util.Map;

import cn.itcast.domain.creditor.CreditorModel;

public interface ICreditorService {

	void addCreditor(CreditorModel creditor);

	void addMultiple(List<CreditorModel> cms);

	List<CreditorModel> findCreditorByCondition(Map<String, Object> map);

	List<Object[]> findCreditorBySum(Map<String, Object> map);

	void checkCreditor(String[] id);

}
