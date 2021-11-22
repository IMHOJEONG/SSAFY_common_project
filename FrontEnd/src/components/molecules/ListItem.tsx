import styled from "styled-components";
import { ButtonPropsType } from "../atoms/Button";
import { InputPropsType } from "../atoms/Input";


interface ListItemProps {
    children: React.ReactElement<ButtonPropsType> | React.ReactElement<InputPropsType>[]
    flexdirection?: string,
    height? : string
}

const StyledListItem = styled.div<ListItemProps>`

    display: flex;
    justify-content: center;
    width: 100%;
    flex-direction: ${(props) => props.flexdirection || "row"}
`;

const ListItem = ({ children, flexdirection }: ListItemProps, ) => {
    
    return (<StyledListItem flexdirection={flexdirection}>
        { children }
    </StyledListItem>);

};

export default ListItem;