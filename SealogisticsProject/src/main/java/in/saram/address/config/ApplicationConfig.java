package in.saram.address.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="refine")
public class ApplicationConfig {
	
	private String ip; 
	private int port;
	private int subPort;
	private String setEncoding;
	private String revEncoding;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getSubPort() {
		return subPort;
	}
	public void setSubPort(int subPort) {
		this.subPort = subPort;
	}
	public String getSetEncoding() {
		return setEncoding;
	}
	public void setSetEncoding(String setEncoding) {
		this.setEncoding = setEncoding;
	}
	public String getRevEncoding() {
		return revEncoding;
	}
	public void setRevEncoding(String revEncoding) {
		this.revEncoding = revEncoding;
	}
	  

}
