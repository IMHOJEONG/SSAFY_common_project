import { atom } from "recoil";


export const ButtonState = atom<string>({
    key: 'buttonState', // unique ID (with respect to other atoms/selectors)
    default: '', // default value (aka initial value)
  });