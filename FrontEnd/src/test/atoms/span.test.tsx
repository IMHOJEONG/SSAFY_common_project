import React from 'react';
import { prettyDOM, render, screen } from '@testing-library/react';
import Span from '../../components/atoms/Span';

test('renders span test', () => {
  const { getByText } = render(<Span span="span입니다." />)
  const header = getByText('span입니다.');
  console.log(prettyDOM(header));
  expect(header).toBeInTheDocument();
});
