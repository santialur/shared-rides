package com.shared.rides.controller;

import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shared.rides.domain.User;
import com.shared.rides.service.ProfileService;

/*
 * Controlador que se encarga de recibir el id del perfil a buscar y luego llama
 * al servicio para obtener el json. Dependiendo si el usuario quiere ver su propio
 * perfil o no; se le pasa el 3° parametro como true o false.
 */

@Controller
public class ProfileController {
	
		@Autowired
		private ProfileService showProfileService;
	
		@RequestMapping(value = "/rate.do")
		public ModelAndView showRating(){
			return new ModelAndView ("rate");
		}
		
		@RequestMapping(value = "/profile.do")
		public ModelAndView showProfile(@RequestParam("user") long id, 
										HttpServletRequest request){
			HttpSession s = request.getSession();
			User u = (User)s.getAttribute("user");
			ModelAndView model = new ModelAndView();
			
			if (id == u.getUserId()){
				model = showProfileService.getProfile(id, request, true);
				model.setViewName("profile");
				return model;
			}
			
			model = showProfileService.getProfile(id, request, false);
			model.setViewName("profile");
			
			return model;
		}
		
		@RequestMapping(value = "/myProfile.do")
		public ModelAndView showMyProfile(HttpServletRequest request){
			HttpSession s = request.getSession();
			User u = (User)s.getAttribute("user");
			ModelAndView model = new ModelAndView();
			long id = u.getUserId();
			
			model = showProfileService.getProfile(id, request, true);
			model.setViewName("profile");
			
			return model;
		}
		
		//Metodo que se encarga de actualizar el rating de un usuario cuando ha sido puntuado por otro
		@RequestMapping(value = "/updateRating.do", method = RequestMethod.POST)
		public @ResponseBody String updateRating(@RequestParam("userId") long userId,
												@RequestParam("prof") int profile,
												@RequestParam("rating") int rating,
												HttpServletRequest request){
			
			User u = (User) request.getSession().getAttribute("user");
			if (showProfileService.calculateRating(u.getUserId(), userId, profile, rating))
				return "Se hizo la puntuacion";
			return "NO SE PUDO HACER LA PUNTUACION";
		}
		
}
