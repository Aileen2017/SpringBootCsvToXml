package com.springboot.csvtoxmlproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExtendedSimpleMappingExceptionResolver extends SimpleMappingExceptionResolver {
	
	public ExtendedSimpleMappingExceptionResolver() {
	    // Enable logging by providing the name of the logger to use
	    setWarnLogCategory(ExtendedSimpleMappingExceptionResolver.class.getName());
	  }

	  @Override
	  public String buildLogMessage(Exception e, HttpServletRequest req) {
	    return "MVC exception: " + e.getLocalizedMessage();
	  }
	    
	  @Override
	  protected ModelAndView doResolveException(HttpServletRequest req,
	        HttpServletResponse resp, Object handler, Exception ex) {
	    // Call super method to get the ModelAndView
	    ModelAndView mav = super.doResolveException(req, resp, handler, ex);
	        
	    // Make the full URL available to the view - note ModelAndView uses
	    // addObject() but Model uses addAttribute(). They work the same. 
	    mav.addObject("url", req.getRequestURL());
	    mav.addObject("exception", ex);
	    return mav;
	  }
	
	

}
