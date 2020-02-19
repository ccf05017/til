# 사용자 정의 객체 다루기
- 사용자 정의 객체 또한 이터러블로 다룰 수 있다.

## 1. Map, Set
- 내장 객체지만, 실질적으로 사용자 정의 객체와 비슷하다.
- 다만 기본적으로 이터러블을 지원하기 때문에 다루기가 좀 더 쉽다.
- Map 예시(filter 적용)
```js
// 이터러블로 다루기
_.go(
    m,
    _.filter(([k, v]) => v % 2),
    _.takeAll,
    console.log
);

// 다시 Map으로 바꾸기
_.go(
    m,
    _.filter(([k, v]) => v % 2),
    entries => new Map(entries),
    console.log
);
```

-  Set 예시(reduce 사용)
```js
const s = new Set();
s.add(10);
s.add(20);
s.add(30);

const add = (a, b) => a + b;

console.log(_.reduce(add, s));
```

## 2. 내가 직접 만든 객체
- 실제로 직접 객체를 만들어서 이터러블 프로그래밍을 해보자
- 내가 만든 객체는 이터러블이 아니기 때문에 well-formed 이터러블을 직접 만들어줘야 한다.
- 이터러블만 지원하면 어떤 객체든 확장해서 사용할 수 있다.
- 중요한 건 OOP와 FP가 대치되는 개념이 아니라 잘 융화되는 개념이라는 점이다.
- 큰 그림은 OOP로 그리고 세부적인 동작은 FP로 접근하는 방법이 자주 사용된다.
- 그러나 OOP로 정의 된 구조까지도 이터러블로 바꿔서 FP로 다룰 수 있다.
- 아래와 같은 보편적인 자바스크립트 클래스 구조를 기반으로 실습을 진행한다.
```js
class Model {
    consturctor(attrs = {}) {
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

class Collection {
    consturctor(models = []) {
        this._models = models;
    }

    at(idx) {
        return this._models[idx];
    }

    add(model) {
        this._models.push(model);
        return this;
    }
}
```

- 전형적인 OOP 스타일 예제
```js
const modelCollection = new Collection();
modelCollection.add(new Model({ id: 1, name: 'AA' }));
modelCollection.add(new Model({ id: 3, name: 'BB' }));
modelCollection.add(new Model({ id: 5, name: 'CC' }));

console.log(modelCollection.at(2).get('name'));
console.log(modelCollection.at(1).get('id'));
```

- 이터러블 없이 강제로 이터러블 프로그래밍 해보기
```js
_.go(
    L.range(3),
    L.map(i => modelCollection.at(i)),
    L.map(m => m.get('name')),
    _.each(console.log)
);
```

- 위의 방식은 안되는 건 아니지만 뭔가 많이 이상하다.
- 매번 필요할 때마다 저렇게 파이프라인을 만들어줄 수는 없다.
- 이터러블을 자체적으로 지원하도록 바꿔보자
```js
class Collection {
    // ... 생략 ...
    *[Symbol.iterator]() {
        for (const model of this._models) {
            yield model;
        }
    }
}
```

- 이터러블을 지원하기 때문에 제대로 처리할 수 있다.
```js
_.go(
    modelCollection,
    L.map(m => m.get('name')),
    _.each(console.log)
);
```

## 3. 직접 만든 객체 응용
- 상속까지 적용해보자.
```js
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
```

- 대개의 경우 컬렉션 형태를 이터러블로 만들면 해당 컬렉션을 하나씩 꺼내주는 형태로 접근하게 된다.
