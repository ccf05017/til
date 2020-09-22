import React, { useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { getPost, goToHome } from '../modules/posts';
import Post from '../components/Post';

export default function PostContainer({ paymentId }) {
  const { data, loading, error } = useSelector(
    state => state.posts.paymentRefundReady[paymentId]
  ) || {
    loading: false,
    data: null,
    error: null
  };
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getPost({ id: paymentId }));
  }, [paymentId, dispatch]);

  if (loading && !data) return <div>로딩중...</div>;
  if (error) return <div>에러 발생!</div>;
  if (!data) return null;

  return (
    <>
      <button onClick={() => dispatch(goToHome())}>홈으로 이동</button>
      <Post post={data} />
    </>
  )
}
