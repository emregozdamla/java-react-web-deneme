import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";


export default function Groups({groups,setGroups,signupformsubmitresult,setSignupformsubmitresult,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) {

  useEffect(()=>{setSignupformsubmitresult("");setUnsuccessfulsignin(false)},[]);
    

  const fetchInfo=()=>{
    axios.defaults.baseURL="http://localhost:8083";
    axios.get("/groups/getallgroups").then((response)=>setGroups(response.data));
  }

    useEffect(()=> {fetchInfo();},[])

    
  return (
    <div>
        
         {groups.map(group=>
         <div>
         <Link to={"/groups/group/"+group.id} >{group.name}</Link>
        </div>
    )} 
    </div>
    
  )
}
