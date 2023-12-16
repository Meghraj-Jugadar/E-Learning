package com.elearning.controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.elearning.entity.Course;
import com.elearning.entity.Payment;
import com.elearning.entity.User;
import com.elearning.repository.CourseRepository;
import com.elearning.repository.PaymentRepository;
import com.elearning.repository.UserRepo;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CourseRepository courseRepo;
	
	@Autowired
	private PaymentRepository payRepo;
	

	@ModelAttribute
	public void commonUser(Principal p, Model m,Model mpass) {
		if (p != null) {
			String email = p.getName();
			User user = userRepo.findByEmail(email);
			m.addAttribute("user", user);
		}
	}
	
	@ModelAttribute
	public void course(Model m) {
		List<Course> list=courseRepo.findAll();
		m.addAttribute("course", list);
		System.out.println(list);
	}
	
	@GetMapping("/home")
	public String home() {
		return "UserHome";
	}
	
	
	@GetMapping("/profile")
	public String profile() {
		return "UserProfile";
	}
	
	@GetMapping("/courses")
	public String course() {
		return "UserCourse";
	}
	
	@GetMapping("/update")
	public String update() {
		return "UserUpdate";
	}

	@GetMapping("/test")
	public String test() {
		return "UserTest";
	}
	
	@PostMapping("/payment")
	public String payment(@RequestParam long id,Model model) {
		Optional<Course> courseOptional = courseRepo.findById(id);
		Course course = courseOptional.orElse(null);
		if(course!=null) {
			model.addAttribute("payment", course);
			return "UserPayment";
		}
		else {
			return "redirect/courses";
		}
		
	}
	
	@PostMapping("/pay")
	public String pay(@RequestParam("userId") int userId,
	                   @RequestParam("courseId") long courseId,
	                   Model model) {
	    Optional<Course> courseOptional = courseRepo.findById(courseId);
	    Optional<User> userOptional = userRepo.findById(userId);

	    if (courseOptional.isPresent() && userOptional.isPresent()) {
	        Course course = courseOptional.get();
	        User user = userOptional.get();

	        Payment payment = new Payment();
	        payment.setUser(user);
	        payment.setCourse(course);
	        payment.setPaymentDate(LocalDateTime.now()); // Assuming you want to set the payment date to the current date and time

	        payRepo.save(payment);

	        model.addAttribute("msg", "Payment Successful");
	        return "UserPaymentSuccess"; // Replace with the actual success page name
	    } else {
	        return "redirect:/courses";
	    }
	}

	@GetMapping("/paymentSuccess")
	public String paySuccess() {
		return "UserPaymentSuccess";
	}
	
}
