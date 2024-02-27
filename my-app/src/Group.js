
import React, { useEffect,useState } from 'react';
import {useParams,Link} from "react-router-dom";
import axios from "axios";



export default function Group({group,setGroup,signupformsubmitresult,setSignupformsubmitresult,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) {

  useEffect(()=>{setSignupformsubmitresult("");setUnsuccessfulsignin(false)},[]);
    
const{groupId}=useParams();


//const[groupMembers,setGroupMembers]=useState([]);
const[groupComments,setGroupComments]=useState([]);
const fetchGroup=()=>{
    axios.defaults.baseURL="http://localhost:8083";
    axios.get("/groups/getonegroupbyid",{params:{groupId:groupId}}).then((response)=>{setGroup(response.data)});
  }
  const fetchComments=()=>{
    axios.defaults.baseURL="http://localhost:8083";
    axios.get("/comments/getcommentsofagroup",{params:{groupId:groupId}}).then((response)=>{setGroupComments(response.data)});
  }
  // const fetchMembers=()=>{
  //   axios.defaults.baseURL="http://localhost:8083";
  //   return axios.get("/groups/getonegroupmembersbygroupid",{params:{groupId:groupId}}).then((response)=>{setGroupMembers(response.data)});
  // }


    useEffect(()=> {
        fetchGroup();
        fetchComments();
        //fetchMembers();
        //fetchUser();
        // fetch("/groups/getonegroupbyid?groupId="+groupId).then(response=>{return response.json()})
        // .then(data=>{setGroup(data)})
     },[])

     const disdiv={
      display:"flex",
      justifyContent:"center",
      alignItems:"center",
      flexDirection:"column",
      rowGap:"20px"
     }
     const baslik={
      width:"300px",
      textAlign:"center",
      border:"1px solid black",
      fontSize:"2rem"
  }



  return (
    
        <div style={disdiv}>
        <div style={baslik}>{group.name}</div>
        {groupComments.map(comment=>
          <div>
            {comment.quotedComment !=null? 
            <div>
            <div style={{border:"1px solid blue",width:"400px"}}>Quoted Comment Owner: <Link to={"/users/user/"+comment.quotedComment.owner.id}>{comment.quotedComment.owner.name} {comment.quotedComment.owner.surname}</Link></div>
            <div style={{border:"1px solid blue",width:"400px"}}>Quoted Comment Content:{comment.quotedComment.content}</div>
            </div> 
            :<div></div>}
            <div style={{border:"1px solid red",width:"400px"}}>Comment Owner: <Link to={"/users/user/"+comment.owner.id}>{comment.owner.name} {comment.owner.surname}</Link></div>
            <div style={{border:"1px solid red",width:"400px"}}>Comment Content: {comment.content}</div>
          </div>
        
        )}
        
        
        </div>
    
    
  )
}
