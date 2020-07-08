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

class Product extends Model {}

class Products extends Collection {
    totalPrice() {
        return _.go(
            this,
            L.map(p => p.get('price')),
            _.reduce((a, b) => a + b) 
        );
    }
}

const products = new Products();
products.add(new Product({ id: 1, price: 10000 }));
console.log(products.totalPrice());
products.add(new Product({ id: 3, price: 25000 }));
console.log(products.totalPrice());
products.add(new Product({ id: 5, price: 35000 }));
console.log(products.totalPrice());
