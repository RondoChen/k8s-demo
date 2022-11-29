import React, { Component } from 'react';
import { withRouter } from 'react-router';
import Util from '../../../Util';
import Info from './Info';

class Index extends Component {
  state = {
    product: null
  }

  loadProduct = id => {
    Util.getEve("product", "/apis/product/detail/" + id + "/").then(res => {
      // console.log('LLLLLLLLl', res);
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      // console.log(1111, res);
      this.setState({ product: res.data });
    });

  }

  componentDidMount() {
    this.loadProduct(this.props.match.params.id)
  }

  render() {
    return (
      <div>
        <Info owner={this} product={this.state.product} />
      </div>
    );
  }
}


export default withRouter(Index);