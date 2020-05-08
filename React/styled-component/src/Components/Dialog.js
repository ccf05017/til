import React from 'react';
import styled from 'styled-components';
import Button from './Button';

const DarkBackground = styled.div`
  position: fixed;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(0, 0, 0, 0.8);
`;

const DialogBlock = styled.div`
  width: 320px;
  padding: 1.5rem;
  background: white;
  border-radius: 2px;
  
  h3 {
    margin: 0;
    font-size: 1.5rem;
  }
  
  P {
    font-size: 1.125rem;
  }
`;

const ButtonGroup = styled.div`
  margin-top: 3rem;
  display: flex;
  justify-content: flex-end;
`;

// 상속 활용한 버튼 재정의
const ShortMarginButton = styled(Button)`
  & + & {
    margin-left: 0.5rem;
  }
`;

function Dialog({ title, children, confirmText, cancelText, visible, onConfirm, onCancel }) {
    if (!visible) return null;

    return (
        <div>
            <DarkBackground>
                <DialogBlock>
                    <h3>{title}</h3>
                    <p>{children}</p>
                    <ButtonGroup>
                        <ShortMarginButton color="gray" onClick={onCancel}>{cancelText}</ShortMarginButton>
                        <ShortMarginButton color="pink" onClick={onConfirm}>{confirmText}</ShortMarginButton>
                    </ButtonGroup>
                </DialogBlock>
            </DarkBackground>
        </div>
    );
}

Dialog.defaultProps ={
    cancelText: "취소",
    confirmText: "확인",
};

export default Dialog;
