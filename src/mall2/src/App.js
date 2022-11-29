import React from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
} from "react-router-dom";


import BasicLayout from './layouts/BasicLayout';
import SimpleLayout from './layouts/SimpleLayout';
import routesConfig from './config/routes';
import cookie from 'react-cookies';
import I18nComponent from './components/I18nComponent';
import resources from './resources/Base';



const buildComponentRouter = (config) => {
  const { component, isSimpleLayout, ...rest } = config;
  const MyComponent = component;
  return <Route {...rest} exact>
    <MyComponent />
  </Route>
}

const routeMap = () => {
  const switchRouters = [];
  const simpleRouters = [];
  let index = 0;

  for (const ele of routesConfig) {
    const isSubMenu = ele.routes && ele.routes.length > 0;
    if (isSubMenu) {
      for (const sele of ele.routes) {
        sele.key = 'route' + index;
        const xRouter = buildComponentRouter(sele);
        if (sele.isSimpleLayout === true) {
          simpleRouters.push(xRouter);
        } else {
          switchRouters.push(xRouter);
        }
        index++;
      }
    } else {
      ele.key = 'route' + index;
      const yRouter = buildComponentRouter(ele);
      if (ele.isSimpleLayout === true) {
        simpleRouters.push(yRouter);
      } else {
        switchRouters.push(yRouter);
      }
      // container.push(<MyRoute path={ele.path} component={ele.component} simpleLayout={ele.simpleLayout} key={'route' + index} />);
    }
    index++;
  }
  return { switchRouters, simpleRouters };
};


export default class App extends I18nComponent {
  constructor(props) {
    super(props);
    this.resources = resources;
  }
  state = {
    isSimpleLayout: false
  }
  componentDidMount() {
    document.title = this.R("SiteName");
    const url = window.location.pathname;
    if (url.indexOf('/account') === 0) {
      this.setState({ isSimpleLayout: true });
      return;
    }
    const ticket = cookie.load('ticket');
    if (!ticket) {
      console.log("未登录...");
      //alert("")
      //window.location = "/account/login?back=" + encodeURIComponent(url);
    }
  }



  render() {
    console.log('Powered by Tencent Cloudnative Traning Camp');
    const routersMap = routeMap();
    return <Router>
      {
        this.state.isSimpleLayout ? <SimpleLayout>
          {routersMap.simpleRouters}
        </SimpleLayout> : <BasicLayout>
          <Switch>
            {routersMap.switchRouters}
          </Switch>
        </BasicLayout>
      }
    </Router>;
  }
}