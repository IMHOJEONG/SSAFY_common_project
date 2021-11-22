import { atom } from "recoil";

export const Description = atom<string>({
  key: 'Description',
  default: '',
})