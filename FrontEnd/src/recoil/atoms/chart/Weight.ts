import { atom } from "recoil";

export const Weight = atom<any>({
  key: 'Weight',
  default: [{
    name: '',
    data: [],
  }]
})