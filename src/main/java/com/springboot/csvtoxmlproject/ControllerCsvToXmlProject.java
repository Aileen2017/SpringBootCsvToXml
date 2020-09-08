package com.springboot.csvtoxmlproject;

import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.csvtoxmlproject.ServiceCsvToXml;

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
		    		//.header("Content-Disposition", "attachment; filename=bonds.xml" )
		            .contentLength(outputStream.toString().length())
		           // .contentType(MediaType.APPLICATION_OCTET_STREAM) 
		            .body(outputStream.toString().getBytes());

	}

}
