import './BasicLayout.less';
import React from 'react';
import { Layout, Menu, Dropdown, Divider, Button, Row, Col, Drawer, List } from 'antd';
import {
  SettingOutlined,
  MenuOutlined,
  ShoppingCartOutlined
} from '@ant-design/icons';
import Util from '../Util';
import I18nComponent from '../components/I18nComponent'
import resources from '../resources/Base';
import MediaQuery from 'react-responsive';


const { Header, Content, Footer } = Layout;

export default class BasicLayout extends I18nComponent {
  constructor(props) {
    super(props);
    this.resources = resources;
  }
  state = {
    mMenuVisible: false,
    tipShow: true,
    lang: 'zh-CN',
    noTip: true,
    catMenus: []
  };

  onMenuClose = () => {
    this.setState({ mMenuVisible: false });
  }
  onTipClose = () => {
    Util.setCookie("noTip", this.state.noTip, (new Date() * 1) / 1000 + 99999999);
    this.setState({ tipShow: false });
  }
  showMenu = () => {
    this.setState({ mMenuVisible: true });
  }

  langs = {
    "zh-CN": "简体中文",
    "en": "English"
  }

  loadCates = _ => {
    Util.getEve("product", "/apis/product-category/categories/").then(res => {
      // console.log(res);
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ catMenus: res.data.items });
    });
  }


  onCollapse = collapsed => {
    this.setState({ collapsed });
  };

  logout = _ => {
    Util.removeCookie('ticket');
    Util.removeCookie('tenant_id');
    Util.removeCookie('nick');
    Util.removeCookie('avatar');
    window.location.reload();
  };
  login = _ => {
    const url = window.location.pathname;
    window.location = "/account/login?back=" + encodeURIComponent(url);
  }


  componentDidMount() {
    // console.log(Util.getUser());
    this.setState({ lang: Util.getLang() });
    this.setState({ tipShow: Util.getCookie("noTip") !== "true" });
    const url = window.location.pathname;
    if (url.indexOf("/account") < 0) {
      this.loadCates();
    }
  }
  langChange = (val) => {
    console.log(val);
    Util.setLang(val.key);
    window.location.reload();
  }
  goList = id => {
    window.location = "/mall/product/list/" + id;
  }
  goShopcart = _ => {
    window.location = "/mall/shopcart";
  }

  render() {
    const user = Util.getUser();
    const { catMenus } = this.state;
    const menuLang = <Menu onClick={this.langChange} selectedKeys={[0]} mode="vertical">
      {
        Object.keys(this.langs).map(k =>
          <Menu.Item key={k}>{this.langs[k]}</Menu.Item>
        )
      }
    </Menu>;
    return (
      <Layout style={{ minHeight: '100vh' }}>
        <Drawer
          title={this.R("SiteName")}
          placement="right"
          closable={true}
          onClose={this.onTipClose}
          visible={this.state.tipShow}
          width="100%"
          style={{ background: 'rgba(117,120,123)' }}
        >
          <div style={{ textAlign: "center", fontSize: "20px", color: "#ff0000" }}>
            {this.R("TextDemoCaution")}
            <br /><br />
            <label>
              <input type="checkbox" checked={this.state.noTip}
                onChange={evt => {
                  this.setState({ noTip: evt.target.checked })
                }} /> {this.R("LabelNotShow")}</label>
            <br /><br />
            <Button type="primary" onClick={this.onTipClose} size="large">{this.R("LabelClose")}</Button>
          </div>
        </Drawer>
        <Layout style={{ padding: 0 }} className="a-content">
          <Header
            style={{
              background: '#eee',
              lineHeight: '24px',
              padding: 0, margin: 0,
              textAlign: 'right', paddingRight: "10px",
              height: "38px"
            }} className="header">
            <div>
              <a href="/mall/order">{this.R("LabelGreeting", user.nick || this.R("LabelDefaultNick"))}</a>
              <Divider type="vertical" />
              {
                user.ticket ? <>
                  <Button type="link" onClick={this.goShopcart} icon={<ShoppingCartOutlined />}>{this.R("MenuShopcart")}</Button>
                  <Button type="link" onClick={this.logout}>{this.R("MenuLogout")}</Button>
                </> :
                  <Button type="link" onClick={this.login}>{this.R("MenuLogin")}</Button>
              }
              {/* <Divider type="vertical" />
              {this.R("MenuProfile")} */}
            </div>
            <div>
              <Dropdown.Button overlay={menuLang} icon={<SettingOutlined />} type='link' />
            </div>
          </Header>

          <Header style={{ background: '#fff', padding: 0, margin: 0 }} >

            <Row>
              <Col xs={{ span: 24 }}
                sm={{ span: 22, offset: 1 }}
                md={{ span: 20, offset: 2 }}
                lg={{ span: 20, offset: 2 }}
                xl={{ span: 18, offset: 3 }} >
                <div className='logo'>
                  <a href='/'><img src="https://microservices-demo-mall-1305426035.cos.ap-beijing.myqcloud.com/mesh-shop-logo.png" title={this.R("SiteName")} alt={this.R("SiteName")} /></a>
                  <a href='/'>{this.R("SiteName")}</a>
                </div>
                <div className="main-menu">
                  <MediaQuery minDeviceWidth={1224}>
                    {
                      catMenus.map(item => (
                        <Button key={item.categoryId} type="link"
                          onClick={_ => { this.goList(item.categoryId) }}>
                          {item.categoryTitle}
                        </Button>
                      ))
                    }
                  </MediaQuery>
                  <MediaQuery maxDeviceWidth={1223}>
                    <MenuOutlined onClick={this.showMenu} style={{ fontSize: "20px", color: "#333", marginRight: "15px" }} />
                    <Drawer
                      title={this.R("SiteName")}
                      placement="right"
                      closable={true}
                      onClose={this.onMenuClose}
                      visible={this.state.mMenuVisible}
                      width="100%"
                      style={{ background: 'rgba(117,120,123)' }}
                    >
                      <List
                        itemLayout="horizontal"
                        dataSource={catMenus}
                        renderItem={item => (
                          <List.Item key={item.categoryId}>
                            <List.Item.Meta
                              title={<a href={"/mall/product/list/" + item.categoryId}>{item.categoryTitle}</a>}
                              description={item.categoryDescription}
                            />
                          </List.Item>
                        )}
                      />
                    </Drawer>
                  </MediaQuery>
                </div>
              </Col>
            </Row>

          </Header>
          <Content style={{ margin: '16px' }}>

            <Row>
              <Col xs={{ span: 24 }}
                sm={{ span: 22, offset: 1 }}
                md={{ span: 22, offset: 1 }}
                lg={{ span: 20, offset: 2 }}
                xl={{ span: 18, offset: 3 }} >
                {this.props.children}
              </Col>
            </Row>
          </Content>
          <Footer style={{ textAlign: 'center' }}> {this.R("Copyright")}</Footer>
        </Layout>
      </Layout >
    );
  }
}