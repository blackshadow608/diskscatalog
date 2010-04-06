package image.grabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Загрузчик страниц. Обеспечивает загрузку определенной страницы.
 * 
 * @author Alexander Levin.
 */
public class PageLoader {

	/*
	 * Каким образом наше приложению будет видно серверу, с которого
	 * производится загрузка изображений.
	 */
	public static final String USER_AGENT_PROPERTY = "http.agent";
	public static final String USER_AGENT_VALUE = "Mozilla/5.0 (Windows; U; Windows NT 5.1; ru; rv:1.9.1.7) Gecko/20091221 Firefox/3.5.7 (.NET CLR 3.5.30729)";

	/**
	 * Загружает страницы, адрес которой передается методу в качестве параметра.
	 * 
	 * @param pageUrl
	 *            - адрес страницы, которую необходимо загрузить.
	 * 
	 * @return Объект страницы {@link Page}.
	 * 
	 * @throws PageLoaderException
	 *             - объект исключения, который выбрасывается при наступлении
	 *             исключительной ситуации.
	 */
	public static Page loadPage(String pageUrl) throws PageLoaderException {
		System.setProperty(USER_AGENT_PROPERTY, USER_AGENT_VALUE);

		HttpURLConnection con = null;
		InputStream is = null;
		InputStreamReader reader = null;
		BufferedReader in = null;
		
		try {
			URL url = new URL(pageUrl);
			con = (HttpURLConnection) url.openConnection();
			con.setConnectTimeout(10000);

			is = con.getInputStream();
			reader = new InputStreamReader(is, "cp1251"); 
			in = new BufferedReader(reader);
			StringBuilder pageContent = new StringBuilder();
			String inputLine = "";

			while ((inputLine = in.readLine()) != null) {
				pageContent.append(inputLine);
			}
			
			return new Page(pageContent.toString());
		} catch (Exception e) {
			throw new PageLoaderException(e);
		} finally {
			if(in != null) {
				try {
					in.close();
				} catch (IOException e) {
					throw new PageLoaderException(e);					
				}
			}
			
			if(reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					throw new PageLoaderException(e);
				}
			}
			
			if(is != null) {
				try {
					is.close();
				} catch (IOException e) {
					throw new PageLoaderException(e);
				}
			}
			
			if(con != null) {
				con.disconnect();
			}
		}
	}

}
