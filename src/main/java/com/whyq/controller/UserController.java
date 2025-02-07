package com.whyq.controller;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whyq.dto.UserDTO;
import com.whyq.entity.SalonOwner;
import com.whyq.service.SalonOwnerService;
import com.whyq.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
    private SalonOwnerService salonOwnerService;
	
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@GetMapping("/registerUser")
	public String registerUser(HttpSession session) {
		session.invalidate();
		
		return "UserSignup";
	}
	
	
	
	@PostMapping("/registerUser")
	public String registerUser(@ModelAttribute UserDTO userDTO, HttpSession session, RedirectAttributes redirectAttributes) {
	    try {
	    	
	    	System.out.println(userDTO);
	        userService.registerUser(userDTO);
	        session.setAttribute("userEmail", userDTO.getEmail());
	        session.setAttribute("userName", userDTO.getName());

	        redirectAttributes.addFlashAttribute("message", "User registered successfully!");
	        
	        return "redirect:/fetchSalon"; // Redirect user to search salons
	    } catch (Exception e) {
	        redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
	        return "redirect:/registerUser";
	    }
	}
	
	
	@GetMapping("/fetchSalon")
	public String fetchSalon(HttpSession session) {
		if(session.getAttribute("userEmail")==null) {
			return "redirect:/login";
			
		}
		
		logger.info("User Logged In Successfully");
		System.out.println(session.getAttribute("userEmail"));
		return "fetchSalon";
	}
	
	
	@PostMapping("/fetchSalon")
	public String fetchSalonByPincode(@RequestParam("pincode") String pincode, Model model) {
	    List<SalonOwner> salons = salonOwnerService.getSalonsByPincode(pincode);
	    model.addAttribute("salons", salons);
	    System.out.println(salons);
	    return "fetchSalon"; // Reload the page with salon results
	}
	
	
	@GetMapping("/Userlogout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy session
        logger.info("User Logged OUT Successfully");
        return "redirect:/login"; // Redirect to login page
    }
}
