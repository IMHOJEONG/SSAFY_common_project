import styled from 'styled-components';

export interface ButtonPropsType {
    backgroundColor?: string;
    onClick?: Function;
    width: string;
    margin: string;
    signInType: "facebook" | "google" | "naver" | undefined;
    border?: string;
    height?: string;
    padding?: string;
    borderRadius?: string;
    color?: string;
}

const StyledButton = styled.button<ButtonPropsType>`
    background: ${props => props.backgroundColor || "white"};
    width: ${props => props.width || "auto"};
    padding: ${props => props.padding || "10px"};
    margin: ${props => props.margin || ""};
    border: ${props => props.border || "none"};
    height: ${props => props.height};
    border-radius: ${props => props.borderRadius};
    color: ${props => props.color || "black"};
    cursor: pointer;
`;

const Button = (props: any): JSX.Element => {
    
    return (
        <>
            <StyledButton backgroundColor={props.backgroundColor}
                width={props.width}
                onClick={props.onClick}
                signInType={props.signInType}
                margin={props.margin}
                border={props.border}
                height={props.height}
                padding={props.padding}
                borderRadius={props.borderRadius}
                color={props.color}
            >
                {props.children}
            </StyledButton>
        </>
    )

}

export default Button;
