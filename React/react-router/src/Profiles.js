import React from 'react';
import Profile from './Profile';
import { Link, Route } from 'react-router-dom';

function Profiles() {
  return (
    <div>
      <ul>
        <li>
          <Link to="/profiles/poppo">poppo</Link>
        </li>
        <li>
          <Link to="/profiles/ita">ita</Link>
        </li>
      </ul>

      <Route
        path="/profiles"
        exact={true}
        render={() => <div>Select User</div>}
      />
      <Route path="/profiles/:username" component={Profile} />
    </div>
  );
}

export default Profiles;
