import React from 'react';
import Styled from 'styled-components/native';

const Container = Styled.View`
  flex: 1;
  align-items: center;
  justify-content: center;
`;
const Label = Styled.Text``;

const EmptyItem = () => {
  return (
    <Container>
      <Label>새로운 할 일을 등록하려면 하단의 "+" 버튼을 눌러주세요</Label>
    </Container>
  );
};

export default EmptyItem;
