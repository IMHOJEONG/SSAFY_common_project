import React from 'react';
import MainContent from "../../components/organisms/Main/Main-content"
import MainNavigation from "../../components/organisms/Main/Main-Navigation"

const MainPage = () : JSX.Element => {
    return (
        <>
            <MainNavigation></MainNavigation>
            <MainContent></MainContent>
        </>);
}

export default MainPage;