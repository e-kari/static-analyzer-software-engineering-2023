public class example4 {
    public static void main(String[] args) {
        shortMethod();
        longMethod();
    }

    public static void shortMethod() {
        // This method starts on line 7 and is 4 lines long.
        System.out.println("This method will not be detected by the line count checker.\n");
    }

    public static void longMethod() {
        // This method starts on line 12 and is 38 lines long.
        System.out.println("This method will be detected by the line count checker.");

        //Random Sentence 1
        System.out.println("What a beautiful day to code!");

        //Random Sentence 2
        System.out.println("Coding is a fun, yet a tedious activity.");

        //Random Sentence 3
        System.out.println("It feels great when your code is working as intended.");

        //Random Sentence 4
        System.out.println("However, it's always a bad feeling when your code falls short.");

        //Random Sentence 5
        System.out.println("The hardest part of coding isn't always the coding.");

        //Random Sentence 6
        System.out.println("It's the part before the coding!");

        //Random Sentence 7
        System.out.println("I like to call it the planning process.");

        //Random Sentence 8
        System.out.println("Going into coding dry could work but you'll sure to hit some roadblocks.");

        //Random Sentence 9
        System.out.println("That's why it's always good to plan ahead and maybe even draw out the structure!");

        //Random Sentence 10
        System.out.println("Eventually, everything will work out and you'll find how easy coding is!");

        //Random Sentence 11
        System.out.println("At least that's what I'd like to believe.");

    }
}
