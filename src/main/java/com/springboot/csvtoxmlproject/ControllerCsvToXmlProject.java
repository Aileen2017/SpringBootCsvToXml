package com.springboot.csvtoxmlproject;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ControllerCsvToXmlProject {
	
	@Autowired
	ServiceCsvToXml serviceCsvToXml;
	
	@GetMapping("/uploadCsv")
	public String uploadCsvFormReturnDownload(Model model) {
		
		return "uploadCsvTemplate";
	}

	
	
	@PostMapping("/uploadCsv_return_download")
	public ResponseEntity uploadCsvSubmitReturnDownload(@RequestParam("file1") MultipartFile file, Model model) throws Exception {

		String fileName = file.getOriginalFilename().split("\\.")[0];

		OutputStream outputStream = serviceCsvToXml.serviceCsvToXmlOutputStream(file);
  
		return ResponseEntity.ok()
		    		.header("Content-Disposition","attachment;filename="+fileName+".xml" )		    		
		            .contentLength(outputStream.toString().length())		    
		            .body(outputStream.toString().getBytes());

	}
	

	
	@PostMapping("/uploadCsv_with_headers_return_download")
	public ResponseEntity uploadCsvWithHeadersSubmit(@RequestParam("hiddenText") String hiddenText, @RequestParam("file1") MultipartFile file, Model model) throws Exception {
	
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
	
	

}
