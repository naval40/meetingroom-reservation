package com.kakao.pay.homework;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.kakao.pay.homework.model.Reservation;
import com.kakao.pay.homework.model.ReservationRepository;
import com.kakao.pay.homework.model.SimpleResponseJSON;


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
	
	@Test
	public void repeatedAddReservation() {
		//when
		when(mockRepo.findByDateAndRoomId(anyObject(), anyLong())).thenReturn(Collections.emptyList());
		
		//where
		Reservation input = Reservation.of("ccc", now, 
				alreadyReserved.get(0).getStartTime(), alreadyReserved.get(0).getEndTime(), 1L, 1L);
		input.setRepeat("on");
		input.setRepeatCount(10);
		
		//then
		SimpleResponseJSON actualResult = mock.addReservation(input);
		assertThat(actualResult.getResponseStatus(), is(200));
		
		//verify
		verify(mockRepo, times(10)).findByDateAndRoomId(anyObject(), anyLong());
		verify(mockRepo, times(10)).save(anyObject());
	}
}
