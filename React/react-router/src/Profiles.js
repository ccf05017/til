import React from 'react';
import Profile from './Profile';
import { NavLink, Route } from 'react-router-dom';
import WithRouterSample from "./WithRouterSample";

function Profiles() {
  return (
    <div>
      <ul>
        <li>
          <NavLink to="/profiles/poppo" activeStyle={{ background: 'black', color: 'white' }}>poppo</NavLink>
        </li>
        <li>
          <NavLink to="/profiles/ita" activeStyle={{ background: 'black', color: 'white' }}>ita</NavLink>
        </li>
      </ul>

      <Route
        path="/profiles"
        exact={true}
        render={() => <div>Select User</div>}
      />
      <Route path="/profiles/:username" component={Profile} />
      <WithRouterSample />
    </div>
  );
}

export default Profiles;
