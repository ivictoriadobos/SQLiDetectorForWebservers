package core.transformers;
import core.exceptions.CoreException;
import core.exceptions.CoreExceptionCauseEnum;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class UrlDecoder {
    public static String decode(String urlEncoded)
    {
        try {
            return URLDecoder.decode(urlEncoded, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            throw new CoreException(CoreExceptionCauseEnum.URL_DECODING_EXCEPTION);
        }
    }
}
