import { ButtonPropsType } from "../atoms/Button";
import { SpanPropsType } from "../atoms/Span";
import styled from "styled-components";

interface BoxPropsType {
    border?: string; 
    opacity?: number;
    flexDirection?: string;
    padding?: string;
    height?: string;
    children?: (React.ReactElement<ButtonPropsType> |
        React.ReactElement<SpanPropsType>)[];
}

const StyledDiv = styled.div<BoxPropsType>`
    display: flex;
    flex-direction: ${(props)=> props.flexDirection || "row"};
    border: ${props => props.border};
    opacity : ${props => props.opacity};
    height: ${props => props.height};
    padding: ${props => props.padding};
`;

const Box = (props: BoxPropsType) => {
        
    return (
        <>
            <StyledDiv
                border={props.border}
                opacity={props.opacity}
                flexDirection={props.flexDirection}
                height={props.height}
                padding={ props.padding }
            >
                { props.children }
            </StyledDiv>
        </>
    );
}

export default Box;