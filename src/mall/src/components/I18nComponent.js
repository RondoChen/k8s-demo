import { Component } from 'react';
import Util from '../Util';

export default class I18nComponent extends Component {
  constructor(props) {
    super(props);
    this.lang = this.getBrowserLang();
    this.resources = {};
  }

  _t = (key) => {
    return this.resources[this.lang][key];
  }

  R = (key, ...args) => {
    let val = key;
    if (this.resources[this.lang] && this.resources[this.lang][key]) {
      val = this.resources[this.lang][key];
    }
    return val.indexOf("%s") >= 0 ? Util.formatString(val, args) : val;
  }

  getBrowserLang = () => Util.getLang();

}