package pl.dentistoffice.config;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServlet extends AbstractAnnotationConfigDispatcherServletInitializer {

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {RootConfig.class};
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class [] {WebConfig.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String [] {"/"};
	}
	
//	for UTF-8 encoding forms data
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
	      FilterRegistration.Dynamic filterRegistration = servletContext.addFilter("characterEncodingFilter", new CharacterEncodingFilter("UTF-8", true, true));
	      filterRegistration.addMappingForUrlPatterns(null, false, "/*");

	      filterRegistration = servletContext.addFilter("hiddenHttpMethodFilter", new HiddenHttpMethodFilter() );
	      filterRegistration.addMappingForUrlPatterns(null, false, "/*");

	    super.onStartup(servletContext);
	}
}
