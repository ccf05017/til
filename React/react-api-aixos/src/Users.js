import React from 'react';
import axios from 'axios';
import useAsync from "./UseAsync";

async function getUsers() {
  const response = await axios.get('https://jsonplaceholder.typicode.com/users');
  return response.data;
}

function Users() {
  const [state, fetchData] = useAsync(getUsers, [], true);

  const { loading, data: users, error } = state;
  if (loading) return <div>loading...</div>;
  if (error) return <div>error!</div>;
  if (!users) return <button onClick={fetchData}>Load</button>;

  return (
    <>
      <ul>
        {users.map(user => (
          <li key={user.id}>
            {user.username} ({user.name})
          </li>
        ))}
      </ul>
      <button onClick={fetchData}>Reload</button>
    </>
  );
}

export default Users
