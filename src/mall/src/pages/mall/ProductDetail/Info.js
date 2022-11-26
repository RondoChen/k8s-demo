import React from 'react';
import ChildComponent from '../../../components/ChildComponent';
import { withRouter } from 'react-router';
import { Row, Col, Button, Tabs, Rate, List, Avatar, Pagination, message } from 'antd';
import { Form, Input } from 'antd';
import resources from '../../../resources/mall/ProductDetail';
import Util from '../../../Util';
import './index.less';

const { TabPane } = Tabs;

class Info extends ChildComponent {
  constructor(props) {
    super(props);
    this.resources = resources;
  }

  state = {
    reviews: [],
    page: 1,
    pageSize: 10,
    total: 0,
    user: {},
    shopcarting: false
  }

  componentDidMount() {
    this.loadReviews(this.props.match.params.id, this.state.pageSize, this.state.page);
  }

  loadReviews = (productId, pageSize, page) => {
    Util.getEve("review", `/apis/product-review/list/${productId}/${page}/${pageSize}/`).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ reviews: res.data.items, total: res.data.total });
    });
  }

  changePage = page => {
    this.setState({ page });
    this.loadReviews(this.props.match.params.id, this.state.pageSize, page);
  }

  onFinish = values => {
    delete values.nick;
    const productId = this.props.match.params.id;
    values.productId = productId;
    Util.postEve("review", `/apis/product-review/post/`, values).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.loadReviews(productId, this.state.pageSize, 1);

      this.formRef.current.setFieldsValue({
        content: "",
      });
    });

  };

  onFinishFailed = errorInfo => {
    console.log('Failed:', errorInfo);
  };

  addCart = id => {
    this.setState({ shopcarting: true });
    Util.postEve("shopcart", `/user/shopcart`, { goods_id: id, quantity: 1 }).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ shopcarting: false });
      message.success(this.R("LabelShopcarted"));
    });
  }

  formRef = React.createRef();


  render() {
    const { product } = this.props;
    const user = Util.getUser();

    // const [reviewForm] = Form.useForm();
    // console.log(user);
    // const [reviewForm] = Form.useForm();
    // reviewForm.setFieldsValue({ nick: user.nick });

    return (
      product && <>
        <div className='brief'>
          <Row gutter={{ xs: 0, sm: 24 }}>
            <Col xs={24} sm={12}>
              <img src={product.productDetailTopImages[0].imgUrl} alt={product.title} className='cover' />
            </Col>
            <Col xs={24} sm={12}>

              <h1 type="primary">{product.title}</h1>
              <p>{product.subtitle} </p>
              <div>
                <Rate allowHalf value={3.5} disabled />
              </div>
              <p className="price">{product.tencentPrice.toFixed(2)} 元</p>
              <p>
                {
                  user.ticket ?
                    <Button type='primary' size='large'
                      style={{ width: '100%' }} disabled={this.state.shopcarting}
                      onClick={() => { this.addCart(product.productId) }}>{
                        this.state.shopcarting ? this.R("LabelShopcarting") : this.R("LabelBuy")}
                    </Button>
                    :
                    <Button type='primary' size='large'
                      style={{ width: '100%' }} disabled={true}>
                      {this.R("LabelBuyAfterLogin")}
                    </Button>
                }
              </p>
              <p>
                预计送达日期: 3 天后 <br />
                有现货<br />
                免费送货<br /><br />
              </p>
              <p>
                获得购买帮助， 立即在线交流 (在新窗口中打开) 或致电 400-000-0000
            </p>
            </Col>
          </Row>
        </div>
        <div className='detail'>

          <Tabs defaultActiveKey="1" size="large">
            <TabPane tab={this.R("LabelDetail")} key="1">
              <Row>
                <Col className='img-content'>
                  {
                    product && <>
                      {
                        product.productDetailContentImages.map(item => <img key={item.imgId} src={item.imgUrl} alt={item.imgId} />)
                      }
                    </>
                  }
                </Col>
              </Row>
            </TabPane>

            <TabPane tab={this.R("LabelReview")} key="3">
              <div style={{ minHeight: "700px" }}>
                <List
                  itemLayout="horizontal"
                  dataSource={this.state.reviews}
                  renderItem={item => (
                    <List.Item className="review">
                      <List.Item.Meta
                        avatar={
                          <Avatar src={item.reviewUserImgUrl || "https://microservices-demo-mall-1305426035.cos.ap-beijing.myqcloud.com/qq.png"} />
                        }
                        title={<>
                          <span>{item.reviewUserName}</span>
                          <span className='date'>{item.createDate}</span>
                        </>}
                        description={<>
                          <Rate allowHalf value={item.score} disabled />
                          <div className='content'>{item.content}</div>
                        </>}
                      />
                    </List.Item>
                  )}
                />

                {
                  this.state.total > this.state.pageSize && <Pagination defaultCurrent={1}
                    current={this.state.page}
                    size={this.state.pageSize}
                    total={this.state.total}
                    onChange={this.changePage}
                  />
                }


                <h4 style={{ marginTop: "40px" }}>{this.R("TitleReviewPublish")}</h4>
                <Form
                  layout="vertical" name="reviewForm"
                  onFinish={this.onFinish}
                  onFinishFailed={this.onFinishFailed}
                  initialValues={{
                    nick: user.nick,
                    score: 2.5
                  }}
                  ref={this.formRef}
                >
                  <Form.Item
                    name="nick"
                    rules={[{ required: true, message: 'Please input your nickname!' }]}
                  >
                    <Input value="2432" readOnly placeholder={this.R("ErrorNeedLogin")} />
                  </Form.Item>
                  <Form.Item name="score">
                    <Rate allowHalf />
                  </Form.Item>
                  <Form.Item
                    name="content"
                    rules={[{ required: true, message: this.R("ErrorContentRequired") }]}
                  >
                    <Input.TextArea rows={4} placeholder={this.R("LabelContent")} />
                  </Form.Item>
                  <Form.Item>
                    <Button type="primary"
                      htmlType="submit"
                      disabled={!user.ticket}>{this.R("LabelPublish")}</Button>
                  </Form.Item>
                </Form>
              </div>
            </TabPane>
          </Tabs>

        </div>
      </>
    );

  }
}


export default withRouter(Info);