import React from 'react';
import { prettyDOM, render, screen } from '@testing-library/react';
import Label from '../../components/atoms/Label';

test('renders label test', () => {
  const { getByText } = render(<Label label="label입니다." />)
  const header = getByText('label입니다.');
  console.log(prettyDOM(header));
  expect(header).toBeInTheDocument();
});
