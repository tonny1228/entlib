package org.llama.library.utils;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageUtil {
	private static Map<String, Image> waterMarks = new HashMap<String, Image>();

	private static Map<String, String> waterMarkUrl = new HashMap<String, String>();

	/**
	 * 缩放图像
	 * 
	 * @param srcImageFile 源图像文件地址
	 * @param result 缩放后的图像地址
	 * @param w 缩放后宽度 固定缩放大小为100像素
	 * @throws IOException
	 */
	public static void scale(File srcImageFile, File result, int w) throws IOException {
		BufferedImage src = ImageIO.read(srcImageFile); // 读入文件
		int width = src.getWidth(); // 得到源图宽
		int height = src.getHeight(); // 得到源图长

		float scale = ((float) width) / ((float) w);

		width = (int) (width / scale);
		height = (int) (height / scale);

		Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage tag = new BufferedImage(width, height, BufferedImage.SCALE_SMOOTH);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		ImageIO.write(tag, "JPEG", result);// 输出到文件流
	}

	/**
	 * 图像切割
	 * 
	 * @param srcImageFile 源图像地址
	 * @param descDir 切片目标文件夹
	 * @param destWidth 目标切片宽度
	 * @param destHeight 目标切片高度
	 */
	public static void cut(String srcImageFile, String descDir, int destWidth, int destHeight) {
		try {
			Image img;
			ImageFilter cropFilter;
			// 读取源图像
			BufferedImage bi = ImageIO.read(new File(srcImageFile));
			int srcWidth = bi.getHeight(); // 源图宽度
			int srcHeight = bi.getWidth(); // 源图高度
			if (srcWidth > destWidth && srcHeight > destHeight) {
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_FAST);
				destWidth = 200; // 切片宽度
				destHeight = 150; // 切片高度
				int cols = 0; // 切片横向数量
				int rows = 0; // 切片纵向数量
				// 计算切片的横向和纵向数量
				if (srcWidth % destWidth == 0) {
					cols = srcWidth / destWidth;
				} else {
					cols = (int) Math.floor(srcWidth / destWidth) + 1;
				}
				if (srcHeight % destHeight == 0) {
					rows = srcHeight / destHeight;
				} else {
					rows = (int) Math.floor(srcHeight / destHeight) + 1;
				}
				// 循环建立切片
				// 改进的想法:是否可用多线程加快切割速度
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < cols; j++) {
						// 四个参数分别为图像起点坐标和宽高
						// 即: CropImageFilter(int x,int y,int width,int height)
						cropFilter = new CropImageFilter(j * 200, i * 150, destWidth, destHeight);
						img = Toolkit.getDefaultToolkit().createImage(
								new FilteredImageSource(image.getSource(), cropFilter));
						BufferedImage tag = new BufferedImage(destWidth, destHeight, BufferedImage.SCALE_SMOOTH);
						Graphics g = tag.getGraphics();
						g.drawImage(img, 0, 0, null); // 绘制缩小后的图
						g.dispose();
						// 输出为文件
						ImageIO.write(tag, "JPEG", new File(descDir + "pre_map_" + i + "_" + j + ".jpg"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 图像类型转换 GIF->JPG GIF->PNG PNG->JPG PNG->GIF(X)
	 */
	public static void convert(String source, String result) {
		try {
			File f = new File(source);
			f.canRead();
			f.canWrite();
			BufferedImage src = ImageIO.read(f);
			ImageIO.write(src, "JPG", new File(result));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 彩色转为黑白
	 * 
	 * @param source
	 * @param result
	 */
	public static void gray(String source, String result) {
		try {
			BufferedImage src = ImageIO.read(new File(source));
			ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
			ColorConvertOp op = new ColorConvertOp(cs, null);
			src = op.filter(src, null);
			ImageIO.write(src, "JPEG", new File(result));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 给图片加水印，但不改变大小
	 * 
	 * @param strOriginalFileName String(原始文件) 就是水印原始文件
	 * @param strWaterMarkFileName String(水印文件)
	 * @param pos 位置 1-9左上到右下
	 * @param
	 */
	public static void waterMark(String site, String strOriginalFileName, String strWaterMarkFileName, int pos) {
		try {
			String url = null;
			strOriginalFileName = url + strOriginalFileName;
			strWaterMarkFileName = url + strWaterMarkFileName;
			File fileOriginal = new File(strOriginalFileName);
			Image imageOriginal = ImageIO.read(fileOriginal);
			int widthOriginal = imageOriginal.getWidth(null);
			int heightOriginal = imageOriginal.getHeight(null);

			Image imageWaterMark = getWatermark(site, strWaterMarkFileName);
			if (imageWaterMark == null)
				return;
			int widthWaterMark = imageWaterMark.getWidth(null);
			int heightWaterMark = imageWaterMark.getHeight(null);

			// 高和宽3倍以上才加水印
			if (widthOriginal / widthWaterMark < 2 || heightOriginal / heightWaterMark < 2)
				return;

			BufferedImage bufImage = new BufferedImage(widthOriginal, heightOriginal, BufferedImage.TYPE_INT_RGB);
			Graphics g = bufImage.createGraphics();
			g.drawImage(imageOriginal, 0, 0, widthOriginal, heightOriginal, null);
			// 水印文件
			switch (pos) {
				case 1:
					g.drawImage(imageWaterMark, 0, 0, widthWaterMark, heightWaterMark, null);
					break;
				case 2:
					g.drawImage(imageWaterMark, (widthOriginal - widthWaterMark) / 2, 0, widthWaterMark,
							heightWaterMark, null);
					break;
				case 3:
					g.drawImage(imageWaterMark, widthOriginal - widthWaterMark, 0, widthWaterMark, heightWaterMark,
							null);
					break;
				case 4:
					g.drawImage(imageWaterMark, 0, (heightOriginal - heightWaterMark) / 2, widthWaterMark,
							heightWaterMark, null);
					break;
				case 5:
					g.drawImage(imageWaterMark, (widthOriginal - widthWaterMark) / 2,
							(heightOriginal - heightWaterMark) / 2, widthWaterMark, heightWaterMark, null);
					break;
				case 6:
					g.drawImage(imageWaterMark, widthOriginal - widthWaterMark, (heightOriginal - heightWaterMark) / 2,
							widthWaterMark, heightWaterMark, null);
					break;
				case 7:
					g.drawImage(imageWaterMark, 0, 0, widthWaterMark, heightOriginal - heightWaterMark, null);
					break;
				case 8:
					g.drawImage(imageWaterMark, (widthOriginal - widthWaterMark) / 2, heightOriginal - heightWaterMark,
							widthWaterMark, heightWaterMark, null);
					break;
				default:
					g.drawImage(imageWaterMark, widthOriginal - widthWaterMark, heightOriginal - heightWaterMark,
							widthWaterMark, heightWaterMark, null);
			}
			g.dispose();

			FileOutputStream fos = new FileOutputStream(strOriginalFileName);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
			encoder.encode(bufImage);
			fos.flush();
			fos.close();
			fos = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回储存的数据
	 * 
	 * @Title: getWatermark
	 * @param site
	 * @param strWaterMarkFileName
	 * @return
	 * @throws IOException
	 */
	private static synchronized Image getWatermark(String site, String strWaterMarkFileName) throws IOException {
		if (waterMarkUrl.get(site) == null || !waterMarkUrl.get(site).equals(strWaterMarkFileName)) {
			waterMarkUrl.put(site, strWaterMarkFileName);
			File fileWaterMark = new File(strWaterMarkFileName);
			Image imageWaterMark = ImageIO.read(fileWaterMark);
			waterMarks.put(site, imageWaterMark);
		}
		return waterMarks.get(site);
	}

}
