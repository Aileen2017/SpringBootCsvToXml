package com.springboot.csvtoxmlproject;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControllerCsvToXmlProject {
	
	
	@Autowired
	ServiceCsvToXml serviceCsvToXml;
	
	@GetMapping("/uploadCsv")
	public String uploadCsvFormReturnDownload(Model model) {
		//throw new NullPointerException();
		return "uploadCsvTemplate";
	}

	
	
	@PostMapping("/uploadCsv_return_download")
	public ResponseEntity uploadCsvSubmitReturnDownload(@RequestParam("file1") MultipartFile file, Model model) throws Exception {
		if(file.isEmpty())
			throw new EmptyFileException();
		String fileName = file.getOriginalFilename().split("\\.")[0];

		OutputStream outputStream = serviceCsvToXml.serviceCsvToXmlOutputStream(file);
  
		return ResponseEntity.ok()
		    		.header("Content-Disposition","attachment;filename="+fileName+".xml" )		    		
		            .contentLength(outputStream.toString().length())		    
		            .body(outputStream.toString().getBytes());

	}
	

	
	@PostMapping("/uploadCsv_with_headers_return_download")
	public ResponseEntity uploadCsvWithHeadersSubmit(@RequestParam("hiddenText") String hiddenText, @RequestParam("file1") MultipartFile file, Model model) throws Exception {
		if(file.isEmpty())
			throw new EmptyFileException();
		System.out.println(hiddenText);
		String[] headers = {};
		if(!hiddenText.isEmpty())
			headers = hiddenText.split(" ");
		OutputStream outputStream = serviceCsvToXml.serviceCsvToXmlOutputStreamWithHeaders(file, headers);	
		String fileName = file.getOriginalFilename().split("\\.")[0];
		return ResponseEntity.ok()
		    		.header("Content-Disposition","attachment;filename="+fileName+".xml" )	    		
		            .contentLength(outputStream.toString().length())
		            .body(outputStream.toString().getBytes());

	}
	
	//Return error in ResponseStatus in a plain web page
	/*	  @ResponseStatus(value=HttpStatus.BAD_REQUEST,
	          reason="Bad Request")  // 409
		  @ExceptionHandler(NullPointerException.class)
		  public void conflict() {
			  	// Nothing to do
		  }*/
	
	
	
	//Return error in default web page error.html, with customised error messages
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception ex) {
		// logger.error("Request: " + req.getRequestURL() + " raised " + ex);

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", ex.getMessage());
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("error");
		return mav;
	}


	//Return error in  user defined web page myerror.html, with customised error messages
	/*	  @ExceptionHandler(EmptyFileException.class)
		  public String handleError(HttpServletRequest req, Exception ex, Model model) {
		   // logger.error("Request: " + req.getRequestURL() + " raised " + ex);

		    ModelAndView mav = new ModelAndView();
		    mav.addObject("exception", ex);
		    mav.addObject("url", req.getRequestURL());
		    mav.setViewName("myerror");
		    return "myerror";
		  }*/
	  
	
	

}
