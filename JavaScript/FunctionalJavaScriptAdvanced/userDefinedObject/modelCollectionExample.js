const _ = require("fxjs/Strict"); 
const L = require("fxjs/Lazy");

Model = class Model {
    constructor(attrs = {}) {
        this._attrs = attrs;
    }

    get(k) {
        return this._attrs[k];
    }

    set(k, v) {
        this._attrs[k] = v;
        return this
    }
}

Collection = class Collection {
    constructor(models = []) {
        this._models = models;
    }

    at(idx) {
        return this._models[idx];
    }
    add(model) {
        this._models.push(model);
        return this;
    }
    *[Symbol.iterator]() {
        // yield *this._models; (같은 표현)
        for (const model of this._models) {
            yield model;
        }
    }
}

const modelCollection = new Collection();
modelCollection.add(new Model({ id: 1, name: 'AA' }));
modelCollection.add(new Model({ id: 3, name: 'BB' }));
modelCollection.add(new Model({ id: 5, name: 'CC' }));

// console.log(modelCollection.at(2).get('name'));
// console.log(modelCollection.at(1).get('id'));

// 강제로 이터러블 써보기
// _.go(
//     L.range(3),
//     L.map(i => modelCollection.at(i)),
//     L.map(m => m.get('name')),
//     _.each(console.log)
// );

// 제대로 된 이터러블
_.go(
    modelCollection,
    L.map(m => m.get('name')),
    _.each(console.log)
);


