import { Divider, message } from "antd"
import { useState } from "react";
import styled from "styled-components";
import Button from "../../atoms/Button";
import Input from "../../atoms/Input"
import ListItem from "../../molecules/ListItem"
import { useRecoilState,  useResetRecoilState } from "recoil";
import axios from "axios";
import { InputState } from "../../../recoil/atoms/InputState";
// @ts-ignore
import jwt_decode from "jwt-decode";
import { useHistory } from "react-router";


const StyeldInput = styled.input`
    display: block;
    box-sizing: border-box;
    width: 100%;
    border-radius: 4px;
    border: 1px solid black;
    padding: 10px 10px;
    margin-bottom: 10px;
    font-size: 14px;
    font-weight: bold;
    color: black;
`;

const StyledInput = {
    'display': 'block',
    'box-sizing': 'border-box',
    'width': '100%',
    'border-radius': '4px',
    'border': '1px solid black',
    'padding': '10px 10px',
    'margin-bottom': '10px',
    'font-size': '14px',
    'font-weight': 'bold',
    'color': 'black'
}

const StyledLabel = styled.label`
  line-height: 2;
  text-align: left;
  display: block;
  margin-bottom: 3px;
  margin-top: 10px;
  font-size: 14px;
  font-weight: bold;
  color: black;
`;

const StyledDiv = styled.div`
    display: flex;
    height: 50px;
`;

const StyledDivColumn = styled.div`
    display: flex;
    flex-direction: column;
    height: 55px;
`;

const LoginContent = (): JSX.Element => {

    const [isShow, setIsShow] = useState(true);

    const [loginInfo, setLoginInfo] = useRecoilState(InputState);
    const resetLoginInfo = useResetRecoilState(InputState);
    const history = useHistory();
    // const [LodalTokenState, setLodalTokenState] = useRecoilState(LoginTokenState);

    const handleShowButton = () => {
        setIsShow(!isShow);
    }

    const success = () => {
        message.success('성공적으로 로그인 되었습니다.');
        history.push('/');
        resetLoginInfo();
    };
    
    const error = () => {
        message.error('아이디나 비밀번호가 잘못되었습니다. 다시 입력해주세요');
        history.push('/login');
        resetLoginInfo();
    };

    const requestLogin = async () => {
        let id = loginInfo.loginEmail;
        let pw = loginInfo.loginPassword;
        console.log(id, pw);
        try {
            
            let responseData = await axios.post('https://i5b305.p.ssafy.io/api/authenticate', {
                email: id,
                password: pw
            });

            let { accessToken, refreshToken } = await responseData.data.data;

            localStorage.setItem('accessToken', accessToken);
            localStorage.setItem('refreshToken', refreshToken);

            axios.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
    
            let checkDate: {
                exp, email, role
            } = jwt_decode(accessToken);
            console.log(checkDate);
            let currentDate = new Date();
            let tokenDate = new Date(checkDate.exp * 1000)
            let diffTime = currentDate.getTime() - tokenDate.getTime();
            
            if (diffTime > 0) {
                let refreshData = await axios.post('/api/reissue', {
                    accessToken: accessToken,
                    refreshToken: refreshToken
                });
                let updatedTokenData = refreshData.data.data;
                axios.defaults.headers.common['Authorization'] = `Bearer ${updatedTokenData.accessToken}`;
                
                localStorage.setItem('accessToken', updatedTokenData.accessToken);
                localStorage.setItem('refreshToken', updatedTokenData.refreshToken);
            }
    
            success();
        }
        catch (err) {
            error();
        }

      
        
    }


    return (
        <>
            <StyledDivColumn >
            <StyledLabel htmlFor="email">이메일</StyledLabel>
                <Input type="text" inputType="loginEmail" placeholder="이메일"
                    style={StyledInput}
                />
            </StyledDivColumn>
            
            <Divider />
            
            <StyledDiv
                >
                <ListItem flexdirection="column">
                <StyledLabel htmlFor="password">비밀번호</StyledLabel>
                    <Input type={isShow === true ? "password" : "text"} inputType="loginPassword" placeholder="비밀번호"
                    
                    style={StyledInput}/>
                </ListItem>
                
                <Button onClick={handleShowButton}
                 backgroundColor="light-gray"
                    color="black"
                    margin="10px "
                    width="100px"
                    height="5vh"
                    borderRadius="10px"
                >{isShow === true ? "show" : "hide"}</Button>
            
            </StyledDiv>

            <Divider />
            
           
                    <StyeldInput 
                type="submit"
                value="로그인"
                onClick={requestLogin}
            />
                    <Divider />
        </>
    );
}

export default LoginContent;
