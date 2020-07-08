import React from 'react';

export default function Post({ post }) {
  const { paymentId, userId, storeId, tokenSystemId, amount, paymentStatus, identifier, createdDate } = post;
  return (
    <div>
      <h1>결제번호: {paymentId}</h1>
      <p>요청자: {userId}</p>
      <p>구매처: {storeId}</p>
      <p>지불수단: {tokenSystemId}</p>
      <p>금액: {amount}</p>
      <p>결제 상태: {paymentStatus}</p>
      <p>승인번호: {identifier}</p>
      <p>요청일시: {createdDate}</p>
    </div>
  )
}
