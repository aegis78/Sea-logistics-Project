package in.saram.address.dao;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.saram.address.vo.AddressVO;
import in.saram.address.vo.LmnNmVO;
import in.saram.address.vo.ParamAddressVO;

@Repository
public class AddressDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	public String getCurrentDateTime() {
        return sqlSession.selectOne("address.getCurrentDateTime");
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<LmnNmVO> getAddressDataList(String koreanEupNmDong) {		
    	return (ArrayList) sqlSession.selectList("address.getLmnNmList", koreanEupNmDong);
    }
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<AddressVO> getAddressList(ParamAddressVO paramAddressVO) {		
    	return (ArrayList) sqlSession.selectList("address.getAddressList", paramAddressVO);
    }
	
	public int getAddressListCount(ParamAddressVO paramAddressVO) {		
    	return Integer.parseInt(sqlSession.selectOne("address.getAddressListCount", paramAddressVO).toString());
    }
	

}
