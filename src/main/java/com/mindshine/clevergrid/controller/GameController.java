package com.mindshine.clevergrid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GameController {

	@RequestMapping("/createnewgame")
	public String cng(){
		return "CreateNewGame";
	}
	
	@RequestMapping("/editgame")
	public String eg(){
		return "EditGame";
	}

	@RequestMapping("/playgame")
	public String pg(){
		return "PlayGame";
	}

	@RequestMapping("/taglist")
	public String tl(){
		return "TagList";
	}
}
