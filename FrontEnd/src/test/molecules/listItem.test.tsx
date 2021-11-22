import { prettyDOM, render } from "@testing-library/react";
import { RecoilRoot } from "recoil";
import Button from "../../components/atoms/Button";
import Input from "../../components/atoms/Input";
import ListItem from "../../components/molecules/ListItem";


test('ListItem test', () => {
 
    const { getAllByText,debug } = render(
        <RecoilRoot>
            <ListItem>
                <Input type="text" ></Input>
                <Button text='button' backgroundColor="blue"></Button>
            </ListItem>
        </RecoilRoot>
    );

  const header = getAllByText('button');
  console.log(header.length, debug());
  expect(header.length).toBe(1);
});