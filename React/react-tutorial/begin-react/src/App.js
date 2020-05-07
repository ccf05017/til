import React, { useRef, useMemo, useCallback, useReducer, createContext } from 'react';
import UserList from "./UserList";
import CreateUser from "./CreateUser";
import useInputs from './UseInputs'
import produce from 'immer';

function countActiveUsers(users) {
    console.log("Counting active users...");
    return users.filter(user => user.active).length;
}

const initialState = {
    users: [
        {
            id: 1,
            username: "poppo",
            email: "poppo@gmail.com",
            active: true
        },
        {
            id: 2,
            username: "saul",
            email: "saul@gmail.com",
            active: false
        },
        {
            id: 3,
            username: "ita",
            email: "ita@naver.com",
            active: false
        },
    ]
};

function reducer(state, action) {
    switch (action.type) {
        case 'CREATE_USER':
            return produce(state, draft => {
                draft.users.push(action.user);
            });
            // return {
            //     inputs: initialState.inputs,
            //     users: state.users.concat(action.user)
            // };
        case 'TOGGLE_USER':
            return produce(state, draft => {
               const user = draft.users.find(user => user.id === action.id);
               user.active = !user.active;
            });
            // return {
            //     ...state,
            //     users: state.users.map(user =>
            //         user.id === action.id
            //             ? {...user, active: !user.active}
            //             : user
            //     )
            // };
        case 'REMOVE_USER':
            return produce(state, draft => {
                const index = draft.users.findIndex(user => user.id === action.id);
                draft.users.splice(index, 1)
            });
            // return {
            //     ...state,
            //     users: state.users.filter(user => user.id !== action.id)
            // };
        default:
            throw new Error('Unhandled Action');
    }
}

export const UserDispatch = createContext(null);

function App() {
    const [state, dispatch] = useReducer(reducer, initialState);

    const [form, onChange, reset] = useInputs({
        username: '',
        email: ''
    });
    const { username, email } = form;

    const nextId = useRef(4);
    const { users } = state;

    const onCreate = useCallback(_ => {
        dispatch({
            type: 'CREATE_USER',
            user: {
                id: nextId.current,
                username,
                email
            }
        });
        nextId.current += 1;
        reset();
    }, [username, email, reset]);

    const count = useMemo(() => countActiveUsers(users), [users]);

    return (
        <UserDispatch.Provider value={dispatch}>
            <CreateUser username={username} email={email} onChange={onChange} onCreate={onCreate} />
            <UserList users={users} />
            <div>활성 사용자 수: {count}</div>
        </UserDispatch.Provider>
    );
}

export default App;
