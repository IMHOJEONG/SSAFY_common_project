import { atom } from "recoil";

export const StudentIndex = atom<any>({
  key: 'StudentIndex',
  default: {index: 0}
})