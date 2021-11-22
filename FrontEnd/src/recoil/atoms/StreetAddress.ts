import { atom } from "recoil";

export const StreetAddress = atom<string>({
  key: 'StreetAddress',
  default: '',
})