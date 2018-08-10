package com.kakao.pay.homework;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kakao.pay.homework.model.Reservation;
import com.kakao.pay.homework.model.ReservationRepository;
import com.kakao.pay.homework.model.Room;
import com.kakao.pay.homework.model.RoomRepository;
import com.kakao.pay.homework.model.SimpleResponseJSON;
import com.kakao.pay.homework.model.User;
import com.kakao.pay.homework.model.UserRepository;

@RestController
public class RoomReservationRestController {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ReservationRepository reservationRepository;
	
	@Autowired
	ReservationService reservationService;
	
	@GetMapping("/users")
	public List<User> getAllUsers() {
		Iterable<User> users = userRepository.findAll();
		
		return StreamSupport.stream(users.spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/rooms")
	public List<Room> getAllRooms() {
		Iterable<Room> rooms = roomRepository.findAll();
		
		return StreamSupport.stream(rooms.spliterator(), false)
				.collect(Collectors.toList());
	}

	@GetMapping("/reservations")
	public List<Reservation> getAllReservations() {
		Iterable<Reservation> reservations = reservationRepository.findAll();
		return StreamSupport.stream(reservations.spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@GetMapping("/reservations/{date}")
	public List<Reservation> getReservationByDate(@RequestParam(name="date", required=false) String dateStr) {
		Iterable<Reservation> reservations = reservationRepository.findByDate(ReservationDateTimeUtils.getLocalDateFromStr(dateStr));
		return StreamSupport.stream(reservations.spliterator(), false)
				.collect(Collectors.toList());
	}
	
	@PostMapping(name = "/reservations/add",  consumes = {MediaType.APPLICATION_JSON_VALUE})
	public SimpleResponseJSON add(@RequestBody Reservation input) {
		inputValidation(input);
		return reservationService.addReservation(input);
		
	}

	private void inputValidation(Reservation input) {
		if (StringUtils.isEmpty(input.getTitle())) {
			throw new IllegalArgumentException("please fill title"); 
		}
		
		if (input.getStartTime() == null) {
			throw new IllegalArgumentException("please fill startTime");
		}

		if (input.getEndTime() == null) {
			throw new IllegalArgumentException("please fill endTime field");
		}

		if (input.getStartTime().isAfter(input.getEndTime())) {
			throw new IllegalArgumentException("startTime can't be bigger than endTime"); 
		}
		
	}
	
}
