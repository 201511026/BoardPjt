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
 * <Controller����>
 * Dispatcher�� Client�κ��� ��� ��û�� ���� �� Controller�� ã��
 *  => "com.javalec.myboard_spring"�� �ִ� @Controller�� ���� �޼ҵ� ��� ã��
 *      @RequestMapping�� ã��
 * */

@Controller //Controller���� �� �� �ְ�
public class BController {
	
	BCommand command;
	
	public JdbcTemplate template;
	
	@Autowired //�� ����� �� ����
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
		Constant.template = this.template; //��𼭳� template�� �� �ֵ��� ConstantŬ������ static���� ����
	}
	

	@RequestMapping("/list")
	public String list(Model model){
		System.out.println("list()");
		
		command = new BListCommand();
		command.execute(model);//�����δ� BController���� ����.
		
		return "list";//������ list.jsp�� ��
	}
	
	//�ۼ��ϴ� ȭ��
	@RequestMapping("/write_view")
	public String write_view(Model model){
		System.out.println("write_view()");
		
		return "write_view";
	}
	
	//�ۼ��Ǿ DB�� ���� �۾�
	@RequestMapping("/write")
	public String write(HttpServletRequest request, Model model){
		//write_view���� �ۼ��� ���� �ޱ� ����
		System.out.println("write()");
		
		model.addAttribute("request", request);//model�� request�� ����
		command = new BWriteCommand();
		command.execute(model);
			
		return "redirect:list";//�ۼ����ϰ� list�������� -> redirect
	}
	
	//�ۼ� ���ϰ� �ۼ��� ���� ����(content)����
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest request, Model model){
		System.out.println("content_view()");
		
		model.addAttribute("request", request);
		command = new BContentCommand();
		command.execute(model);//content�� �´� dao...����
		
		return "content_view";
	}
	
	//����
	@RequestMapping(method=RequestMethod.POST, value="/modify")
	public String modify(HttpServletRequest request, Model model){
		System.out.println("modify()");
			
		model.addAttribute("request", request);
		command = new BModifyCommand();
		command.execute(model);
			
		return "redirect:list";
	}
	
	//�亯����
	@RequestMapping("/reply_view")
	public String reply_view(HttpServletRequest request, Model model){
		System.out.println("reply_view()");
				
		model.addAttribute("request", request);
		command = new BReplyViewCommand();
		command.execute(model);
				
		return "reply_view";
	}
	
	//�亯�ۼ�
	@RequestMapping("/reply")
	public String reply(HttpServletRequest request, Model model){
		System.out.println("reply()");
				
		model.addAttribute("request", request);
		command = new BReplyCommand();
		command.execute(model);
				
		return "redirect:list";
	}
	
	//����
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, Model model){
		System.out.println("delete()");
		
		model.addAttribute("request", request);
		command = new BDeleteCommand();
		command.execute(model);
		
		return "redirect:list";
	}
}
