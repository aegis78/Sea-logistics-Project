package in.saram.address.service;


import java.util.ArrayList;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import in.saram.address.dao.AddressDAO;
import in.saram.address.util.Utils;
import in.saram.address.vo.AddressVO;
import in.saram.address.vo.LmnNmVO;
import in.saram.address.vo.ParamAddressVO;

@Service
public class AddressService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AddressDAO address;
	
	
	@Cacheable("getCurrentTime")
	public String getCurrentTime() {
		
		String strTime = "";
		strTime = address.getCurrentDateTime();
		
		return strTime;
		
		
	}
	
	public ArrayList<LmnNmVO> getLmnAddress(String koreanEupNmDong) {
		
		
		ArrayList<LmnNmVO> lnmList = address.getAddressDataList(koreanEupNmDong);
		
		return lnmList;
		
		
	}
	
	
	public int getAddressListCount(ParamAddressVO paramAddressVO) {
		
		logger.info("address name === {}", paramAddressVO.getAddress());
		
		return address.getAddressListCount(paramAddressVO);
		
	}
	
	public HashMap<String, Object> getAddressList(ParamAddressVO paramAddressVO) {
		
		HashMap<String, Object> addrListMap = new HashMap<String, Object>();
		
		String searchAddr = paramAddressVO.getAddress();
		
		//특수문자 제거
		searchAddr = Utils.StringSTextReplace(searchAddr);
		paramAddressVO.setAddress(searchAddr);		
		
		//공백 체크
		if (Utils.StringBlankChk(searchAddr) ) {
			paramAddressVO.setSpace(1);
		}
		
		
		if( paramAddressVO.getStartPosition() <= 0) {
			addrListMap.put("totalCnt", getAddressListCount(paramAddressVO));						
		}
		
		ArrayList<AddressVO> addrGetList = address.getAddressList(paramAddressVO); 
		
		addrListMap.put("addrList", addrGetList);
		
		
		
		return addrListMap;
		
	}
	
	
	
	
	
}
