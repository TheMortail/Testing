import axios from "axios";

const Service = {
    sendMessage: (name, age, address) => {
        let msg = {
            name: name,
            age: age,
            address: address
        }
        return axios.post('http://localhost:8080/user', msg);
    }

}


export default Service;