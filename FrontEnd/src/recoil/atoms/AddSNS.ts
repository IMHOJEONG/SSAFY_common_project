import { atom } from "recoil";

export const AddSNS = atom<Array<string>>({
  key: 'AddSNS',
  default: [],
})