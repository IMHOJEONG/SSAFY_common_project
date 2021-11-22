import React, {useEffect, useState} from 'react'
import { CircularProgressbar } from 'react-circular-progressbar';
import 'react-circular-progressbar/dist/styles.css';
import axios from 'axios';
import {Table, Row, Col, Divider} from 'antd'
import { Pibw } from '../../../recoil/atoms/chart/Pibw';
import { useRecoilValue } from 'recoil';
import { SelectedWeight } from '../../../recoil/atoms/chart/SelectedWeight';
import { SelectedHeight } from '../../../recoil/atoms/chart/SelectedHeight';

const columns = [
  {
    title: '내 체중상태',
    dataIndex: 'wei',
    key: 'wei',
  },
  {
    title: '계산한 체중대비 백분율(PIBW)',
    dataIndex: 'pibw',
    key: 'pibw',
  },
];

const dataSource = [
  {
    key: '1',
    wei: '저체중',
    pibw: '90% 미만',
  },
  {
    key: '2',
    wei: '정상체중',
    pibw: '90% 이상 110% 미만',
  },
  {
    key: '3',
    wei: '과체중',
    pibw: '110% 이상 120% 미만',
  },
  {
    key: '4',
    wei: '비만',
    pibw: '경도 120% 이상 130% 미만 / 중정도 130% 이상 160% 미만 / 고도 160% 이상',
  },
];

function Obesity2() {
  const percentage = useRecoilValue(Pibw)
  const height = useRecoilValue(SelectedHeight)
  const weight = useRecoilValue(SelectedWeight)

  return (
    <Row style={{height: "70%", width: "70%", margin: "auto", display: "flex"}}>
    <Col span={12} style={{height: "30%", margin: "auto", marginTop: "1rem"}}>
      <Table columns={columns} dataSource={dataSource} pagination={false} title={() => '비만도(%)= 표준 체중 대비 백분율(%) = 측정 체중/표준 체중 × 100'} footer={() => `나의 키: ${height} 나의 체중: ${weight}`} />
    </Col>
    <Col span={12} style={{height: "30%"}}>
      <div style={{height: "100%"}}>
        <h1>나의 PIBW는 {percentage}% 입니다.</h1>
        <Divider />
        <div style={{ width: 200, height: 200, margin: 'auto'}}>
          <CircularProgressbar value={percentage} maxValue={200} text={`${percentage}%`} />;
        </div>
        <Divider />
        <p>체중대비 백분율(PIBW, Percent of Ideal Body Weight): 나의 체중이 표준 체중과 비교해 얼마나 차이가 나는지 확인해보세요.
        </p>
      </div>
    </Col>
  </Row>

  )
}

export default Obesity2