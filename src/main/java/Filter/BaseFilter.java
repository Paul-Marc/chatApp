package Filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Zuordnung zu Person: Paul Conrad
 * 
 * Zweck: Der Grundlegende Filter aus dem Tutorium/Vorlesung
 */
@WebFilter("/BaseFilter")
public class BaseFilter implements Filter {
	protected FilterConfig config;

	public BaseFilter() {
	}

	/**
	 * Gibt den Speicherplatz wieder frei
	 */
	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
	}

	public void init(FilterConfig fConfig) throws ServletException {
		this.config = fConfig;
	}

}
