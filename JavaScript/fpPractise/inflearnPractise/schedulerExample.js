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
        return _.delay(1000, Impt.payments[page]);
    },

    cancelPayment: imp_id => Promise.resolve(`${imp_id}: 취소 완료`)
};

// 가맹점 DB Mock
const DB = {
    getOrders: ids => _.delay(100, [
        { id: 1 },
        { id: 3 },
        { id: 7 }
    ])
};

async function job() {
    const payments = await _.go(
        L.range(1, Infinity),
        L.map(Impt.getPayments),
        _.takeUntil(({ length }) => length < 3),
        _.flat
    );

    console.log(payments);
};

job();
