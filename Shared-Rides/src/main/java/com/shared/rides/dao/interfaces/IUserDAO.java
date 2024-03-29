package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Association;
import com.shared.rides.domain.Driver;
import com.shared.rides.domain.Pedestrian;
import com.shared.rides.domain.User;

public interface IUserDAO {
	
	public boolean save(User user);
	public User load(long id);
	public User update(User user);
	public User delete(User user);
	public List<User> listAll();
	public User loadByEmail(User user);
	public void newAssoc(User user, Association assoc);
	public List<Association> getMyRequests(User u);
	public List<Long> getAllSchedule(User u);
	public User getLastUser();
	public void newPed (User u, Pedestrian ped);
	public void newDriver (User u, Driver driver);
}
