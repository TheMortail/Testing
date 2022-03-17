import './App.css';
import {useState} from "react";
import User from "./components/user/User";
import UserList from "./components/userList/UserList";
import Input from "./components/input/Input";
import Service from "./services/Service"

function App() {
  const [users] = useState([])

  let onSendMessage = (name, age, address) => {
      Service.sendMessage(name, age, address).then(res => {
          console.log('Sent', res);
          window.location.reload(false);
      }).catch(err => {
          console.log('Error Occured while sending message to api');
      })
  }

  return (
      <div className="App">
        <Input onSendMessage={onSendMessage} />
        <User users={users}/>
        <UserList/>
        </div>
  )
}

export default App;