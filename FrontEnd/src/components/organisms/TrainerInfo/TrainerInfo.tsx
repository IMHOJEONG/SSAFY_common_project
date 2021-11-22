import React, { useState, useEffect } from "react";
import "antd/dist/antd.css";
import "./styles.css";
import styled from "styled-components";
import { List, message, Spin, Image, Row, Col, Space, Button, Divider } from "antd";
import {
  FacebookOutlined as facebook,
  InstagramOutlined as instagram,
  TwitterOutlined as twitter,
  YoutubeOutlined as youtube,
} from "@ant-design/icons";

import InfiniteScroll from "react-infinite-scroller";
import axios from "axios";
import { Email } from '../../../recoil/atoms/Reservation/Email'
import { useRecoilState } from 'recoil'
import { ReservationState } from '../../../recoil/atoms/Reservation/ReservationState'
import profileimagedefault from '../../../assets/pages/profile/profileimagedefault.png'

const StyledList = styled(List)`
  .ant-list-item-meta {
    margin-bottom: 0;
  }

  .ant-list-item-extra {
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .ant-list-item-action {
    margin-left: 1rem;
    margin-top: 0;
  }
`;

const StyledSpin = styled(Spin)`
  .demo-loading-container {
    width: 300px;
    height: 300px;
  }
  .demo-loading-container ant-spin{
    width: 100%;
    height: 100%;
  }
  .ant-spin-dot{
    width: 10px;
    height: 10px;
  }
  .ant-spin-dot-item{
    width: 50px;
    height: 50px;
  }
  
`;

const StyledDivider = styled(Divider)`
  margin-top: 10px;
  border-top-width: 10px;
`;

const IconText = ({ icon, text }) => (
  <Space style={{
    fontSize: "30px"
  }}>
    {React.createElement(icon)}
    {text}
  </Space>
);

const labelStyle = {
  'fontSize': '20px',
  'marginBottom': '5px',
  "font-weight" : "bold",
};


function TrainerInfo () {
  let accessToken = localStorage.getItem('accessToken');
  const [ data, setData ] = useState<any>([])
  const [ loading, setLoading ] = useState(false)
  const [ hasMore, setHasMore ] = useState(true)
  const [ num, setNum ] = useState(0)
  const [ email, setEmail ] = useRecoilState(Email)
  const [ reservationTab, setReservationTab ] = useRecoilState(ReservationState)


  const fetchData = (callback) => {
    axios.get(`https://i5b305.p.ssafy.io/api/pt/teacherlist?page=${num}&size=5`, {
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
      .then((res) => {

      callback(res)
        setNum(num + 1);
    })
  }
  
  useEffect(() => {
    fetchData((res) => {
      console.log(res.data.data.teacherList);
      let trainerData = data;
      trainerData = trainerData.concat(res.data.data.teacherList);
      setData(trainerData)
      
      // console.log(trainerData);
      // setData(res.data.data.teacherList)
    })
  }, [])


  const handleInfiniteOnLoad = () => {
    let trainerData = data;
    setLoading(true)
    if (data.length > 500) {
      message.warning("트레이너를 모두 불러왔습니다.");
      setHasMore(false)
      setLoading(false)
      return;
    }
    
      
      fetchData((res) => {
        console.log(res.data.data.teacherList);
        trainerData = trainerData.concat(res.data.data.teacherList);
        setData(trainerData)
        // console.log(trainerData);
        setLoading(false)
      });
    
  };

  return (
    <div className="demo-infinite-container" style={{
      height: "86vh",
      padding: "8px 16px"
    }}>
      <InfiniteScroll
        initialLoad={false}
        pageStart={0}
        loadMore={handleInfiniteOnLoad}
        hasMore={!loading && hasMore}
        useWindow={false}
      >
        <StyledList
          itemLayout="vertical"
          dataSource={data}
          renderItem={(item: any) => (
            <List.Item
              key={item.id}
              
              extra={<Button type="primary" size="large"
                style={{
                  borderRadius: "10px"
                }}
                onClick={() => {
                setEmail(item.email)
                setReservationTab(!reservationTab)
              }}>예약하기</Button>}

              style={{
                background: "white",
                border: "5px solid gray",
                borderRadius: "20px",
                marginTop: "8px",
                padding : "10px"
              }}
            >
              <List.Item.Meta
                avatar={
                  item.profilePicture !== null ?
                    <Image
                      width={150}
                      src={item.profilePicture.pictureAddr}
                      style={{
                        borderRadius: "20px"
                      }}
                    />
                    :
                    <Image
                    width={150}
                      src={profileimagedefault}
                      style={{
                        borderRadius: "20px"
                      }}
                  />
                  
                  // <Image
                  //   width={150}
                  //   src="https://imgtag.co.kr/images/210729/210729_125520/3EYZwp.jpg"
                  // />
                }
                title={
                  <Row>
                    <Col span={6} >
                      <label
                      style={
                        labelStyle
                      }
                      >소개</label>
                      
                      <StyledDivider />
                      <p>닉네임: {item.nickname}</p>
                      <p>성별: {item.gender} </p>
                      <p>나이: {item.age}</p>
                      <p>전공: {item.major}</p>
    

                      {item.snsList[0] && <a style={{marginRight: "1rem"}}
                  href={`https://www.${item.snsList[0]['platform']}.com/${item.snsList[0]['url']}`}
                  target="_blank"
                  rel="noreferrer"
                >
                  {item.snsList[0]['platform'] === 'facebook' && <IconText
                          icon={facebook}
                    text=""
                    key="list-vertical-star-o"
                  />}
                  {item.snsList[0]['platform'] === 'instagram' && <IconText
                    icon={instagram}
                    text=""
                    key="list-vertical-star-o"
                  />}
                  {item.snsList[0]['platform'] === 'twitter' && <IconText
                    icon={twitter}
                    text=""
                    key="list-vertical-star-o"
                  />}
                  {item.snsList[0]['platform'] === 'youtube' && <IconText
                    icon={youtube}
                    text=""
                    key="list-vertical-star-o"
                  />}
                </a>}
                {item.snsList[1] && <a
                style={{marginRight: "1rem"}} 
                href={`https://www.${item.snsList[1]['platform']}.com/${item.snsList[1]['url']}`}
                target="_blank"
                rel="noreferrer"
              >
                {item.snsList[1]['platform'] === 'facebook' && <IconText
                  icon={facebook}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[1]['platform'] === 'instagram' && <IconText
                  icon={instagram}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[1]['platform'] === 'twitter' && <IconText
                  icon={twitter}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[1]['platform'] === 'youtube' && <IconText
                  icon={youtube}
                  text=""
                  key="list-vertical-star-o"
                />}
              </a>}
                {item.snsList[2] && <a
                style={{marginRight: "1rem"}}
                href={`https://www.${item.snsList[2]['platform']}.com/${item.snsList[2]['url']}`}
                target="_blank"
                rel="noreferrer"
              >
                {item.snsList[2]['platform'] === 'facebook' && <IconText
                  icon={facebook}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[2]['platform'] === 'instagram' && <IconText
                  icon={instagram}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[2]['platform'] === 'twitter' && <IconText
                  icon={twitter}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[2]['platform'] === 'youtube' && <IconText
                  icon={youtube}
                  text=""
                  key="list-vertical-star-o"
                />}
              </a>}
                {item.snsList[3] && <a
                style={{marginRight: "1rem"}}
                href={`https://www.${item.snsList[3]['platform']}.com/${item.snsList[3]['url']}`}
                target="_blank"
                rel="noreferrer"
              >
                {item.snsList[3]['platform'] === 'facebook' && <IconText
                  icon={facebook}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[3]['platform'] === 'instagram' && <IconText
                  icon={instagram}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[3]['platform'] === 'twitter' && <IconText
                  icon={twitter}
                  text=""
                  key="list-vertical-star-o"
                />}
                {item.snsList[3]['platform'] === 'youtube' && <IconText
                  icon={youtube}
                  text=""
                  key="list-vertical-star-o"
                />}
              </a>}
                    </Col>
                    <Col span={6}>
                      <label style={
                        labelStyle}>연락처</label>
                      <StyledDivider />
                      <p>{item.tel}</p>
                      <p>{item.email}</p>
                    </Col>
                    <Col span={6}>
                      <label style={labelStyle}>경력</label>
                      <StyledDivider />
                      {item.careers.map((career:any, index:any) => (
                        <p>{career.company} / {career.role}</p>
                      ))}
                    </Col>
                    <Col span={6}>
                      <label style={labelStyle}>자격증</label>
                      <StyledDivider />
                      {item.certificates.map((certificate:any, index:any) => (
                        <p>{certificate.name} / {certificate.publisher}</p>
                      ))}
                    </Col>
                  </Row>
                }
                description={item.description} 
              />
            </List.Item>
          )}
        >
          {loading && hasMore && (
            <div className="demo-loading-container">
              <StyledSpin />
            </div>
          )}
        </StyledList>
      </InfiniteScroll>
    </div>
  );
}

export default TrainerInfo;
