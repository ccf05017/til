import React from 'react';
import { Link } from 'react-router-dom';

// posts: 포스트 목록
function PostList({ posts }) {
  return (
    <ul>
      {posts.map(post => (
        <li key={post.paymentId}>
          <Link to={`/${post.paymentId}`}>환불금액: {post.amount} 요청일: {post.createdDate}</Link>
        </li>
      ))}
    </ul>
  );
}

export default PostList;
