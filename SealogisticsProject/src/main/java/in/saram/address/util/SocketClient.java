package in.saram.address.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import in.saram.address.config.ApplicationConfig;


public class SocketClient {

	static Logger logger = LoggerFactory.getLogger(SocketClient.class);

	private Socket socket = null;

	public SocketClient(String strIP, int nPort) throws Exception {
		
		try {

			if ((strIP == null) || ("".equals(strIP)) || (nPort < 0) || (nPort > 65535)) {
				throw new Exception("[수지원넷소프트] IP주소나 포트가 적절히 설정되지 않았습니다");
			}

			InetSocketAddress isa = new InetSocketAddress(strIP, nPort);
			logger.debug("Connecting to " + strIP + ":" + nPort);

			this.socket = new Socket();
			this.socket.setSoTimeout(3000);
			this.socket.setSoLinger(false, 1);

			this.socket.connect(isa);

			logger.debug("Connected");

		} catch (Exception e) {
			this.socket.close();
			logger.error("Socket Connection Error", e);
		}
	}

	public static SocketClient Connect(String strIP, int nPort) throws Exception {

		SocketClient socketClient = null;

		try {

			// socketClient = new SocketClient(appConfig.getIp(),
			// appConfig.getPort());
			socketClient = new SocketClient(strIP, nPort);

		} catch (Exception e) {

			logger.error("[수지원넷소프트] 정제엔진서버와 연결 실패", e);

		}

		return socketClient;
	}

	public String SocketSendRecv(String strSend, String encoding) throws Exception {
		OutputStream os;
		InputStream is;
		String strRecv;
		byte TmpBuf[];
		os = null;
		is = null;
		strRecv = "";
		
		TmpBuf = strSend.getBytes(encoding);

		try {
			try {

				os = socket.getOutputStream();
				os.write(TmpBuf);
				os.flush();

			} catch (IOException e) {
				logger.error("SEND IO ERROR ", e);

			} catch (Exception e) {
				logger.error("SEND ERROR", e);

			}
			try {
				int at = 0;
				int iRead = 0;
				int len = 10;
				byte count[] = new byte[10];
				byte temp[] = new byte[10];
				is = socket.getInputStream();
				do {
					iRead = is.read(temp, 0, len);
					System.arraycopy(temp, 0, count, at, iRead);
					at += iRead;
					len -= iRead;
				} while (len != 0);
				len = Integer.parseInt(new String(count));
				logger.info("Total Byte to RECV : [" + len + "]");
				byte inData[] = new byte[len];
				temp = new byte[len];
				at = 0;
				iRead = 0;
				do {
					iRead = is.read(temp, 0, len);
					System.arraycopy(temp, 0, inData, at, iRead);
					at += iRead;
					len -= iRead;
				} while (len != 0);
				// strRecv = new String(inData, appConfig.getRevEncoding());
				strRecv = new String(inData, "euc-kr");
				logger.info("Receive Data =[" + strRecv + "]");
			} catch (IOException e) {

				logger.error("RECV IO ERROR", e);

			} catch (Exception e) {

				logger.error("RECV ERROR", e);

			} finally {
				try {
					is.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		} catch (Exception e) {
			throw e;
		}
		// goto _L1
		// local1;
		try {
			os.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			is.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		socket.close();
		logger.info("Disconnect");
		// JVM INSTR ret 15;
		// _L1:
		return strRecv;
	}

}
