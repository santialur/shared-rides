package com.shared.rides.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.shared.rides.dao.interfaces.IAssociationDAO;
import com.shared.rides.dao.interfaces.IUserDAO;
import com.shared.rides.domain.Association;
import com.shared.rides.domain.State;
import com.shared.rides.domain.User;

/*
 * Este servicio se encarga de devolver la lista de personas asociadas y pendientes de recibir respuesta
 * de un usuario en particular.
 * El metodo getPeople retorna un json formado por dos jsonArray; uno formado por las personas ya asociadas,
 * donde se muestra el nombre de la persona y la foto de perfil (ya que ya estan asociados y no es mas un
 * dato privado); y otra lista donde se encuentran todas las personas pendientes de responder una solicitud
 * enviada por el usuario o aquellas personas que le han enviado una solicitud a la persona logueada y debe
 * responder; en este caso, se envia el nombre de la persona, sin la foto de perfil y tambien si el usuario
 * es el suplicante o el aplicante de la asociacion.
 */

@Service
public class PeopleService {

	@Autowired
	private IUserDAO userDAO;
	@Autowired
	private IAssociationDAO assocDAO;
	
	public String getPeople(long userId){
		User u = userDAO.load(userId);
		
		List <Association> applicantAssocList = u.getAssociations();
		List <Association> supplierAssocList = userDAO.getMyRequests(u);	
		List <Long> acceptedIdList = new ArrayList<Long>();
		List <Long> pendingIdList = new ArrayList<Long>();
		JsonArray jsonList = new JsonArray();
		JsonArray pendingList = new JsonArray();
		JsonArray acceptedList = new JsonArray();
		
		//Aca estoy viendo directamente las asociaciones que yo recibi que pude o no haber aceptado
		for(int i = 0; i < applicantAssocList.size(); i++){
			Association assoc = applicantAssocList.get(i);
			User applier = assoc.getApplicantID();
			if (assoc.getState().equals(State.PENDING) && pendingIdList.contains(applier.getUserId())==false){
				completeList(applier, pendingIdList, pendingList, true, isAssoc(u, applier.getUserId()));
			}
			if (assoc.getState().equals(State.ACCEPTED) && acceptedIdList.contains(applier.getUserId())==false){
				completeList(applier, acceptedIdList, acceptedList, false, true);
			}
		}

		//Aca estoy buscando aquellas asociaciones que yo he enviado a otras personas
		for (int j = 0; j < supplierAssocList.size(); j++){
			Association assoc = supplierAssocList.get(j);
			long idSupplier = assocDAO.getSupplierId(assoc);
			
			User supplier = userDAO.load(idSupplier);
			
			if (assoc.getState().equals(State.ACCEPTED) && acceptedIdList.contains(supplier.getUserId())==false){
				completeList(supplier, acceptedIdList, acceptedList, false, true);
			}
		}
		jsonList.add(pendingList);
		jsonList.add(acceptedList);		
		return jsonList.toString();
		
	}
	
	private void completeList(User u, List<Long> idList, JsonArray list, boolean pending, boolean isAssoc){
		String fullNameSupplier = u.getName() + " " + u.getSurname();
		String pictureSupplier = u.getPicture();
		
		JsonObject jsonUser = new JsonObject();
		jsonUser.addProperty("userId", u.getUserId());
		jsonUser.addProperty("name", fullNameSupplier);
		
		if (pending){
			if(isAssoc){
				jsonUser.addProperty("pic", pictureSupplier);				
			}
			else{
				jsonUser.addProperty("pic", "user.png");	
			}
		}
		else jsonUser.addProperty("pic", pictureSupplier);
		
		idList.add(u.getUserId());
		list.add(jsonUser);
	}
	
	private boolean isAssoc(User u, Long userId){
		List <Association> applicantAssocList = u.getAssociations();
		List <Association> supplierAssocList = userDAO.getMyRequests(u);
		boolean isAssociation = false;
		
		for (Association assoc : applicantAssocList){
			if (assoc.getApplicantID().getUserId() == userId && assoc.getState().equals(State.ACCEPTED)){
				isAssociation = true;
				break;
			}
		}
		if (!isAssociation){
			for (int j = 0; j < supplierAssocList.size(); j++){
				//Obtengo el id del usuario al cual le envie la peticion
				long supplierId = assocDAO.getSupplierId(supplierAssocList.get(j));
				if (userId == supplierId && supplierAssocList.get(j).getState().equals(State.ACCEPTED)){
					isAssociation = true;
					break;
				}
			}
		}		
		return isAssociation;
	}
	
}
