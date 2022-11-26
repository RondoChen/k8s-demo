import React from 'react';
import {
    Row, Col
} from 'antd';

import I18nComponent from '../../../components/I18nComponent';
import resources from '../../../resources/account/Login';
import LoginForm from './LoginForm';
import './index.less'


export default class Index extends I18nComponent {
    constructor(props) {
        super(props);
        this.resources = resources;
    }


    render() {
        return (

            <Row>
                <Col xxl={{ span: 4, offset: 10 }} lg={{ span: 6, offset: 9 }} md={{ span: 8, offset: 8 }} xs={{ span: 22, offset: 1 }}>
                    <h1>ccccc</h1>
                    <LoginForm owner={this}></LoginForm>
                </Col>
            </Row>

        );
    }
}
