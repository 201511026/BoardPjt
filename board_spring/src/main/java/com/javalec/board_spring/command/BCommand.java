package com.javalec.board_spring.command;

import org.springframework.ui.Model;

public interface BCommand {

	public void execute(Model model);
}
