import { atom } from "recoil";

interface InputStateType {
  [inputType: string]: string | number;
}

export const InputState = atom<InputStateType>({
  key: 'InputState',
  default: {
    loginEmail: '',
    loginPassword: '',
    
  }
})