package com.ebanma.cloud.lang3;


import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ArchUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * @author 鹿胜宝
 * @date 2023/03/19
 */

public class LangTest {

    @Test
    public void test() {
        String t1 = StringUtils.abbreviate("abcde", 4);
        System.out.println(t1);

        String s = StringUtils.appendIfMissing("abc", "xyz");
        System.out.println(s);

        String s1 = StringUtils.appendIfMissingIgnoreCase("abcXYZ", "xyz");
        System.out.println(s1);

        String cat = StringUtils.capitalize("cat");
        System.out.println(cat);

        String cat1 = StringUtils.uncapitalize("CAT");
        System.out.println(cat1);

        String ab = StringUtils.center("ab", 5, "-_");
        System.out.println(ab);

        String chomp = StringUtils.chomp("abc\r\n");
        System.out.println(chomp);

        boolean contains = StringUtils.contains("abc", "a");
        System.out.println(contains);

        int i = StringUtils.countMatches("adsada", "a");
        System.out.println(i);

        String s2 = StringUtils.deleteWhitespace(" sda adsa  ad ");
        System.out.println(s2);

        String difference = StringUtils.difference("dasdasd", "da");
        System.out.println(difference + "?");

        boolean abd = StringUtils.startsWithAny("abcxyz", new String[]{"abc", "dasd"});
        System.out.println(abd);

        String commonPrefix = StringUtils.getCommonPrefix(new String[]{"abc", "af"});
        System.out.println(commonPrefix);

        int i1 = StringUtils.lastOrdinalIndexOf("aabaabaa", "ab", 2);
        System.out.println(i1);

        boolean blank = StringUtils.isBlank(" "); //true
        System.out.println(blank);

        boolean empty = StringUtils.isEmpty(" ");  //false
        System.out.println(empty);

        boolean numeric = StringUtils.isNumericSpace("12 3");
        System.out.println(numeric);

        String join = StringUtils.join(new String[]{"aa", "bb", "cc"}, "-");
        System.out.println(join);
        String[] str2 = new String[]{"a", "b", "c"};
        String joined = StringUtils.join(new String[]{"a", "b", "c"}, ",");
        System.out.println(joined);
        String join1 = StringUtils.join(new int[]{1, 2, 3}, ';');
        System.out.println(join1);

        String i_am_a_number = StringUtils.swapCase("I am a number");
        System.out.println(i_am_a_number);

        String replace = StringUtils.replace("alibaba", "a", "p");
        System.out.println(replace);
        String overlay = StringUtils.overlay("alibaba", "pua", 3, 3);
        System.out.println(overlay);
        String alibaba = StringUtils.replaceEach("alibaba", new String[]{"ali", "ba"}, new String[]{"taobao", "ma"});
        System.out.println(alibaba);

        String c = StringUtils.repeat('c', 4);
        System.out.println(c);

        String ali = StringUtils.reverse("ali");
        System.out.println(ali);

        String remove = StringUtils.remove("alibaba", "a");
        System.out.println(remove);

        String[] split = StringUtils.split("da.dasd...dasda.g", ".", 3);
        System.out.println(Arrays.toString(split));
        String[] strings = StringUtils.splitByWholeSeparator("aad-addd-faf-aff", "-a", 2);
        System.out.println(Arrays.toString(strings));
        String[] strings1 = StringUtils.splitByWholeSeparatorPreserveAllTokens("da.dasd...dasda.g", ".", 5);
        System.out.println(Arrays.toString(strings1));

        String strip = StringUtils.strip(" ab c ");
        System.out.println(strip);
        String s3 = StringUtils.stripToNull("");
        System.out.println(s3);
        String s4 = StringUtils.stripToEmpty("");
        System.out.println(s4);

        String aa = StringUtils.substring("aadasd", 2, 3);
        System.out.println(aa);

        String dasdf = StringUtils.left("dasdf", 2);
        System.out.println(dasdf);
        String dasdf1 = StringUtils.right("dasdf", 2);
        System.out.println(dasdf1);
        String dasdasf = StringUtils.mid("dasdasf", 2, 4);
        System.out.println(dasdasf);
        String s5 = StringUtils.substringBefore("dasfasf", "fa");
        System.out.println(s5);
        String s6 = StringUtils.substringBeforeLast("fdasfasfas", "a");
        System.out.println(s6);
        String s7 = StringUtils.substringAfter("jklasdj", "j");
        System.out.println(s7);
        String s8 = StringUtils.substringAfterLast("jklasdj2", "j");
        System.out.println(s8);
        String s9 = StringUtils.substringBetween("dfafhgdgsfahs", "a", "s");
        System.out.println(s9);
    }

    @Test
    public void test2() {
        String s = RandomStringUtils.randomNumeric(4);
        System.out.println(s);
        String abcdefg = RandomStringUtils.random(3, "abcdefg");
        System.out.println(abcdefg);
        System.out.println(RandomStringUtils.random(3,true,false));
        System.out.println(RandomStringUtils.random(3,false,true));

        boolean digits = NumberUtils.isDigits("1324.4324");
        System.out.println(digits);
        int max = NumberUtils.max(1, 2, 3, 45, 65, 211, 3);
        System.out.println(max);
        boolean number = NumberUtils.isNumber("0321.1");
        System.out.println(number);

        Integer[] integers = ArrayUtils.toArray(1, 2, 3);
        Integer[] integers1 = ArrayUtils.toArray(1, 2, 3);
        System.out.println(ArrayUtils.isEquals(integers1,integers));
        boolean contains = ArrayUtils.contains(integers, 4);
        System.out.println(contains);
        Map<Object, Object> objectObjectMap = ArrayUtils.toMap(new String[][]{{ "RED", "#FF0000" }, { "GREEN", "#00FF00" }, { "BLUE", "#0000FF" }});
        System.out.println(objectObjectMap);
    }

    @Test
    public void test3() throws ParseException {
        Date date = DateUtils.addDays(new Date(), 3);
        System.out.println(date);
        boolean sameDay = DateUtils.isSameDay(new Date(), new Date());
        System.out.println(sameDay);
        Date date1 = DateUtils.parseDate("2020-2-2 12:22:22", "yyyy-MM-dd HH:mm:ss");
        System.out.println(date1);

        String dasdas = DigestUtils.md5Hex("123456"+"salt");
        System.out.println(dasdas);
        String s = DigestUtils.md5Hex("123456" + "salt");
        if(s.equalsIgnoreCase(dasdas)) {
            System.out.println("pass");
        }
    }

    @Test
    public void test4() {
        System.out.println(ArchUtils.getProcessor().getArch());
        System.out.println(ArchUtils.getProcessor().getType());
        System.out.println(ArchUtils.getProcessor().isIA64());
    }

}
