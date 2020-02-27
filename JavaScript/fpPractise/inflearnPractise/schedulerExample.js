const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");

const Impt = {
    payments: {
        1: [
            { imp_id: 11, order_id: 1, amount: 15000 },
            { imp_id: 12, order_id: 2, amount: 25000 },
            { imp_id: 13, order_id: 3, amount: 10000 }
        ],
        2: [
            { imp_id: 14, order_id: 4, amount: 25000 },
            { imp_id: 15, order_id: 5, amount: 45000 },
            { imp_id: 16, order_id: 6, amount: 15000 }
        ],
        3: [
            { imp_id: 17, order_id: 7, amount: 20000 },
            { imp_id: 18, order_id: 8, amount: 30000 }
        ],
        4: [],
        5: [],
        // ,,,
    },

    // ImPort API Mock
    getPayments: page => {
        console.log(`http://..?page=${page}`);
        return _.delay(100 * 3, Impt.payments[page]);
    },

    cancelPayment: imp_id => Promise.resolve(`${imp_id}: 취소 완료`)
};

// 가맹점 DB Mock
// 현재는 뭘 넣건 정해진 값만 준다.
const DB = {
    getOrders: ids => _.delay(100, [
        { id: 1 },
        { id: 3 },
        { id: 7 }
    ])
};

async function job() {
    // API 1회당 최대로 불러올 수 있는 데이터의 양
    const max_number = 3;

    // payments의 마지막까지 돌면서 모든 자료를 뽑아내자
    const payments = await _.go(
        L.range(1, Infinity),
        L.map(Impt.getPayments),
        _.takeUntil(({ length }) => length < max_number),
        _.flat
    );

    // 가맹점 DB의 Payments 정보들을 빼내자
    const order_ids = await _.go(
        payments,
        _.map(({ order_id }) => order_id),
        _.flat,
        DB.getOrders,
        _.map(({ id }) => id),
        _.flat
    );

    // 결제가 정상완료 된(가맹정 DB에 있는) 정보들 외에는 취소 처리
    await _.go(
        payments,
        _.reject(({ order_id }) => order_ids.includes(order_id)),
        _.map(({ imp_id }) => imp_id),
        L.map(Impt.cancelPayment),
        _.each(console.log)
    )
};

// 7초 주기로 같은 작업을 반복하도록 재귀처리
(function recur() {
    Promise.all([
        _.delay(7000, undefined),
        job(),
    ]).then(recur);
})();
