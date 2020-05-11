import React, { useEffect } from 'react';

function HistorySample({ history }) {
  const goBack = () => {
    history.goBack();
  };

  const goHome = () => {
    history.push('/');
  };

  const replaceToHome = () => {
    history.replace('/');
  };

  useEffect(() => {
    console.log(history);
    const unblock = history.block('Really leave?');
    return () => {
      unblock();
    }
  }, [history]);

  return (
    <div>
      <button onClick={goBack}>Back</button>
      <button onClick={goHome}>Home</button>
      <button onClick={replaceToHome}>Home (Replace)</button>
    </div>
  );
}

export default HistorySample;
