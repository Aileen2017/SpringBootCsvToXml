package com.springboot.csvtoxmlproject;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

@Service
public class ServiceCsvToXml {
	
	static String rootElementName = "Datas";

	public int serviceCsvToXml(MultipartFile multipartFile) throws Exception {

			String fileName = multipartFile.getOriginalFilename();
			String[] names = fileName.split("\\.");
			String newFileName = names[0]+".xml";
			InputStream is = multipartFile.getInputStream();
			List<List<String>> lists = readCsv(is);
			//String[] headers = {"", ""}; 
			String[] headers = {"bonds","name", "currency", "maturity", "yield", "coupon" };
			Document document = convertListToDocument(lists, headers);
			return transformToXml(document, newFileName);
	}
	
	public OutputStream serviceCsvToXmlOutputStream(MultipartFile multipartFile) throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		String[] names = fileName.split("\\.");
		String newFileName = names[0]+".xml";
		InputStream is = multipartFile.getInputStream();
		List<List<String>> lists = readCsv(is);
		//String[] headers = {"", ""}; 
		String[] headers = {"bonds","name", "currency", "maturity", "yield", "coupon" };
		Document document = convertListToDocument(lists, headers);
		return transformToXmlStream(document);
	}
	
	
	
	public int serviceCsvToXml(MultipartFile multipartFile, String[] headers) throws Exception {

		String fileName = multipartFile.getOriginalFilename();
		String[] names = fileName.split("\\.");
		String newFileName = names[0]+".xml";
		InputStream is = multipartFile.getInputStream();
		List<List<String>> lists = readCsv(is);
		//String[] headers = {"", ""}; 
		//String[] headers = {"bonds","name", "currency", "maturity", "yield", "coupon" };
		Document document = convertListToDocument(lists, headers);
		return transformToXml(document, newFileName);

}
	
	/*	public int serviceCsvToXml2(MultipartFile multipartFile) {
				try {
					String fileName = multipartFile.getOriginalFilename();
					InputStream is = multipartFile.getInputStream();
					List<List<String>> lists = readCsv(is);
					String[] headers = {"", ""}; 
					Document document = convertListToDocument(lists, headers);
					return transformToXml(document, fileName);
					
				}catch(Exception e) {
					System.out.println(e.toString());
					throw e;
				}
				
	
	
			}*/



	Document convertListToDocument(List<List<String>> listOfRows, String[] headers) throws Exception{  //throws ParserConfigurationException, InstantiationException, IllegalAccessException {
		 
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document document = db.newDocument();
		Element element = document.createElement(rootElementName);
		document.appendChild(element);
		int rowPos = 0;
		for(List<String> row : listOfRows) {
			Element childElement = document.createElement("data"+rowPos);
			element.appendChild(childElement);
			int pos = 0;
			for(String nodeValue : row) {		

				Element ccElement  = document.createElement(headers[pos]);
				Text tn = document.createTextNode(row.get(pos));
				ccElement.appendChild(tn);
				childElement.appendChild(ccElement);
				pos++;

			}
			rowPos++;

		}


		return document;




	}


	int transformToXml(Document document, String fileName) throws TransformerException, FileNotFoundException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();	
		File file = new File(fileName);
		StreamResult result = new StreamResult(new FileOutputStream(file));	
		transformer.transform(new DOMSource(document), result);
		return 1;

	}
	
	OutputStream transformToXmlStream(Document document) throws TransformerException, FileNotFoundException {
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();	
		//File file = new File(fileName);
		StreamResult result = new StreamResult(new ByteArrayOutputStream());	
		transformer.transform(new DOMSource(document), result);
		return result.getOutputStream();
	
	}


	List<List<String>> readCsv(InputStream is) throws FileNotFoundException {

		Scanner scanner = new Scanner(is);
		List<List<String>> lists = new ArrayList<List<String>>();
		while(scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] values = line.split(" ");
			List<String> list1 = Arrays.asList(values);
			lists.add(list1);


		}
		return lists;

	}



}
