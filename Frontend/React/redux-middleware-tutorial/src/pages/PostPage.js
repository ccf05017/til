import React from 'react';
import PostContainer from '../containers/PostContainer';

export default function PostPage({ match }) {
  const { id } = match.params;

  return <PostContainer paymentId={parseInt(id, 10)} />;
}
