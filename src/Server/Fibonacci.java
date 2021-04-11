package Server;

public class Fibonacci {
    public int calculateFibonacci(int n) {
        int answer = 1;
        int previous1 = 1, previous2;
        for (int i = 3; i <= n; i++) {
            previous2 = previous1;
            previous1 = answer;
            answer = previous1 + previous2;
        }
        return answer;
    }
}
