import React, { useState } from 'react'
import { useSetRecoilState } from "recoil";
import DaumPostcode from 'react-daum-postcode';
import 'antd/dist/antd.css';
import { Modal, Button } from 'antd';
import { Zipcode } from "../../../recoil/atoms/Zipcode";
import { StreetAddress } from "../../../recoil/atoms/StreetAddress";


function Postcode() {
  const setZipcode = useSetRecoilState(Zipcode)
  const setStreetAddress = useSetRecoilState(StreetAddress)

  const handleComplete = (data:any) => {
    let fullAddress = data.address;
    let extraAddress = ''; 
    
    if (data.addressType === 'R') {
      if (data.bname !== '') {
        extraAddress += data.bname;
      }
      if (data.buildingName !== '') {
        extraAddress += (extraAddress !== '' ? `, ${data.buildingName}` : data.buildingName);
      }
      fullAddress += (extraAddress !== '' ? ` (${extraAddress})` : '');
    }
    setZipcode(data.zonecode)
    setStreetAddress(data.roadAddress)
    setIsModalVisible(false);
  }

  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = () => {
    setIsModalVisible(true);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <>
    <Button type="primary" onClick={showModal}>
      주소 검색
    </Button>
    <Modal title="주소 검색" visible={isModalVisible} onCancel={handleCancel} footer={null}>
      {isModalVisible && 
        <DaumPostcode
          onComplete={handleComplete}
          // autoClose
        />
      }
    </Modal>
    </>
  )
}

export default Postcode
