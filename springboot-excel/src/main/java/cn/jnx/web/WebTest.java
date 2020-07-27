package cn.jnx.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.excel.EasyExcel;

import cn.jnx.bean.Gqsj;
import cn.jnx.bean.Member;
import cn.jnx.listener.GqsjListener;
import cn.jnx.mapper.SaveExcelDao;

@Controller
public class WebTest {
	
	@Autowired
	private SaveExcelDao sed;
	 /**
     * 文件上传
     * <p>1. 创建excel对应的实体对象 参照{@link UploadData}
     * <p>2. 由于默认一行行的读取excel，所以需要创建excel一行一行的回调监听器，参照{@link UploadDataListener}
     * <p>3. 直接读即可
	 * @throws IOException 
     */
	@PostMapping("/upload")
	@ResponseBody
	public String uploadExcel(MultipartFile file) throws IOException {
//		EasyExcel.read(file.getInputStream(), Member.class, new MemberListener(sed)).sheet().doRead();
	    EasyExcel.read(file.getInputStream(), Gqsj.class, new GqsjListener(sed)).sheet().doRead();
	    return "success";
	}
	
	
	 /**
     * 文件下载（失败了会返回一个有部分数据的Excel）
     * <p>1. 创建excel对应的实体对象 参照{@link DownloadData}
     * <p>2. 设置返回的 参数
     * <p>3. 直接写，这里注意，finish的时候会自动关闭OutputStream,当然你外面再关闭流问题不大
     */
    @GetMapping("/download")
    public void download(HttpServletResponse response) throws IOException {
        // 这里注意 有同学反应使用swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        String fileName = URLEncoder.encode("测试", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), Member.class).sheet("模板").sheetNo(1).doWrite(data());
        
    }
	
    private List<Member> data() {
        List<Member> list = new ArrayList<Member>();
        for (int i = 0; i < 100; i++) {
        	Member data = new Member();
            data.setBirthday(new Date());
            data.setIdCard("32200"+i);
            data.setJobNo("0"+i);
            data.setName("name"+i+"号");
            data.setPersionNo(i);
            data.setSex(i%2==0?"男":"女");
            list.add(data);
        }
        return list;
    }
    @ResponseBody
    @PostMapping("/login")
    public String login(String loginName,String loginPasswd,String f_yhlx,HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        System.out.println(cookie);
        request.getSession().setAttribute("username", "张三");
        request.getSession().setAttribute("user_id", "008");
        System.out.println("loginName:"+loginName);
        System.out.println("loginPasswd:"+loginPasswd);
        System.out.println("f_yhlx:"+f_yhlx);
        return "success";
    }
    
    @ResponseBody
    @PostMapping("/uploadDate")
    public String uploadDate(HttpServletRequest request) {
        Cookie[] cookie = request.getCookies();
        System.out.println(cookie);
        System.out.println("sessionid= "+ request.getSession().getId());
        System.out.println("user_id= "+ request.getSession().getAttribute("user_id"));
        System.out.println("username = "+ request.getSession().getAttribute("username"));
        return "success";
    }
    
}
