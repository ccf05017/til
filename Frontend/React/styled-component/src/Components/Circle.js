import React from 'react';
import styled, { css } from 'styled-components';

const StyledCircle = styled.div`
      width: 5rem;
      height: 5rem;
      background: ${props => props.color};
      border-radius: 50%;
      ${props => props.huge &&
        css`
            width: 10rem;
            height: 10rem;
        `}
    `;

function Circle({ color, huge }) {

    return (
        <div>
            <StyledCircle color={color} huge={huge}/>
        </div>
    );
}

export default Circle;
