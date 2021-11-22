import { atom } from "recoil";

export const Time = atom<string>({
  key: 'Time',
  default: '',
})