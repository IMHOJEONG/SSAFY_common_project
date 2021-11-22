import React, {useState, useEffect } from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import { List, Avatar, Button } from 'antd';
import { useRecoilState} from 'recoil'
import { StudentIndex } from '../../../recoil/atoms/chart/StudentIndex';
import { Bmi } from '../../../recoil/atoms/chart/Bmi';
import { SelectedWeight } from '../../../recoil/atoms/chart/SelectedWeight';
import { SelectedHeight } from '../../../recoil/atoms/chart/SelectedHeight';
import { Pibw } from '../../../recoil/atoms/chart/Pibw';
import { Weight } from '../../../recoil/atoms/chart/Weight';
import { SelectedInfo } from '../../../recoil/atoms/chart/SelectedInfo';

function StudentList(props: any) {
  const data = props
  const myStudent = props.myStudent
  const [ studentIndex, setStudentIndex ] = useRecoilState(StudentIndex)
  const [ myBmi, setMyBmi ] = useRecoilState(Bmi)
  const [ myPibw, setMyPibw ] = useRecoilState(Pibw)
  const [ myHeight, setMyHeight ] = useRecoilState(SelectedHeight)
  const [ myWeight, setMyWeight ] = useRecoilState(SelectedWeight)
  const [series, setSeries] = useRecoilState(Weight);
  const [ selectedUser, setSelectedUser] = useRecoilState(SelectedInfo)

  
  return (
    <>
      <div style={{marginTop: "8rem"}}>
      {data.myStudent.map((student: any, index: any) => (
        <div style={{display: "flex", marginLeft: "1rem"}}>
          <Button 
            block
            style={{marginBottom: "1.5rem"}}
            onClick={() => {
              let today = new Date()
              let month = today.getMonth()
              setStudentIndex({index})
              setMyBmi(Math.round(myStudent[index].weightList[month] / myStudent[index].heightList[month] / myStudent[index].heightList[month] * 10000))
              setMyHeight(myStudent[index].heightList[month])
              setMyWeight(myStudent[index].weightList[month])
              setMyPibw(Math.round(myStudent[index].weightList[month]/(myStudent[index].heightList[month]*myStudent[index].heightList[month]*22/10000)*100))
              setSeries([{
                name: "체중 (kg)",
                data: myStudent[index].weightList.map((weight: any, index: any) => { return weight !== -1 ? weight : null})
              }])
              setSelectedUser(myStudent[index])
            }}
          >
            {student.username}님의 차트보기
          </Button>
        </div>
      ))}
      </div>
    </>
  )
}

export default StudentList
