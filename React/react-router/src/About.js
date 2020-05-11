import React from 'react';
import qs from 'qs';

function About({ location }) {
  const query = qs.parse(location.search, {
    ignoreQueryPrefix: true
  });

  // query로 불러온 인자는 모두 문자열 타입이다.
  const detail = query.detail === 'true';

  return (
    <div>
      <h1>Introduction</h1>
      <p>
        This page is about practicing react router
      </p>
      {detail && <p>detail is true</p>}
    </div>
  )
}

export default About;
