package in.saram.address.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.saram.address.config.ApplicationConfig;
import in.saram.address.service.AddressService;
import in.saram.address.service.RefineAddressService;
import in.saram.address.vo.GreetingVO;
import in.saram.address.vo.LmnNmVO;
import in.saram.address.vo.ParamAddressVO;


@RestController
@RequestMapping(value = { "/api/v1" }, produces = MediaType.APPLICATION_JSON_VALUE)
public class SampleController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AddressService service;	
	
	@Autowired
	private RefineAddressService refineAddressService;
	
	@Autowired
	private ApplicationConfig appConfig; 
	
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/")
	public Map<String, String> index() {
		
		Map<String, String> testMap = new HashMap<String, String>();
		
		testMap.put("1", "a");
		testMap.put("2", "b");
		testMap.put("3", "c");
		testMap.put("4", "d");
		testMap.put("현재시간", service.getCurrentTime());
		
				
		
		//return word + service.getCurrentTime();
		
		return testMap;
	}
	
	
	@RequestMapping(value="/address1/{address}", method=RequestMethod.GET)
	public Map<String, Object> address(@PathVariable String address) {
		
		
		Map<String, Object> testMap = new HashMap<String, Object>();
		
		testMap.put("1", "a");
		testMap.put("2", "b");
		testMap.put("3", "c");
		testMap.put("4", "d");
		testMap.put("현재시간", service.getCurrentTime());
		
		
		
		
		
		ArrayList<LmnNmVO> addList = service.getLmnAddress(address);
		testMap.put("address", addList);
		testMap.put("result", "ok");
		testMap.put("resultCode", "200");
		
		
				
		
		//return word + service.getCurrentTime();
		
		return testMap;
	}
	
	
	@RequestMapping("/greeting")
    public GreetingVO greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new GreetingVO(counter.incrementAndGet(),
                            String.format("Hello %s!", name));
    }
	
	
	@RequestMapping("/refine")
	@Cacheable("refine")
    public Map greeting2(@RequestParam(value="name", defaultValue="World") String name) {
		
		Map totalMap = new HashMap();
		Map rtnMap = new HashMap();
		Map rtnMap2= new HashMap();
		
		try {
			rtnMap = refineAddressService.getRfnAddrMap("44012", "경기 수원시 장안구 조원1동 조원주공뉴타운아파트 101~217동", "213동 1904호");
			rtnMap2 = refineAddressService.getRfnAddrMap("08708", "서울 관악구 봉천동", "1690-86 그린행운 103", "N");
		} catch (Exception e) {
			logger.error("reFineAddress ERROR", e);
		}
		
		
		totalMap.put("addr1", rtnMap);
		totalMap.put("addr2", rtnMap2);
		totalMap.put("config", appConfig.getIp() + appConfig.getPort() + appConfig.getRevEncoding());
		
		return totalMap;
		
       
    }
	
	@RequestMapping("/address/{address}")
	@Cacheable("address")
    public Map<String, Object> unifiedAddress(@PathVariable String address) {
		Map<String, Object> totalMap = new HashMap<String, Object>();
		
		ParamAddressVO paramAddressVO = new ParamAddressVO();		
		paramAddressVO.setAddress(address);	
		
		//service.getAddressList(paramAddressVO);
		
		totalMap.put("address", service.getAddressList(paramAddressVO));
		return totalMap;
		
	}
	
	

}
