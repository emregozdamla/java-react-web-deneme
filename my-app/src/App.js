import React, { useEffect,useState } from 'react';
import {useParams,useNavigate,Link} from "react-router-dom";
import axios from "axios";
import logo from './logo.svg';
import './App.css';
import Navbar from './Navbar';
import { BrowserRouter,Routes,Switch,Route } from 'react-router-dom';
import Home from './Home';
import Groups from './Groups';
import Group from "./Group";
import User from "./User";
import Register from "./Register";
import Login from "./Login";
function App() {

  const[unsuccessfulsignin,setUnsuccessfulsignin]=useState(false);
  const [signupformsubmitresult,setSignupformsubmitresult]=useState("");
  const [user,setUser]=useState({});
  let[groups,setGroups]=useState([]);
  const [group,setGroup]=useState({});
  const [user2,setUser2]=useState({});
  return (
    <div>
    <BrowserRouter>
    <Navbar user={user} setUser={setUser}></Navbar>
    
    <Routes>

    <Route exact path="/" element={<Home 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </Home>}></Route>

    <Route exact path="/groups" element={<Groups 
    groups={groups} setGroups={setGroups} 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </Groups>}></Route>

    <Route exact path="/groups/group/:groupId" element={<Group 
    group={group} setGroup={setGroup} 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </Group>}></Route>

    <Route exact path="/users/user/:user2Id" element={<User 
    user2={user2} setUser2={setUser2} 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult}>
    </User>}></Route>

    <Route exact path="/register" element={<Register 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} 
    signupformsubmitresult={signupformsubmitresult} setSignupformsubmitresult={setSignupformsubmitresult} >
    </Register>}></Route>

    <Route exact path="/login" element={<Login 
    unsuccessfulsignin={unsuccessfulsignin} setUnsuccessfulsignin={setUnsuccessfulsignin} 
    user={user} setUser={setUser} signupformsubmitresult={signupformsubmitresult} 
    setSignupformsubmitresult={setSignupformsubmitresult}>
    </Login>}></Route>
    
    </Routes>
    </BrowserRouter>
    </div>
    
    
  );
}

export default App;
