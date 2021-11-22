import { atom } from "recoil";

export const AddCERT = atom<Array<string>>({
  key: 'AddCERT',
  default: [],
})