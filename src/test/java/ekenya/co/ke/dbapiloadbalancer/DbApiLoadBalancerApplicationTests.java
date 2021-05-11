package ekenya.co.ke.dbapiloadbalancer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
class DbApiLoadBalancerApplicationTests {

    @Test
    void contextLoads() {

        String[] numbers = {"20","30","40","45"};

        for (int i = 0; i < numbers.length ; i++){

            int j = i + 1;

            if (j == numbers.length-1 || j == numbers.length){
                System.out.println(numbers[i] +" will be treated as an integer "+i);
            }else{
                System.out.println(numbers[i]+ " will be treated as a string "+i);
            }
        }
    }

}
