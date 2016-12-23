package in.saram.address.util;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;

import in.saram.address.config.ApplicationConfig;


public class XmlDataParser {
	Logger logger = LoggerFactory.getLogger(this.getClass());


	public static List arMacLV1 = null;
	public static List arMacLV2 = null;

	public Map getAddrMapList(String strXML, String encoding) throws Exception {
		HashMap retMap = null;
		List retData = null;
		Element elmtRoot = null;
		try {
			elmtRoot = parseXML(strXML, encoding);
			if (elmtRoot != null) {
				if (arMacLV1 == null) {
					arMacLV1 = getMacro(elmtRoot, "LV1_ELMT");
				}
				if (arMacLV2 == null) {
					arMacLV2 = getMacro(elmtRoot, "LV2_ELMT");
				}
				retMap = getRootAttribute(elmtRoot);
				retData = getLevelAttribute(retMap, elmtRoot, (String) retMap.get("RCD2"), arMacLV1, arMacLV2);
				if (retData != null) {
					retMap.put("DATA_CNT", retData.size());
					retMap.put("DATA", retData);
				}
			}
		} catch (Exception e) {
			logger.error("GetAddrMapData Error", e);
		}
		return retMap;
	}

	public Map getAddrMapList(String strXML, int nMultiFrom, int nMultiTo, String encoding) throws Exception {
		HashMap retMap = null;
		List retData = null;
		List retTmpData = null;
		Element elmtRoot = null;
		try {
			elmtRoot = parseXML(strXML, encoding);
			if (elmtRoot != null) {
				if (arMacLV1 == null) {
					arMacLV1 = getMacro(elmtRoot, "LV1_ELMT");
				}
				if (arMacLV2 == null) {
					arMacLV2 = getMacro(elmtRoot, "LV2_ELMT");
				}
				retMap = getRootAttribute(elmtRoot);
				retTmpData = getLevelAttribute(retMap, elmtRoot, (String) retMap.get("RCD2"), arMacLV1, arMacLV2);
				if (retTmpData != null) {
					logger.info("===> 특정블록 호출(" + retTmpData.size() + ") : " + nMultiFrom + "~" + nMultiTo);
					retData = new ArrayList();
					for (int i = 0; i < retTmpData.size(); i++) {
						if ((i >= nMultiFrom) && (i <= nMultiTo)) {
							logger.info("===> 특정블록 추가 : " + i);
							retData.add(retTmpData.get(i));
						}
					}
					retMap.put("DATA_CNT", retTmpData.size());
					retMap.put("DATA", retData);
				}
			}
		} catch (Exception e) {
			logger.error("GetAddrMapData Error", e);
		}
		return retMap;
	}

	private Element parseXML(String strXML, String encoding) throws Exception {
		ByteArrayInputStream m_bais = null;
		SAXBuilder m_builder = null;
		Document m_doc = null;
		try {
			m_bais = new ByteArrayInputStream(strXML.getBytes(encoding));
			m_builder = new SAXBuilder();
			m_doc = m_builder.build(m_bais);

		} catch (Exception e) {
			logger.error("XML PARSE ERROR : [" + strXML + "]");

		}

		return m_doc.getRootElement();
	}

	private HashMap getRootAttribute(Element ROOT) throws Exception {
		HashMap rootMap = null;
		String tmpBuf = null;
		String[] keys = { "RCD1", "RCD2", "RCD3", "CODJT", "RMG1", "RMG2", "RMG3" };
		try {
			if (ROOT != null) {
				rootMap = new HashMap();
				for (int i = 0; i < keys.length; i++) {
					if ((tmpBuf = ROOT.getChildText(keys[i])) != null) {
						rootMap.put(keys[i], tmpBuf);
					} else {
						rootMap.put(keys[i], "");
					}
				}
			}
		} catch (Exception e) {
			logger.error("RootMap Create ERROR", e);
		}
		logger.info("===> 루트 노드 항목 : " + rootMap);
		return rootMap;
	}

	private List getLevelList(Element ROOT, String strLevel) throws Exception {
		ArrayList retList = null;
		List tmpList = null;
		Element tmpElmt = null;
		try {
			if ((ROOT != null) && (strLevel != null) && (!"".equals(strLevel.trim()))) {
				tmpList = ROOT.getChildren();
				if (tmpList != null) {
					retList = new ArrayList();
					for (int i = 0; i < tmpList.size(); i++) {
						tmpElmt = (Element) tmpList.get(i);
						if ((tmpElmt != null) && (tmpElmt.getName().equals(strLevel))) {
							retList.add(tmpElmt);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("GetLevelList ERROR", e);
		}
		if (retList.size() == 0) {
			return null;
		}
		return retList;
	}

	private List getLevelAttribute(Map mapRoot, Element ROOT, String RCD2, List arMacLV1, List arMacLV2) throws Exception {
		List retList = null;
		Map dataMap = null;
		String sb = "RSTUV";
		Element tmpChild1Elmt = null;
		Element tmpChild2Elmt = null;

		List tmpLevel1List = null;
		List tmpLevel2List = null;

		int nIDX_D = 0;
		int nIDX_P = 0;
		int nChildCnt = 0;
		if ((RCD2 == null) || ("".equals(RCD2.trim())) || (ROOT == null) || (mapRoot == null)) {
			return null;
		}
		try {
			tmpLevel1List = getLevelList(ROOT, "LV1");
			if (tmpLevel1List != null) {
				retList = new ArrayList();
				for (int i = 0; i < tmpLevel1List.size(); nIDX_D++) {
					tmpChild1Elmt = (Element) tmpLevel1List.get(i);
					dataMap = new HashMap();
					if (tmpChild1Elmt.getChildren().size() == 0) {
						logger.debug("tmpChild1Elmt.getChildren().size() == 0   DEBUG ME!!");
					} else {
						dataMap.put("NODE", "D");
						dataMap.put("IDX", nIDX_D);
						for (int j = 0; j < arMacLV1.size(); j++) {
							dataMap.put(arMacLV1.get(j), tmpChild1Elmt.getChildText((String) arMacLV1.get(j)));
						}
						if (sb.indexOf(RCD2.substring(0, 1)) != -1) {
							String NADM = tmpChild1Elmt.getChildText("NADM");
							String NADS = tmpChild1Elmt.getChildText("NADS");
							if ((NADM != null) && (NADS != null)) {
								dataMap.put("DISP", NADM + " " + NADS);
							} else {
								logger.debug("*ADM, *ADS 키가 존재하지 않으므로 DISP 키를 만들지 않습니다(직접 조합사용)");
							}
						} else {
							String JADM = tmpChild1Elmt.getChildText("JADM");
							String JADS = tmpChild1Elmt.getChildText("JADS");
							if ((JADM != null) && (JADS != null)) {
								dataMap.put("DISP", JADM + " " + JADS);
							} else {
								logger.debug("*ADM, *ADS 키가 존재하지 않으므로 DISP 키를 만들지 않습니다(직접 조합사용)");
							}
						}
						retList.add(dataMap);

						tmpLevel2List = getLevelList(tmpChild1Elmt, "LV2");
						if (tmpLevel2List != null) {
							dataMap.put("CNT", tmpLevel2List.size());
						} else {
							dataMap.put("CNT", "0");
						}
						if (tmpLevel2List != null) {
							int l = 0;
							for (nIDX_P = 0; l < tmpLevel2List.size(); nChildCnt++) {
								tmpChild2Elmt = (Element) tmpLevel2List.get(l);
								dataMap = new HashMap();
								if (tmpChild2Elmt.getChildren().size() == 0) {
									logger.debug("tmpChild2Elmt.getChildren().size() == 0   DEBUG ME!!");
								} else {
									dataMap.put("NODE", "P");
									dataMap.put("IDX", nIDX_P);
									dataMap.put("CNT", "0");
									for (int m = 0; m < arMacLV2.size(); m++) {
										dataMap.put(arMacLV2.get(m),
												tmpChild2Elmt.getChildText((String) arMacLV2.get(m)));
									}
									if (sb.indexOf(RCD2.substring(0, 1)) != -1) {
										String JADM = tmpChild1Elmt.getChildText("JADM");
										String JADS = tmpChild1Elmt.getChildText("JADS");
										if ((JADM != null) && (JADS != null)) {
											dataMap.put("DISP", JADM + " " + JADS);
										} else {
											logger.debug("*ADM, *ADS 키가 존재하지 않으므로 DISP 키를 만들지 않습니다(직접 조합사용)");
										}
										dataMap.put("NADM", tmpChild1Elmt.getChildText("NADM"));
										dataMap.put("NADS", tmpChild1Elmt.getChildText("NADS"));
									} else {
										String NADM = tmpChild1Elmt.getChildText("NADM");
										String NADS = tmpChild1Elmt.getChildText("NADS");
										if ((NADM != null) && (NADS != null)) {
											dataMap.put("DISP", NADM + " " + NADS);
										} else {
											logger.debug("*ADM, *ADS 키가 존재하지 않으므로 DISP 키를 만들지 않습니다(직접 조합사용)");
										}
									}
									retList.add(dataMap);
								}
								l++;
								nIDX_P++;
							}
						}
					}
					i++;
				}
			}
		} catch (Exception e) {
			throw new Exception("[수지원넷소프트] 수신된 XML 데이터 파싱에 실패하였습니다.");
		}
		for (int k = 0; k < retList.size(); k++) {
			logger.info("===> 데이터 영역(" + k + ") : " + retList.get(k));
		}
		return retList;
	}
	
	private List getMacro(Element ROOT, String strElmtName)
		    throws Exception
		  {
		    ArrayList macList = null;
		    String tmpMac = null;
		    

		    int nFind = 0;
		    try
		    {
		      if ((ROOT != null) || ((strElmtName != null) && (!"".equals(strElmtName.trim()))))
		      {
		        macList = new ArrayList();
		        tmpMac = ROOT.getChildText(strElmtName);
		        String[] tmpSplit = tmpMac.split("\\+");
		        for (int i = 0; i < tmpSplit.length; i++)
		        {
		          String tmpBuf = tmpSplit[i];
		          nFind = tmpBuf.indexOf("(");
		          macList.add(tmpBuf.substring(1, nFind));
		        }
		      }
		    }
		    catch (Exception e)
		    {
		      logger.error("GetMacro ERROR", e);
		    }
		    logger.info("===> 데이터 영역 해쉬키 : " + macList);
		    return macList;
		  }

}
