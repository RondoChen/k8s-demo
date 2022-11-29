import React, { Component } from 'react';
import { withRouter } from 'react-router';
import { List, Row, Col, Pagination } from 'antd';
import Util from '../../../Util';
import './index.less';

class Index extends Component {
  state = {
    products: [],
    page: 1,
    pageSize: 10,
    total: 0,
    category: null
  }
  loadCateDetail = (id) => {
    Util.getEve("product", `/apis/product-category/category/${id}/`).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ category: res.data });
    });
  }

  loadProducts = (cateId, pageSize, page) => {
    Util.getEve("product", `/apis/product/list/${cateId}/${page}/${pageSize}/`).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      this.setState({ products: res.data.items, total: res.data.total });
    });

  }

  componentDidMount() {
    const { id } = this.props.match.params;
    const { pageSize, page } = this.state;
    this.loadProducts(id, pageSize, page);
    this.loadCateDetail(id);
  }
  changePage = page => {
    this.setState({ page });
    this.loadProducts(this.props.match.params.id, this.state.pageSize, page);
  }

  render() {
    return (
      <div className='list'>
        {this.state.category && <div className='category'>
          <h4>
            {this.state.category.categoryTitle}
          </h4>
          <p>
            {this.state.category.categoryDescription}
          </p>
        </div>}
        <List
          grid={{
            gutter: 8,
            xs: 1,
            sm: 2,
            md: 2,
            lg: 2,
            xl: 3,
            xxl: 4,
          }}
          dataSource={this.state.products}
          renderItem={item => (
            <List.Item key={item.productId}>
              <Row gutter={10}>
                <Col span={6}>
                  <a href={"/mall/product/detail/" + item.productId}>
                    <img alt={item.title} src={item.coverImgUrl + "/200"} title={item.title} />
                  </a>
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
        {
          this.state.total > this.state.pageSize && <Pagination defaultCurrent={1}
            current={this.state.page}
            size={this.state.pageSize}
            total={this.state.total}
            onChange={this.changePage}
          />
        }
      </div>
    );
  }
}


export default withRouter(Index);