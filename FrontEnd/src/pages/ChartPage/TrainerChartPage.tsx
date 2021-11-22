import React, {useState, useEffect} from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import { Layout } from 'antd';
import StudentList from '../../components/organisms/ChartTrainer/StudentList';
import axios from 'axios';
import { useRecoilState } from 'recoil'
import { Weight } from '../../recoil/atoms/chart/Weight';


import { Swiper, SwiperSlide } from "swiper/react";
// Import Swiper styles
import "swiper/components/effect-fade/effect-fade.min.css"
import "swiper/swiper.min.css";
import "swiper/components/pagination/pagination.min.css"

import "./styles.css";
import man from "../../assets/pages/chart/man.jpg"

import SwiperCore, {
  EffectFade, Mousewheel,Pagination
} from 'swiper/core';
import styled from "styled-components";
import WeightFlowChart from '../../components/organisms/ChartTrainer/WeightFlowChart';
import BmiChart from '../../components/organisms/ChartTrainer/BmiChart';
import PibwChart from '../../components/organisms/ChartTrainer/PibwChart'
import { StudentIndex } from '../../recoil/atoms/chart/StudentIndex';
import { SelectedInfo } from '../../recoil/atoms/chart/SelectedInfo';
import MainNavigation from '../../components/organisms/Main/Main-Navigation';

// install Swiper modules
SwiperCore.use([EffectFade, Mousewheel,Pagination]);

const ImgDiv = styled.div`
  position: relative;
  background-image: url(${man});
  height: 100%;
  width: 100%;
  background-size: cover;
`

const ImgContent = styled.div`
  position: absolute;
  top:50%;
  left:50%;
  transform: translate(-50%, -50%);                                                                   
  font-size:4rem;
  color: white;
  z-index: 2;
  text-align: center;
`;

const ImgCover = styled.div`
  position: absolute;
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.4);                                                                 
  z-index:1;
`;

const { Sider, Content } = Layout;
function TrainerChartPage() {
  const [series, setSeries] = useRecoilState(Weight);
  const [studentIndex, setStudentIndex ] = useRecoilState(StudentIndex)
  const [myStudent, setMyStudent] = useState<any>([])
  const [seletedStudent, setSelectedStudent] = useState<any>({})
  const [selectedUser, setSelectedUser] = useRecoilState(SelectedInfo)
  let accessToken = localStorage.getItem('accessToken');

  useEffect(() => {
    let today = new Date()
    let month = today.getMonth()
    axios.get(
      'https://i5b305.p.ssafy.io/api/health/mystudents', {
        headers: {
          "Authorization": `Bearer ${accessToken}`
        }
      }
    )
    .then((response) => {
      setMyStudent(response.data.data.studentHealthList)
      setSelectedStudent(studentIndex.index)

  })
  }, [])

  return (
    <>
    <MainNavigation />
    <Layout style={{height: "100vh"}}>
      <Sider><StudentList myStudent={myStudent}/></Sider>
      <Layout style={{height: "100vh"}}>
        <Content>
          <div style={{height: "100vh"}}>
          <Swiper direction={'vertical'} slidesPerView={1} spaceBetween={30} mousewheel={true} pagination={{"clickable": true}} className="mySwiper">
            <SwiperSlide>
              <ImgDiv>
              <ImgContent>
                {selectedUser.username && <h1 style={{color:'white'}}>{selectedUser.username}님의 건강 차트</h1>}
              </ImgContent>
              <ImgCover></ImgCover>
              </ImgDiv>
            </SwiperSlide>
            {selectedUser.username &&
            <>
            <SwiperSlide>
              <BmiChart myStudent={myStudent} />
            </SwiperSlide>
            <SwiperSlide>
              <PibwChart />
            </SwiperSlide>
            <SwiperSlide >
            {/* style={{backgroundColor: "#343E59"}} */}
              <div style={{height: "70%", width: "70%", margin: "auto"}}>
                <WeightFlowChart />
              </div>
            </SwiperSlide>
            </>}
          </Swiper>
          </div>
        </Content>
      </Layout>
    </Layout>
    </>
  )
}

export default TrainerChartPage
