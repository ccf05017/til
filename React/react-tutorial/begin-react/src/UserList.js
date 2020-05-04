import React from 'react';
const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");

function User({ user, onRemove }) {
    const { username, email, id } = user;

    return (
        <div>
            <b>{username}</b><span>({email})</span>
            {/* 아래 코드는 콜백으로 '함수의 실행'을 전달 */}
            <button onClick={_ => onRemove(id)}>삭제</button>
        </div>
    );
}

function UserList({ users, onRemove }) {
    return (
        <div>
            {
                users.map(
                    user => (<User user={user} key={user.id} onRemove={onRemove}/>)
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
