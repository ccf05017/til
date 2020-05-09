import React, { useReducer } from 'react';

const initialTodos = [
  {
    id: 1,
    text: 'create project',
    done: true
  },
  {
    id: 2,
    text: 'read project',
    done: true
  },
  {
    id: 3,
    text: 'update project',
    done: false
  },
  {
    id: 4,
    text: 'delete project',
    done: false
  }
];

function todoReducer(state, action) {
  switch (action.type) {
    case 'CREATE':
      return state.concat(action.todo);
    case 'TOGGLE':
      return state.map(
        todo => todo.id === action.id ? { ...todo, done: !todo.done } : todo
      );
    case 'REMOVE':
      return state.filter(todo => todo.id !== action.id);
    default:
      throw new Error(`Unhandled Action Type: ${action.type}`);
  }
}

export function TodoProvider({ children }) {
  const [state, dispatch] = useReducer(todoReducer, initialTodos);
  return children;
}
