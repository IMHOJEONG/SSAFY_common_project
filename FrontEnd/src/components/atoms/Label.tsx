import React from 'react';
import Styled from 'styled-components'

interface LabelPropsType {
  color?: string;
  fontSize?: string;
  backgroundcolor?: string;
  fontweight?: string;
}

const StyledLabel = Styled.label<LabelPropsType>`
  color: ${(props) => props.color || "#000000"};
  font-size: ${(props) => props.fontSize || "16px"};
  background: ${(props) => props.backgroundcolor || "#fff"};
  font-weight: ${(props) => props.fontweight || ""};
`;

const Label = ( props: any):JSX.Element => {
  return (
    <StyledLabel color={props.color} fontSize={props.fontSize}
      backgroundcolor={props.backgroundcolor}
      fontweight={ props.fontweight }
    >{props.label}</StyledLabel>
  );
};

export default Label;