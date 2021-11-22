import React from 'react';
import Styled from 'styled-components'

export interface SpanPropsType {
  color?: string;
  fontSize?: string;
  backgroundColor?: string;
  textAlign?: string;
}

const StyledSpan = Styled.span<SpanPropsType>`
  color: ${(props) => props.color || "#000000"};
  font-size: ${(props) => props.fontSize || "16px"};
  background-color: ${(props) => props.backgroundColor || "transparent"};
  text-align: ${(props) => props.textAlign || "none"};
`;

const Span = (props: any): JSX.Element => {
  return (
    <StyledSpan 
      color={props.color}
      fontSize={props.fontSize}
      backgroundColor={props.backgroundColor}
      textAlign={ props.textAlign }
    >
      {props.children} 
    </StyledSpan>
  );
};

export default Span;