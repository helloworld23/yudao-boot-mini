package cn.iocoder.yudao.module.validation.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * URL 编码工具类，用于处理带中文或特殊字符的文件下载链接
 */
public final class EncoderUtil {

    private EncoderUtil() {
        // 禁止实例化
    }

    /**
     * 对 URL 中的文件名部分进行 UTF-8 编码
     *
     * @param fileUrl 原始 URL（文件名在最后一段）
     * @return 编码后的完整 URL
     */
    public static String encodeFileUrl(String fileUrl) {
        int lastSlashIndex = fileUrl.lastIndexOf('/');
        if (lastSlashIndex == -1 || lastSlashIndex == fileUrl.length() - 1) {
            throw new IllegalArgumentException("URL 中未找到有效文件名");
        }

        String baseUrl = fileUrl.substring(0, lastSlashIndex + 1);
        String fileName = fileUrl.substring(lastSlashIndex + 1);
        try {
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            return baseUrl + encodedFileName;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("编码文件名失败", e);
        }
    }
}
