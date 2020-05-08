import React from 'react';
import styled, { css } from 'styled-components';

function Circle() {

    return styled.div`
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
}

export default Circle;
