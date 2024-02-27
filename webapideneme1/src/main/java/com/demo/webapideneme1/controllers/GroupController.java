package com.demo.webapideneme1.controllers;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.services.CommentService;
import com.demo.webapideneme1.services.GroupService;
import com.demo.webapideneme1.services.UserService;
@CrossOrigin
@RestController
@RequestMapping("/groups")
public class GroupController {
	GroupService groupService;
	UserService userService;
	CommentService commentService;
	
	public GroupController(GroupService groupService, UserService userService, CommentService commentService) {
		super();
		this.groupService = groupService;
		this.userService = userService;
		this.commentService = commentService;
	}
	@GetMapping("/getallgroups")
	public List<Group> getAllGroups()
	{
		List<Group>groups=groupService.getAllGroups();
		
		return groups;
		
	}
	@GetMapping("/getgroupsofaowner")
	public List<Group> getGroupsOfAOwner(long userId)
	{
		List<Group> groups=groupService.getGroupsOfAOwner(userId);
		return groups;
	}
	@GetMapping("/getgroupsofamember")
	public List<Group> getGroupsOfAMember(long userId)
	{
		List<Group> groups=groupService.getGroupsOfAMember(userId);
		return groups;
	}
	@GetMapping("/getonegroupbyid")
	public Group getOneGroupById(long groupId)
	{
		Optional<Group> group=groupService.getOneGroupById(groupId);
		return group.get();
	}
	@GetMapping("/getonegroupmembersbygroupid")
	public List<User> getOneGroupMembersByGroupId(long groupId)
	{
		Optional<Group> group=groupService.getOneGroupById(groupId);
		return group.get().getMembers();
	}
	@PostMapping("/creategroup")
	public String createGroup(Long ownerId,String name)
	{
		Optional<User> owner= userService.getOneUserById(ownerId);
		if(!name.matches("^[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{1,20}(\\s[öüÖÜĞğşŞçÇıİ|a-z|A-Z]{1,20}){0,10}$"))
			return "Grup is not suitable to format. Group could not be created";
		List<Group> groups=groupService.getAllGroups();
		for(Group g : groups)
		{
			if(g.getName().equals(name)) return "A group name cannot be the same as the name of another group. Group creation unsuccessful";
		}
		if(owner==null) return "Group cannot be created. Group owner not found";
		if(owner.isPresent())
		{
			Group group=new Group();
			group.setOwner(owner.get());
			if(group.getMembers()!=null)
			{
				group.getMembers().add(owner.get());
				group.setMembers(group.getMembers());
			}else
			{
				List<User> members=new ArrayList<User>();
				members.add(owner.get());
				group.setMembers(members);
			}
			 
			group.setName(name);
			groupService.saveGroup(group);
			return "Group successfully created";
		
		}else return "Group could not be created";
	}
	@PutMapping("/updategroupname")
	public String updateGroupName(long userId,long groupId,String newgroupname)
	{
		User user=userService.getOneUserById(userId).get();
		Group group=groupService.getOneGroupById(groupId).get();
		if(user!=null && group!=null && user==group.getOwner() )
		{
			group.setName(newgroupname);
			groupService.saveGroup(group);
			return "Group name successfully updated";
		}
		return "Group name cannot be updated";
	}
	@DeleteMapping("/deletegroupbyid")
	public String deleteGroupById(long groupId)
	{
		Optional<Group> group=groupService.getOneGroupById(groupId);
		if(group.isPresent())
		{
			groupService.deleteGroupById(group.get());
			return "Group successfully deleted";
		}else return "Group not found";
	}
	@PostMapping("/beamemberofgroup")
	public String beAMemberOfGroup(long groupId,long userId)
	{
		Optional<User> user=userService.getOneUserById(userId);
		Optional<Group> group=groupService.getOneGroupById(groupId);
		if(group.isPresent()&&user.isPresent())
		{
			if(group.get().getOwner()!=user.get()&&!group.get().getMembers().contains(user.get()))
			{
				group.get().getMembers().add(user.get());
				groupService.saveGroup(group.get());
				return "Process is successful";
			}
			else return "User is already a member or owner of the group";
		}else return "Group not found";
	}
	@PostMapping("/exitgroup")
	public String exitGroup(long groupId,long userId)
	{
		Optional<User> user=userService.getOneUserById(userId);
		Optional<Group> group=groupService.getOneGroupById(groupId);
		if(group.isPresent()&&user.isPresent())
		{
			if(group.get().getOwner()!=user.get()&&group.get().getMembers().contains(user.get()))
			{
				group.get().getMembers().remove(user.get());
				groupService.saveGroup(group.get());
				return "Process is successful";
			}
			else return "User is the owner or not a member of the group";
		}else return "Group not found";
	}
	
     
	
	

}
