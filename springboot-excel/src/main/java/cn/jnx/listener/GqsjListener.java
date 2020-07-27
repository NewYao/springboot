package cn.jnx.listener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import cn.jnx.bean.Gqsj;
import cn.jnx.mapper.SaveExcelDao;

public class GqsjListener extends AnalysisEventListener<Gqsj>{

    private static final Logger LOGGER = LoggerFactory.getLogger(MemberListener.class);

    private SaveExcelDao saveExcelDao;
    private Date startTime ;
    private Date endTime ;
    /**
     * 使用spring管理需要传入需要spring管理的SaveExcelDao
     * 
     * @param dao
     */
    public GqsjListener(SaveExcelDao dao) {
            saveExcelDao = dao;
            startTime=new Date();
    }

    /**
     * 每隔10条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;

    // 自定义用于暂时存储data。
    
    private List<Gqsj> datas = new ArrayList<Gqsj>();
    @Override
    public void invoke(Gqsj gqsj, AnalysisContext context) {
        LOGGER.info("当前行：{}", context.readRowHolder().getRowIndex());
        LOGGER.info("解析到一条数据：{}", JSON.toJSONString(gqsj));
        datas.add(gqsj);// 数据存储到list，供批量处理，或后续自己业务逻辑处理。
        if (datas.size() >= BATCH_COUNT) {
            processingData();// 处理已读取的数据
        }
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
        processingData();
        endTime = new Date();
        LOGGER.info("共耗时:{}ms！", (endTime.getTime() - startTime.getTime()));
    }

    /**
     * 进行数据处理
     */
    private void processingData() {
        saveData();// 进行储存相关业务
        clearData();// 清空datas中的数据
    }

    /**
     * 进行储存相关业务
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", datas.size());
        saveExcelDao.insertGqsjBatch(datas);
    }

    /**
     * 清空datas中的数据
     */
    private void clearData() {
        LOGGER.info("清空以保存的数据!");
        datas.clear();
    }

}
