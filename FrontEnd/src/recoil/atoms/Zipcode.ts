import { atom } from "recoil";

export const Zipcode = atom<string>({
  key: 'Zipcode',
  default: '',
})