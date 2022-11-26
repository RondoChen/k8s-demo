import React from 'react';
import { Table } from 'antd';
import ChildComponent from '../../../components/ChildComponent';
import resources from '../../../resources/mall/Order'
import './index.less';
import Util from '../../../Util';
import moment from 'moment';



class List extends ChildComponent {
  constructor(props) {
    super(props);
    this.resources = resources;

  }

  state = {
    orders: [],
    total: 0,
    pageSize: 20
  }

  componentDidMount() {
    this.loadOrders();
  }


  loadOrders = (page) => {
    page = page || 1;
    Util.getEve("order", "/user/orders", { page }).then(res => {
      if (!res.success) {
        console.error(res.error.message);
        return;
      }
      // console.log(res.data);
      this.setState({ orders: res.data.items, total: res.data.total, pageSize: res.data.limit });
    });
  }

  render() {
    const orderCols = [
      {
        title: this.R("order_create_date"),
        dataIndex: 'create_date',
        key: 'create_date',
        render: (cDate) => {
          return moment(cDate).format("YYYY-MM-DD HH:mm");
        }
      },
      {
        title: this.R("order_total"),
        dataIndex: 'total',
        key: 'total',
      },
      {
        title: this.R("order_memo"),
        dataIndex: 'memo',
        key: 'memo',
      },
      // {
      //   title: this.R("LabelDetail"),
      //   dataIndex: 'id',
      //   key: 'id',
      //   render: (id, record) => {
      //     return <Button>{this.R("LabelOrderDetail")}</Button>
      //   }
      // }
    ];
    const orders = this.state.orders.map(el => {
      el.key = el.id;
      return el;
    })

    return (
      <div>
        <Table dataSource={orders} columns={orderCols}
          pagination={{ total: this.state.total, size: this.state.pageSize }} />
      </div>

    );
  }
}




export default List;