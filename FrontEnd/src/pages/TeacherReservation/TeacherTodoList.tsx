import Title from "antd/lib/typography/Title";
import styled from "styled-components";

const StyledFooter = styled.div`
    background-image:url(https://ogymbucket.s3.ap-northeast-2.amazonaws.com/running.jpg);
    height: 10vh;
    text-align: center;
    
`;

const StyledTitle = styled(Title)`
&.ant-typography {
    color: white;
  }
`;

const TeacherTodoList = () => {
    return (
        <StyledFooter>
            <StyledTitle >
                JUST DO IT!
            </StyledTitle>
        </StyledFooter>
    );
}

export default TeacherTodoList;