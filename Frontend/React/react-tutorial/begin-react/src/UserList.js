import React, { useContext } from 'react';
import { UserDispatch } from "./App";

const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");

const User = React.memo(function User({ user }) {
    const { username, email, id, active } = user;
    const dispatch = useContext(UserDispatch);

    return (
        <div>
            <b style={{
                    color: active ? 'green' : 'black',
                    cursor: 'pointer'
                }}
               onClick={_ => dispatch({
                   type: 'TOGGLE_USER',
                   id: id
               })}
            >
                {username}
            </b>
            <span>({email})</span>
            {/* 아래 코드는 콜백으로 '함수의 실행'을 전달 */}
            <button onClick={_ => dispatch({
                type: 'REMOVE_USER',
                id: id
            })}>삭제</button>
        </div>
    );
});

function UserList({ users }) {
    return (
        <div>
            {
                users.map(
                    user => (<User user={user} key={user.id} />)
                )
            }
            {/*{*/}
            {/*    _.go(*/}
            {/*        users,*/}
            {/*        L.map(user => (<User user={user} key={user.id} />)),*/}
            {/*        C.takeAll*/}
            {/*    )*/}
            {/*}*/}
        </div>
    );
}

export default React.memo(UserList);
