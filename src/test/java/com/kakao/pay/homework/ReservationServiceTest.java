package com.kakao.pay.homework;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kakao.pay.homework.model.Reservation;
import com.kakao.pay.homework.model.ReservationRepository;


@RunWith(MockitoJUnitRunner.class)
public class ReservationServiceTest {

	@Mock
	ReservationRepository mockRepo;

	@InjectMocks
	ReservationService mock;
	
	
	LocalDate now = LocalDate.now();
	List<Reservation> alreadyReserved = new ArrayList<>();
	
	@Before
    public void setUp() {
		alreadyReserved.add(Reservation.of("aaa", now, LocalTime.now().minusSeconds(2), LocalTime.now().plusSeconds(2), 1L, 1L));
    }
	
	@Test(expected = RuntimeException.class)
	public void unValidStartTime() {
		//when
		when(mockRepo.findByDateAndRoomId(now, 1L)).thenReturn(alreadyReserved);
		
		//where
		Reservation input = Reservation.of("bbb", now, LocalTime.now().minusSeconds(1), LocalTime.now().plusSeconds(10), 1L, 1L);
		
		//then
		mock.addReservation(input);
	}

	@Test(expected = RuntimeException.class)
	public void unValidEndTime() {
		//when
		when(mockRepo.findByDateAndRoomId(now, 1L)).thenReturn(alreadyReserved);
		
		//where
		Reservation input = Reservation.of("bbb", now, LocalTime.now().minusSeconds(10), LocalTime.now().plusSeconds(1), 1L, 1L);
		
		//then
		mock.addReservation(input);
	}

	@Test(expected = RuntimeException.class)
	public void sameTimeSouldNotReserved() {
		//when
		when(mockRepo.findByDateAndRoomId(now, 1L)).thenReturn(alreadyReserved);
		
		//where
		Reservation input = Reservation.of("bbb", now, alreadyReserved.get(0).getStartTime(), alreadyReserved.get(0).getEndTime(), 1L, 1L);
		
		//then
		mock.addReservation(input);
		
	}
}
