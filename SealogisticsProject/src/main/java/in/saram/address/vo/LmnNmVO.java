package in.saram.address.vo;

public class LmnNmVO {
	
	private String zip;
	private String zipSn;
	private String koreanAtptNm;
	private String koreanSignguNm;
	private String koreanEupNmDong;
	private String dtAdd;
	private String etcLnbr;
	
		
	
	public String getZip() {
		return zip;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public String getZipSn() {
		return zipSn;
	}



	public void setZipSn(String zipSn) {
		this.zipSn = zipSn;
	}



	public String getKoreanAtptNm() {
		return koreanAtptNm;
	}



	public void setKoreanAtptNm(String koreanAtptNm) {
		this.koreanAtptNm = koreanAtptNm;
	}



	public String getKoreanSignguNm() {
		return koreanSignguNm;
	}



	public void setKoreanSignguNm(String koreanSignguNm) {
		this.koreanSignguNm = koreanSignguNm;
	}

	
	public String getKoreanEupNmDong() {
		return koreanEupNmDong;
	}



	public void setKoreanEupNmDong(String koreanEupNmDong) {
		this.koreanEupNmDong = koreanEupNmDong;
	}



	public String getDtAdd() {
		return dtAdd;
	}



	public void setDtAdd(String dtAdd) {
		this.dtAdd = dtAdd;
	}



	public String getEtcLnbr() {
		return etcLnbr;
	}



	public void setEtcLnbr(String etcLnbr) {
		this.etcLnbr = etcLnbr;
	}



	public String toString() {
		
		String str = String.format("LmnNm[zip=%s,zinSn=%s,koreanAtptNm=%s,koreanSignguNm=%s,koreanEupNmDong=%s,dtAdd=%s,etcLnbr=%s,"
				, zip, zipSn, koreanAtptNm, koreanSignguNm, koreanEupNmDong, dtAdd, etcLnbr);
		
		return str;
	}

}
