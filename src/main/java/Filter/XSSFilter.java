package Filter;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Filtert schaedlichen XSS-Code aus den request heraus.
 */
@WebFilter(urlPatterns = { "/XSSFilter" }, servletNames = { "AddContactServlet", "ChangePWServlet",
		"ChatPartnerProfileServlet", "CreateNewGroupServlet", "EditProfileServlet", "LoadChatsServlet",
		"LoadContactServlet", "LoginServlet", "LogOutServlet", "NewGroupToDBServlet", "NewMessageServelet",
		"RegisterServlet", "LeaveGroupServlet", "AddUserToGroupServlet", "UpdateProfileServlet", "UpdatePWServlet" })
public class XSSFilter extends BaseFilter implements Filter {
	/**
	 * Hauptmethode für beginn der Filter-Chain
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		XSSRequestWrapper wrapper = new XSSRequestWrapper((HttpServletRequest) request);
		chain.doFilter(wrapper, response);
	}

}

/**
 * Klasse zur überprüfen eines Strings auf XSS-Angriffe
 */
class XSSRequestWrapper extends HttpServletRequestWrapper implements HttpServletRequest {
	public XSSRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	public String getParameter(String str) {
		str = super.getParameter(str);
		if (str != null) {
			Pattern scriptPattern = null;
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			str = scriptPattern.matcher(str).replaceAll("");
			scriptPattern = Pattern.compile("<script>", Pattern.CASE_INSENSITIVE);
			str = scriptPattern.matcher(str).replaceAll("");
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE);
			str = scriptPattern.matcher(str).replaceAll("");
			scriptPattern = Pattern.compile("onmouseover=", Pattern.CASE_INSENSITIVE);
			str = scriptPattern.matcher(str).replaceAll("");
		}
		return str;
	}
}
