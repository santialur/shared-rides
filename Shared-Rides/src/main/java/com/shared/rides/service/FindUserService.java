package com.shared.rides.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.Shift;
import com.shared.rides.domain.State;
import com.shared.rides.domain.Stop;
import com.shared.rides.domain.Track;
import com.shared.rides.domain.User;
import com.shared.rides.util.DistanceHaversine;
import com.shared.rides.util.ReadGPXFile;

/*
 Servicio que se encarga de buscar todas los usuarios que pasan por el marker seleccionado
 por el usuario. 
 Si el profile es 1 --> El usuario busca un peaton
 Si el profile es 2 --> El usuario busca un conductor
 shift 1 --> turno mañana
 shift 2 --> turno tarde
 */

@Service
public class FindUserService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAssociationDAO assocDAO;
	@Autowired
	private ServletContext context;
	
	private List<User> userList;
	private double [][] markers;
	private int dist;
	private List<Integer> distanceList;
	
	public String findUsers(long userId, int profile, int shift, String m){
		boolean needRemove;
		float minDistance = 1000;
		distanceList = new ArrayList<Integer>();
		userList = userDAO.listAll();
		/*
		 * Me elimino a mi mismo de la lista para no aparecer
		 */
		for (int i = 0; i < userList.size(); i++){
			if (userList.get(i).getUserId() == userId) userList.remove(i);
		}

		parseMarkers(m);
		//Filtro la lista de acuerdo al perfil y el turno
		filterList(profile, shift);
		//Con la lista filtrada, vemos que usuarios tienen una distancia menor a 10 cuadras = 1000 mts.
	
		if (profile == 1){
			//Si estoy buscando un peatón; quiere decir que soy un conductor y que en la vista marque un
			//track; por ende tengo varios markers y los tengo que comparar con la direccion de cada peaton
			for(int i = 0; i < userList.size() ; i++){
				minDistance = 1000;
				
				List<Stop> stopList = userList.get(i).getPedestrian().getStops();
				
				needRemove = true;
				for (Stop s : stopList){
					for(int j = 0; j < markers.length; j++){
						dist = DistanceHaversine.calculateDistance(markers[j][1], markers[j][0], s.getLat(), s.getLon());
						if (dist < 1000){ 
							needRemove = false;
							if (dist < minDistance) minDistance = dist;
						}
					}
				}
					if (needRemove){ 
						userList.remove(i);
						i--;
					}
					else{
						int distance = Math.round(minDistance/100);
						distanceList.add(distance);
					}
				
			}
		}
		else{
			String pathFile = context.getInitParameter("gpx-upload");
			//Sino estamos buscando un conductor; por ende tenemos que comparar todos tracks de los conductores
			//con el único marker que se encuentra en la matriz "markers" 
			for(int i = 0; i < userList.size(); i++){
				minDistance = 1000;
				List<Track> trackList = userList.get(i).getDriver().getTracks();
				//Por cada track asociado, tengo que buscar todos los puntos de ese track
				for(int j = 0; j < trackList.size(); j++){
					double [][] trackPoints = ReadGPXFile.readFile(trackList.get(j).getPathFile(), pathFile);
					needRemove = true;
					
					for (int k = 0; k < trackPoints.length; k++){
						dist = DistanceHaversine.calculateDistance(markers[0][1], markers[0][0], trackPoints[k][0], trackPoints[k][1]);
						if (dist < 1000){ 
							needRemove = false;
							if (dist < minDistance) minDistance = dist;
						}
					}
					if (needRemove){
						trackList.remove(j);
						j--;
					}
				}
				//Si no hay ningun track que pase cerca elimino el usuario de la lista
				if(trackList.isEmpty()){ 
					userList.remove(i);
					i--;
				}
				else{
					int distance = Math.round(minDistance/100);
					distanceList.add(distance);
				}
			}
		}
	
		return createJson(userId, profile).toString();
	}

	
	public String defaultFindUser(long userId, int profile, int shift){
		boolean needRemove = true;
		float minDistance = 1000;
		distanceList = new ArrayList<Integer>();
		userList = userDAO.listAll();
		
		User u = userDAO.load(userId);
		/*
		 * Me elimino a mi mismo de la lista para no aparecer
		 */
		for (int i = 0; i < userList.size(); i++){
			if (userList.get(i).getUserId() == userId) userList.remove(i);
		}
		//Filtro la lista de acuerdo al perfil y el turno
		filterList(profile, shift);
		
		String pathFile = context.getInitParameter("gpx-upload");
		
		//Con la lista filtrada, vemos que usuarios tienen una distancia menor a 10 cuadras = 1000 mts.		
		if (profile == 1){
			//Aca obtengo toda la lista de tracks del usuario logueado
			//Si estoy buscando un peatón; quiere decir que soy un conductor
			for(int i = 0; i < userList.size() ; i++){
				minDistance = 1000;
				
				List<Stop> stopList = userList.get(i).getPedestrian().getStops();
				
				List<Track> trackList = new ArrayList<Track>();
				for(Track t : u.getDriver().getTracks()){
					trackList.add(t);
				}	
				
				//Por cada track asociado, tengo que buscar todos los puntos de ese track
				for(int j = 0; j < trackList.size(); j++){
					needRemove = true;
					for(Stop s : stopList){
						if(s.getDay() == trackList.get(j).getDay() && s.getInout() == trackList.get(j).getInout()){
							double [][] trackPoints = ReadGPXFile.readFile(trackList.get(j).getPathFile(), pathFile);	
							for (int k = 0; k < trackPoints.length; k++){						
								dist = DistanceHaversine.calculateDistance(s.getLat(), s.getLon(), trackPoints[k][0], trackPoints[k][1]);
								if (dist < 1000){ 
									needRemove = false;
									if (dist < minDistance) minDistance = dist;
								}	
							}
							break;
						}
					}
					if (needRemove){
						trackList.remove(j);
						j--;
					}
				}
				if (trackList.isEmpty()){ 
					userList.remove(i);
					i--;
				}
				else{
					int distance = Math.round(minDistance/100);
					distanceList.add(distance);
				}
			}
		}
		else{
			List<Stop> stopList = u.getPedestrian().getStops();
			//Sino estamos buscando un conductor; por ende tenemos que comparar todos tracks de los conductores
			//con el único marker que se encuentra en la matriz "markers" 
			for(int i = 0; i < userList.size(); i++){
				minDistance = 1000;
				List<Track> trackList = userList.get(i).getDriver().getTracks();
				//Obtengo los stops del usuario logueado
				List<Stop> auxStopList = stopList;
				
				//Por cada track asociado, tengo que buscar todos los puntos de ese track
				for(int j = 0; j < trackList.size(); j++){
					needRemove = true;
					for(Stop s : auxStopList){
						if(s.getDay() == trackList.get(j).getDay() && s.getInout() == trackList.get(j).getInout()){
							double [][] trackPoints = ReadGPXFile.readFile(trackList.get(j).getPathFile(), pathFile);	
							for (int k = 0; k < trackPoints.length; k++){						
								dist = DistanceHaversine.calculateDistance(s.getLat(), s.getLon(), trackPoints[k][0], trackPoints[k][1]);
								if (dist < 1000){ 
									needRemove = false;
									if (dist < minDistance) minDistance = dist;
								}	
							}
							break;
						}
					}
					if (needRemove){
						trackList.remove(j);
						j--;
					}
				}
				//Si no hay ningun track que pase cerca elimino el usuario de la lista
				if(trackList.isEmpty()){ 
					userList.remove(i);
					i--;
				}
				else{
					int distance = Math.round(minDistance/100);
					distanceList.add(distance);
				}
			}
		}
		return createJson(userId, profile).toString();
	}
	
	
	//Funcion que filtra la lista de usuarios dependiendo el perfil y el turno
	private void filterList(int profile, int shift){
		boolean isDelete = false;
		//Peaton y turno mañana
		if (profile == 1 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getPedestrian() == null || !(u.getShift().equals(Shift.MORNING))){
					userList.remove(i);
					isDelete = true;
				}
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}		
		//Peaton y turno tarde
		if (profile == 1 && shift == 2){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getPedestrian() == null || !(u.getShift().equals(Shift.AFTERNOON))){
					userList.remove(i);
					isDelete = true;
				}
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}		
		//Conductor y turno mañana
		if (profile == 2 && shift == 1){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getDriver() == null || !(u.getShift().equals(Shift.MORNING))){
					userList.remove(i);
					isDelete = true;				
				}			
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}
		//Conductor y turno tarde
		if (profile == 2 && shift == 2){
			for(int i = 0; i < userList.size(); i++){
				User u = userList.get(i);
				if(u.getDriver() == null || !(u.getShift().equals(Shift.AFTERNOON))){
					userList.remove(i);
					isDelete = true;
				}
				if(isDelete){ 
					i--;
					isDelete = false;
				}
			}
		}
	}
	
	//Funcion que me convierte la lista de usuarios en un JSONArray
	private JsonArray createJson(long userId, int profile){
		User u = userDAO.load(userId);
		boolean isAssociation;
		
		JsonArray jsonList = new JsonArray();
		
		//Ordenamos las listas antes de agregarlos al json
		BubbleSortList();
		
		for (int i = 0; i < userList.size(); i++){
			isAssociation = false;
			JsonObject jsonUser = new JsonObject();
			
			jsonUser.addProperty("id", userList.get(i).getUserId());;
			jsonUser.addProperty("name", userList.get(i).getName());;
			jsonUser.addProperty("surname", userList.get(i).getSurname());
			jsonUser.addProperty("distance", distanceList.get(i));
			
			//Agregamos imagen de acuerdo si esta o no asociado
			for (Association assoc : u.getAssociations()){
				if (assoc.getApplicantID().getUserId() == userList.get(i).getUserId() && assoc.getState().equals(State.ACCEPTED)){
					isAssociation = true;
					break;
				}
			}
			if (!isAssociation){
				List<Association> myRequestsList = userDAO.getMyRequests(u);
				for (int j = 0; j < myRequestsList.size(); j++){
					//Obtengo el id del usuario al cual le envie la peticion
					long supplierId = assocDAO.getSupplierId(myRequestsList.get(j));
				
					if (userList.get(i).getUserId() == supplierId && myRequestsList.get(j).getState().equals(State.ACCEPTED)){
						isAssociation = true;
						break;
					}
				}
			}
			if (isAssociation) jsonUser.addProperty("picture", userList.get(i).getPicture());
			else jsonUser.addProperty("picture", "user.png");
			
			//Vemos el tipo de perfil para saber que rating tomar de esa persona
			if(profile == 1){
				jsonUser.addProperty("rating", userList.get(i).getPedestrian().getRating());
			}
			else{
				jsonUser.addProperty("rating", userList.get(i).getDriver().getRating());
			}
	
			jsonList.add(jsonUser);
		}		
		
		return jsonList;
	}	
	
	//Función que se encarga de convertir el String de los markers recibido en la vista a una matriz
	private void parseMarkers(String m){
		JsonParser parser = new JsonParser();
		Object obj = parser.parse(m);		
		JsonArray arrayMarkers = (JsonArray) obj;
		markers = new double [arrayMarkers.size()][2];
		
		for (int i = 0; i < arrayMarkers.size(); i++){
			JsonObject jsonMarker = (JsonObject) arrayMarkers.get(i);
			markers[i][0] = jsonMarker.get("lon").getAsFloat();
			markers[i][1] = jsonMarker.get("lat").getAsFloat();
		}
	}
	
	public String validateDefaultFind(int typeUser, User u){
		
		JsonObject json = new JsonObject();
		boolean validate = true;

		if (typeUser == 1 && u.getDriver() == null) validate = false;
		if (typeUser == 2 && u.getPedestrian() == null) validate = false;

		json.addProperty("validate", validate);
		
		return json.toString();
	}
	
	private void BubbleSortList(){
		int i, j, aux;
		User auxUser;
		
		for(i = 0; i < distanceList.size(); i++)
			for (j = 0; j < distanceList.size() - i - 1; j++){
				if (distanceList.get(j+1) < distanceList.get(j)){
                    aux = distanceList.get(j);
                    distanceList.set(j,distanceList.get(j+1));
                    distanceList.set(j+1, aux);
                    
                    auxUser = userList.get(j);
                    userList.set(j, userList.get(j+1));
                    userList.set(j+1, auxUser);
                }
			}		
	}
	
}

	