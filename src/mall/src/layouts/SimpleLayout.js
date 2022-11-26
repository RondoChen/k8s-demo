import './BasicLayout.less';
import React from 'react';
import { Layout, Row, Col } from 'antd';
import I18nComponent from '../components/I18nComponent';
import resources from '../resources/Base';
const { Header, Content, Footer } = Layout;

export default class BasicLayout extends I18nComponent {
    constructor(props) {
        super(props);
        this.resources = resources;
    }

    render() {
        return (
            <Layout style={{ minHeight: '100vh' }}>

                <Row>
                    <Col xxl={{ span: 4, offset: 10 }} lg={{ span: 6, offset: 9 }} md={{ span: 8, offset: 8 }} xs={{ span: 22, offset: 1 }}>

                        <Header style={{ background: 'rgba(255,255,255)', padding: 0, borderBottom: '1px', marginBottom: '20px' }}>
                            <div className='logo'>
                                <img src="https://microservices-demo-mall-1305426035.cos.ap-beijing.myqcloud.com/mesh-shop-logo.png" alt='logo' />
                                {this.R("SiteName")}
                            </div>
                        </Header>
                    </Col>
                </Row>
                <Content>
                    {this.props.children}
                </Content>
                <Footer style={{ textAlign: 'center' }}> {this.R("Copyright")} </Footer>
            </Layout>
        );
    }
}
