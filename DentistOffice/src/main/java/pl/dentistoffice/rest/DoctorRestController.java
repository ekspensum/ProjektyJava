package pl.dentistoffice.rest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.List;

import javax.xml.ws.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import pl.dentistoffice.entity.Doctor;
import pl.dentistoffice.service.UserService;

@RestController
@RequestMapping(path = "/mobile")
public class DoctorRestController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping(path = "/doctors")
	public ResponseEntity<List<Doctor>> getDoctors(){
		List<Doctor> allDoctors = userService.getAllDoctors();

//		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//		
//		try {
//			ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
//			outputStream.writeObject(allDoctors);
//			outputStream.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return byteArrayOutputStream.toByteArray();
		
		ResponseEntity<List<Doctor>> responseEntity = new ResponseEntity<>(allDoctors, HttpStatus.OK);
		return responseEntity;
		
//		return allDoctors;
	}

	
}
