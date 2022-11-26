import React from 'react';
import {
  Form,
  Input,
  Checkbox,
  Button,
} from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { LockOutlined } from '@ant-design/icons';

import ChildComponent from '../../../components/ChildComponent';
import resources from '../../../resources/account/Login';
import util from '../../../Util';


class LoginForm extends ChildComponent {
  constructor(props) {
    super(props);
    this.resources = resources;
  }
  state = {
    loginErrorText: '',
    expires_in: 0,
    tenants: [],
    logging: false
  };
  hasErrors = (fieldsError) => {
    return Object.keys(fieldsError).some(field => fieldsError[field]);
  }

  componentDidMount() {
  }


  goBack = _ => {
    const query = util.parseQuery();
    window.location = query.back;
  }

  handleSubmit = values => {
    this.setState({ loginErrorText: '', logging: true });
    // console.log("object");
    util.postEve('passport', '/open/account/login', values).then(res => {
      // console.log(res);
      if (!res.success) {
        this.setState({ loginErrorText: res.error.message });
        return;
      }
      this.setState({ logging: false });
      util.setCookie('nick', res.data.nick, res.data.expires_in);
      util.setCookie('avatar', res.data.avatar, res.data.expires_in);
      util.setCookie('ticket', res.data.ticket, res.data.expires_in);
      // this.setState({ expires_in: res.data.expires_in });

      this.goBack();
    });

  };

  render() {
    return (
      <div className="login-form">
        <h4>{this.R("TitleLogin")}</h4>
        <Form onFinish={this.handleSubmit}>
          <Form.Item name="passport"
            rules={[{ required: true, message: this.R("ErrorPassportRequired") }]}>
            <Input
              prefix={<UserOutlined style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder={this.R("TipPassport")}
            />
          </Form.Item>
          <Form.Item name="password"
            rules={[{ required: true, message: this.R("ErrorPasswordRequired") }]}>
            <Input.Password
              prefix={<LockOutlined style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder={this.R("TipPassword")}
            />
          </Form.Item>
          <Form.Item>
            <Checkbox>{this.R("LabelRememberMe")}</Checkbox>
            <a className="login-form-forgot" href="/account/register">{this.R("LabelRegister")}</a>
            <Button type="primary" htmlType="submit" className="login-form-button" disabled={this.state.logging}>
              {this.state.logging ? this.R("LabelLogging") : this.R("LabelLogin")}
            </Button>

            <div className="error">{this.state.loginErrorText}</div>

          </Form.Item>
        </Form>
      </div>

    );
  }
}

export default LoginForm;