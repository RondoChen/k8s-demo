import I18nComponent from './I18nComponent';

export default class ChildComponent extends I18nComponent {
  constructor(props) {
    super(props);
    const { name, owner } = this.props;
    if (!owner) {
      throw new Error("Please set 'owner' property, sample: <ChildComponent owner={this} ref='compName' />");
    }
    this.owner = owner;
    if (name) {
      if (!owner.children) {
        owner.children = {};
      }
      if (owner.children[name]) {
        console.warn("'" + name + "' has already prefered, please change another name.");
      }
      owner.children[name] = this;
    }
  }
}