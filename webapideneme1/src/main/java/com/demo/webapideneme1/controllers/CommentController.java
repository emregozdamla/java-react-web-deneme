package com.demo.webapideneme1.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.webapideneme1.models.Comment;
import com.demo.webapideneme1.models.Group;
import com.demo.webapideneme1.models.User;
import com.demo.webapideneme1.services.CommentService;
import com.demo.webapideneme1.services.GroupService;
import com.demo.webapideneme1.services.UserService;
@CrossOrigin
@RestController
@RequestMapping("/comments")
public class CommentController {
	CommentService commentService;
	UserService userService;
	GroupService groupService;
	
	

//	@GetMapping("/{commentId}")
//	public Comment getOneCommentById(@PathVariable("commentId") long commentId)
//	{
//		Optional<Comment> comment=commentService.getOneCommentById(commentId);
//		if(comment.isPresent())
//		{
//			
//			return comment.get();
//		}else return null;
//	}
	
	public CommentController(CommentService commentService, UserService userService, GroupService groupService) {
		super();
		this.commentService = commentService;
		this.userService = userService;
		this.groupService = groupService;
	}


	@GetMapping("/getonecommentbyid")
	public Comment getOneCommentById(long commentId)
	{
		Optional<Comment> comment=commentService.getOneCommentById(commentId);
		if(comment.isPresent())
		{
			
			return comment.get();
		}else return null;
	}
	
	@GetMapping("/getcommentsofagroup")
	public List<Comment> getCommentsOfAGroup(long groupId)
	{
		List<Comment> comments=commentService.getCommentsOfAGroup(groupId);
		return comments;
	}
	@GetMapping("/getallcomments")
	public List<Comment> getAllComments()
	{
		List<Comment> comments=commentService.getAllComments();
		
		return comments;
	}
	
	@PostMapping("/createcomment")
	public String createComment(Long userId, String content,Long commentIdToBeQuoted,Long groupId)
	{
		Optional<User> user=userService.getOneUserById(userId);
		Optional<Group> group= groupService.getOneGroupById(groupId);
		String result="";
		Optional<Comment> commentToBeQuoted=null;
		if(commentIdToBeQuoted!=null)
		commentToBeQuoted=commentService.getOneCommentById(commentIdToBeQuoted);
		Comment comment;
		
		if(user.isPresent()&&group.isPresent())
		{
			comment=new Comment();
			comment.setOwner(user.get());
			comment.setGroup(group.get());
			comment.setContent(content);
			if(commentToBeQuoted!=null)
			{
				if(commentToBeQuoted.isPresent())
				{
					if(commentToBeQuoted.get().getGroup()==group.get())
					comment.setQuotedComment(commentToBeQuoted.get());
				}else comment.setQuotedComment(null);
			}
			result =commentService.saveComment(comment);
			return result;
			
		} else return "User or group not found";
		
	}
	
	@PutMapping("/updatecomment")
	public String updateComment(Long commentId,String newcontent)
	{
		Optional<Comment> comment=commentService.getOneCommentById(commentId);
		if(comment.isPresent())
		{
			comment.get().setContent(newcontent);
			String result=commentService.saveComment(comment.get());
			
			return "Comment succesfully updated";
		}else return "Comment not found";
	}
	@DeleteMapping("/deletecomment")
	public String deleteComment(long commentId)
	{
		String result=commentService.deleteComment(commentId);
		return result;
	}
	
	

}
