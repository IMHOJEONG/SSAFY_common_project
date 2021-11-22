import { Modal, Tabs } from "antd";
import { useHistory } from "react-router-dom";
import { useRecoilState, useRecoilValue } from "recoil";
import styled from "styled-components";
import LoginContent from "../../components/organisms/Login/Login-content";
import RegisterStudent from "../../components/organisms/Register/Register-Student";
import RegisterTrainer from "../../components/organisms/Register/Register-Trainer";
import { InputState } from "../../recoil/atoms/InputState";
// @ts-ignore
import jwt_decode from "jwt-decode";
const { TabPane } = Tabs;

const StyledTabs = styled(Tabs)`
    width: 100%;

    .ant-tabs-tab, .ant-tabs-nav-list {
        width: 100%;
    }  
`;

const StyledTabPane = styled(TabPane)`
    width: 100%;
`;

export interface LoginInfo {
    accessToken: string,
    refreshToken: string
}

const LoginPage = (): JSX.Element => {
        
    const history = useHistory();
    let accessToken = localStorage.getItem('accessToken');
    if (accessToken) {

        let { role }: { role: string }  = jwt_decode(accessToken);
        
        if (role === 'ROLE_PTSTUDENT') {
            history.push('/studentreservation');
        }
        else if (role === 'ROLE_PTTEACHER') {
            history.push('/teacherreservation');
        }
    }
    // const handleCancel = () => {
    //     setIsModalVisible(false);
    // }

    return (
        <>
            <Modal title={null}
                closable={false}
                visible={true}
                footer={null}
                // onOk={handleOk}
                // onCancel={handleCancel}
            >
                <StyledTabs  type="card">
                    <StyledTabPane tab="LOGIN" key="1">
                        <LoginContent />
                    </StyledTabPane>
                    
                    <StyledTabPane tab="REGISTER" key="2">
                        <StyledTabs type="card">
                          <StyledTabPane tab="TRAINEE" key="3">
                            <RegisterStudent />
                          </StyledTabPane>
                          <StyledTabPane tab="TRAINER" key="4">
                            <RegisterTrainer />
                          </StyledTabPane>
                        </StyledTabs>
                    </StyledTabPane>

                </StyledTabs>
            </Modal>
        </>
    );
}

export default LoginPage;
