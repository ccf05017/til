const FxJS = require("fxjs");
const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");

const fetch = require('node-fetch');

const $ = {};

$.el = html => {
    const wrapper = document.createElement("div");
    wrapper.innerHTML = html;

    return wrapper.children[0];
};

$.qs = (sel, parent=document) => parent.querySelector(sel);
$.qsa = (sel, parent=document) => parent.querySelectorAll(sel);

$.find = _.curry($.qs);
$.findAll = _.curry($.qsa);

$.addEl = _.curry((target, el) => target.appendChild(el));

$.closest = _.curry((sel, el) => el.closest(sel));

$.remove = el => el.parentNode.removeChild(el);

$.addEvent = (e, f) => els => _.each(
    el => el.addEventListener(e, f),
    _.isIterable(els) ? els : [els]
);

$.addClass = _.curry((name, el) => el.classList.add(name));

const userBalanceHtml = ({data}) => `
    <div class="balance">${data.balance} 코인<div>
`;

const buttonHtml = () => `
    <button type=button value="결제">결제</button>
`;

const payInputHtml = () => `
    <input class="pay-input" />
`;

const tokenSystemId = 4;
const userId = 2;

// 서버 통신 모킹 처리
const mockServerData = {
  data: {
      balance: 50
  }
};
const getUserBalance = () => new Promise(resolve => setTimeout(() => resolve(mockServerData), 200));

const fetchToJson = res => res.json();

const draw = {};

const payFirstPageHtml = () => `
    <div class="phone-content">
        <div class="nav-bar-container">
            <div class="title-box">Demo</div>
            <div class="menu-button"></div>
        </div>
        <div class="main-content-container">
            <div class="token-info">
                <img src="token-info.png" alt="" class="token-info-img">
                <div class="token-info-name">이런저런상품권</div>
            </div>
            <div class="balance-info">
                <div class="display-name">잔액</div>
            </div>
            <div class="token-img-wrapper">
                <img src="add.png" alt="" class="add-token">
            </div>
        </div>
        <div class="primary-button-line-container">
            <div class="primary-button">
                <div class="primary-button-img charge"></div>
                <div class="primary-button-name">충전</div>
            </div>
            <div class="primary-button">
                <div class="primary-button-img present"></div>
                <div class="primary-button-name">선물</div>
            </div>
            <div class="primary-button">
                <div class="primary-button-img register"></div>
                <div class="primary-button-name">등록</div>
            </div>
        </div>
        <div class="pay-button-container">
            <div class="pay-button">결제하기</div>
        </div>
    </div>
`;

draw.draw = () => {
    _.go(
        payFirstPageHtml,
        $.el,
        $.addEl($.qs('.phone-container'))
    );

    _.go(
        getUserBalance(),
        userBalanceHtml,
        $.el,
        $.addEl($.qs('.balance-info'))
    );
};

export default draw;
