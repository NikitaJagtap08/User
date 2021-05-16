package com.assignment.User;

import com.assignment.User.entity.User;
import com.assignment.User.exception.UserNotFoundException;
import com.assignment.User.service.UserService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.List;

@SpringBootTest(classes = UserApplication.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserApplicationTests {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	UserService userService;

	@Autowired
	ObjectMapper jsonMapper;

	@Test
	public void addUserTest() throws Exception {
		String URI="/api/users";
		User user=new User("","nikitajagtap228@gmail.com","+91 12345 67890");
		String jsonBody = jsonMapper.writeValueAsString(user);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonBody))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(400,status);
		String contentAsString = mvcResult.getResponse().getContentAsString();
		System.out.println(contentAsString);


		String expectedMessage = "size must be between 5 and 32";
		Assertions.assertTrue(contentAsString.contains(expectedMessage));
	}

	@Test
	@Order(1)
	public void addUserTest1() throws Exception {
		String URI="/api/users";
		User user=new User("Nikita","nikitajagtap228@gmail.com","+91 12345 67890");
		String jsonBody = jsonMapper.writeValueAsString(user);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonBody))
				.andReturn();

		User newUser = userService.addUser(user);
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200,status);
		String contentAsString = mvcResult.getResponse().getContentAsString();
		Assertions.assertTrue(contentAsString.contains("added successfully"));
	}
	@Test
	@Order(2)
	public void addUserTest2() throws Exception {
		String URI="/api/users";
		User user1=new User("riyaansh","riyansh@gmail.com","+91 12345 67890");
		String jsonBody = jsonMapper.writeValueAsString(user1);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.content(jsonBody))
				.andReturn();

		User newUser = userService.addUser(user1);
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200,status);
		String contentAsString = mvcResult.getResponse().getContentAsString();
		Assertions.assertTrue(contentAsString.contains("added successfully"));
	}
	@Test
	@Order(3)
	public void getAllUsersTest() throws Exception {
		String URI="/api/users";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200,status);
		String contentAsString = mvcResult.getResponse().getContentAsString();
		List<User> userList = jsonMapper.readValue(contentAsString, new TypeReference<List<User>>() {
		});
		Assertions.assertTrue(userList.size()>0);
	}
	@Test
	@Order(4)
	public void getUserByIdTest() throws Exception {
		String URI="/api/users/1";

		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(URI)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200,status);
		String contentAsString = mvcResult.getResponse().getContentAsString();
		User user = jsonMapper.readValue(contentAsString, User.class);
		Assertions.assertEquals(1,user.getId());
	}
	@Test
	@Order(5)
	public void UpdateUserTest() throws Exception {
		String URI="/api/users/2";
		User user1=new User("samarth","riyansh@gmail.com","+91 12345 67890");
		String jsonBody = jsonMapper.writeValueAsString(user1);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(URI)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		userService.updateUser(2L, user1);
		int status = mvcResult.getResponse().getStatus();
		Assertions.assertEquals(200,status);


	}
	@Test
	@Order(6)
	public void deleteUserTest() throws Exception {
		String URI="/api/users/3";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(URI)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();

		Exception exception=Assertions.assertThrows(UserNotFoundException.class,() -> {
			userService.deleteUser(3L);
		});
		String expectedMessage = "User not found";
		String actualMessage = exception.getMessage();

		Assertions.assertTrue(actualMessage.contains(expectedMessage));
	}

}
