import * as React from "react";
import { useForm } from "react-hook-form";
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { Zipcode } from "../../../recoil/atoms/Zipcode";
import { StreetAddress } from "../../../recoil/atoms/StreetAddress";
import styled from "styled-components";
import Postcode from "../../molecules/postcode/Postcode";
import { useRecoilValue } from "recoil";
import axios from "axios";
import {useState, useEffect} from 'react';
import AddSns from "../../molecules/register/AddSns";
import { AddSNS } from '../../../recoil/atoms/AddSNS'
import AddCert from "../../molecules/register/AddCert";
import { AddCERT } from '../../../recoil/atoms/AddCERT';
import AddCareer from "../../molecules/register/AddCareer";
import { AddCAREER } from "../../../recoil/atoms/AddCAREER"
import { useHistory } from "react-router";
import { message, Space } from 'antd'
import arrow from "../../../assets/pages/register/arrow.jpg";

const ErrorP = styled.p`
  color: red;
`;

const StyledForm = styled.form`
  max-width: 800px;
  margin: 0 auto;
`;

const StyeldInput = styled.input`
  display: block;
  box-sizing: border-box;
  width: 100%;
  border-radius: 4px;
  border: 1px solid black;
  padding: 10px 10px;
  margin-bottom: 10px;
  font-size: 14px;
  font-weight: bold;
  color: black;
`;

const StyledLabel = styled.label`
  line-height: 2;
  text-align: left;
  display: block;
  margin-bottom: 3px;
  margin-top: 10px;
  font-size: 14px;
  font-weight: bold;
  color: black;
`;

const StyledSelect = styled.select`
  width: 200px;
  padding: 0.8em 0.5em;
  border: 1px solid #999;
  font-family: inherit;
  background: url(${arrow}) no-repeat 95% 50%;
  border-radius: 0px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;

  .select::-ms-expand {
    display: none;
  }
`;

interface FormValues {
  email: string;
  password: string;
  confirmPassword: string;
  username: string;
  nickname: string;
  zipcode: string;
  streetAddress: string;
  detailedAddress: string;
  phone: string;
  gender: number;
  major: string;
  price: number;
  description: string;
  age: number;
}

const schema = yup.object().shape({
  email: yup.string()
    .required("이메일을 입력해주세요")
    .min(3, "올바른 이메일 형식이 아닙니다")
    .max(50, "올바른 이메일 형식이 아닙니다")
    .matches(/[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i, "올바른 이메일 형식이 아닙니다"),
  password: yup.string()
    .required("비밀번호를 입력해주세요")
    .min(3, "비밀번호는 3자 이상 입력해주세요")
    .max(20, "비밀번호는 20자 이하 입력해주세요"),
  confirmPassword: yup.string()
    .oneOf([yup.ref("password"), null], "비밀번호가 일치하지 않습니다")
    .required("비밀번호를 입력해주세요"),
  username: yup.string()
    .required("이름을 입력해주세요")
    .min(2, "이름은 2자 이상 입력해주세요")
    .max(10, "이름은 10자 이하로 입력해주세요")
    .matches(/[ㄱ-ㅎㅏ-ㅣ가-힣]/g, "한글을 입력해주세요"),
  nickname: yup.string()
    .required("닉네임을 입력해주세요")
    .min(2, "이름은 2자 이상 입력해주세요")
    .max(10, "이름은 10자 이하로 입력해주세요"),
  zipcode: yup.string()
    .required("주소 검색을 눌러서 입력해주세요"),
  streetAddress: yup.string()
    .required("주소 검색을 눌러서 입력해주세요"),
  detailedAddress: yup.string()
    .required("상세 주소를 입력해주세요"),
  phone: yup.string()
    .required("전화번호를 입력해주세요")
    .max(14, "올바른 전화번호 형식이 아닙니다"),
  gender: yup.number()
    .required()
    .typeError("성별을 선택해주세요"),
  major: yup.string()
    .required('전공을 입력해주세요')
    .max(20, '올바른 전공을 입력해주세요'),
  price: yup.number()
    .required('금액을 입력해주세요')
    .typeError("금액을 입력해주세요")
    .min(10000, '올바른 금액을 입력해주세요')
    .max(200000, '올바른 금액을 입력해주세요'),
  age: yup.number()
    .required('나이를 입력해주세요')
    .typeError("나이를 입력해주세요")
    .min(10, '나이를 다시 입력해주세요')
    .max(150, '나이를 다시 입력해주세요'),  
  description: yup.string()
    .required('자기소개를 입력해주세요')
    .max(50, '50글자를 넘길 수 없습니다'),
});

function RegisterStudent() {
  const history = useHistory();

  const [inputPhone, setInputPhone] = useState('');
  const zipcode = useRecoilValue(Zipcode)
  const streetAddress = useRecoilValue(StreetAddress)
  const adsns = useRecoilValue(AddSNS)
  const adcert = useRecoilValue(AddCERT)
  const adcareer = useRecoilValue(AddCAREER)
  
  const { register, setValue, handleSubmit, formState: {errors} } = useForm<FormValues>({
    resolver: yupResolver(schema)
  });

  const onSubmit = (data: FormValues) => {
    axios.post('https://i5b305.p.ssafy.io/api/user', {
      "email" : data.email,
      "password" : data.password,
      "username" : data.username,
      "nickname" : data.nickname,
      "gender" : data.gender,
      "tel" : data.phone,
      "zipCode" : data.zipcode,
      "street" : data.streetAddress,
      "detailedAddress" : data.detailedAddress,
      "role" : "ROLE_PTTEACHER",
      "major": data.major,
      "price": data.price,
      "description": data.description,
      "certificates": adcert,
      "snsAddrs": adsns,
      "careers": adcareer,
      "age": data.age
    })
    .then((response) => {
      message.success('성공적으로 회원가입 되었습니다.');
      history.push('/');
    })
    .catch((e) => {
      message.success(e.response.data);
    })
  }


  const phoneChange = (e) => {
    const regex = /^[0-9\b -]{0,13}$/;
    if (regex.test(e.target.value)) {
      setInputPhone(e.target.value);
    }
  }

  useEffect(() => {
    if (inputPhone.length === 10) {
      setInputPhone(inputPhone.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3'));
    }
    if (inputPhone.length === 13) {
      setInputPhone(inputPhone.replace(/-/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'));
    }
  }, [inputPhone]);

  return (
    <StyledForm onSubmit={handleSubmit(onSubmit)} >
      <StyledLabel htmlFor="email">이메일</StyledLabel>
      <StyeldInput type="text" placeholder="이메일"{...register("email")} minLength={3} maxLength={50}/>
      {errors.email?.message && <ErrorP>{errors.email?.message}</ErrorP>}

      <StyledLabel htmlFor="password">비밀번호</StyledLabel>
      <StyeldInput type="password" placeholder="비밀번호(8~20자)" {...register("password")} minLength={3} maxLength={20} />
      {errors.password?.message && <ErrorP>{errors.password?.message}</ErrorP>}

      <StyledLabel htmlFor="confirmPassword">비밀번호 확인</StyledLabel>
      <StyeldInput type="password" placeholder="비밀번호 확인"{...register("confirmPassword")} minLength={3} maxLength={20}/>
      {errors.confirmPassword?.message && <ErrorP>{errors.confirmPassword?.message}</ErrorP>}

      <StyledLabel htmlFor="username">이름</StyledLabel>
      <StyeldInput type="text" placeholder="이름"{...register("username")} minLength={2} maxLength={10}/>
      {errors.username?.message && <ErrorP>{errors.username?.message}</ErrorP>}

      <StyledLabel htmlFor="nickname">닉네임</StyledLabel>
      <StyeldInput type="text" placeholder="닉네임"{...register("nickname")} minLength={2} maxLength={10} />
      {errors.nickname?.message && <ErrorP>{errors.nickname?.message}</ErrorP>}

      <StyledLabel htmlFor="zipcode">주소검색</StyledLabel>
      <Space>
      <Postcode />

      <StyeldInput type="text" placeholder="우편 번호"{...register("zipcode")} value={zipcode} readOnly />

      </Space>
      
      {errors.zipcode?.message && <ErrorP>{errors.zipcode?.message}</ErrorP>}

      <StyeldInput type="text" placeholder="도로명 주소"{...register("streetAddress")} value={streetAddress} readOnly />
      {errors.streetAddress?.message && <ErrorP>{errors.streetAddress?.message}</ErrorP>}

      <StyeldInput type="text" placeholder="상세 주소"{...register("detailedAddress")} />
      {errors.detailedAddress?.message && <ErrorP>{errors.detailedAddress?.message}</ErrorP>}

      <StyledLabel htmlFor="phone">전화번호</StyledLabel>
      <StyeldInput type="text" placeholder="phone"{...register("phone")} maxLength={14}  onChange={phoneChange} value={inputPhone} />
      {errors.phone?.message && <ErrorP>{errors.phone?.message}</ErrorP>}
      
      <StyledLabel htmlFor="gender">성별</StyledLabel>
      <StyledSelect {...register("gender")} id="gender">
        <option value="">Select</option>
        <option value="0">남성</option>
        <option value="1">여성</option>
      </StyledSelect>
      {errors.gender?.message && <ErrorP>{errors.gender?.message}</ErrorP>}

      <StyledLabel htmlFor="age">나이</StyledLabel>
      <StyeldInput type="number" placeholder="나이"{...register("age")} min={10} max={150} />
      {errors.age?.message && <ErrorP>{errors.age?.message}</ErrorP>}

      <StyledLabel htmlFor="major">전공</StyledLabel>
      <StyeldInput type="text" placeholder="전공"{...register("major")} maxLength={20}/>
      {errors.major?.message && <ErrorP>{errors.major?.message}</ErrorP>}

      <StyledLabel htmlFor="certifications">자격증</StyledLabel>
      <AddCert />

      <StyledLabel htmlFor="careers">커리어</StyledLabel>
      <AddCareer />

      <StyledLabel htmlFor="price">시간당금액</StyledLabel>
      <StyeldInput type="number" placeholder="시간당금액"{...register("price")} min={10000} max={200000}/>
      {errors.price?.message && <ErrorP>{errors.price?.message}</ErrorP>}

      <StyledLabel htmlFor="description">자기소개</StyledLabel>
      <StyeldInput type="text" placeholder="자기소개"{...register("description")} maxLength={50}/>
      {errors.description?.message && <ErrorP>{errors.description?.message}</ErrorP>}

      <StyledLabel htmlFor="sns">SNS</StyledLabel>
      <AddSns />
      
      <StyeldInput 
        type="submit"
        value="회원가입"
        onClick={() => {
          setValue('zipcode', `${zipcode}`);
          setValue('streetAddress', `${streetAddress}`);
      }} 
      />
    </StyledForm>
  );
}

export default RegisterStudent;
