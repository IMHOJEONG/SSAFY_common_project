import { atom } from "recoil";


export const ModalState = atom < boolean >({
    key: 'modalState',
    default: true
})