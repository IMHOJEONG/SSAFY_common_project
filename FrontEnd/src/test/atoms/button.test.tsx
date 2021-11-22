import React from 'react';
import { prettyDOM, render, screen } from '@testing-library/react';
import Button from '../../components/atoms/Button';

test('renders button test', () => {
  const { getByText } = render(<Button text="button" />)
  const header = getByText('button');
  console.log(prettyDOM(header));
  expect(header).toBeInTheDocument();
});
