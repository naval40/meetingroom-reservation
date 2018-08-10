package com.kakao.pay.homework;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.kakao.pay.homework.model.InitStaticValues;
import com.kakao.pay.homework.model.Reservation;
import com.kakao.pay.homework.model.ReservationRepository;
import com.kakao.pay.homework.model.Room;
import com.kakao.pay.homework.model.RoomRepository;
import com.kakao.pay.homework.model.User;
import com.kakao.pay.homework.model.UserRepository;

@SpringBootApplication
public class MeetingroomReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(MeetingroomReservationApplication.class, args);
	}
	@Bean
	public CommandLineRunner init(UserRepository userRepo, RoomRepository roomRepo, ReservationRepository reservationRepo) {
		return (args) -> {
			InitStaticValues.USER_POOL.forEach(
				userStr -> userRepo.save(User.of(userStr))	
			);
			InitStaticValues.ROOM_POOL.forEach(
				roomName -> roomRepo.save(Room.of(roomName))
			);
			//insert 1 reservation
			LocalTime startTime = LocalTime.now().withMinute(0).withSecond(0);
			LocalTime endTime = LocalTime.now().plusHours(2).withMinute(30).withSecond(0);
			reservationRepo.save(
				Reservation.of("my-reservation", LocalDate.now(), startTime, endTime, 1L, 1L)
			);
			
		};
	}

}
