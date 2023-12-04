public class VariableShadowingTest {

    // instance variable
    private int x = 5;

    public void testMethod() {
        int x = 10;

        // Some code that uses the local variable x
        System.out.println("Local variable x: " + x);

        // Access the instance variable using 'this'
        System.out.println("Instance variable x: " + this.x);

        // Introducing variable shadowing issue in the same scope
        int x = 20;
        System.out.println("Shadowed variable x: " + x);
    }

    public static void main(String[] args) {
        VariableShadowingTest test = new VariableShadowingTest();
        test.testMethod();
    }
}
