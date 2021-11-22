import { SpanPropsType } from "../atoms/Span";


export interface TitleProps {
    children : React.ReactElement<SpanPropsType>
}


const Title = ({ children } : TitleProps): JSX.Element => {
    
    return (
        <>
            { children }
        </>
    );
}

export default Title;