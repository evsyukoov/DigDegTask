import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class ParserTests {
    Parser parser = new Parser();
    @Test
    public void   testNonValideBadBrackets1()
    {
        String res = parser.parse("2[2[ab]3[g2[[6[i]]]]a");
        assertNull(res);
    }

    @Test
    public void   testNonValideBadBrackets2()
    {
        String res = parser.parse("[qfqf]");
        assertNull(res);
    }

    @Test
    public void   testNonValideEmptyBrackets()
    {
        String res = parser.parse("[]");
        assertNull(res);
    }

    @Test
    public void   testNonValideBadBrackets3()
    {
        String res = parser.parse("a2[wfrw3[d]");
        assertNull(res);
    }

    @Test
    public void   testNonValideBadBrackets4()
    {
        String res = parser.parse("a2[wf[rw3[d]]]]4er[adffe]]");
        assertNull(res);
    }

    @Test
    public void   testNonValideBadBrackets5()
    {
        String res = parser.parse("a2[wfrw3[d]]]");
        assertNull(res);
    }

    @Test
    public void   testNoDigitBeforeBrackets()
    {
        String res = parser.parse("2[wef[qrq]]");
        assertNull(res);
    }

    @Test
    public void   testBadDigitUsage1()
    {
        String res = parser.parse("2[qf3qf]");
        assertNull(res);
    }

    @Test
    public void   testBadDigitUsage2()
    {
        String res = parser.parse("2[wef3asa[qrq]]");
        assertNull(res);
    }

    @Test
    public void   testBadDigitUsage3()
    {
        String res = parser.parse("2[qf3[a]qf]2");
        assertNull(res);
    }

    @Test
    public void   testBadDigitUsage4()
    {
        String res = parser.parse("2[wefasa2[qrq]]2[rt2]");
        assertNull(res);
    }

    @Test
    public void   notAllowedSymbol()
    {
        String res = parser.parse("2[wefasa2[qrq]]2[rt.2]");
        assertNull(res);
    }

    @Test
    public void   easyTest1()
    {
        String res = parser.parse("ab");
        assertEquals("ab", res);
    }

    @Test
    public void   easyTest2()
    {
        String res = parser.parse("3[xyz]4[xy]z");
        assertEquals("xyzxyzxyzxyxyxyxyz", res);
    }

    @Test
    public void   easyTest3()
    {
        String res = parser.parse("2[3[x]y]");
        assertEquals("xxxyxxxy", res);
    }

    @Test
    public void   easyTest4()
    {
        String res = parser.parse("2[ab]");
        assertEquals("abab", res);
    }

    @Test
    public void   depthTest1()
    {
        String res = parser.parse("a2[2[xz]a3[x2[yz]]]");
        assertEquals("axzxzaxyzyzxyzyzxyzyzxzxzaxyzyzxyzyzxyzyz", res);
    }

    @Test
    public void   depthTest2()
    {
        String res = parser.parse("a2[2[xz]a3[x2[yz]]fe]q");
        assertEquals("axzxzaxyzyzxyzyzxyzyzfexzxzaxyzyzxyzyzxyzyzfeq", res);
    }

    @Test
    public void   depthTest3()
    {
        String res = parser.parse("2[2[ab]3[g2[6[i]]]]");
        assertEquals("ababgiiiiiiiiiiiigiiiiiiiiiiiigiiiiiiiiiiiiababgiiiiiiiiiiiigiiiiiiiiiiiigiiiiiiiiiiii", res);
    }

    @Test
    public void   depthTest4()
    {
        String res = parser.parse("2[2[ab]3[g2[6[i]]]]a");
        assertEquals("ababgiiiiiiiiiiiigiiiiiiiiiiiigiiiiiiiiiiiiababgiiiiiiiiiiiigiiiiiiiiiiiigiiiiiiiiiiiia", res);
    }

    @Test
    public void   longStringTest1()
    {
        String res = parser.parse("2[ab2[cd]4[a]ft3[sa]g]at");
        assertEquals("abcdcdaaaaftsasasagabcdcdaaaaftsasasagat", res);
    }

    @Test
    public void   longStringTest2()
    {
        String res = parser.parse("o2[x2[y]2[z2[abc]ac2[av]a]]");
        assertEquals("oxyyzabcabcacavavazabcabcacavavaxyyzabcabcacavavazabcabcacavava", res);
    }

    @Test
    public void   longStringTest3()
    {
        String res = parser.parse("a2[a10[er]2[qfqff]]4[wer]");
        assertEquals("aaererererererererererqfqffqfqffaererererererererererqfqffqfqffwerwerwerwer", res);
    }

    @Test
    public void   bigDepthTest()
    {
        String res = parser.parse("a2[b2[c2[d2[e2[2[2[c]]]]]]]");
        assertEquals("abcdecccccccceccccccccdeccccccccecccccccccdecccccccceccccccccdecccccccceccccccccbcdecccccccceccccccccdeccccccccecccccccccdecccccccceccccccccdeccccccccecccccccc", res);
    }

    @Test
    public void   crazyTest()
    {
        String res = parser.parse("2[fr2[AS3[tyu]a2[hh4[r2[wq]]]aa]e2[rt]10[nn]3[2[2[cc]a]a]]");
        // 2*   (frAStyutyutyuahhrwqwqrwqwqrwqwqrwqwqhhrwqwqrwqwqrwqwqrwqwqaaAStyutyutyuahhrwqwqrwqwqrwqwqrwqwqhhrwqwqrwqwqrwqwqrwqwqaa
        //ertrtnnnnnnnnnnnnnnnnnnnnccccaccccaaccccaccccaaccccaccccaa)
        assertEquals(res, "frAStyutyutyuahhrwqwqrwqwqrwqwqrwqwqhhrwqwqrwqwqrwqwqrwqwqaa" +
                "AStyutyutyuahhrwqwqrwqwqrwqwqrwqwqhhrwqwqrwqwqrwqwqrwqwqaa" +
                "ertrtnnnnnnnnnnnnnnnnnnnnccccaccccaaccccaccccaaccccaccccaafr" +
                "AStyutyutyuahhrwqwqrwqwqrwqwqrwqwqhhrwqwqrwqwqrwqwqrwqwqaa" +
                "AStyutyutyuahhrwqwqrwqwqrwqwqrwqwqhhrwqwqrwqwqrwqwqrwqwqaa" +
                "ertrtnnnnnnnnnnnnnnnnnnnnccccaccccaaccccaccccaaccccaccccaa");
    }



}

