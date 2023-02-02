#include <iostream>
using namespace std;

int result[100][2] = { 0,0 };
int result_count = 0;

void fibonacci(int n);
void fibonacci_even(int n);
void fibonacci_odd(int n);

int main() {
    int test_case = 0;
    int input_case = 0;
    int while_count = 0;

    cin >> test_case;
    while (while_count < test_case) {
        cin >> input_case;
        fibonacci(input_case);
        while_count++;
    }

    while_count = 0;
    while (while_count < result_count) {
        cout << result[while_count][0] << " " << result[while_count][1] << endl;
        while_count++;
    }
    
}

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