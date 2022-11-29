import React from 'react';
import {
  LoadingOutlined,
} from '@ant-design/icons';
// import { withRouter } from 'react-router';
import { Row, Col, InputNumber, Divider, Button, Switch, Modal, Form, Input, Radio } from 'antd';
import Util from '../../../Util';
import './index.less';
import I18nComponent from '../../../components/I18nComponent';
import resources from '../../../resources/mall/Shopcart'

class Index extends I18nComponent {
  constructor(props) {
    super(props);
    this.resources = resources;
  }

  state = {
    carts: [],
    cartLoaded: false,
    cartChanging: false,
    cartTotal: 0,
    checkedCarts: [],
    orderMakerVisible: false
  }

  loadShopcart = _ => {
    const user = Util.getUser();
    if (!user.ticket) {
      this.setState({ cartLoaded: true });
      return false;
    }
    Util.getEve("shopcart", `/user/shopcart`).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ carts: res.data, cartLoaded: true });
    });

  }

  componentDidMount() {
    this.loadShopcart();
  }

  changeCarts = (goods_id, quantity) => {
    const carts = this.state.carts;
    const cart = carts.filter(ele => ele.goods_id === goods_id)[0];
    const cartIndex = carts.indexOf(cart);
    if (quantity > 0) {
      cart.quantity = quantity;
    } else {
      carts.splice(cartIndex, 1);
    }
    const cartChanging = false;
    this.setState({ carts, cartChanging });
  }

  onQuantityChange = (goods_id, quantity) => {
    if (quantity === '') {
      return false;
    }
    this.setState({ cartChanging: true });
    Util.putEve("shopcart", `/user/shopcart`, {
      goods_id, quantity
    }).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.changeCarts(goods_id, quantity);
    });
  }
  onGoodsCheck = (checked, item) => {
    const checkedCarts = this.state.checkedCarts;
    if (checked) {
      checkedCarts.push(item);
    } else {
      let xIndex = -1;
      checkedCarts.forEach((ele, index) => {
        if (ele.goods_id === item.goods_id) {
          xIndex = index;
        }
      });
      if (xIndex >= 0) {
        checkedCarts.splice(xIndex, 1);
      }
    }
    const cartTotal = checkedCarts.reduce((curVal, ele1) => (ele1.price * ele1.quantity + curVal), 0);

    // console.log(checkedCarts, cartTotal);
    this.setState({
      checkedCarts, cartTotal
    })
  }
  showOrderMaker = _ => {
    this.setState({ orderMakerVisible: true });
  }

  onSaveOrder = values => {
    // console.log(values, this.state.checkedCarts);
    // const orderData = values;
    values.items = this.state.checkedCarts.map(ele => ({
      goods_id: ele.goods_id,
      quantity: ele.quantity
    }));
    Util.postEve("order", `/user/order?ticket=` + Util.getCookie("ticket"), values).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      window.location = "/mall/order"
    });

  }

  render() {
    return (
      <div className='list'>
        <h4>
          {this.R("LabelShopcart")}
        </h4>
        {
          this.state.cartLoaded ? null : <div style={{ textAlign: "center" }}>
            <LoadingOutlined style={{ fontSize: '26px', color: '#08c' }} /> Loading...
          </div>
        }
        {
          this.state.carts.map(item => (
            <Row key={"k" + item.goods_id}>
              <Col lg={{ span: 4 }} md={{ span: 8 }} xs={{ span: 8 }} >
                <a href={"/mall/product/detail/" + item.goods_id}>
                  <img src={item.cover_img_url} alt={item.title} className="litte-img" />
                </a>
              </Col>
              <Col lg={{ span: 20 }} md={{ span: 16 }} xs={{ span: 16 }}>
                <div className="title">
                  <a href={"/mall/product/detail/" + item.goods_id}>{item.title}</a>
                </div>
                <div className="price">{item.price.toFixed(2)}</div>
                <div className="quantity">
                  <InputNumber min={0} max={500} defaultValue={item.quantity} disabled={this.state.cartChanging}
                    onChange={(val) => { this.onQuantityChange(item.goods_id, val) }} />
                </div>
                <div className="quantity">
                  {this.R("LabelSum")}: {(item.price * item.quantity).toFixed(2)}
                </div>
                <div>
                  <Switch checkedChildren={this.R("LabelChoosePay")}
                    unCheckedChildren={this.R("LabelChooseNotPay")}
                    defaultChecked={false} onChange={checked => {
                      this.onGoodsCheck(checked, item)
                    }} />
                </div>
              </Col>
              <Divider />
            </Row>
          ))
        }
        {
          this.state.cartLoaded && this.state.carts.length > 0 ?
            <>
              <div style={{ textAlign: "right", fontSize: "16px", marginBottom: "20px" }}>
                {this.R("LabelShopcartTotal")}: {this.state.cartTotal.toFixed(2)}
              </div>
              <div style={{ textAlign: "center" }} size="large">
                <Button type="primary" disabled={this.state.cartTotal === 0} onClick={this.showOrderMaker}>{this.R("LabelPay")}</Button>
              </div>
            </> : <div>No goods.</div>
        }

        <Modal
          title={this.R("TitleOrderMaker")}
          visible={this.state.orderMakerVisible}
          onCancel={_ => { this.setState({ orderMakerVisible: false }) }}
          footer={null}
        >
          <Form
            labelCol={{ span: 4 }}
            wrapperCol={{ span: 14 }}
            layout="horizontal"
            onFinish={this.onSaveOrder}
          >
            <Form.Item label={this.R("order_zipcode")} name="zipcode"
              rules={[{ required: true }]}
            >
              <Input />
            </Form.Item>
            <Form.Item label={this.R("order_district")} name="district"
              rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item label={this.R("order_address")} name="address"
              rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item label={this.R("order_consignee_name")} name="consignee_name"
              rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item label={this.R("order_consignee_tel")} name="consignee_tel"
              rules={[{ required: true }]}>
              <Input />
            </Form.Item>
            <Form.Item label={this.R("LabelPayType")}>
              <Radio.Group defaultValue="a">
                <Radio.Button value="a">货到付款</Radio.Button>
                <Radio.Button value="b" disabled={true}>微信支付</Radio.Button>
              </Radio.Group>
            </Form.Item>
            <Form.Item label={this.R("order_memo")} name="memo"   >
              <Input.TextArea rows={2} />
            </Form.Item>
            <div style={{ textAlign: "center" }}>
              <Button type="primary" htmlType="submit">
                Submit</Button>
            </div>

          </Form>
        </Modal>
      </div>
    );
  }
}


export default Index;