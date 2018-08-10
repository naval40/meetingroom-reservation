package com.kakao.pay.homework.model;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Long> {
	//find by date
	List<Reservation> findByDate(LocalDate date);
	
	//find by date & room
	List<Reservation> findByDateAndRoomId(LocalDate date, Long roomId);
}
