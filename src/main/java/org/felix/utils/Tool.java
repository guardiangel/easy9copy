package org.felix.utils;

/**
 * @author Felix
 */
public final class Tool {


    /**
     * 判断传入的字符是否为空
     * @param charSequence
     * @return
     */
    public static boolean isBlank(final CharSequence charSequence) {
        if (charSequence == null || charSequence.length() == 0) {
            return true;
        }
        int length = charSequence.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(charSequence.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        String t = "test";
        System.err.println(isBlank(t));
    }
}
