# 프론트엔드를 이터러블로 다뤄보자
- 프론트에도 함수형을 적용할 수 있다.
- 어쩌면 여기가 더 많이 필요한 곳일 수 있다.

## 1. 무지막지하게 많은 이미지를 다뤄보자
- 간소하게 표현되는 것들은 인자이다. (함수형 고인물들의 암묵적 약속 같은 것)

## 2. 들어온 이미지를 제거해보자

## 3. 커스텀 경고창
- 기본 경고창은 모바일 환경의 설정에 따라 보이지 않을 수 있다.
- 기본 경고창은 형태가 정해져 있기 때문에 UX를 저하시킬 수 있다.
- _.tap(): 파이프라인의 위에서 내려온 인자를 그대로 아래로 전달한다.

## 4. 클래스를 함수형으로 다루기
- 아래의 상황에서 두가지의 중복이 발생하고 있다.
- 비슷한 동작을 수행하는 Ui 모듈의 confirm, alert 중복
- Ui 모듈의 세부 모듈 안에서 버튼이 추가될 때마다 _.tap()함수의 추가 중복
- 이 중복을 모두 함수형 패러다임으로 전환해서 해결할 수 있다.
```js
Ui.confirm = msg => new Promise(resolve => _.go(
    `
        <div class="confirm">
            <div class="body">
                <div class="msg">${msg}</div>
                <div class="buttons">
                    <button type="button" class="cancel">취소</div>        // <- 이 부분과
                    <button type="button" class="ok">확인</div>
                </div>
            </div>
        </div>
    `,
    $.el,
    $.append($.qs('body')),
    _.tap(                              // <- 이 부분은 늘 쌍으로 움직인다. 아래의 버튼도 마찬가지다.
        $.find('.ok'),
        $.on('click', e => _.go(
            e.currentTarget,
            $.closest('.confirm'),
            $.remove,
            _ => resolve(true)
        ))
    ),
    _.tap(
        $.find('.cancel'),
        $.on('click', e => _.go(
            e.currentTarget,
            $.closest('.confirm'),
            $.remove,
            _ => resolve(false)
        ))
    )
));

Ui.alert = msg => new Promise(resolve => _.go(
    `
        <div class="confirm">
            <div class="body">
                <div class="msg">${msg}</div>
                <div class="buttons">
                    <button type="button" class="ok">확인</div>
                </div>
            </div>
        </div>
    `,
    $.el,
    $.append($.qs('body')),
    _.tap(
        $.find('.ok'),
        $.on('click', e => _.go(
            e.currentTarget,
            $.closest('.confirm'),
            $.remove,
            _ => resolve(true)
        ))
    )
));
```

- 위의 중복 체거를 위한 추상화를 잔뜩 높인 코드
```js
Ui.message = ({ msg, btns }) => new Promise(resolve => _.go(
    `
        <div class="confirm">
            <div class="body">
                <div class="msg">${msg}</div>
                <div class="buttons">
                    ${_.strMap(btn => `
                        <button type="button" class="${btn.type}">${btn.name}</div>
                    `, btns)}
                </div>
            </div>
        </div>
    `,
    $.el,
    $.append($.qs('body')),
    // 이 부분에서 map + 전개연산자 통해 함수를 평가시킨다.
    ..._.map(btn => _.tap(
        $.find(`.${btn.type}`),
        $.on('click', e => _.go(
            e.currentTarget,
            $.closest('.confirm'),
            $.remove,
            _ => resolve(btn.value)
        ))
    ), btns)
));
```

- 여기에 커링까지 끼얹으면 마치 상위 클래스로 서브 클래스를 만드는 것 같은 효과를 낸다.
```js
Ui.message = _.curry((btns, msg) => new Promise(resolve => _.go(
    `
        <div class="confirm">
            <div class="body">
                <div class="msg">${msg}</div>
                <div class="buttons">
                    ${_.strMap(btn => `
                        <button type="button" class="${btn.type}">${btn.name}</div>
                    `, btns)}
                </div>
            </div>
        </div>
    `,
    $.el,
    $.append($.qs('body')),
    // 이 부분에서 map + 전개연산자 통해 함수를 평가시킨다.
    ..._.map(btn => _.tap(
        $.find(`.${btn.type}`),
        $.on('click', e => _.go(
            e.currentTarget,
            $.closest('.confirm'),
            $.remove,
            _ => resolve(btn.value)
        ))
    ), btns)
)));

Ui.confirm = Ui.message(
    [
        { name: '취소', type: 'cancel', value: false },
        { name: '확인', type: 'ok', value: true }
    ]
);

Ui.alert = Ui.message(
    [
        { name: '확인', type: 'ok', value: true }
    ]
);
```

## 5. 이미지 동시성 다루기
- 이미지가 고용량이 될수록 현재의 코드는 이미지가 제각각 생기는 현상이 심해진다.
- 이터러블 프로그래밍을 통해 이런 비동기 상황을 다룰 수 있다.
- 아래 예시는 지연 처리를 통해 하나씩 준비된 이미지를 불러온다.
```js
// 이미지 로딩 시간을 제어하는 함수
_.tap(
    $.findAll('img'),
    L.map(img => new Promise(resolve => {           // 이 부분에서는 로드된 이미지를 준비만 한다.
        img.onload = () => resolve(img);
        img.src = img.getAttribute('lazy-src')
    })),
    _.each(img => img.classList.add('fade-in'))    // 실제로 여기서 이미지를 불러옴
),
```

- 모두 동시에 처리하기 위한 함수
```js
_.tap(
    $.findAll('img'),
    L.map(img => new Promise(resolve => {
        img.onload = () => resolve(img);
        img.src = img.getAttribute('lazy-src')
    })),
    C.takeAll,
    _.each($.addClass('fade-in'))
),
```

## 6. 부하 조절하기
- 동시에 모두 불러올 때 초기에 너무 과부하가 심하다.
- 이 부하를 분산시켜서 좀 더 UX를 향상시켜보자
- 기존의 작업에 부하분산기가 추가된 예제
```js
// 부하를 분산시켜주는 함수
lazy => {
    const r = L.range(Infinity);
    return _.go(
        lazy,
        _.groupBy(_ => Math.floor(r.next().value / 4)),
        L.values,
        L.map(L.map(f => f())),
        L.map(C.takeAll),
        _.each(_.each($.addClass('fade-in')))
    );
}
```

- 위와 같은 상황이 자주 발생할 것 같다면 이를 모듈화 하는 것도 가능하다.
```js
Images.loader = limit => _.tap(
    $.findAll('img'),
    L.map(img => _ => new Promise(resolve => {
        img.onload = () => resolve(img);
        img.src = img.getAttribute('lazy-src')
    })),

    // 부하를 분산시켜주는 함수
    lazy => {
        const r = L.range(Infinity);
        return _.go(
            lazy,
            _.groupBy(_ => Math.floor(r.next().value / limit)),
            L.values,
            L.map(L.map(f => f())),
            L.map(C.takeAll),
            _.each(_.each($.addClass('fade-in')))
        );
    }
);
```

## 7. 고차함수를 사용해서 더 잘게 나눠보자
- 고차함수의 특징은 도메인(데이터형)이 없다.
- 추상화를 극도로 높인 함수
- 데이터형과 관련된 부분을 모두 분리해내고 데이터형과 관련 없는 부분을 추상화 한다.
- 데이터형 관련 코드 분리
```js
// lazy 함수 내부의 특정 데이터형을 모두 제거함
lazy => {
    const r = L.range(Infinity);
    return _.go(
        lazy,
        _.groupBy(_ => Math.floor(r.next().value / limit)),
        L.values,
        L.map(L.map(f => f())),
        L.map(C.takeAll)
    );
},
_.each(_.each($.addClass('fade-in')))   // <- img 타입과 관련된 함수를 밖으로 빼냄
```

- 이제 위 함수의 추상화를 높여 범용적인 함수를 만들 준비가 됐다.
- 지연 평가가 준비된 이터러블을 받아 limit만큼씩 동시에 실행하는 함수를 만들 수 있다.
```js
// 부하를 분산시켜주는 함수
C.takeAllWithLimit = _.curry((limit, iter) => {
    const r = L.range(Infinity);

    return _.go(
        iter,
        _.groupBy(_ => Math.floor(r.next().value / limit)),
        L.values,
        L.map(L.map(f => f())),
        L.map(C.takeAll)
    );
});
```

- 위의 코드를 통해 높은 추상화를 얻고 범용적으로 재사용할 수 있는 함수 모듈을 추출할 수 있다.

## 8. 상위 스코프 변수를 사용하는 함수와 아닌 함수를 쪼개기
- 좀 더 추상화되고 사용하기 쉬운 함수를 만들 수 있다.
- 아래의 코드에서 groupBy 함수를 제외하면 아무도 상위 스코프의 r 상수를 사용하지 않는다.
```js
C.takeAllWithLimit = _.curry((limit, iter) => {
    const r = L.range(Infinity);

    return _.go(
        iter,
        _.groupBy(_ => Math.floor(r.next().value / limit)),
        L.values,
        L.map(L.map(f => f())),
        L.map(C.takeAll)
    );
});
```

- 자기들끼리만 놀도록 묶어줄 수 있다.
```js
_.groupBySize = _.curry((size, iter) => {
    const r = L.range(Infinity);

    return _.groupBy(_ => Math.floor(r.next().value / size), iter);
});
```

- 재사용성뿐 아니라 기존 사용하던 함수에서 문장을 제거하고 식으로만 나타낼 수 있다.
```js
C.takeAllWithLimit = _.curry((limit, iter) => 
    _.go(
        iter,
        _.groupBySize(limit),
        L.values,
        L.map(L.map(f => f())),
        L.map(C.takeAll)
    )
);
```
