import styled from "styled-components";
import TeacherCalender from "./TeacherCalender/TeacherCalender";
import TeacherReservationConfirm from "./TeacherReservationConfirm/TeacherReservationConfirm"
import '@fontsource/roboto';
import MainNavigation from "../../components/organisms/Main/Main-Navigation";
import TeacherTodoList from "./TeacherTodoList";
const StyledDiv = styled.div`
   display: flex;
`;
const StyledColumnDiv = styled.div`
   display: flex;
   flex-direction: column;

`;

const NavigationButtonHeader = styled.div`
    height: 14vh;
    background-image : url("https://ogymbucket.s3.ap-northeast-2.amazonaws.com/teacher_navbar.jpg")
`;

const TeacherReservation = (): JSX.Element => {
    return (
        <>
            <StyledDiv>
                <StyledColumnDiv
                    >
                    <NavigationButtonHeader>
                        <MainNavigation position="sticky" /> 
                    </NavigationButtonHeader>
                    <TeacherReservationConfirm />
                </StyledColumnDiv>
                <StyledColumnDiv>
                    <TeacherCalender />
                    <TeacherTodoList />
                </StyledColumnDiv>
            </StyledDiv>
        </>
    );
}

export default TeacherReservation;