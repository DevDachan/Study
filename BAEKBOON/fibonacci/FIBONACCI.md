![image](https://user-images.githubusercontent.com/111109411/216261431-8fc26bf7-0465-4d88-b38a-597a510ac1cf.png)

# 문제 풀이
- 처음 그냥 Global variable을 생성해 추가하는 방법으로 해당 문제를 풀었을 때는 시간 초과가 발생했다. 그래서 그냥 전체 문제의 규칙을 찾아 해당 규칙을 찾아 함수를 정의해 사용하도록 함
<img src="https://user-images.githubusercontent.com/111109411/216261749-4d1317d9-cadd-4064-ac0e-b33f4f7c9bf2.png" width=60%>



```
void fibonacci(int n) {
    if (n == 0) {
        result[result_count][1] = 0;
        result[result_count][0] = 1;
        result_count++;
    }
    else if (n % 2 == 0) {
        fibonacci_even(n);
    }
    else {
        fibonacci_odd(n);
    }
}


void fibonacci_even(int n) {
    int zero_index = 1;
    int one_index = 1;

    for (int i = 0; i < (n / 2) - 1; i++) {
        zero_index = zero_index + one_index;
        one_index = one_index + zero_index;
    }
    result[result_count][1] = one_index;
    result[result_count][0] = zero_index;
    result_count++;
}

void fibonacci_odd(int n) {
    int zero_index = 0;
    int one_index = 1;

    for (int i = 0; i < n / 2; i++) {
        zero_index = zero_index + one_index;
        one_index = one_index + zero_index;
    }
    result[result_count][1] = one_index;
    result[result_count][0] = zero_index;
    result_count++;
}
```
