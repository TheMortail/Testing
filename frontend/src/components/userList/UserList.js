import React from 'react';
import axios from 'axios';

const URL = "http://localhost:8080";
export default class UserList extends React.Component {
    state = {
        posts: []
    }

    componentDidMount() {
        axios.get(URL+'/user')
            .then(res => {
                const posts = res.data;
                this.setState({ posts });
            })
    }

    deleteRow(id, e){
        axios.delete(URL+'/user/'+id)
            .then(res => {
                console.log(res);
                console.log(res.data);

                const posts = this.state.posts.filter(item => item.id !== id);
                this.setState({ posts });
                window.location.reload(false);

            })
    }

    render() {
        return (
            <div align={"center"}>
                <table className="table table-bordered">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Age</th>
                        <th>Address</th>
                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {this.state.posts.map((post) => (
                        <tr>
                            <td>{post.userId}</td>
                            <td>{post.name}</td>
                            <td>{post.age}</td>
                            <td>{post.address}</td>
                            <td>
                                <button className="btn btn-danger" onClick={(e) => this.deleteRow(post.userId, e)}>Delete</button>
                            </td>
                        </tr>
                    ))}
                    </tbody>

                </table>
            </div>
        )
    }
}