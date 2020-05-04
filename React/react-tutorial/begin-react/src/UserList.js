import React, { useEffect } from 'react';
const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");

function User({ user, onRemove, onToggle }) {
    const { username, email, id, active } = user;
    useEffect(_ => {
        console.log(user);
    });

    return (
        <div>
            <b style={{
                    color: active ? 'green' : 'black',
                    cursor: 'pointer'
                }}
               onClick={_ => onToggle(id)}
            >
                {username}
            </b>
            <span>({email})</span>
            {/* 아래 코드는 콜백으로 '함수의 실행'을 전달 */}
            <button onClick={_ => onRemove(id)}>삭제</button>
        </div>
    );
}

function UserList({ users, onRemove, onToggle }) {
    return (
        <div>
            {
                users.map(
                    user => (<User user={user} key={user.id} onRemove={onRemove} onToggle={onToggle}/>)
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

export default UserList;
