package com.kakao.pay.homework;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomReservationRestControllerTest {
	 @Autowired
	 private MockMvc mvc;
	 
	 @Test
	 public void getAllUsersTest() throws Exception {
		 mvc.perform(MockMvcRequestBuilders.get("/users").accept(MediaType.APPLICATION_JSON))
		 	.andExpect(status().isOk())
            .andExpect(jsonPath("$[0].userId", is(notNullValue())))
        	;
	 }
	 
	 @Test
	 public void getCustomerTest() throws Exception {
		 mvc.perform(MockMvcRequestBuilders.get("/rooms").accept(MediaType.APPLICATION_JSON))
		 	.andExpect(status().isOk())
		 	.andExpect(jsonPath("$[0].roomId", is(notNullValue())))
		 	;
	 }
}
