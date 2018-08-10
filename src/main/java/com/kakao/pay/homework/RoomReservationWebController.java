package com.kakao.pay.homework;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kakao.pay.homework.model.RoomRepository;
import com.kakao.pay.homework.model.UserRepository;

@Controller
public class RoomReservationWebController {

	@Autowired
	RoomRepository roomRepository;
	
	@Autowired
	ReservationService reservationService;
	
	@Autowired
	UserRepository userRepository;
	

	@GetMapping("/list")
    public String list(@RequestParam(name="date", required=false) String dateStr, Model model) {
    	LocalDate date = ReservationDateTimeUtils.getLocalDateFromStr(dateStr);
        model.addAttribute("date", date);
		model.addAttribute("rooms", roomRepository.findAll());
        model.addAttribute("reservations", reservationService.getGroupedReservation(date));
        

        return "list";
    }
	
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("date", LocalDate.now());
		model.addAttribute("users", userRepository.findAll());
		model.addAttribute("rooms", roomRepository.findAll());
		return "add";
	}

	@GetMapping("/")
	public String index() {
		return "index";
	}


}
