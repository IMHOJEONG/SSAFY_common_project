import { Carousel, Image } from "antd";
import styled from "styled-components";
import crossFit from '../../../assets/pages/mainPage/carousel/crossfit.jpg';
import dumbBell from '../../../assets/pages/mainPage/carousel/dumbbells.jpg';
import weightLift from '../../../assets/pages/mainPage/carousel/weight_lifting.jpg';
import Button from "../../atoms/Button";
import Span from "../../atoms/Span";
import Box from "../../molecules/Box";
import Title from "../../molecules/Title";
import { useHistory } from 'react-router-dom';

const StyledCarousel = styled(Carousel)`

  .slick-dots {
    position: fixed;
  }
  .slick-dots li button {
    opacity: 0.4;
    height: 100vh;
  }

 .slick-dots li.slick-active button {
    opacity: 1;
    background: linear-gradient(to left, rgba(0,0,0,0.4) 50%, #fff 50%);
    background-size: 200% 100%;
    background-position: bottom right;
    transition: all 3s ease-in;
}

`;

const MainTextDiv = styled.div`
  width: 50%;
  position: fixed;
  top: 0px;
  bottom: 0px;
  margin: 10% 25%;
`;


const MainContent = (): JSX.Element => {
    
     const onChange = (a: any, b?: any, c?: any) :void  => {
        console.log(a, b, c);
      }
  
  const history = useHistory();
  
    return (
        <>
            <StyledCarousel afterChange={onChange}
                autoplay
            >
                <Image
            src={crossFit} preview={false}
                />
                <Image
                  src={dumbBell} preview={ false }   
                />
                <Image
                  src={weightLift} preview={ false }      
                />
            
        </StyledCarousel>
        <MainTextDiv>

          <Box
            border="16px solid rgba(255,255,255,0.5)"
            flexDirection="column"
            height="80%"
            padding="15%"
          >
              <Title>
              <Span
                color="Black"
                fontSize="35px"
                textAlign="center"
              >
                WELCOME TO O-GYM
              </Span>
              </Title>
            <Button
              width="192px"
              height="52px"
              margin="20px auto"
              backgroundColor="#18a0fb"
              padding="15.3px 65.8px 15.7px 65.2px"
              borderRadius="6px"
              color="white"
              onClick={()=> history.push('/login')}
            >Join Us</Button>
          </Box>
        </MainTextDiv>
        </>
    );
}

export default MainContent;