package cn.jnx.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;

import cn.jnx.bean.Member;

public class ExcleUtil {
	static Logger log = LoggerFactory.getLogger(ExcleUtil.class);

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("e:/员工表.xls");
		readExcleHasModel();
	}

	public static void readExcle(File file) {
		List<LinkedHashMap<Object, Object>> data = EasyExcel.read(file).sheet(0).doReadSync();
		for (LinkedHashMap<Object, Object> row : data) {
			for (Object key : row.keySet()) {
				System.out.print(row.get(key));
			}
			System.out.println("");
		}
	}

	 /**
     * 最简单的读
     * <p>1. 创建excel对应的实体对象 参照{@link DemoData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link DemoDataListener}
     * <p>3. 直接读即可
     */
    public static void readExcleHasModel() {
        String fileName = "e:/员工表.xls";
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(fileName, Member.class, null).sheet().doRead();
    }
	
	
	
}
