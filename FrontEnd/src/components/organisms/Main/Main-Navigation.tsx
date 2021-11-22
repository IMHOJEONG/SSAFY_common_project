import { Modal, Typography, Button, Image, Row, Col, message } from "antd";
import { useState } from "react";
import styled from "styled-components";
import { Link } from "react-router-dom";
import OGYM from '../../../assets/pages/mainPage/navButton/ogym.jpg';
import barChart from "../../../assets/pages/mainPage/navButton/bar-chart.png"
import conference from "../../../assets/pages/mainPage/navButton/conference.png"
import onlineBooking from "../../../assets/pages/mainPage/navButton/online-booking.png"
import reserved from "../../../assets/pages/mainPage/navButton/reserved.png"
import work from "../../../assets/pages/mainPage/navButton/work.png"
// @ts-ignore
import jwt_decode from "jwt-decode";
import { useEffect } from "react";
import { useHistory } from "react-router";
import axios from 'axios';
const { Text } = Typography;
const StyledModal = styled(Modal)`
  position: fixed;
  top: 0px;
  left: 0px;
  bottom: 0px;
  height: "100vh";
  // animation: "none",
  // transition: "350ms",
  width: 100%;
  
  .ant-modal-wrap {
    height: 100%;
    width: 100%;
  }

  .ant-modal-content {
    height: 100%;
    width: 100vw;
    display: flex;
    flex-direction: column;
  }

  .ant-modal-body {
    display: flex;
    padding: 0;
    height: 100%
  }


`;

export interface MainNavigationType {
  position?: string;
}


const StyledButton = styled(Button)<MainNavigationType>`
    position: ${props=>props.position || "fixed"};
    z-index: 2;
    top: 10px;
    left: 10px;
    width: 100px;
    height: 100px;
    object-fit: cover;
    // background-image: url(${OGYM});
`;

const StyledLogo = styled(Button)<MainNavigationType>`
    position: ${props=>props.position || "fixed"};
    top: 5px;
    left: 10px;
    width: 50px;
    height: 50px;
    object-fit: cover;
    // background-image: url(${OGYM});
`;


const StyledCircledImage = styled(Image)`
  clip-path : circle(50%);
`;

const MainNavigation = (props): JSX.Element => {

    const [isVisible, setIsVisible] = useState<boolean>(false);
    const [logged, setLogged] = useState<string>('');
    const [role, setRole] = useState<string>('');
  const history = useHistory();
  try {

    
    useEffect(() => {
      const accessToken = localStorage.getItem('accessToken');
    
      if (accessToken != null) {
        
        const decoded: {
          nickname,role
        } = jwt_decode(accessToken);
        if (decoded['nickname']) {
          setLogged(decoded['nickname']);
          setRole(decoded['role']);
        }
        else {
        
        }
      }
    }, [])
    
      
    }

    catch (error) {
      
    }
  
  
    const clickMenuButton = () => {
      if (role === "") {
        history.push('/login')
      }
        setIsVisible(!isVisible)
    }

    const clickModalClose = () => {
        setIsVisible(!isVisible)
  }
  
  const clickLoginButton = () => {
    history.push('/login');
  }
  const clickLogoutButton = () => {
    history.push('/');
    console.log('test')
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    setLogged('');
    setRole('');
    message.info('성공적으로 로그아웃 되었습니다');
    setIsVisible(!isVisible);
  }

  const clickConferenceRoom = () => {
    
    const accessToken = localStorage.getItem('accessToken');

    if (accessToken != null) {
        
      const decoded: {
        nickname, role
      } = jwt_decode(accessToken);
    
      if (decoded['nickname']) {
        axios.get(`https://i5b305.p.ssafy.io/api/pt/nowreservation`, {
          headers: {
            "Authorization": `Bearer ${accessToken}`
          }
        }).then(({ data }) => {
                
          if (data.data === null) {
            message.error('현재 시간에 확인된 예약이 없습니다');
            setIsVisible(!isVisible)
            history.push('/');
            throw new Error();
          }
          else {
            history.push('/dovideo');
          }
        }).catch(() => {

        });
      }
    }
    else {
      setIsVisible(!isVisible);
      history.push('/');
      message.error('로그인 해주세요!');
    }

  }

    return (
        <>
            <StyledButton onClick={clickMenuButton}
          shape="circle" position={props.position}
          icon={<StyledCircledImage src={OGYM} preview={false} />}
        >
        
            </StyledButton>

            <StyledModal
          title={
            <Row gutter={2}>
              <Col span={18}>
              <StyledLogo  onClick={clickMenuButton}
          shape="circle" position={props.position}
          icon={<StyledCircledImage src={OGYM} preview={false} />} />
              </Col>
                {
                logged !== '' ? (
                  <>
                    <Col span={3} style={{
                      marginTop: "0.3rem",
                      marginRight: "-4em"
                    }}>
                    <Text>
                      {logged}님
                      
                      환영합니다
                      </Text>
                        </Col>

                      <Col>
                      <Button onClick={() =>
                        history.push('/profile')
                      }>내 정보</Button>
                      <Button onClick={
                        clickLogoutButton
                      }>

                        Logout
                      </Button>
                    
                    </Col>
                    </>
                  )
                  :
                  <>
                    <Col span={3}>
                    <Text>
                      로그인이 필요합니다
                      </Text>
                    </Col>
                    <Col >

                    <Button type="primary"
                      onClick={ clickLoginButton}
                    >
                      Login
                        </Button>  
                    </Col>
                  </>
                }
              
            </Row>
          }
                visible={isVisible}
                onOk={clickModalClose}
                onCancel={clickModalClose}
                transitionName="ant-move-up"
                maskTransitionName=""
                footer={null}
            >
              {/* 모달 내용 */}
              <Row style={{flex: 1, display: "flex", alignItems: "stretch"}}>

            {role === 'ROLE_PTSTUDENT' ? 
            
            <Col span={12} style={{ backgroundColor: "#F08C8C", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}>
              <Link to={'/studentchart'} style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", height: "50%", width: "15%" }}>
              <img src={barChart} alt="내 건강분석" style={{width: "80%"}}/>
              </Link>
              <Link to={'/studentchart'}>
              <p style={{color: "white", fontSize: "1.5rem"}}>내 건강 분석</p>
              </Link>
            </Col>
          
              :
              
              <Col span={12} style={{ backgroundColor: "#F08C8C", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}>

              </Col>
            }
            
            {role === 'ROLE_PTTEACHER' ?
            
            <Col span={12} style={{backgroundColor: "#dcdcdc", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column"}}>
            <Link to={'/trainerchart'} style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", height: "50%", width: "15%" }}>
            <img src={work} alt="내 학생 관리" style={{width: "80%"}}/>
            </Link>
            <Link to={'/trainerchart'}>
            <p style={{color: "white", fontSize: "1.5rem", marginTop: "1rem"}}>내 학생 관리</p>
            </Link>
              </Col>
              
              :
              <Col span={12} style={{backgroundColor: "#dcdcdc", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column"}}>
              </Col>
            }
            
            {role === 'ROLE_PTTEACHER' ?
            <Col span={12} style={{backgroundColor: "#dcdcdc", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column"}}>
            <Link to={'/teacherreservation'} style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", height: "50%", width: "15%" }}>
            <img src={onlineBooking} alt="예약 현황 확인" style={{width: "80%"}}/>
            </Link>
            <Link to={'/teacherreservation'}>
            <p style={{color: "white", fontSize: "1.5rem", marginTop: "1rem"}}>예약 현황 확인</p>
            </Link>
              </Col>
              
              :
              <Col span={12} style={{backgroundColor: "#dcdcdc", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column"}}>
              </Col>
            }

            {role === 'ROLE_PTSTUDENT' ?
            
            <Col span={12} style={{backgroundColor: "#96C7ED", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}>
            <Link to={'/studentreservation'} style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", height: "50%", width: "15%" }}>
              <img src={reserved} alt="예약" style={{width: "80%"}}/>
            </Link>
            <Link to={'/studentreservation'}>
              <p style={{color: "white", fontSize: "1.5rem", marginTop: "1rem"}}>PT 예약 / 취소</p>
            </Link>
              </Col>
              
              :

              <Col span={12} style={{backgroundColor: "#96C7ED", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}>
              </Col>

              
              
              
            }
                
            {role === 'ROLE_PTSTUDENT' ?
              <Col span={12} style={{ backgroundColor: "#91F8D0", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}
              onClick={clickConferenceRoom}
              >
                <div style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", height: "50%", width: "15%" }}>
                  <img src={conference} alt="예약" style={{width: "80%"}}/>
                </div>
                <div>
                  <p style={{color: "white", fontSize: "1.5rem", marginTop: "1rem"}}>PT 화상 채팅방 접속하기</p>
                </div>
              </Col>

              :
              <Col span={12} style={{backgroundColor: "#91F8D0", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}>
              </Col>
            }
                
            {role === 'ROLE_PTTEACHER' ? 
            
              <Col span={12} style={{ backgroundColor: "#dcdcdc", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column" }}
                onClick={clickConferenceRoom}
              >
            <div style={{display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column", height: "50%", width: "15%" }}>
            <img src={conference} alt="예약" style={{width: "80%"}}/>
            </div>
            <div>
            <p style={{color: "white", fontSize: "1.5rem", marginTop: "1rem"}}>PT 화상 채팅방 개설하기</p>
            </div>
              </Col>
              
              :

              <Col span={12} style={{backgroundColor: "#dcdcdc", display: "flex", justifyContent: "center", alignItems: "center", flexDirection: "column"}}>
                </Col>
            }
                
                
              </Row>
            </StyledModal>
            
        </>
    );
}

export default MainNavigation;
