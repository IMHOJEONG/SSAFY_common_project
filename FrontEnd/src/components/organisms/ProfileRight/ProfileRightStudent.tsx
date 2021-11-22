import React from 'react'
import { useState, useEffect, useCallback } from 'react';
import axios from 'axios'
import ReactDOM from 'react';
import 'antd/dist/antd.css'
import { List, Avatar } from 'antd'
import './ProfileRightStudent.css'

function ProfileRightStudent() {
  const [myReservation, setMyReservation] = useState<any[]>([])
  let accessToken = localStorage.getItem('accessToken');
  
  useEffect(() => {
    axios.get('https://i5b305.p.ssafy.io/api/pt/reservation', {
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
    .then((response) => {
      setMyReservation(response.data.data)
      console.log(response.data.data)
    })
  }, [])

  return (
      <>
      <List
        header={<div style={{fontWeight:700, fontSize: "1.3rem", borderTop: "1px solid black", paddingTop: "0.7rem", borderBottom: "1px solid black", paddingBottom: "0.7rem"}}>예약현황</div>}
        style={{overflowY: "scroll", height: "500px"}}
        itemLayout="horizontal"
        dataSource={myReservation}
        renderItem={myReservation => (
          <List.Item
            style={{borderBottom: "0.3px solid grey"}}
          >
            <List.Item.Meta
              avatar={<Avatar src={myReservation.profileUrl} />}
              title={<p style={{fontWeight: 700, fontSize: "1rem"}}>트레이너 {myReservation.nickname}</p>}
              description={<p style={{fontWeight: 700, fontSize: "1rem"}}>운동부위 {myReservation.description}</p>}
            />
            <div style={{marginRight: "1rem", fontWeight: 700, fontSize: "1rem"}}>{
                myReservation.reservationTime.substring(0, 4) + "년 " +
                myReservation.reservationTime.substring(5, 7) + "월 " +
                myReservation.reservationTime.substring(8, 10) + "일 " +
                myReservation.reservationTime.substring(11, 13) + "시"
              }</div>
      </List.Item>
        )}
  />
  </>
  )
}

export default ProfileRightStudent
