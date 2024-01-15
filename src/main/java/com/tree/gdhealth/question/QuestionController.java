package com.tree.gdhealth.question;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.tree.gdhealth.customer.franchisebranch.FranchiseBranchService;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.vo.Branch;
import com.tree.gdhealth.vo.Question;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class QuestionController {
	@Autowired QuestionService questionService;
	@Autowired FranchiseBranchService franchiseBranchService;

	@GetMapping("/question/questionList")
	public String questionList(Model model, Question question, HttpSession session) {
		
		
	    List<Question> list = questionService.questionList();
	    model.addAttribute("list", list);
	
	    
	    
	    LoginEmployee loginEmployee = (LoginEmployee)session.getAttribute("loginEmployee");
	    Integer customerNo = ((Integer)session.getAttribute("customerNo"));
	    
	    if(loginEmployee != null) {
		    int employeeNo = loginEmployee.getEmployeeNo();
		    int branchNo = loginEmployee.getBranchNo();
		    int branchLevel = loginEmployee.getBranchLevel();
		    
		    model.addAttribute("employeeNo", employeeNo);
		    model.addAttribute("branchNo", branchNo);
		    model.addAttribute("branchLevel", branchLevel);
		    
		    
		    System.out.println("employeeNo: " + employeeNo);
		    System.out.println("branchNo: " + branchNo);
		    System.out.println("branchLevel: " + branchLevel);
	    }
	    
	    if(customerNo != null) {
		    model.addAttribute("customerNo", customerNo);
		    System.out.println("customerNo: " + customerNo);
	    }
	    return "/question/questionList";
	}

	@GetMapping("/question/questionOne")
	public String questionOne(HttpSession session, Model model, int questionNo, Question question) {
		
		Question qsOne = questionService.questionOne(questionNo);
		model.addAttribute("qsOne", qsOne);
		return "/question/questionOne";
	}
	
	@PostMapping("/question/questionOne")
	public String questionOne(HttpSession session, int questionNo) {
		System.out.println("questionNo : "+ questionNo);
		
		return "redirect:/question/questionOne?questionNo=" + questionNo;
	}
	
	@GetMapping("/question/addQuestion")
	public String addQuestion(HttpSession session, Model model, Question question) {
		if(session.getAttribute("customerNo")== null) {
			 return "redirect:/customer/login";
		}
		int customerNo = ((Integer)session.getAttribute("customerNo"));
		List<Branch> branchList = franchiseBranchService.branchInfo();
		
		
		model.addAttribute("branchList", branchList);
		model.addAttribute("customerNo", customerNo);
		
		
		
		
		return "/question/addQuestion";
	}
	
	@PostMapping("/question/addQuestion")
	public String addQuestion(HttpSession session, int branchNo, String questionTitle, Question question) {
		if(session.getAttribute("customerNo")== null) {
			 return "redirect:/customer/login";
		}
		System.out.println(branchNo + "<--branchNo");
		System.out.println(questionTitle + "<--questionTitle");

		int row = questionService.addQuestion(question);
		return "redirect:/question/questionList";
	}
	
	@GetMapping("/question/updateQuestion")
	public String updateQuestion(HttpSession session, int questionNo, Model model, Question question) {
		if(session.getAttribute("customerNo")== null) {
			 return "redirect:/customer/login";
		}
		int customerNo = ((Integer)session.getAttribute("customerNo"));
		List<Branch> branchList = franchiseBranchService.branchInfo();
		
		model.addAttribute("customerNo", customerNo);
		model.addAttribute("branchList", branchList);
		model.addAttribute("questionNo", questionNo);
		;
		return "/question/updateQuestion";
	}
	
	@PostMapping("/question/updateQuestion")
	public String updateQuestion(HttpSession session, Model model, int questionNo, int branchNo, Question question) {
		if(session.getAttribute("customerNo")== null) {
			 return "redirect:/customer/login";
		}
		model.addAttribute("branchNo", branchNo);
		int row = questionService.updateQuestion(question);
		return "redirect:/question/questionList";
	}
	
	@GetMapping("/question/deleteQuestion")
	public String deleteQuestion(HttpSession session, Question question) {
		if(session.getAttribute("customerNo")== null) {
			return "redirect:/customer/login";
		}
		int row = questionService.deleteQuestion(question);
		
		return "redirect:/question/questionList";
	}
}