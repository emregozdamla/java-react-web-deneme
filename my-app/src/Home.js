import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";

export default function Home({signupformsubmitresult,setSignupformsubmitresult,user,setUser,unsuccessfulsignin,setUnsuccessfulsignin}) {
  useEffect(()=>{setSignupformsubmitresult("");setUnsuccessfulsignin(false)},[]);

  return (
    
    <div>
      {
      Object.keys(user).length!==0 ?
      <div> Welcome {user.name} {user.surname}</div>
       : <div>Home</div>
    }
    </div>
    
    
   
  )
}
