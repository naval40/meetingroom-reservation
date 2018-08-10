package com.kakao.pay.homework;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kakao.pay.homework.model.Reservation;
import com.kakao.pay.homework.model.ReservationRepository;
import com.kakao.pay.homework.model.Room;
import com.kakao.pay.homework.model.SimpleResponseJSON;

@Service("reservationService")
public class ReservationService {

	@Autowired
	private ReservationRepository reservationRepository;
	
	public SimpleResponseJSON addReservation(final Reservation input) {
		
		this.validateReservation(input);
		if (!"on".equals(input.getRepeat())) {
			reservationRepository.save(input);
		} else {
			IntStream.range(0, input.getRepeatCount()).forEach(index -> {
				Reservation each = Reservation.of(input.getTitle(), input.getDate().plusWeeks(index), input.getStartTime(), input.getEndTime(), input.getUserId(), input.getRoomId());
				reservationRepository.save(each);
			});
		}
		return SimpleResponseJSON.of("saved successfully!!",200);
		
	}
	
	private void validateReservation(Reservation input) {
		List<Reservation> reservations = reservationRepository.findByDateAndRoomId(input.getDate(), input.getRoomId());
		if (!reservations.isEmpty()) {
			reservations.forEach(reservation -> {
				//sameTime check
				if (input.getStartTime().equals(reservation.getStartTime()) || input.getEndTime().equals(reservation.getEndTime())) {
					throw new IllegalArgumentException("same startTime or endTime from already reserved!");
				}
				//startTime check
				if (input.getStartTime().isAfter(reservation.getStartTime()) && input.getStartTime().isBefore(reservation.getEndTime()) ) {
					throw new IllegalArgumentException("there are reservation in startTime! please change startTime");
				}
				//endTime check
				if (input.getEndTime().isAfter(reservation.getStartTime()) && input.getEndTime().isBefore(reservation.getEndTime()) ) {
					throw new IllegalArgumentException("there are reservation in endTime! please change startTime");
				}
			});
		}
	}

	public Map<Room, List<Reservation>> getGroupedReservation(LocalDate date) {
		List<Reservation> reservations = reservationRepository.findByDate(date);
		Map<Room, List<Reservation>> roomGrouped = reservations.stream()
				.sorted(Comparator.comparing(Reservation::getStartTime))
				.collect(Collectors.groupingBy(Reservation::getRoom, LinkedHashMap::new, Collectors.toList()));
		return roomGrouped;
	}

}
