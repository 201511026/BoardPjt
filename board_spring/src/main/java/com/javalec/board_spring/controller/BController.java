package com.javalec.board_spring.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalec.board_spring.command.BCommand;
import com.javalec.board_spring.command.BContentCommand;
import com.javalec.board_spring.command.BDeleteCommand;
import com.javalec.board_spring.command.BListCommand;
import com.javalec.board_spring.command.BModifyCommand;
import com.javalec.board_spring.command.BReplyCommand;
import com.javalec.board_spring.command.BReplyViewCommand;
import com.javalec.board_spring.command.BWriteCommand;
import com.javalec.board_spring.util.Constant;

/*
 * <Controller제작>
 * Dispatcher가 Client로부터 모든 요청을 받은 후 Controller를 찾음
 *  => "com.javalec.myboard_spring"에 있는 @Controller가 붙은 메소드 모두 찾음
 *      @RequestMapping도 찾음
 * */

@Controller //Controller역할 할 수 있게
public class BController {
	
	BCommand command;
	
	public JdbcTemplate template;
	
	@Autowired //빈 끌어올 수 있음
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template; //어디서나 template쓸 수 있도록 Constant클래스에 static으로 선언
	}
	

	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("list()");
		
		command = new BListCommand();
		command.execute(model);//실제로는 BController에서 수행.
		
		return "list";//끝나면 list.jsp로 감
	}
	
	//작성하는 화면
	@RequestMapping("/write_view")
	public String write_view(Model model){
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	//작성되어서 DB에 들어가는 작업
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model){
		//write_view에서 작성한 폼을 받기 위해
		System.out.println("write()");
		
		model.addAttribute("request", request);//model에 request를 넣음
		command = new BWriteCommand();
		command.execute(model);
			
		return "redirect:list";//작성다하고 list페이지로 -> redirect
	}
	
	//작성 다하고 작성한 글의 내용(content)보기
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model){
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		command = new BContentCommand();
		command.execute(model);//content에 맞는 dao...실행
		
		return "content_view";
	}
	
	//수정
	@RequestMapping(method=RequestMethod.POST, value="/modify")
	public String modify(HttpServletRequest request, Model model){
		System.out.println("modify()");
			
		model.addAttribute("request", request);
		command = new BModifyCommand();
		command.execute(model);
			
		return "redirect:list";
	}
	
	//답변보기
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model){
		System.out.println("reply_view()");
				
		model.addAttribute("request", request);
		command = new BReplyViewCommand();
		command.execute(model);
				
		return "reply_view";
	}
	
	//답변작성
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model){
		System.out.println("reply()");
				
		model.addAttribute("request", request);
		command = new BReplyCommand();
		command.execute(model);
				
		return "redirect:list";
	}
	
	//삭제
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model){
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
}
