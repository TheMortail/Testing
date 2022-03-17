import React, { useState } from 'react'
import TextField from '@material-ui/core/TextField';

const Input = ({ onSendMessage }) => {
    const [name, setName] = useState("")
    const [age, setAge] = useState("")
    const [address, setAddress] = useState("")

    let onNameChange = (e) => {
        setName(e.target.value)
    }

    let onAgeChange = (e) => {
        setAge(e.target.value)
    }

    let onAddressChange = (e) => {
        setAddress(e.target.value)
    }

    let onSubmit = () => {
        setName("")
        setAge("")
        setAddress("")
        onSendMessage(name, age, address);
    }

    return (
        <div className="input">
                <h2>Add new user</h2>
            <TextField label="Name"
                       placeholder="Name"
                       onChange={e => onNameChange(e)}
                       value={name}
            />
            <TextField label="Age"
                       placeholder="Age"
                       onChange={e => onAgeChange(e)}
                       value={age}
            />
            <TextField label="Address"
                       placeholder="Address"
                       onChange={e => onAddressChange(e)}
                       value={address}
            />

            <button  onClick={onSubmit}
                    style={{ height: "50px", width: "70px" }}>
                Send
            </button>
        </div>
    );
}


export default Input