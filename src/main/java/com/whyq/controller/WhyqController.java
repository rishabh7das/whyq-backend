package com.whyq.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.whyq.dto.LoginDTO;
import com.whyq.dto.SalonOwnerDTO;
import com.whyq.entity.SalonOwner;
import com.whyq.entity.User;
import com.whyq.service.SalonOwnerService;
import com.whyq.service.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class WhyqController {

    @Autowired
    private SalonOwnerService salonOwnerService;
    
    @Autowired
    private UserService userService;


    @PostMapping("/registerSalonOwner")
    public String registerSalonOwner(@ModelAttribute SalonOwnerDTO salonOwnerDTO,HttpSession session, Model model) {
        try {
        	// saving the details
            salonOwnerService.registerSalonOwner(salonOwnerDTO);
            
         // Store Owner in Session
            session.setAttribute("ownerEmail", salonOwnerDTO.getEmail());
            session.setAttribute("ownerName", salonOwnerDTO.getOwnerName());
            
            
            model.addAttribute("message", "Salon Owner registered successfully!");
            return "redirect:/dashboard";
        } catch (RuntimeException e) {
            model.addAttribute("message", "Error: " + e.getMessage());
        }
        return "registrationsuccess";
    }
    
    @GetMapping("/dashboard")
    public String ownerDashboard(HttpSession session, Model model) {
        if (session.getAttribute("ownerEmail") == null) {
            return "redirect:/login"; // Redirect to login if session is missing
        }
        
        String email = session.getAttribute("ownerEmail").toString();
        SalonOwnerDTO so = salonOwnerService.getSalonOwnerProfile(email);
        model.addAttribute("owner", so);
        session.setAttribute("ownerObj", so);
        System.out.println(so.getServiceIds());
//        session.setAttribute("salonOwner", so);
        
        return "ownerDashboard"; // Load Dashboard
    }

    @GetMapping("/registerSalonOwner")
    public String showRegistrationForm(Model model) {
        model.addAttribute("salonOwner", new SalonOwnerDTO());
        return "salonOwnerSignup";
    }
    
    @GetMapping("/login")
    public String login() {
    	return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Destroy session
        return "redirect:/login"; // Redirect to login page
    }
    
    @GetMapping("/ownerProfile")
    public String ownerProfile(HttpSession session, Model model) {
    	if (session.getAttribute("ownerEmail") == null) {
            return "redirect:/login"; // Redirect to login if session is missing
        }
    	String email = session.getAttribute("ownerEmail").toString();
    	SalonOwnerDTO so = salonOwnerService.getSalonOwnerProfile(email);
        model.addAttribute("owner", so);
//        System.out.println(so.getServiceIds());
    	return "salonOwnerProfile";
    }
    
    
//    about page
    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }
    
    
    
    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute SalonOwnerDTO salonOwnerDTO, Model model, RedirectAttributes redirectAttributes,HttpSession session) {
//        salonOwnerService.updateSalonOwner(salonOwnerDTO);
//        model.addAttribute("message", "Profile updated successfully!");
        redirectAttributes.addFlashAttribute("message", "Profile updated successfully!");
        salonOwnerService.updateSalonOwner(salonOwnerDTO);
        session.setAttribute("ownerName", salonOwnerDTO.getOwnerName());
    	System.out.println(salonOwnerDTO);
        return "redirect:/ownerProfile";
    }
    

    
    
 // Handle Login Submission
    @PostMapping("/login")
    public String login(@ModelAttribute LoginDTO loginDTO, HttpSession session, Model model) {
    	
    	
//    	System.out.println("login post method invoked "+ loginDTO);
        if ("salonOwner".equals(loginDTO.getRole())) {
            // Salon Owner Login
        	System.out.println("login post method invoked");
            SalonOwner owner = salonOwnerService.authenticateOwner(loginDTO);
            if (owner != null) {
                session.setAttribute("ownerEmail", owner.getEmail());
                session.setAttribute("ownerName", owner.getOwnerName());
                return "redirect:/dashboard"; // Redirect Salon Owner to Dashboard
            }
        } 
        else {
//             User Login (Implement User Authentication Later)
        	System.out.println("Real login user");
        	User user = userService.authenticateUser(loginDTO.getEmail(), loginDTO.getPassword());
        	if(user!=null) {
        		session.setAttribute("userEmail", loginDTO.getEmail());
        		return "redirect:/fetchSalon";

        	}
        	

            return "login"; // Redirect User to Fetch Salon Page
        }
        
        model.addAttribute("error", "Invalid email or password!");
        System.out.println("LOGIN USER ELLE");
        return "login"; // Show login page with error
    }
    
    
    
}





















//package com.whyq.controller;
//
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.servlet.ModelAndView;
//
//@Controller
//public class WhyqController {
//
//    @GetMapping("home")
//    public String viewHome(Model model) {
//        return "index"; // This refers to index.html in the templates folder
//    }
//    
//    @GetMapping("/register/salon")
//    public String registerSalon() {
//    	
//    	
//    	return "salonOwnerSignup";
//    }
//
//}

//@PostMapping("/login")
//public String loginSalonOwner(@ModelAttribute LoginDTO loginDTO, HttpSession session, Model model) {
//  SalonOwner owner = salonOwnerService.authenticateOwner(loginDTO);
//
//  if (owner != null) {
//      // Store owner info in session
//      session.setAttribute("ownerEmail", owner.getEmail());
//      session.setAttribute("ownerName", owner.getOwnerName());
//
//      return "redirect:/dashboard"; // Redirect to dashboard
//  } else {
//      model.addAttribute("error", "Invalid email or password!");
//      return "login"; // Reload login page with error message
//  }
//}
