import React, { useEffect } from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import { DatePicker, Space } from 'antd';
import { useRecoilState } from 'recoil'
import { Date } from '../../recoil/atoms/Reservation/Date';
import axios from 'axios';
import { Email } from '../../recoil/atoms/Reservation/Email';
import { PossibleTime } from '../../recoil/atoms/Reservation/PossibleTime';
import moment from 'moment'

function StudentCalendar() {
  let accessToken = localStorage.getItem('accessToken');
  const [ date, setDate ] = useRecoilState(Date)
  const [ email, setEamil ] = useRecoilState(Email)
  const [ possibleTime , setPossibleTime ] = useRecoilState(PossibleTime)

  function onChange(date, dateString) {
    setDate(dateString)
  }

  useEffect(() => {
    axios.get(`https://i5b305.p.ssafy.io/api/pt/reservation/${email}`, {
      headers: {
        "Authorization": `Bearer ${accessToken}`
      }
    })
    .then((response) => {
      let possibleArray = response.data.data
      let temp:any = []
      for (let i = 0; i < possibleArray.length; i++) {
        let key:string = possibleArray[i].substring(0, 10)
        let value:string = possibleArray[i].substring(11, 16)
        if (date === key) {
          temp.push(value)
        }
      }
      setPossibleTime(temp)
      
    })
  }, [date])

  function disabledDate(current) {
    return current && current < moment().endOf("day").subtract(1, "days");
  }


  return (
    <Space direction="vertical" size={12}>
      <DatePicker onChange={onChange} disabledDate={disabledDate}/>

    </Space>

  );
}

export default StudentCalendar;