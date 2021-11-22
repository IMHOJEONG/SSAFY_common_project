import React from "react";
import { useState, useEffect } from "react";
import ReactApexChart from "react-apexcharts";
import axios from 'axios'
import { useRecoilState } from 'recoil'
import { Weight } from "../../../recoil/atoms/chart/Weight";

function WeightFlowChart() {
  const [series, setSeries] = useRecoilState(Weight);
  // const [series, setSeries] = useState([{
  //   name: "체중(kg)",
  //   data: []}]);
  let accessToken = localStorage.getItem('accessToken');

  useEffect(() => {
    let today = new Date()
    let month = today.getMonth()
    axios.get(
      "https://i5b305.p.ssafy.io/api/health/myhealth", {
        headers: {
          "Authorization": `Bearer ${accessToken}`
        }
      }
    )
    .then((response) => {
      setSeries([{
      name: "체중 (kg)",
      data: response.data.data.weightList.map((weight: any, index: any) => { return weight !== -1 ? weight : null})
    }])
  }
    )
  }, [])

  const options:Object = {
    chart: {
      height: 350,
      type: "line",
      zoom: {
        enabled: false
      },
      // dropShadow: {
      //   enabled: true,
      //   top: 18,
      //   left: 7,
      //   blur: 10,
      //   opacity: 0.2,
      // },
      dropShadow: {
        enabled: true,
        top: 3,
        left: 2,
        blur: 4,
        opacity: 1,
      },
      toolbar: {
        show: false,
      },
    },
    // colors: ["#77B6EA"],
    dataLabels: {
      enabled: true,
    },
    stroke: {
      curve: "smooth",
    },
    title: {
      text: "월별 체중 변화",
      align: "left",
    },
    grid: {
      show: true,
      padding: {
        bottom: 0
      }
      // borderColor: "#e7e7e7",
      // row: {
      //   colors: ["#f3f3f3", "transparent"],
      //   opacity: 0.5,
      // },
    },
    markers: {
      size: 1,
    },
    xaxis: {
      categories: [
        "Jan",
        "Feb",
        "Mar",
        "Apr",
        "May",
        "Jun",
        "Jul",
        "Aug",
        "Sept",
        "Oct",
        "Nov",
        "Dec",
      ],
      title: {
        text: "Month",
      },
    },
    yaxis: {
      title: {
        text: "체중(kg)",
      },
      min: 45,
      max: 120,
    },
    legend: {
      position: "top",
      horizontalAlign: "right",
      floating: true,
      offsetY: -25,
      offsetX: -5,
    },
  };

  return (
    <div id="chart">
      <ReactApexChart
        options={options}
        series={series}
        type="line"
        height={500}
        color={"#777"}
      />
    </div>
  );
}

export default WeightFlowChart;
