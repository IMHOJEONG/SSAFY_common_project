import {Row, Col, Divider,  Image, Table, Button, Modal, message } from 'antd'
import { useEffect, useState } from 'react'
import {
  FacebookOutlined, 
  TwitterOutlined, 
  InstagramOutlined, 
  YoutubeOutlined 
  } from '@ant-design/icons'
import AWS from 'aws-sdk'
import axios from 'axios'
import styled from 'styled-components'
import { useHistory } from 'react-router'
import profileimagedefault from '../../../assets/pages/profile/profileimagedefault.png'

const ImageButton = styled.label`
  text-align: center;
  padding: 6px 20px;
  background-color:#FF6600;
  margin-left: 0.7rem;
  margin-right: 0.7rem;
  border-radius: 4px;
  color: white;
  cursor: pointer;
`

const StyledLabel = styled.label`
  font-size: 1rem;
  font-weight: 700;
`

const StyledP = styled.p`
font-size: 1rem;
font-weight: 500;
`;

const careerColumns = [
  {
    title: '회사명',
    dataIndex: 'company',
    key: 'company',
  },
  {
    title: '직책',
    dataIndex: 'role',
    key: 'role',
  },
  {
    title: '입사일자',
    dataIndex: 'startDate',
    key: 'startDate',
  },
  {
    title: '퇴사일자',
    dataIndex: 'endDate',
    key: 'endDate',
  },
]

const certificateColumns = [
  {
    title: '자격증명',
    dataIndex: 'name',
    key: 'name',
  },
  {
    title: '발행기관',
    dataIndex: 'publisher',
    key: 'publihser',
  },
  {
    title: '취득일자',
    dataIndex: 'date',
    key: 'date',
  },
]

function ProfileDetail(props) {
  let accessToken = localStorage.getItem('accessToken');
  const { certificates, careers }  = props.userInfo
  const snsAddress = props.userInfo.snss
  const [ facebook, setFacebook ] = useState({})
  const [ instagram, setInstagram ] = useState({})
  const [ twitter, setTwitter ] = useState({})
  const [ youtube, setYoutube ] = useState({})
  const [ imageName, setImageName ] = useState('')
  const [ imageURL, setImageURL ] = useState('')
  const [logged, setLogged] = useState<string>('');
  const [role, setRole] = useState<string>('');
  const history = useHistory()

  const isEmpty = (param) => {
    return Object.keys(param).length === 0
  }

  useEffect(() => {
    for (let sns in snsAddress ) {
      if (snsAddress[sns].platform === "facebook") {
        setFacebook(snsAddress[sns])
      } else if (snsAddress[sns].platform === "instagram") {
        setInstagram(snsAddress[sns])
      } else if (snsAddress[sns].platform === "twitter") {
        setTwitter(snsAddress[sns])
      } else {
        setYoutube(snsAddress[sns])
      }
    }

    axios.get('https://i5b305.p.ssafy.io/api/user/email, profilePictureURL', {
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
    .then((response) => {
      setImageName(response.data.data.email);
      setImageURL(response.data.data.profilePictureURL)
    })
  })

  AWS.config.update({
    region: "ap-northeast-2",
    accessKeyId: `${process.env.REACT_APP_S3_ACCESS_KEY_ID}`, // accessKeyId
    secretAccessKey: `${process.env.REACT_APP_S3_SECRET_ACCESS_KEY}`, // secretAccessKey
    
  })

  function upLoad (e:any) {
    const file = e.target.files[0]

    // S3 SDK에 내장된 업로드 함수
    const upload = new AWS.S3.ManagedUpload({
      params: {
        Bucket: "ogymbucket",
        Key: `profiles/${imageName}`, // 업로드할 파일명 (* 확장자를 추가해야 합니다!)
        ContentType: e.target.files[0].type,
        Body: file, // 업로드할 파일 객체
      },
    })

    const promise = upload.promise()

    promise.then(
      function (data) {
        axios({
          method: 'patch',
          url: 'https://i5b305.p.ssafy.io/api/user',
          data: {
            "url" : data.Location
          },
          headers: {
            "Authorization": `Bearer ${accessToken}`
          }
        })
        message.success("이미지 업로드에 성공했습니다.")
        window.location.reload()
      },
      function (err) {
        return message.error("이미지 업로드에 실패했습니다")
      }
    )
  }

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

  function widhdrawal () {

    axios({
      method: 'delete',
      url: 'https://i5b305.p.ssafy.io/api/user',
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
    .then((response) => {
      message.success('성공적으로 탈퇴 되었습니다.');
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
      setLogged('');
      setRole('');
      setIsModalVisible(false);
      history.push('/')
    })
    .catch((err) => {
      message.error('회원탈퇴를 실패했습니다.')
    })
  }


  return (
    <>
      <div className="containerProfile" style={{marginTop: "0.8rem"}}>
          <Row>
            <Divider />
            <Col span={5} style={{display: "flex", flexDirection: "column", marginRight: '1rem', marginTop: "1rem"}}>
              {imageURL !== null ?
              <Image src={imageURL} alt="프로필"  style={{width: "90%", marginLeft: "auto", marginRight: "auto"}}/> 
              :
              <Image src={profileimagedefault} alt="프로필"  style={{width: "90%", marginLeft: "auto", marginRight: "auto"}}/> 
              }
              
              <ImageButton htmlFor="input-image" style={{marginTop: "2rem"}}>프로필 업로드</ImageButton>
              <input type="file" id="input-image" onChange={upLoad} style={{display: "none", marginTop: "2rem"}} />
              <div style={{display: 'flex', justifyContent: "space-between", marginTop: "1rem", marginLeft: "0.7rem", marginRight: "0.7rem"}}>
              {isEmpty(facebook) ?
                <p><FacebookOutlined style={{ fontSize: '1.5rem'}}/></p> :
                <a
                  href={`https://www.facebook.com/${facebook["url"]}`}
                  target="_blank"
                  rel="noreferrer">
                  <FacebookOutlined style={{ fontSize: '1.5rem'}}/>
                </a>}
              {isEmpty(twitter) ?
                <p><TwitterOutlined style={{ fontSize: '1.5rem'}}/></p> :
                <a
                  href={`https://www.twitter.com/${twitter["url"]}`}
                  target="_blank"
                  rel="noreferrer">
                  <TwitterOutlined style={{ fontSize: '1.5rem'}}/>
                </a>}
              {isEmpty(instagram) ?
                <p><InstagramOutlined style={{ fontSize: '1.5rem'}}/></p> :
                <a
                  href={`https://www.instagram.com/${instagram["url"]}`}
                  target="_blank"
                  rel="noreferrer">
                  <InstagramOutlined style={{ fontSize: '1.5rem'}}/>
                </a>}
              {isEmpty(youtube) ?
                <p><YoutubeOutlined style={{ fontSize: '1.5rem'}}/></p> :
                <a
                  href={`https://www.youtube.com/${youtube["url"]}`}
                  target="_blank"
                  rel="noreferrer">
                  <YoutubeOutlined style={{ fontSize: '1.5rem'}}/>
                </a>}
              </div>
            </Col>
            <Col span={17} style={{display: "flex", flexDirection: "column"}}>
              <div style={{marginTop: "1rem"}}>
                <Row style={{paddingTop: "20.8px", borderTop: "1px solid black"}}>
                <Col span={8}>
                  <StyledLabel style={{fontWeight:700}}>이름</StyledLabel>
                </Col>
                <Col span={16}>
                  <StyledP>{props.userInfo.username}</StyledP>
                </Col>
                <Col span={8}>
                  <StyledLabel style={{fontWeight:700}}>나이</StyledLabel>
                </Col>
                <Col span={16}>
                  <StyledP>{props.userInfo.age}</StyledP>
                </Col>
                <Col span={8}>
                  <StyledLabel style={{fontWeight:700}}>이메일</StyledLabel>
                </Col>
                <Col span={16}>
                  <StyledP>{props.userInfo.email}</StyledP>
                </Col>
                <Col span={8}>
                  <StyledLabel style={{fontWeight:700}}>연락처</StyledLabel>
                </Col>
                <Col span={16}>
                  <StyledP>{props.userInfo.tel}</StyledP>
                </Col>
                <Col span={8}>
                  <StyledLabel style={{fontWeight:700}}>성별</StyledLabel>
                </Col>
                <Col span={16}>
                  <StyledP>{props.userInfo.gender}</StyledP>
                </Col>
                <Col span={8}>
                  <StyledLabel style={{fontWeight:700}}>전공</StyledLabel>
                </Col>
                <Col span={16}>
                  <StyledP>{props.userInfo.major}</StyledP>
                </Col>
              </Row >
                <div style={{marginBottom: "1rem", fontSize: "1.3rem", fontWeight:700, marginTop: "1.3rem", paddingTop: "0.8rem", borderTop: "1px solid black"}}>커리어</div>
                <div>
                  <Table dataSource={careers} columns={careerColumns} pagination={false} />
                </div>
                <div style={{marginBottom: "1rem", fontSize: "1.3rem", fontWeight:700, marginTop: "2.2rem", paddingTop: "0.8rem", borderTop: "1px solid black"}}>자격증</div>
                <Table dataSource={certificates} columns={certificateColumns} pagination={false} />
                <div>
                </div>
              </div>
              <div style={{paddingTop: "1rem", borderTop: "1px solid black", marginTop: "2rem", marginBottom: "1rem"}}>
              <Button type="primary" onClick={showModal} danger block>
                  회원탈퇴
                </Button>
                <Modal title="회원탈퇴" visible={isModalVisible} onOk={widhdrawal} onCancel={handleCancel}>
                  <p>정말 탈퇴 하시겠습니까?</p>
                </Modal>
              </div>
              <Divider />
            
            </Col>
          </Row>
          <Divider />

      </div>
    </>
  )
}

export default ProfileDetail
