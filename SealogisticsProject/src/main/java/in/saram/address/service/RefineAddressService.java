package in.saram.address.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import in.saram.address.config.ApplicationConfig;
import in.saram.address.util.SocketClient;
import in.saram.address.util.XmlDataParser;

@Service
public class RefineAddressService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApplicationConfig appConfig;
	
	@Cacheable("getRfnAddrMaps")
	public Map getRfnAddrMap(String zip, String inAdm, String inAds) throws Exception {

		SocketClient socketClient = null;
		XmlDataParser xml = null;

		String strXml = "";
		String strInput = "";

		try {
			socketClient = SocketClient.Connect(appConfig.getIp(), appConfig.getPort());

			strInput = zip + "|" + inAdm + "|" + inAds;
			logger.info(strInput);
			strXml = socketClient.SocketSendRecv(strInput, appConfig.getRevEncoding());

			xml = new XmlDataParser();
			// return xml.getAddrMapList(strXml);
		} catch (Exception e) {
			logger.error("Socket Xml Return ERROR", e);
		}

		return xml.getAddrMapList(strXml, appConfig.getRevEncoding());
	}
	
	@Cacheable("getRfnAddrMaps")
	public Map getRfnAddrMap(String zip, String inAdm, String inAds, String isNewAddr) throws Exception {
		SocketClient socketClient = null;
		String strXml = "";
		String strInput = "";
		String strisNewAddr = isNewAddr;
		XmlDataParser xml = null;

		try {
			socketClient = SocketClient.Connect(appConfig.getIp(), appConfig.getPort());
			if ((isNewAddr == null) || (!isNewAddr.equals("N")) || (!isNewAddr.equals("J"))) {
				strisNewAddr = "F";
			}
			strInput = zip + "|" + inAdm + "|" + inAds + "|" + strisNewAddr;
			logger.info(strInput);
			strXml = socketClient.SocketSendRecv(strInput, appConfig.getRevEncoding());

			xml = new XmlDataParser();

		} catch (Exception e) {
			logger.error("Socket Xml Return ERROR", e);
		}

		return xml.getAddrMapList(strXml, appConfig.getRevEncoding());
	}

}
