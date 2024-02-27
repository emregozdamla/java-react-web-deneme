package com.demo.webapideneme1.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.models.ViewModel;
import com.demo.webapideneme1.services.UserService;
@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {
	UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/getsearchedusers")
	public List<User> getSearchedUsers(String searchedWords)
	{
		return userService.getSearchedUsers(searchedWords);
	}
	@GetMapping("/getallusers")
	public ViewModel getAllUsers()
	{
		ViewModel vm=new ViewModel();
		List<User> users= userService.getAllUsers();
		if(users==null) vm.setResult(false);
		if(users!=null) 
		{
			vm.setResult(true);
			vm.setUsers(users);
		}
		return vm;
		
	}
	@GetMapping("/getoneuserbyid")
	public User getOneUserById(long userId)
	{
		Optional<User> user=userService.getOneUserById(userId);
		if(user.isPresent())
		{
			return user.get();
			
		}else return null;
		
	}
	@GetMapping("/getoneuserbyusername")
	public User getOneUserByUsername(String username)
	{
		User user=userService.getOneUserByUsername(username);
		return user;
	}
	@PostMapping("/adduser")
	public  String addUser(String name, String surname, String username,  String password, MultipartFile userImage) throws IOException 
	{
		User user=new User();
		user.setName(name);
		user.setSurname(surname);
		user.setUsername(username);
		user.setPassword(password);
		if(userImage!=null&&userImage.getContentType()!=null) 
		{
			if(!userImage.getContentType().equals("image/jpeg")&&!userImage.getContentType().equals("image/png")) 
			return "Image file is not suitable to the format. Please load a jpeg or png file";
			else user.setUserImage(userImage.getBytes());
		}
		
		String result=userService.saveUser(user);
		return result;
		
		
	}
	@PostMapping("/enteruser")
	public User enterUser(String username,String password) 
	{
		User user=userService.getOneUserByUsername(username);
		if(user==null) return null;
		boolean result= userService.enterUser(username,password);
		if(result==true) return user;
		else return null;
	}
	@PostMapping("/exituser")
	public User exitUser()
	{
		return null;
	}
	@PutMapping("/updateuser")
	public String updateUser(long id, String newname,String newsurname, String newusername,MultipartFile newuserimage) throws IOException
	{
		String result=userService.updateUser(id, newname,newsurname, newusername,newuserimage);
		return result;
	}
	@DeleteMapping
	public String deleteUser(long id)
	{
		String result=userService.deleteUser(id);
		return result;
	}
	
	@PutMapping("/changeuserpassword")
	public String changeUserPassword(long userId, String newpassword)
	{
		Optional<User> user=userService.getOneUserById(userId);
		if(!newpassword.matches("^[öüÖÜĞğşŞçÇıİa-zA-Z0-9]{2,20}$")) return "New password is not suitable to the format.";
		if(user.isPresent())
		{
			user.get().setPassword(newpassword);
			userService.saveUser(user.get());
			return "Password change is successful";
		}else return "User not found";
	}
	

}
