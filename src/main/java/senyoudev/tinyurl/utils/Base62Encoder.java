package senyoudev.tinyurl.utils;

public class Base62Encoder {
    // This String below is the character set that we will use to encode the URL.
    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    // This is the base of the number system that we are using.
    private static final int BASE = 62;

    public static String encode(long value) {
        StringBuilder sb = new StringBuilder();
        // This loop will keep dividing the value by the base and appending the remainder to the StringBuilder.
        while (value != 0) {
            sb.append(BASE62.charAt((int) (value % BASE)));
            value /= BASE;
        }
        // We need to reverse the StringBuilder because the remainder is appended in reverse order.
        return sb.reverse().toString();
    }
}
