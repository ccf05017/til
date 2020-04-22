const _ = require("fxjs/Strict");
const L = require("fxjs/Lazy");
const C = require("fxjs/Concurrency");
const axios = require("axios");

const axiosRequest = (method, url, data) => {
    return axios({
        method,
        url: url,
        data,
        headers: {
            'Access-Control-Allow-Origin': '*'
        }
    })
};

async function job(bulkCount, method, url, data) {
    console.log("============= job started =============");
    await _.go(
        L.range(bulkCount),
        L.map(async _ => {
            try {
                return await axiosRequest(method, url, data)
            } catch (e) {
                return e;
            }
        }),
        L.map(({ data }) => console.log(data)),
        C.takeAll
    );
}

const scheduler = (delayTime, bulkCount, method, url, data) => {
    (function recur() {
        Promise.all([
            _.delay(delayTime, undefined),
            job(bulkCount, method, url, data)
        ]).then(recur);
    })();
};

scheduler(1000, 1000, 'get', 'http://localhost:9000/send');
