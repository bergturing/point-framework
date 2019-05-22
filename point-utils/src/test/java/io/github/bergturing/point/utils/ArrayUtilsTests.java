package io.github.bergturing.point.utils;

import org.junit.Test;

/**
 * 数组工具类的测试类
 *
 * @author bergturing@qq.com
 * @date 2019/5/19
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
        assert ArrayUtils.isNullOrEmpty(objectNullArray);
        assert ArrayUtils.isNullOrEmpty(objectEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(objectArray);

        // boolean
        assert ArrayUtils.isNullOrEmpty(booleanNullArray);
        assert ArrayUtils.isNullOrEmpty(booleanEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(booleanArray);

        // byte
        assert ArrayUtils.isNullOrEmpty(byteNullArray);
        assert ArrayUtils.isNullOrEmpty(byteEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(byteArray);

        // short
        assert ArrayUtils.isNullOrEmpty(shortNullArray);
        assert ArrayUtils.isNullOrEmpty(shortEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(shortArray);

        assert ArrayUtils.isNullOrEmpty(charNullArray);
        assert ArrayUtils.isNullOrEmpty(charEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(charArray);

        // int
        assert ArrayUtils.isNullOrEmpty(intNullArray);
        assert ArrayUtils.isNullOrEmpty(intEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(intArray);

        // long
        assert ArrayUtils.isNullOrEmpty(longNullArray);
        assert ArrayUtils.isNullOrEmpty(longEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(longArray);

        // float
        assert ArrayUtils.isNullOrEmpty(floatNullArray);
        assert ArrayUtils.isNullOrEmpty(floatEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(floatArray);

        // double
        assert ArrayUtils.isNullOrEmpty(doubleNullArray);
        assert ArrayUtils.isNullOrEmpty(doubleEmptyArray);
        assert !ArrayUtils.isNullOrEmpty(doubleArray);
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
        assert !ArrayUtils.nonNullAndEmpty(objectNullArray);
        assert !ArrayUtils.nonNullAndEmpty(objectEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(objectArray);

        // boolean
        assert !ArrayUtils.nonNullAndEmpty(booleanNullArray);
        assert !ArrayUtils.nonNullAndEmpty(booleanEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(booleanArray);

        // byte
        assert !ArrayUtils.nonNullAndEmpty(byteNullArray);
        assert !ArrayUtils.nonNullAndEmpty(byteEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(byteArray);

        // short
        assert !ArrayUtils.nonNullAndEmpty(shortNullArray);
        assert !ArrayUtils.nonNullAndEmpty(shortEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(shortArray);

        assert !ArrayUtils.nonNullAndEmpty(charNullArray);
        assert !ArrayUtils.nonNullAndEmpty(charEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(charArray);

        // int
        assert !ArrayUtils.nonNullAndEmpty(intNullArray);
        assert !ArrayUtils.nonNullAndEmpty(intEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(intArray);

        // long
        assert !ArrayUtils.nonNullAndEmpty(longNullArray);
        assert !ArrayUtils.nonNullAndEmpty(longEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(longArray);

        // float
        assert !ArrayUtils.nonNullAndEmpty(floatNullArray);
        assert !ArrayUtils.nonNullAndEmpty(floatEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(floatArray);

        // double
        assert !ArrayUtils.nonNullAndEmpty(doubleNullArray);
        assert !ArrayUtils.nonNullAndEmpty(doubleEmptyArray);
        assert ArrayUtils.nonNullAndEmpty(doubleArray);
    }
}
