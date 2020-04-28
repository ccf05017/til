import React from 'react';

function Hello({ color, name }) {
    const colorRed = {
        color
    };

    return <div style={colorRed}>Hello {name}!</div>;
}

Hello.defaultProps = {
    name: "no name"
};

export default Hello;
