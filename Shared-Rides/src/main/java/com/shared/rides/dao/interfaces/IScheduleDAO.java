package com.shared.rides.dao.interfaces;

import java.util.List;

import com.shared.rides.domain.Schedule;

public interface IScheduleDAO {

	public boolean save(Schedule sch);
	public Schedule load(long id);
	public Schedule update(Schedule sch);
	public Schedule delete(Schedule sch);
	public List<Schedule> listAll();
	public Schedule getLastSchedule();
}
