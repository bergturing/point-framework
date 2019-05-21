package com.bergturing.point.utils;

import org.junit.Test;

import static com.bergturing.point.utils.ArrayUtils.isNullOrEmpty;
import static com.bergturing.point.utils.ArrayUtils.nonNullAndEmpty;

/**
 * 数组工具类的测试类
 *
 * @author bergturing@qq.com
 * @apiNote 2019/5/19
 */
public class ArrayUtilsTests {
    @Test
    public void testIsNullOrEmpty() {
        Object[] objectNullArray = null;
        Object[] objectEmptyArray = {};
        Object[] objectArray = {new Object()};

        boolean[] booleanNullArray = null;
        boolean[] booleanEmptyArray = {};
        boolean[] booleanArray = {Boolean.TRUE};

        byte[] byteNullArray = null;
        byte[] byteEmptyArray = {};
        byte[] byteArray = {12};

        short[] shortNullArray = null;
        short[] shortEmptyArray = {};
        short[] shortArray = {24};

        char[] charNullArray = null;
        char[] charEmptyArray = {};
        char[] charArray = {'A'};

        int[] intNullArray = null;
        int[] intEmptyArray = {};
        int[] intArray = {36};

        long[] longNullArray = null;
        long[] longEmptyArray = {};
        long[] longArray = {48L};

        float[] floatNullArray = null;
        float[] floatEmptyArray = {};
        float[] floatArray = {60.0F};

        double[] doubleNullArray = null;
        double[] doubleEmptyArray = {};
        double[] doubleArray = {72.0};

        // Object
        assert isNullOrEmpty(objectNullArray);
        assert isNullOrEmpty(objectEmptyArray);
        assert !isNullOrEmpty(objectArray);

        // boolean
        assert isNullOrEmpty(booleanNullArray);
        assert isNullOrEmpty(booleanEmptyArray);
        assert !isNullOrEmpty(booleanArray);

        // byte
        assert isNullOrEmpty(byteNullArray);
        assert isNullOrEmpty(byteEmptyArray);
        assert !isNullOrEmpty(byteArray);

        // short
        assert isNullOrEmpty(shortNullArray);
        assert isNullOrEmpty(shortEmptyArray);
        assert !isNullOrEmpty(shortArray);

        assert isNullOrEmpty(charNullArray);
        assert isNullOrEmpty(charEmptyArray);
        assert !isNullOrEmpty(charArray);

        // int
        assert isNullOrEmpty(intNullArray);
        assert isNullOrEmpty(intEmptyArray);
        assert !isNullOrEmpty(intArray);

        // long
        assert isNullOrEmpty(longNullArray);
        assert isNullOrEmpty(longEmptyArray);
        assert !isNullOrEmpty(longArray);

        // float
        assert isNullOrEmpty(floatNullArray);
        assert isNullOrEmpty(floatEmptyArray);
        assert !isNullOrEmpty(floatArray);

        // double
        assert isNullOrEmpty(doubleNullArray);
        assert isNullOrEmpty(doubleEmptyArray);
        assert !isNullOrEmpty(doubleArray);
    }

    @Test
    public void testNonNullAndEmpty() {
        Object[] objectNullArray = null;
        Object[] objectEmptyArray = {};
        Object[] objectArray = {new Object()};

        boolean[] booleanNullArray = null;
        boolean[] booleanEmptyArray = {};
        boolean[] booleanArray = {Boolean.TRUE};

        byte[] byteNullArray = null;
        byte[] byteEmptyArray = {};
        byte[] byteArray = {12};

        short[] shortNullArray = null;
        short[] shortEmptyArray = {};
        short[] shortArray = {24};

        char[] charNullArray = null;
        char[] charEmptyArray = {};
        char[] charArray = {'A'};

        int[] intNullArray = null;
        int[] intEmptyArray = {};
        int[] intArray = {36};

        long[] longNullArray = null;
        long[] longEmptyArray = {};
        long[] longArray = {48L};

        float[] floatNullArray = null;
        float[] floatEmptyArray = {};
        float[] floatArray = {60.0F};

        double[] doubleNullArray = null;
        double[] doubleEmptyArray = {};
        double[] doubleArray = {72.0};

        // Object
        assert !nonNullAndEmpty(objectNullArray);
        assert !nonNullAndEmpty(objectEmptyArray);
        assert nonNullAndEmpty(objectArray);

        // boolean
        assert !nonNullAndEmpty(booleanNullArray);
        assert !nonNullAndEmpty(booleanEmptyArray);
        assert nonNullAndEmpty(booleanArray);

        // byte
        assert !nonNullAndEmpty(byteNullArray);
        assert !nonNullAndEmpty(byteEmptyArray);
        assert nonNullAndEmpty(byteArray);

        // short
        assert !nonNullAndEmpty(shortNullArray);
        assert !nonNullAndEmpty(shortEmptyArray);
        assert nonNullAndEmpty(shortArray);

        assert !nonNullAndEmpty(charNullArray);
        assert !nonNullAndEmpty(charEmptyArray);
        assert nonNullAndEmpty(charArray);

        // int
        assert !nonNullAndEmpty(intNullArray);
        assert !nonNullAndEmpty(intEmptyArray);
        assert nonNullAndEmpty(intArray);

        // long
        assert !nonNullAndEmpty(longNullArray);
        assert !nonNullAndEmpty(longEmptyArray);
        assert nonNullAndEmpty(longArray);

        // float
        assert !nonNullAndEmpty(floatNullArray);
        assert !nonNullAndEmpty(floatEmptyArray);
        assert nonNullAndEmpty(floatArray);

        // double
        assert !nonNullAndEmpty(doubleNullArray);
        assert !nonNullAndEmpty(doubleEmptyArray);
        assert nonNullAndEmpty(doubleArray);
    }
}
