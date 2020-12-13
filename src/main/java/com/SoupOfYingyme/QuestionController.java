package com.SoupOfYingyme;

import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class QuestionController {
	
	@Autowired
	QuestionDataRepository repository;
	
	@Autowired
	private QuestionDataService service;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView top(
			ModelAndView mav) {
		mav.setViewName("top");
		List<QuestionData> list = service.getAllIdDesk();
		mav.addObject("questionlist", list);
		return mav;
	}
	
	@RequestMapping(value = "/goodvertop", method = RequestMethod.GET)
	public ModelAndView goodVerTop(
			ModelAndView mav) {
		mav.setViewName("goodVerTop");
		List<QuestionData> list = service.getAllGoodDesk();
		mav.addObject("questionlist", list);
		return mav;
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.GET)
	public ModelAndView index(
			@ModelAttribute("formModel") QuestionData questiondata,
			ModelAndView mav) {
		mav.setViewName("post");
		return mav;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public ModelAndView post(
			@ModelAttribute("formModel") QuestionData questiondata,
			ModelAndView mav) {
		repository.saveAndFlush(questiondata);
		mav.setViewName("top");
		List<QuestionData> list = service.getAllIdDesk();
		mav.addObject("questionlist", list);
		return mav;
//	@Transactional(readOnly=false)
//	public ModelAndView form(
//			@ModelAttribute("formModel") QuestionData questiondata,
//			ModelAndView mav) {
//		repository.saveAndFlush(questiondata);
//		return new ModelAndView("redireck:/post");
	}
	
	@RequestMapping(value = "/addgood/{num}")
	@Transactional(readOnly=false)
	public ModelAndView addGood(
			@PathVariable long num,
			ModelAndView mav) {
		service.addGood(num);
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value = "/goodveraddgood/{num}")
	@Transactional(readOnly=false)
	public ModelAndView goodVerAddGood(
			@PathVariable long num,
			ModelAndView mav) {
		service.addGood(num);
		return new ModelAndView("redirect:/goodvertop");
	}
	
	@PostConstruct
	public void init() {
		QuestionData q1 = new QuestionData();
		q1.setQuestion("ある男が、とある海の見えるレストランで「ウミガメのスープ」を注文した。\r\n"
				+ "スープを一口飲んだ男は、それが本物の「ウミガメのスープ」であることを確認し、勘定を済ませて帰宅した後、自殺した。一体、なぜ？");
		q1.setAnswer("男はかつて数人の仲間と海で遭難し、とある島に漂着した。食料はなく、仲間たちは生き延びるために力尽きて死んだ者の肉を食べ始めたが、男はかたくなに拒否していた。見かねた仲間の一人が、「これはウミガメのスープだから」と嘘をつき、男に人肉のスープを飲ませ、救助が来るまで生き延びさせた。男はレストランで飲んだ「本物のウミガメのスープ」とかつて自分が飲んだスープの味が違うことから真相を悟り、絶望のあまり自ら命を絶った。");
		repository.saveAndFlush(q1);
		
		QuestionData q2 = new QuestionData();
		q2.setQuestion("ある男が銀行の前に車を停め、銀行の中に走りこんだ。男は25人を動けなくし、200ドルを持って銀行を飛び出した。"
				+ "一部始終を見ていた警官が男を呼び止め、「そんなことをしてはいかん」と叱咤したが、警官はすぐに男を解放した。なぜだろう？");
		q2.setAnswer("男は、200ドルをおろすために銀行の前に車を停めた。そのせいで渋滞になり、うしろの25人のドライバーが動けなくなってしまったため、警官に怒られたのだ。");
		repository.saveAndFlush(q2);
		
		QuestionData q3 = new QuestionData();
		q3.setQuestion("アントニーとクレオパトラが、エジプトの屋敷の床で息絶えていた。屍のそばには、割れた金魚鉢。彼らの体に傷はなく、毒を飲んだ形跡もない。死亡時、屋敷には誰も居なかった。アントニーとクレオパトラはどうやって死んだのだろうか？");
		q3.setAnswer("アントニーとクレオパトラは金魚だった。事故で金魚鉢が割れ、2匹は不運にも床の上で死んでしまったのだ。");
		repository.saveAndFlush(q3);
	}
	
}
