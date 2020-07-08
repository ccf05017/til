import React from 'react';

function Hello({ color, name, isSpecial }) {
    const colorRed = {
        color
    };

    return (
        <div style={
            colorRed
        }>
            {isSpecial ? <b>*</b> : null}
            Hello {name}!
        </div>
    );
}

Hello.defaultProps = {
    name: "no name"
};

export default Hello;
