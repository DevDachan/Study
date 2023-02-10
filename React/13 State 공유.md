# 13. State 공유

## 하위 컴포넌트에서 State 공유하기

**하위 컴포넌트**

```jsx
function BoilingVerdict(props){
	if(props.celsius >= 100){
		return <p>물이 끓습니다. </p>
	}
	return <p>물이 끓지 않습니다.</p>
}
```

**상위 컴포넌트**

```jsx
function Calculator(props){
	const [temperature, setTemperature] = useState('');

	const handleChange = (event) => {
		setTemperature(event.target.value);
	} 
	return (
		<fieldset>
			<legend> 섭씨 온도를 입력하세요: </legend>
			<input 
				value={temperature}
				onChange={handleChange} />
			<BoilingVerdict
					celsius={parseFloat(temperature)} />
		</fieldset>	
	);
}
```

## 입력 컴포넌트 추출하기

- 섭씨 온도와 화씨 온도를 따로 입력 받을 수 있게 하기 위함

**하위 컴포넌트**

```jsx
const scaleNames = {
	c:'섭씨',
	f:'화씨'
}

function TemperatureInput(props){
	const [temperature, setTemperature] = useState('');

	const handleChange = (event) => {
		setTemperature(event.target.value);
	} 
	return (
		<fieldset>
			<legend> 온도를 입력하세요(단위: {scaleNames[props.scale]}): </legend>
			<input 
				value={temperature}
				onChange={handleChange} />
		</fieldset>	
	);
}
```

**상위 컴포넌트**

```jsx
function Calculator(props){
	return (
		<div>
			<TemperatureInput scale="c" />
			<TemperatureInput scale="f" />
		</div>
	);
}
```

- 이렇게 코드를 작성 할 경우 온도의 값을 나타내는 state가 하위 컴포넌트에서 존재하기 때문에 값이 다르게 나올 수 있다.
- 그렇기 때문에 섭씨 온도와 화씨 온도를 동기화 시켜줘야 한다.

```jsx
function toCelsius(fahrenheit){
	return (fahrenheit - 32) * 5 / 9;
}
function toFahrenheit(celsius){
	return (fahrenheit * 9 / 5) + 32;
}

function tryConvert(temperature, convert){
	const input = parseFloat(temperature);
	if(Number.isNaN(input)){
		return '';
	}
	const output = convert(input);
	const rounded = Math.round(output * 1000) / 1000;
	return rounded.toString(); 
}

//-------------- 실제 사용---------------------------------
tryConvert('abc', toCelsius);       // empty string
tryConvert('10.22', toFahrenheit);  // 50.396
```

## Shared State 적용하기

- Lifting State UP: 하위 컴포넌트의 state를 공통 상위 컴포넌트로 올림
    
    ```jsx
    return (
    	...
    	// 변경 전: <input value={temperature} onChange={handleChange} />
    	<input value={props.temperature} onChange={handleChange} />
    	...
    )
    ```
    
    - 이렇게 되면 온도 값을 컴포넌트의 state에서 가져오는 것이 아니라 props에서 가져오게 된다.
    - 때문에 해당 값은 컴포넌트의 state를 사용하는 것이 아니기에 값이 변경될 때 상위 컴포넌트로 변경된 값을 전달해야 한다.
        
        ```jsx
        const handleChange = (event) => {
        	// 변경 전: setTemperature(event.target.value);
        	props.onTemperatureChange(event.target.value);
        }
        ```
        
- **최종 Temperatureinput (하위 컴포넌트)**
    
    ```jsx
    function TemperatureInput(props){
    	const handleChange = (event) => {
    		props.onTemperatureChange(event.target.value);
    	} 
    	return (
    		<fieldset>
    			<legend> 온도를 입력하세요(단위: {scaleNames[props.scale]}): </legend>
    			<input 
    				value={props.temperature}
    				onChange={handleChange} />
    		</fieldset>	
    	);
    }
    ```
    
- **최종 Calculator (상위 컴포넌트)**
    
    ```jsx
    function Calculator(props){
    	const [temperature, setTemperature] = useState('');
    	const [scale, setScale] = useState('c');
    
    	const handleCelsiusChange = (temperature) => {
    		setTemperature(temperature);
    		setScale('c');
    	} 
    	const handleFahrenheitChange = (temperature) => {
    		setTemperature(temperature);
    		setScale('f');
    	} 
    
    	const celsius = scale === 'f' ? tryConvert(temperature, toCelsius) : temperature;
    	const fahrenheit = scale === 'c' ? tryConvert(temperature, toFahrenheit) : temperature;
    
    	return (
    			<div>
    			<TemperatureInput 
    					scale="c" 
    					temperature={celsius}
    					onTemperatureChange={handleCelsiusChange}
    			/>
    			<TemperatureInput 
    					scale="f" 
    					temperature={fahrenheit}
    					onTemperatureChange={handleFahrenheitChange}
    			/>
    			<BoilingVerdict celsius={parseFlaot(celsius)} />
    		</div>
    	);
    }
    ```
    

![Untitled](13%20State%20%E1%84%80%E1%85%A9%E1%86%BC%E1%84%8B%E1%85%B2%202247ed28a02a42389c06c9088fce50f2/Untitled.png)

## 실습 예제

**TemperatureInput.jsx**

```jsx
const scaleNames = {
    c: "섭씨",
    f: "화씨",
};

function TemperatureInput(props) {
    const handleChange = (event) => {
        props.onTemperatureChange(event.target.value);
    };

    return (
        <fieldset>
            <legend>
                온도를 입력해주세요(단위:{scaleNames[props.scale]}):
            </legend>
            <input value={props.temperature} onChange={handleChange} />
        </fieldset>
    );
}

export default TemperatureInput;
```

**Calcurator.jsx**

```jsx
import React, { useState } from "react";
import TemperatureInput from "./TemperatureInput";

function BoilingVerdict(props) {
    if (props.celsius >= 100) {
        return <p>물이 끓습니다.</p>;
    }
    return <p>물이 끓지 않습니다.</p>;
}

function toCelsius(fahrenheit) {
    return ((fahrenheit - 32) * 5) / 9;
}

function toFahrenheit(celsius) {
    return (celsius * 9) / 5 + 32;
}

function tryConvert(temperature, convert) {
    const input = parseFloat(temperature);
    if (Number.isNaN(input)) {
        return "";
    }
    const output = convert(input);
    const rounded = Math.round(output * 1000) / 1000;
    return rounded.toString();
}

function Calculator(props) {
    const [temperature, setTemperature] = useState("");
    const [scale, setScale] = useState("c");

    const handleCelsiusChange = (temperature) => {
        setTemperature(temperature);
        setScale("c");
    };

    const handleFahrenheitChange = (temperature) => {
        setTemperature(temperature);
        setScale("f");
    };

    const celsius =
        scale === "f" ? tryConvert(temperature, toCelsius) : temperature;
    const fahrenheit =
        scale === "c" ? tryConvert(temperature, toFahrenheit) : temperature;

    return (
        <div>
            <TemperatureInput
                scale="c"
                temperature={celsius}
                onTemperatureChange={handleCelsiusChange}
            />
            <TemperatureInput
                scale="f"
                temperature={fahrenheit}
                onTemperatureChange={handleFahrenheitChange}
            />
            <BoilingVerdict celsius={parseFloat(celsius)} />
        </div>
    );
}

export default Calculator;
```

**index.jsx**

```jsx
import Calculator from './demo9(shared_state)/Calculator';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <Calculator />
  </React.StrictMode>,
  document.getElementById('root')
);
```