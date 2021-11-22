import React from "react";
import styled from "styled-components";
import axios from "axios";
import { useEffect, useState } from "react";
import { useHistory } from "react-router-dom";
import { message, Modal, Button, Divider, List } from 'antd'

const StyledDiv = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;

function ReservationCancel(props) {
  let accessToken = localStorage.getItem('accessToken');
  const history = useHistory();
  const myReservationList = props.reservationList
  

  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <div>
      <List
            header={<div style={{marginLeft: "1rem"}}>예약현황</div>}
            itemLayout="horizontal"
            dataSource={myReservationList}
            renderItem={(item:any) => (
              <List.Item
                actions={[<>
                <Button type="primary" onClick={showModal}>예약 취소</Button>
                <Modal
            title="예약 취소"
            visible={isModalVisible}
            onOk={() => {
                axios({
                  method: 'delete',
                  url: 'https://i5b305.p.ssafy.io/api/pt/reservation',
                  data: {
                    "ptTeacherEmail" : item.email,
                    "reservationTime" : item.reservationTime,
                    "description": item.description
                  },
                  headers: {
                    "Authorization": `Bearer ${accessToken}`
                  }
                })
                .then((response) => {
                  message.success('예약이 취소되었습니다.')
                  setIsModalVisible(false)
                  window.location.reload()
                  // history.push('/profile')
                })
            }}
            onCancel={handleCancel}
          >
            <p>예약 날짜</p>
            <p>{item.reservationTime.substring(0, 4) + "년 " +
              item.reservationTime.substring(5, 7) + "월 " +
              item.reservationTime.substring(8, 10) + "일 "}</p>
            <Divider />
            <p>예약 시간</p>
            <p>{item.reservationTime.substring(11, 13) + ":00시"}</p>
          </Modal></>
              ]}
              >
                <List.Item.Meta
                  style={{marginLeft: '1rem'}}
                  title={<span>            {
                    item.reservationTime.substring(0, 4) + "년 " +
                    item.reservationTime.substring(5, 7) + "월 " +
                    item.reservationTime.substring(8, 10) + "일 " +
                    item.reservationTime.substring(11, 13) + "시"
                  }</span>}
                  description={item.nickname + '  /  ' + item.description}
                />
              </List.Item>
            )}
       />
    </div>
  );
}

export default ReservationCancel;