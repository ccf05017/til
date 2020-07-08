import React, { useState, useRef } from 'react';

function MultiInputSample() {
    const [inputs, setInputs] = useState({
        name: '',
        nickname: '',
    });
    const { name, nickname } = inputs;
    const nameInput = useRef(); // ref 정의. 객체 형태로 생성된다.

    const onChange = (e) => {
        const { name, value } = e.target;

        setInputs({
           ...inputs,
           [name]: value,
        });
        // 풀어낸 표현
        // const nextInputs = {
        //     ...inputs,
        //     [name]: value,
        // };
        //
        // setInputs(nextInputs);
    };

    const onReset = () => {
        setInputs({
            name: '',
            nickname: '',
        });
        // {ref}.{current}.{DOM 메서드}
        nameInput.current.focus();
    };

    return (
        <div>
            <input name="name" placeholder="이름" onChange={onChange} value={name} ref={nameInput}/>
            <input name="nickname" placeholder="닉네임" onChange={onChange} value={nickname}/>
            <button onClick={onReset}>초기화</button>
            <div>
                <b>값: </b>
                {name} ({nickname})
            </div>
        </div>
    );
}

export default MultiInputSample;
