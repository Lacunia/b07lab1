import java.io.File;

public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3)); // expected output = 0.0

        double [] c1 = {6,5}; // 6 + 5x^3
        int [] e1 = {0,3};
        Polynomial p1 = new Polynomial(c1, e1);
        System.out.println("polynomial p1: coe = [");
        for(double c : p1.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : p1.exp) {
            System.out.println(e + " ");
        } 

        double [] c2 = {-9,-2}; // -9x^4 - 2x
        int [] e2 = {4,1};
        Polynomial p2 = new Polynomial(c2, e2);
        System.out.println("polynomial p2: coe = [");
        for(double c : p2.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : p2.exp) {
            System.out.println(e + " ");
        } 

        double [] c3 = {-6,-2}; // -6-2x
        int [] e3 = {0,1};
        Polynomial p3 = new Polynomial(c3, e3);
        System.out.println("polynomial p3: coe = [");
        for(double c : p3.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : p3.exp) {
            System.out.println(e + " ");
        } 


        Polynomial s = p1.add(p2); // expected = 6 + 5x^3 - 9x^4 - 2x
        System.out.println("polynomial s: coe = [");
        for(double c : s.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : s.exp) {
            System.out.println(e + " ");
        } 
        System.out.println("s(0.1) = " + s.evaluate(0.1)); // expected output = 5.8041

        Polynomial s2 = p1.add(p3); // expected = 5x^3 - 2x
        System.out.println("polynomial s2: coe = [");
        for(double c : s2.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : s2.exp) {
            System.out.println(e + " ");
        } 
        System.out.println("s2(0.1) = " + s2.evaluate(0.1)); // expected output = -0.195
        
        Polynomial m = p1.multiply(p2); // expected = -45x^7 - 12x - 64x^4
        System.out.println("polynomial m: coe = [");
        for(double c : m.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : m.exp) {
            System.out.println(e + " ");
        } 
        System.out.println("m(0.1) = " + m.evaluate(0.1)); // expected output = -1.2064045 
        
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");

        p1.saveToFile("p1.txt");
        p2.saveToFile("p2.txt");
        p3.saveToFile("p3.txt");
        s.saveToFile("s.txt");
        s2.saveToFile("s2.txt");
        m.saveToFile("m.txt");

        File file1 = new File("L:\\Lucy\\UTSC\\4. Fall 2024 courses\\CSCB07\\Lab 2\\b07lab1\\new1.txt");
        Polynomial new1 = new Polynomial(file1);
        System.out.println("polynomial new1: coe = [");
        for(double c : new1.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : new1.exp) {
            System.out.println(e + " ");
        } 

        File file2 = new File("L:\\Lucy\\UTSC\\4. Fall 2024 courses\\CSCB07\\Lab 2\\b07lab1\\new2.txt");
        Polynomial new2 = new Polynomial(file2);
        System.out.println("polynomial new2: coe = [");
        for(double c : new2.coe) {
            System.out.println(c + " ");
        } 
        System.out.println("] ");
        System.out.println("exp = [");
        for(int e : new2.exp) {
            System.out.println(e + " ");
        } 
    }
}