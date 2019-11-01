package pl.dentistoffice.rest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pl.dentistoffice.entity.Patient;
import pl.dentistoffice.service.UserService;

@RestController
@RequestMapping(path = "/mobile")
public class AuthRestController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path = "/login")
	public Patient login(@RequestParam("username") final String username, 
						 @RequestParam("password") final String password,
						 HttpServletResponse response) {
	
		Patient loggedPatient = userService.loginMobilePatient(username, password);
		if(loggedPatient != null) {
			String encodeTokenBase64 = null;
			try {
				Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
				byte [] key = "haslo".getBytes("UTF-8");
				key = Arrays.copyOf(key, 16);
				Key secretKey = new SecretKeySpec(key, "AES");
				cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
				byte[] doFinal = cipher.doFinal(loggedPatient.getToken().getBytes());
				encodeTokenBase64 = Base64.getEncoder().encodeToString(doFinal);
				System.out.println("AuthRestController - encodeTokenBase64: "+encodeTokenBase64);
			} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
				e.printStackTrace();
			} catch (InvalidKeyException e) {
				e.printStackTrace();
			} catch (IllegalBlockSizeException e) {
				e.printStackTrace();
			} catch (BadPaddingException e) {
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (InvalidAlgorithmParameterException e) {
				e.printStackTrace();
			}
		
			response.setHeader("token", encodeTokenBase64);
		}
		return loggedPatient;
	}
	
	@GetMapping(path = "/logout")
	public String logout() {
		
		return "logout";
	}

	public boolean authentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			String encodeTokenBase64 = request.getHeader("token");
			byte[] encodeTokenByte = null;

			if (encodeTokenBase64 != null) {
				encodeTokenByte = Base64.getDecoder().decode(encodeTokenBase64);
				String decodeToken = null;
				try {
					Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
					byte[] key = "haslo".getBytes("UTF-8");
					key = Arrays.copyOf(key, 16);
					Key secretKey = new SecretKeySpec(key, "AES");
					cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(new byte[16]));
					byte[] doFinal = cipher.doFinal(encodeTokenByte);
					// byte [] decodeTokenByte = Base64.getDecoder().decode(doFinal);
					decodeToken = new String(doFinal);
				} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
					e.printStackTrace();
				} catch (InvalidKeyException e) {
					e.printStackTrace();
				} catch (IllegalBlockSizeException e) {
					e.printStackTrace();
				} catch (BadPaddingException e) {
					e.printStackTrace();
				}
				System.out.println("AuthRestController - Decode token " + decodeToken);
				Patient patient = userService.findMobilePatientByToken(decodeToken);
				if (patient != null) {
					return true;
				} else {
					response.sendError(403);
				}
			} else {
				response.sendError(403);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return false;
	}
}
