// install (please make sure versions match peerDependencies)
// yarn add @nivo/core @nivo/bullet
import { ResponsiveBullet } from "@nivo/bullet";
import styled from "styled-components";
// make sure parent container have a defined height when using
// responsive component, otherwise height will be 0 and
// no chart will be rendered.
// website examples showcase many properties,
// you'll often use just a few of them.
import { useRecoilState } from "recoil";
import { Weight } from "../../../recoil/atoms/chart/Weight";
import { useEffect, useState } from "react";
import axios from "axios";
import { Divider, Row, Col, Table } from 'antd'
import { StudentIndex } from "../../../recoil/atoms/chart/StudentIndex";
import { useRecoilValue } from "recoil";
import { Bmi } from "../../../recoil/atoms/chart/Bmi";
import { SelectedWeight } from "../../../recoil/atoms/chart/SelectedWeight";
import { SelectedHeight } from "../../../recoil/atoms/chart/SelectedHeight";

const columns = [
  {
    title: '내 체중상태',
    dataIndex: 'wei',
    key: 'wei',
  },
  {
    title: '계산한 체질량지수(BMI)',
    dataIndex: 'bmi',
    key: 'bmi',
  },
];

const dataSource = [
  {
    key: '1',
    wei: '저체중',
    bmi: '18.5 미만',
  },
  {
    key: '2',
    wei: '정상체중',
    bmi: '18.5이상 23미만',
  },
  {
    key: '3',
    wei: '과체중',
    bmi: '23이상 25미만',
  },
  {
    key: '4',
    wei: '비만',
    bmi: '경도 25이상 30미만 / 중정도 30이상 35미만 / 고도 35 이상',
  },
];

const BmiChart = (props:any) => {
  const selectedIndex = useRecoilValue(StudentIndex)
  const myInfo = props.myStudent
  const myBmi = useRecoilValue(Bmi)
  const height = useRecoilValue(SelectedHeight)
  const weight = useRecoilValue(SelectedWeight)
  const [manData, setManData] = useState([
    {
      id: "나의 BMI",
      ranges: [0, 18.5, 22.9, 24.9, 29.9, 34.9, 60],
      measures: [20],
    },
  ]);

  useEffect(() => {
  }, [])

  return (
    <>
      <Row style={{height: "70%", width: "70%", margin: "auto", display: "flex"}}>
        <Col span={12} style={{height: "30%", margin: "auto", marginTop: "1rem"}}>
          <Table columns={columns} dataSource={dataSource} pagination={false} title={() => 'BMI= 체중(kg) /(키(m)× 키(m))'} footer={() => `나의 키: ${height} 나의 체중: ${weight}`} />
        </Col>
        <Col span={12} style={{height: "30%"}}>
          <div style={{height: "100%"}}>
            <h1>나의 BMI는 {myBmi} 입니다.</h1>
            <Divider />
            <ResponsiveBullet
              data={manData}
              margin={{ top: 50, right: 90, bottom: 50, left: 90 }}
              spacing={46}
              titleAlign="start"
              titleOffsetX={-70}
              measureSize={0.2}
            />
            <Divider />
            <p>신체질량지수(BMI: Body Mass Index)는 질병관리본부에서 제공하는 계산법으로
              운동선수, 임산부, 어린이 및 노인은 이 수치가 맞지 않을 수 있습니다.
            </p>
          </div>
        </Col>
      </Row>
    </>
  );
};


export default BmiChart;