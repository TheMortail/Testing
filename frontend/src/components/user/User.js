import React from 'react'

const User = ({ users }) => {

    let renderAd= (user) => {
        const { userId, name, age, address } = user;
        return (
            <li>
                <div className="userId">{userId}</div>
                <div className="name">{name}</div>
                <div className="age">{age}</div>
                <div className="address">{address}</div>
            </li>
        );
    };

    return (
        <ul className="list">
            {users.map(msg => renderAd(msg))}
        </ul>
    )
}

export default User