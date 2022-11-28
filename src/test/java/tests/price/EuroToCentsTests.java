package tests.price;

import com.scraper.price.EuroToCents;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EuroToCentsTests {
    EuroToCents euroToCents;

    @BeforeEach
    public void setup() {
        euroToCents = new EuroToCents();
    }

    @AfterEach
    public void teardown() {
        euroToCents = null;
    }

    @Test
    public void testConvertZeroEuroZeroCents() {
        //Setup
        String price = "";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(-1, value);
    }

    @Test
    public void testConvert3333EuroToCents() {
        //Setup
        String price = "3333";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333300, value);
    }

    @Test
    public void testConvertCommaSeparator() {
        //Setup
        String price = ",";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(-1, value);
    }

    @Test
    public void testConvert3333EuroToCentsWithCommaSeparator() {
        //Setup
        String price = "3,333";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333300, value);
    }

    @Test
    public void testConvert33Euro33Cents() {
        //Setup
        String price = "33.33";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(3333, value);
    }

    @Test
    public void testConvert3333EuroAnd33CentsWithCommaSeparator() {
        //Setup
        String price = "3,333.33";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333333, value);
    }

    @Test
    public void testConvert3333EuroAnd33CentsWithASpaceCharacter() {
        //Setup
        String price = " 3,333.33";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333333, value);
    }

    @Test
    public void testConvert3333EuroAnd33CentsWithEuroSymbol() {
        //Setup
        String price = "€ 3,333.33";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333333, value);
    }

    @Test
    public void testConvert3333EuroAnd33CentsWithATab() {
        //Setup
        String price = "\t€ 3,333.33";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333333, value);
    }

    @Test
    public void testConvert3333EuroAnd33CentsWithANewLine() {
        //Setup
        String price = "\n€ 3,333.33";

        //Exercise
        int value = euroToCents.convert(price);

        //Verify
        Assertions.assertEquals(333333, value);
    }
}