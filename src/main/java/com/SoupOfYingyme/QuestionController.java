package com.SoupOfYingyme;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.SoupOfYingyme.login.Account;
import com.SoupOfYingyme.login.AccountRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class QuestionController {

	@Autowired
	QuestionDataRepository repository;

	@Autowired
	private QuestionDataService service;
	
	@Autowired
	AccountRepository accountRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView top(ModelAndView mav) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			mav.setViewName("login_top");
			List<QuestionData> list = service.getAllIdDesk();
			mav.addObject("questionlist", list);			
			return mav;
		} else {
			mav.setViewName("top");
			List<QuestionData> list = service.getAllIdDesk();
			mav.addObject("questionlist", list);
			return mav;
		}
	}

	@RequestMapping(value = "/goodvertop", method = RequestMethod.GET)
	public ModelAndView goodVerTop(ModelAndView mav) {
		mav.setViewName("goodVerTop");
		List<QuestionData> list = service.getAllGoodDesk();
		mav.addObject("questionlist", list);
		return mav;
	}

	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ModelAndView index(@ModelAttribute("formModel") QuestionData questiondata, ModelAndView mav) {
		mav.setViewName("post");
		return mav;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	@Transactional(readOnly = false)
	public ModelAndView post(
			@ModelAttribute("formModel") @Validated QuestionData questiondata, 
			BindingResult result,
			ModelAndView mav) {
		ModelAndView res = null;
		if (!result.hasErrors()) {
			repository.saveAndFlush(questiondata);
			res = new ModelAndView("redirect:/");
		} else {
			mav.setViewName("post");
			mav.addObject("msg", "値が不適切です");
			res = mav;
		}
		return res;
	}

	

	
	@GetMapping("/addGood")
	@ResponseBody
	public String test(@RequestParam("id") String id) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(principal instanceof UserDetails) {
			User user = (User)principal;
			Account account = accountRepository.findByUsername(user.getUsername());
			
			Optional<QuestionData> questionData = repository.findById(Long.valueOf(id));
			if (questionData == null) {
				return null;
			} 
			QuestionData question = questionData.get();
			
			
			if(!question.getGood_account().contains(account)) {
				
				
			
			
				int goodPoints = questionData.get().getGood();
				goodPoints++;
				questionData.get().setGood(goodPoints);
			
			
				Set<Account> good_account = questionData.get().getGood_account();
				good_account.add(account);
				question.setGood_account(good_account);
			
				repository.saveAndFlush(questionData.get());
			
				questionData.get().setQuestion(encode(question.getQuestion()));
				questionData.get().setAnswer(encode(question.getAnswer()));
				return getJson(question);
			} else {
				return " ";
			}
		} else {
			return "";
		}
	}

	private String encode(String data) {
		if (StringUtils.isEmpty(data)) {
			return data;
		}
		String retVal = null;
		try {
			retVal = URLEncoder.encode(data, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.err.println(e);
		}
		return retVal;
	}

	private String getJson(QuestionData questionData) {
		String retVal = null;
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			retVal = objectMapper.writeValueAsString(questionData);
		} catch (JsonProcessingException e) {
			System.err.println(e);
		}
		return retVal;
	}

	@PostConstruct
	public void init() {
		QuestionData q1 = new QuestionData();
		q1.setQuestion("ある男が、とある海の見えるレストランで「ウミガメのスープ」を注文した。\r\n"
				+ "スープを一口飲んだ男は、それが本物の「ウミガメのスープ」であることを確認し、勘定を済ませて帰宅した後、自殺した。一体、なぜ？");
		q1.setAnswer(
				"男はかつて数人の仲間と海で遭難し、とある島に漂着した。食料はなく、仲間たちは生き延びるために力尽きて死んだ者の肉を食べ始めたが、男はかたくなに拒否していた。見かねた仲間の一人が、「これはウミガメのスープだから」と嘘をつき、男に人肉のスープを飲ませ、救助が来るまで生き延びさせた。男はレストランで飲んだ「本物のウミガメのスープ」とかつて自分が飲んだスープの味が違うことから真相を悟り、絶望のあまり自ら命を絶った。");
		repository.saveAndFlush(q1);

		QuestionData q2 = new QuestionData();
		q2.setQuestion("ある男が銀行の前に車を停め、銀行の中に走りこんだ。男は25人を動けなくし、200ドルを持って銀行を飛び出した。"
				+ "一部始終を見ていた警官が男を呼び止め、「そんなことをしてはいかん」と叱咤したが、警官はすぐに男を解放した。なぜだろう？");
		q2.setAnswer("男は、200ドルをおろすために銀行の前に車を停めた。そのせいで渋滞になり、うしろの25人のドライバーが動けなくなってしまったため、警官に怒られたのだ。");
		repository.saveAndFlush(q2);

		QuestionData q3 = new QuestionData();
		q3.setQuestion(
				"アントニーとクレオパトラが、エジプトの屋敷の床で息絶えていた。屍のそばには、割れた金魚鉢。彼らの体に傷はなく、毒を飲んだ形跡もない。死亡時、屋敷には誰も居なかった。アントニーとクレオパトラはどうやって死んだのだろうか？");
		q3.setAnswer("アントニーとクレオパトラは金魚だった。事故で金魚鉢が割れ、2匹は不運にも床の上で死んでしまったのだ。");
		repository.saveAndFlush(q3);
	
		
	}

	

}
