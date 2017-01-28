package com.mindshine.clevergrid.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/search")
	public String search() {
		return "SearchResult";
	}

	@RequestMapping("/dashboard")
	public String dashboard() {
		return "Dashboard";
	}

	@RequestMapping("/settings")
	public String settings() {
		return "Settings";
	}

	@RequestMapping("/userprofile")
	public String profile() {
		return "Profile";
	}

	@RequestMapping("/help")
	public String help() {
		return "Help";
	}

}
