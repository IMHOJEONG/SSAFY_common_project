import { atom } from "recoil";

export const SpanState = atom<string>({
  key: 'SpanState',
  default: '',
})