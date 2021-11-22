import { prettyDOM, render } from "@testing-library/react";
import { RecoilRoot } from "recoil";
import Button from "../../components/atoms/Button";
import ButtonList from "../../components/molecules/ButtonList";


test('ButtonList test', () => {
 
    const { getAllByText,debug } = render(
        <RecoilRoot>
            <ButtonList >
                <Button text="button" backgroundColor="green"></Button>
                <Button text="button" backgroundColor="white"></Button>
                <Button text="button" backgroundColor="yellow"></Button>
            </ButtonList>
        </RecoilRoot>
    );

  const header = getAllByText('button');
  console.log(header.length, debug());
  expect(header.length).toBe(3);
});
