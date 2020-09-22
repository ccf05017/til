import React, {useState} from 'react';

function MultiInputPractise() {
    const [inputs, setInputs] = useState({
        name: '',
        birth: '',
        address: '',
    });

    const { name, birth, address } = inputs;

    const onChange = (e) => {
        const {name, value} = e.target;

        setInputs({
            ...inputs,
            [name]: value
        });
        // 전개 연산자를 사용하면 이전 상태의 객체가 펼쳐질 것.
        // {
        //     name: '이전 상태값',
        //     birth: '이전 상태값',
        //     address: '이전 상태값',
        // }
        // 그 뒤에 있는 [name]: value를 통해 onChange 이벤트로 수신받은 새 데이터를 덮어씀
        // {
        //     name: '새 데이터',
        //     birth: '이전 상태값',
        //     address: '이전 상태값',
        // }
    };

    const onReset = _ => {
        setInputs({
            name: '',
            birth: '',
            address: ''
        })
    };

    return (
        <div>
            <input placeholder="이름" value={name} name="name" onChange={onChange}/>
            <input placeholder="생년월일" value={birth} name="birth" onChange={onChange}/>
            <input placeholder="주소" value={address} name="address" onChange={onChange}/>
            <button onClick={onReset}>초기화</button>
            <div>
                이름: {name} 생년월일: {birth} 주소: {address}
            </div>
        </div>
    )
}

export default MultiInputPractise;
