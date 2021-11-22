import { atom } from "recoil";

export const Email = atom<string>({
  key: 'Email',
  default: '',
})