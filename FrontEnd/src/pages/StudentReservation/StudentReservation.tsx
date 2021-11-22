import React, {useState, useEffect} from 'react'
import styled from 'styled-components'
import 'antd/dist/antd.css';
import { Layout, Menu } from 'antd';
import { Row, Col, Button, message, Collapse } from 'antd';
import axios from 'axios';
import './styles.css';
import StudentCalendar from '../../components/molecules/StudentCalendar';
import TimeSchedule from '../../components/molecules/TimeSchedule';
import MainNavigation from '../../components/organisms/Main/Main-Navigation';
import TrainerInfo from '../../components/organisms/TrainerInfo/TrainerInfo';
import { ReservationState } from '../../recoil/atoms/Reservation/ReservationState';
import { useRecoilState } from 'recoil';
import { Email } from '../../recoil/atoms/Reservation/Email';
import { Time } from '../../recoil/atoms/Reservation/Time';
import { Date } from '../../recoil/atoms/Reservation/Date';
import { ReservationList } from '../../recoil/atoms/Reservation/ReservationList';
import ReservationCancel from '../../components/organisms/ReservationCancel/ReservationCancel';
import arrow from '../../assets/pages/register/arrow.jpg'

const { Sider } = Layout;
const { SubMenu } = Menu;
const { Panel } = Collapse

const StyledSelect = styled.select`
  width: 200px;
  padding: 0.8em 0.5em;
  border: 1px solid #999;
  font-family: inherit;
  background: url(${arrow}) no-repeat 95% 50%;
  border-radius: 0px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;

  .select::-ms-expand {
    display: none;
  }
`;

function StudentReservation() {
  let accessToken = localStorage.getItem('accessToken');
  // const [selectReservation, setSelectReservation] = useState(false);
  const [teacherList, setTeacherList] = useState<any>([])
  const [reservationTab, setReservationTab] = useRecoilState(ReservationState)
  const [email, setEmail] = useRecoilState(Email)
  const [time, setTime] = useRecoilState(Time)
  const [date, setDate] = useRecoilState(Date)
  const [reservationList, setReservationList] = useRecoilState(ReservationList)
  const [exercise, setExercise] = useState('')



  function ptReservation () {
    if (time === "") {
      message.error('날짜와 시간을 선택해주세요.')
      return
    }
    if (exercise === "") {
      message.error('운동 부위를 선택해주세요.')
      return
    }

    axios({
      method: 'post',
      url: 'https://i5b305.p.ssafy.io/api/pt/reservation',
      data: {
        ptTeacherEmail : email,
        reservationTime : date+"T"+time+":00",
        description : exercise
      },
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
    .then((response) => {
      message.success('성공적으로 예약 되었습니다.');
      setReservationTab(!reservationTab)
      setTime('')
      setDate('')
      // history.push('/profile')
      window.location.reload()
    })
    .catch((e) => {
      message.error('예약에 실패했습니다')
    })
  }

  useEffect(() => {
    
    axios.get(
      'https://i5b305.p.ssafy.io/api/pt/teacherlist', {
        headers: {
          "Authorization": `Bearer ${accessToken}`
        }
      }
    )
    .then((response) => {
      setTeacherList(response.data.data.teacherList)
    })

    axios.get('https://i5b305.p.ssafy.io/api/pt/reservation', {
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
    .then((response) => {
      setReservationList(response.data.data)
    })
  }, [])


  return (
    <div style={{ backgroundColor: "#F3F4FA" }}>
      <div style={{
        height: "14vh",
        backgroundImage: "url('https://ogymbucket.s3.ap-northeast-2.amazonaws.com/teacher_navbar.jpg')"
      }}>
        <MainNavigation />

      </div>
      <Row>
      <Col span={18} style={{}}>
          <TrainerInfo />
      </Col>
      <Col span={6} style={{height: '85vh', background: "none", overflowY: "auto"}}>
        { reservationTab ?
          <div style={{overflowY: "auto"}}>
            <Collapse defaultActiveKey={['1', '4']} style={{overflowY: "auto"}}>
              <Panel header="날짜 선택" key="1">
              <StudentCalendar />
              </Panel>
              <Panel header="시간 선택" key="2">
              <TimeSchedule />
              </Panel>
              <Panel header="운동 부위" key="3">
              <StyledSelect onChange={(e) => setExercise(e.target.value)}>
                <option value="">Select</option>
                <option value="상체">상체</option>
                <option value="하체">하체</option>
                <option value="등">등</option>
                <option value="가슴">가슴</option>
                <option value="어깨">어깨</option>
              </StyledSelect>
              </Panel>
              <Panel header="예약 확인" key="4">
                <p>날짜</p>  
                <p>{date}</p>
                <p>시간</p>
                <p>{time}</p>
              </Panel>
              
              
            </Collapse>
            <Row>
              <Col span={12}>
              <Button block onClick={(e) => {setReservationTab(!reservationTab)}}>예약닫기</Button>
              </Col>
              <Col span={12}>
              <Button type="primary" onClick={ptReservation} block>예약하기</Button>
              </Col>
              
              
              </Row>
          </div>
          :
            <div>
              <ReservationCancel reservationList={reservationList} />
            </div>
          }
      </Col>
      </Row>
    </div>
  )
}

export default StudentReservation