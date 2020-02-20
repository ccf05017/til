const _ = require("fxjs/Strict"); 
const L = require("fxjs/Lazy");

const impt = {
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
        return _.delay(1000, impt.payments[page]);
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

// async function은 무조건 Promise를 리턴한다.
async function job() {
    // 결제된 결제모듈측 payments를 가져온다.
    // page 단위로 가져오는 걸 하나로 합친다. (내용이 없을 때까지 다 가져온다.)
    const payments = await _.go(
        L.range(1, Infinity),
        L.map(impt.getPayments),
        L.takeUntil(({ length }) => length < 3),
        _.flat
    );

    console.log(payments);
}

job();
