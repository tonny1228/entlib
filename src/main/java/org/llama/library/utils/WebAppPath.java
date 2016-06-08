package org.llama.library.utils;

import org.apache.commons.lang.*;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * WEB应用目录
 *
 * @author 刘祥栋
 * @version 1.1 修改只能识别web工程的bug
 * @ClassName: FileUtils
 * @date 2010-11-12 下午03:57:02
 */
public final class WebAppPath {

    /**
     * 系统根目录路径
     */
    private static String WEB_FOLDER;

    private static File classesFolder;

    private static File webRootFolder;

    private static File webinfFolder;

    /**
     * 默认私有构筑方法
     * <p>
     * </p>
     * <p>
     * Description:
     * </p>
     */
    private WebAppPath() {
        super();
    }

    static {
        try {
            if (Thread.currentThread().getContextClassLoader().getResource("/") != null) {
                classesFolder = new File(URLDecoder.decode(
                        Thread.currentThread().getContextClassLoader().getResource("/").getPath(), "utf-8"));
//                WEB_FOLDER = new File(URLDecoder.decode(
//                        Thread.currentThread().getContextClassLoader().getResource("/").getPath(), "utf-8"))
//                        .getParentFile().getParentFile().getAbsolutePath();
            } else {
                classesFolder =
                        new File(URLDecoder.decode(Thread.class.getResource("/").getPath(), "utf-8"));
            }


            File webinfFile = classesFolder.getParentFile();
            File webRoot = webinfFile.getParentFile();
            if (webinfFile.exists() && webinfFile.getName().equals("WEB-INF")) {
                webinfFolder = webinfFile;
                if (webRoot.exists()) {
                    webRootFolder = webRoot;
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 系统根目录物理路径
     *
     * @return 系统根目录物理路径
     */
    public static String webRootPath() {
        return webRootFolder == null ? null : webRootFolder.getAbsolutePath();
    }

    /**
     * 系统根目录物理路径
     *
     * @return 系统根目录物理路径
     */
    public static File webRoot() {
        return webRootFolder;
    }

    /**
     * 系统根目录物理路径
     *
     * @return 系统根目录物理路径
     */
    public static String webinfPath() {
        return webinfFolder == null ? null : webinfFolder.getAbsolutePath();
    }

    /**
     * 系统根目录物理路径
     *
     * @return 系统根目录物理路径
     */
    public static File webinf() {
        return webinfFolder;
    }

    /**
     * 系统根目录物理路径
     *
     * @return 系统根目录物理路径
     */
    public static String classesPath() {
        return ObjectUtils.toString(classesFolder);
    }

    /**
     * 系统根目录物理路径
     *
     * @return 系统根目录物理路径
     */
    public static File classes() {
        System.out.println(classesFolder);
        return classesFolder;
    }

}
