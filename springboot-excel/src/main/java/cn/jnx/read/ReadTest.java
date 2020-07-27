package cn.jnx.read;

import java.util.LinkedHashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;

import cn.jnx.bean.Gqsj;
import cn.jnx.bean.Member;
import cn.jnx.listener.CommonListener;
import cn.jnx.listener.GqsjListener;
import cn.jnx.listener.MemberListener;
import cn.jnx.mapper.SaveExcelDao;
import cn.jnx.util.FileUtil;

public class ReadTest {

	static Logger log = LoggerFactory.getLogger(ReadTest.class);

//	String filePath = FileUtil.getFilePath("member.xls");
	String filePath = "f://gqsj.xlsx";
	@Autowired
	private SaveExcelDao sed;
	
	/**
	 * 最简单的读,无实体类映射
	 * <p>
	 * 1、EasyExcel只负责读这个动作
	 * <p>
	 * 2 、由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ExcelListener}
	 * <p>
	 * 3、直接读即可
	 * <p>
	 * 4、相应处理在自定义的监听器中处理
	 */
	//@Test
	public void readExcle() {
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(filePath, new CommonListener(sed)).sheet().doRead();
	}

	/**
	 * 最简单的读，有实体类映射
	 * <p>
	 * 1、创建excel对应的实体对象 参照{@link Member}
	 * <p>
	 * 2、由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link ExcelListener}
	 * <p>
	 * 3、直接读即可
	 * <p>
	 * 4、相应处理在自定义的监听器中处理
	 */
//	@Test
	public void readExcleWithModel() {
		// 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
		EasyExcel.read(filePath, Member.class, new MemberListener(sed)).sheet().doRead();
	}

	/**
	 * 同步的返回，不推荐使用，如果数据量大会把数据放到内存里面,保证数据量少可以使用，最好保证1000以内
	 * <p>
	 * 无实体类,使用LinkedHashMap<Integer, String>接收
	 */
	public void readSync() {
		List<LinkedHashMap<Integer, String>> data = EasyExcel.read(filePath).sheet(0).doReadSync();
		for (LinkedHashMap<Integer, String> row : data) {
			log.info("读取到的数据 {}", JSON.toJSONString(row));
		}
	}

	/**
	 * 同步的返回()，不推荐使用，如果数据量大会把数据放到内存里面,保证数据量少可以使用，最好保证1000以内
	 * <p>
	 * 有实体类
	 */
	public void readSyncWithModel() {
		List<Member> data = EasyExcel.read(filePath).head(Member.class).sheet(0).doReadSync();
		for (Member row : data) {
			log.info("读取到的数据 {}", JSON.toJSONString(row));
		}
	}

	
//	@Test
	public void readExcleWithModel2() {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(filePath, Gqsj.class, new GqsjListener(sed)).sheet().doRead();
    }
	
	
}
