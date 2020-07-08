import axios from 'axios';

export const getPosts = async () => {
  const response = await axios.get('http://api.apay.ga:8080/payment/store/2/refundReady?pageSize=10');
  const { content } = response.data.data;
  return content;
}

export const getPostById = async ({ id }) => {
  const response = await axios.get(`http://api.apay.ga:8080/payment/${id}/refundReadyDetail`);
  const { data } = response.data;
  return data;
}
