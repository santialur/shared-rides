package com.shared.rides.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.domain.User;
import com.shared.rides.service.LoginService;
import com.shared.rides.service.RequestAssociationService;


@Controller
public class LoginController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private LoginService loginService;
	@Autowired
	private RequestAssociationService assocService;
	
	@RequestMapping(value = "login.do")
	public ModelAndView loadLogin(){
		return new ModelAndView("login");
	}
	
	
	@RequestMapping(value = "validate.do", method = RequestMethod.POST)
	public ModelAndView userValidation(@RequestParam("email") String email , @RequestParam("pwd") String pwd, HttpServletRequest request)
            throws ServletException, IOException {
        logger.info("Returning hello view");
        ModelAndView model = new ModelAndView();
        
        if(loginService.validate(email, pwd, request)){
            return new ModelAndView("redirect:/main.do");
        }
        model.addObject("validate", false);	
        model.setViewName("login");
        return model;
    }
	
	@RequestMapping(value="logout.do")
	public ModelAndView logOut(HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("user");
		//Actualizamos la ultima fecha de logueo
		loginService.saveLastLoginDate(u.getUserId());
		request.getSession().invalidate();
		return new ModelAndView("redirect:/");
	}
	
	@RequestMapping(value="updateLoginDate.do")
	public @ResponseBody String updateLoginDate(HttpServletRequest request){
		User u = (User) request.getSession().getAttribute("user");
		//Actualizamos la ultima fecha de logueo cuando apreta la campana
		loginService.saveLastLoginDate(u.getUserId());
		return "Actualizado";
	}
	
}
