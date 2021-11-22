import React from 'react';
import { atom, useRecoilState } from 'recoil';
import { InputState } from '../../recoil/atoms/InputState';



export interface InputPropsType {
  type: string
  inputType?: string
  value?: string | number
  inputName?: string
  onChange?: (e: React.ChangeEvent<HTMLInputElement>) => void
  placeholder?: string
  style?: object

}



function Input({ type, inputType = 'loginEmail',  ...props }: InputPropsType): JSX.Element {
  const [text, setText] = useRecoilState(InputState);

  const onChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setText({...text, [inputType]: event.target.value});
  }

  return <input type={type} name={props.inputName} style={props.style} value={text[inputType]} onChange={onChange} placeholder={props.placeholder}/>;
}

export default Input; 