import React from 'react';
const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");

function User({ user }) {
    return (
        <div>
            <b>{user.username}</b><span>({user.email})</span>
        </div>
    );
}

function UserList() {
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

export default UserList;
