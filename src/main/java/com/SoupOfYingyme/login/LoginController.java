package com.SoupOfYingyme.login;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	public ModelAndView register(
			@ModelAttribute("accountModel") Account account,
			ModelAndView mav
			) {
		mav.setViewName("register");
		return mav;
	}
	
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@Transactional(readOnly=false)
	public ModelAndView registerform(
			@ModelAttribute("accountModel") @Validated Account account,
			BindingResult result,
			ModelAndView mav) {
		ModelAndView res = null;
		if(!result.hasErrors()) {
			if(accountRepository.findByUsername(account.getUsername()) == null) {
				account.setPassword(passwordEncoder.encode(account.getPassword()));
				accountRepository.saveAndFlush(account);
				res = new ModelAndView("redirect:/login");
			} else {
				mav.setViewName("register");
				mav.addObject("msg", "このユーザー名は登録されています");
				res = mav;
			}
		} else {
			mav.setViewName("register");
			res = mav;
		}
		
		return res;
	}
	
	@RequestMapping(value="/find", method = RequestMethod.GET)
	public ModelAndView find(ModelAndView mav) {
		mav.setViewName("find");
		mav.addObject("value", "");
		Iterable<Account> list = accountRepository.findAll();
		mav.addObject("userlist", list);
		return mav;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public ModelAndView search(HttpServletRequest request,
			ModelAndView mav) {
		String param = request.getParameter("fstr");
		Account account = accountRepository.findByUsername(param);
		String username = account.getUsername();
		mav.setViewName("find");
		mav.addObject("value",username);
		return mav;
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostConstruct
	public void init() {
		Account a1 = new Account();
		a1.setUsername("user");
		a1.setPassword(passwordEncoder.encode("password"));
		accountRepository.saveAndFlush(a1);
	}
}
