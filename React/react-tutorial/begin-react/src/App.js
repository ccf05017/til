import React, { useRef } from 'react';
import UserList from "./UserList";

function App() {
  const users = [
    {
      id: 1,
      username: "poppo",
      email: "poppo@gmail.com"
    },
    {
      id: 2,
      username: "saul",
      email: "saul@gmail.com"
    },
    {
      id: 3,
      username: "ita",
      email: "ita@naver.com"
    },
  ];

  const nextId = useRef(4);

  const onCreate = () => {
    console.log(nextId.current);
    nextId.current += 1;
  };

  return (
      <UserList users={users}/>
  );
}

export default App;
