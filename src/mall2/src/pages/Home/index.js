import React from 'react';
import { Carousel, List, Card, Row, Col, Divider } from 'antd';
import I18 from '../../components/I18nComponent'
import resources from '../../resources/mall/Home';
import Util from '../../Util';
import './index.less';

const { Meta } = Card;

export default class Home extends I18 {
  constructor(props) {
    super(props);
    this.resources = resources;
  }

  state = {
    dataBanner: [],
    dataTopProducts: [],
    cateTopProducts: []
  }
  loadBanner = _ => {
    Util.getEve("product", "/apis/product-mall/banner-product-list/4/").then(res => {
      //  console.log('banner', res);
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      // console.log(1111, res);
      this.setState({ dataBanner: res.data.items });
    });
  }
  loadTopProducts = _ => {
    Util.getEve("product", "/apis/product-mall/top-product-list/12/").then(res => {
      //  console.log('ppp', res);
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      // console.log(1111, res);
      this.setState({ dataTopProducts: res.data });
    });
  }

  loadCateProducts = _ => {
    Util.getEve("product", "/apis/product-mall/product-list-in-categories/4/").then(res => {

      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ cateTopProducts: res.data.items });
    });
  }

  componentDidMount() {
    this.loadBanner();
    this.loadTopProducts();
    this.loadCateProducts();
  }
  render() {
    const { dataBanner, dataTopProducts } = this.state;
    return (
      <>
        <div className="banner">
          <Carousel autoplay effect="slide">
            {
              dataBanner.length > 0 &&
              dataBanner.map(ele => (
                <div key={ele.productId}>
                  <a href={"/mall/product/detail/" + ele.productId}><img src={ele.bannerImgUrl} alt={ele.title} /></a>
                </div>
              ))
            }
          </Carousel>

        </div>
        <div className="list">
          <h4>{dataTopProducts.categoryTitle}</h4>
          <List
            grid={{
              gutter: 8,
              xs: 2,
              sm: 2,
              md: 3,
              lg: 3,
              xl: 4,
              xxl: 4,
            }}
            dataSource={dataTopProducts.productList}
            renderItem={item => (
              <List.Item key={item.productId}>
                <a href={"/mall/product/detail/" + item.productId}>
                  <Card
                    hoverable
                    style={{ width: "100%" }}
                    cover={<img alt={item.title} src={item.coverImgUrl} title={item.title} />}
                  >
                    <Meta title={item.title} description={<div className="price">{"￥" + item.tencentPrice}</div>} />
                  </Card>
                </a>
              </List.Item>
            )}
          />
        </div>
        <div>
          {
            this.state.cateTopProducts.map(cate => <div className="list" key={cate.categoryId}>
              <Divider />
              <h4>{cate.categoryTitle}</h4>
              <List
                grid={{
                  gutter: 8,
                  xs: 2,
                  sm: 2,
                  md: 2,
                  lg: 2,
                  xl: 2,
                  xxl: 4,
                }}
                dataSource={cate.productList}
                renderItem={item => (
                  <List.Item key={item.productId}>
                    <Row gutter={10}>
                      <Col span={6}>
                        <a href={"/mall/product/detail/" + item.productId}><img alt={item.title} src={item.coverImgUrl + "/200"} title={item.title} /></a>
                      </Col>
                      <Col span={18}>
                        <h5><a href={"/mall/product/detail/" + item.productId}>{item.title}</a> </h5>
                        <p className='desc'>{item.subtitle} </p>
                        <p className='price'>{item.tencentPrice.toFixed(2)} 元 </p>
                      </Col>
                    </Row>
                  </List.Item>
                )}
              />
            </div>)

          }
        </div>
      </>
    );
  }
}

