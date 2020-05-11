import React from 'react';
import axios from 'axios';
import useAsync from './UseAsync'

async function getUser(id) {
  const response = await axios.get(`https://jsonplaceholder.typicode.com/users/${id}`);
  return response.data;
}

function User({ id }) {
  // id가 바뀔 때마다 데이터를 새로 가져올 것이다.
  const [state] = useAsync(() => getUser(id), [id]);
  const { loading, data: user, error } = state;

  if (loading) return <div>loading...</div>;
  if (error) return <div>Error!</div>;
  if (!user) return null;

  return (
    <div>
      <h2>{user.username}</h2>
      <p><b>Email: </b> {user.email}</p>
    </div>
  );
}

export default User;
