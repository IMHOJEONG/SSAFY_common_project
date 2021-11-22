import styled from "styled-components";
import { ButtonPropsType } from "../atoms/Button";

interface ButtonListProps {
  children: React.ReactElement<ButtonPropsType>[]
}

const StyledListButton = styled.div`
  display: flex;
  justify-content: center;
`;

const ButtonList = ({ children } : ButtonListProps) => {
    
    return (
        <StyledListButton>
          { children }
        </StyledListButton>
    );
}

export default ButtonList;