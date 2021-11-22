import React, { useEffect, useState } from "react";
// Import Swiper React components
import { Swiper, SwiperSlide } from "swiper/react";
import axios from "axios";

// Import Swiper styles
import "swiper/components/effect-fade/effect-fade.min.css"
import "swiper/swiper.min.css";
import "swiper/components/pagination/pagination.min.css"

import "./styles.css";
import man from "../../assets/pages/chart/man.jpg"


// import Swiper core and required modules
import SwiperCore, {
  EffectFade, Mousewheel,Pagination
} from 'swiper/core';
import styled from "styled-components";
import BmiChart from "../../components/organisms/ChartStudent/BmiChart";
import WeightFlowChart from "../../components/organisms/ChartStudent/WeightFlowChart";
import PibwChart from "../../components/organisms/ChartStudent/PibwChart"
import MainNavigation from "../../components/organisms/Main/Main-Navigation";

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

function StudentChartPage() {
  const [username, setUsername] = useState('')
  let accessToken = localStorage.getItem('accessToken');

  useEffect(() => {
    axios.get(
      'https://i5b305.p.ssafy.io/api/user/username', {
        headers: {
          "Authorization": `Bearer ${accessToken}`
        }
      }
    )
    .then((response) => {
      setUsername(response.data.data.username)
    })
  }, [])
  
  return (
    <>
      <MainNavigation />
      <Swiper direction={'vertical'} slidesPerView={1} spaceBetween={30} mousewheel={true} pagination={{"clickable": true}} className="mySwiper">
        <SwiperSlide>
          <ImgDiv>
          <ImgContent>
            <h1 style={{color:'white'}}>{username}님의</h1>
            <h1 style={{color:'white'}}>건강 차트</h1>
          </ImgContent>
          <ImgCover></ImgCover>
          </ImgDiv>
        </SwiperSlide>
        <SwiperSlide>
          <BmiChart/>
        </SwiperSlide>
        <SwiperSlide>
          <PibwChart />
        </SwiperSlide>
        <SwiperSlide >
        {/* style={{backgroundColor: "#343E59"}} */}
          <div style={{height: "70%", width: "70%", margin: "auto"}}>
            {/* <WeightFlow /> */}
            <WeightFlowChart />
          </div>
        </SwiperSlide>

      </Swiper>
    </>
  )
}

export default StudentChartPage;