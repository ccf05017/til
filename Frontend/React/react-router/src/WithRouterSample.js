import React from 'react';
import { withRouter } from 'react-router-dom';

function WithRouterSample({ location, match, history }) {
  return (
    <div>
      <h4>Location</h4>
      <textarea value={JSON.stringify(location, null, 2)} readOnly />
      <h4>match</h4>
      <textarea value={JSON.stringify(match, null, 2)} readOnly />
      <button onClick={() => history.push('/')}>Home</button>
    </div>
  );
};

export default withRouter(WithRouterSample);
