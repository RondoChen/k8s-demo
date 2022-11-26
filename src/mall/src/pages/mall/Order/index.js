import React from 'react';
import './index.less';
import I18nComponent from '../../../components/I18nComponent';
import resources from '../../../resources/mall/Order';
import List from './List';

class Index extends I18nComponent {
  constructor(props) {
    super(props);
    this.resources = resources;
  }

  render() {
    return (
      <div className='list'>
        <h4>
          {this.R("LabelOrder")}
        </h4>
        <List owner={this} />
      </div>
    );
  }
}



export default Index;

