package fr.kdefombelle.xmlcompare.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class StringUtil {

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Static fields/initializers
    //~ ----------------------------------------------------------------------------------------------------------------

    private static final int PAGE_BUFFER = 512;

    //~ ----------------------------------------------------------------------------------------------------------------
    //~ Methods
    //~ ----------------------------------------------------------------------------------------------------------------

    public static InputStream loadResource(Class<?> clazz, String path) throws IOException {
        InputStream is = clazz.getClassLoader().getResourceAsStream(path);

        if (is == null)
            throw new IOException("The resource [" + path + "] cannot be found");

        return is;
    }

    public static String fromInputStream(InputStream is, String encoding) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] arrayByte = new byte[PAGE_BUFFER];
        try {
            int iLenRead = is.read(arrayByte);
            while (iLenRead != -1) {
                baos.write(arrayByte, 0, iLenRead);
                iLenRead = is.read(arrayByte);
            }
        } finally {
            try {
                baos.close();
            } finally {
                is.close();
            }
        }

        return (hasText(encoding)) ? new String(baos.toByteArray(), encoding) : new String(baos.toByteArray());
    }

    private static boolean hasText(String str) {
        if (str == null)
            return false;

        final int strLen = str.length();
        for (int i = 0; i < strLen; i++)
            if (!Character.isWhitespace(str.charAt(i)))
                return true;

        return false;
    }

}