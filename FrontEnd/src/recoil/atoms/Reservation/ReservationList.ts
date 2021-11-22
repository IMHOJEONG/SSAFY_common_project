import { atom } from "recoil";

export const ReservationList = atom<any>({
  key: 'ReservationList',
  default: [],
})