import 'antd/dist/antd.css';
import { Radio } from 'antd';
import { Time } from '../../recoil/atoms/Reservation/Time';
import { useRecoilState, useRecoilValue } from 'recoil'
import { PossibleTime } from '../../recoil/atoms/Reservation/PossibleTime';
import { Date as DateState } from '../../recoil/atoms/Reservation/Date';

const timeData = [
  "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00", "17:00", "19:00", "20:00", "21:00", "22:00"
]

function TimeSchedule() {
  const [ value, setValue ] = useRecoilState(Time)
  const chooseDate = useRecoilValue(DateState);
  const [possibleTime, setPossibleTime] = useRecoilState(PossibleTime)
  
  let testTimeData = timeData.map((e) => {
    let val = e.split(":");
    let chooseDateValue = new Date(chooseDate);
    let hour = parseInt(val[0]);
    chooseDateValue.setHours(hour);
    return chooseDateValue;
  });
  console.log(testTimeData, value, chooseDate)
  let nowDate = new Date();
  function onChange (e) {
    setValue(e.target.value)
  }

  return (
    <>
      <Radio.Group onChange={onChange} value={value} buttonStyle="solid" >
        {timeData.map((td, index) => (
           testTimeData[index] > nowDate && !possibleTime.includes(td) && <Radio.Button value={td}>{td}</Radio.Button>  
        ))}
    </Radio.Group>
    </>
  )
}

export default TimeSchedule