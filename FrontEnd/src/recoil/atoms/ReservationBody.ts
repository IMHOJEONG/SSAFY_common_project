import { atom } from "recoil";

export const ReservationBody = atom<any>({
  key: 'ReservationBody',
  default: {
    ptTeacherEmail : "",
    reservationTime : "",
		description : ""
  },
})