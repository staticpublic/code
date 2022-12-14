package com.controller;

import com.pojo.*;
import com.service.*;
import org.apache.ibatis.javassist.tools.reflect.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends HttpServlet {
    @Autowired
    private UserService userService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private User_playerService user_playerService;

    //点击游客登录
    @RequestMapping("/toLogin")
    public String toLogin() {
        return "adminLogin.jsp";
    }

    //登录
    @RequestMapping("/Login")
    public String Login(user u, HttpSession session){
        System.out.println(u.getUsername());
        user formatpass=userService.selectByRegName(u.getUsername());
        //成功登录
        String des="";
        System.out.println(formatpass.getIdentity());
        if(formatpass!=null&&formatpass.getPassword().equals(u.getPassword())&&formatpass.getIdentity().equals("admin")){
            session.setAttribute("adminName",u.getUsername());
//            user temp=new user();
//            temp.setUsername(u.getUsername());
            //记录登录时间
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            Date date = new Date(System.currentTimeMillis());
//            temp.setLastLoginTime(formatter.format(date));
            session.setAttribute("thisLoginTime",date);
            session.setAttribute("username",u.getUsername());
//            userService.updateByTime(temp);
            return "redirect:/admin/afterLogin.jsp";
        }else{
            return "redirect:/admin/failToLogin.jsp";
        }
    }

    //找回密码
    @RequestMapping("/tofind")
    public String toforget(HttpSession session){
        session.setAttribute("pwd","");
        return "redirect:/admin/findpwd.jsp";
    }

    @RequestMapping("/find")
    public String find(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String username1= new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        user u=userService.selectByRegName(username1);
        if(u==null){
            return "redirect:/admin/userNameError.jsp";
        }{//找到了user
            session.setAttribute("userInfo",u);
            return "redirect:/admin/findpwd.jsp";
        }
    }

    @RequestMapping("/findout")
    public String findout(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String answer= new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");
        user t= (user) session.getAttribute("userInfo");
        if(answer.equals(t.getAnswer())){
            session.setAttribute("pwd","密码是："+t.getPassword());
        }
        return "redirect:/admin/findpwd.jsp";
    }


    //进入创建一个新staff
    @RequestMapping("/toNewUser")
    public String toNewUser(){
        return "redirect:/admin/newUser.jsp";
    }
    //创建
    @RequestMapping("/newUser")
    public String newUser(user us, HttpServletResponse resp, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("realname") String realname, @RequestParam("telephone") String telephone, @RequestParam("email") String email, @RequestParam("address") String address, @RequestParam("gender") String gender, user u, @RequestParam("attachUnit") String attachUnit, HttpServletRequest request, HttpSession  session) throws IOException {
        String des="";
        //不要让用户名重复
        user t=userService.selectByRegName(username);
        if(t==null){
            //判断电话号码是否合法
            if(request.getParameter("telephone").length()==11){
                int flag=0;
                for(int i=0;i<request.getParameter("telephone").length();i++){
                    if(request.getParameter("telephone").charAt(i) <= '0'|| request.getParameter("telephone").charAt(i) >= '9'){
                        flag=1;
                    }
                }
                if(flag==0){
                    username=new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
                    String password1=new String(request.getParameter("password").getBytes("iso-8859-1"),"utf-8");
                    String address1=new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
                    String gender1=new String(request.getParameter("gender").getBytes("iso-8859-1"),"utf-8");
                    String realname1=new String(request.getParameter("realname").getBytes("iso-8859-1"),"utf-8");
                    String attachUnit1=new String(request.getParameter("attachUnit").getBytes("iso-8859-1"),"utf-8");
                    System.out.println(address1);
                    //记录登录时间
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                    Date date = new Date(System.currentTimeMillis());

                    //合法
                    user temp=new user();
                    temp.setUsername(username);
                    temp.setPassword(password1);
                    temp.setAddress(address1);
                    temp.setEmail(email);
                    temp.setGender(gender1);
                    temp.setRealname(realname1);
                    temp.setTelephone(telephone);
                    temp.setIdentity("staff");
                    temp.setFoundtime(formatter.format(date));
                    System.out.println(temp.getIdentity());
                    temp.setAttachUnit(attachUnit1);
                    //session.setAttribute("userInfo",u);
                    /**
                     * 上传头像
                     */
                    //定义 文件名
                    String filename=null;
                    //定义文件保存的本地路径
                    String localPath=request.getServletContext().getRealPath("")+"/img";
                    System.out.println(localPath);
                    /*
                     * 获取前台传过来的file值，如果不为空，则执行该if里面的代码
                     * 需要注意的是：这里我将MultipartFile即file属性定义在了商品的实体类中。
                     */
                    if(!us.getFile().isEmpty()){
                        //用uuid随机生成一个文件裸名
                        String uuid = UUID.randomUUID().toString().replaceAll("-","");
                        //获得文件类型（可以判断如果不是图片，禁止上传）
                        String contentType=us.getFile().getContentType();
                        if (!contentType.equals("image/jpeg")  && !contentType.equals("image/png") && !contentType.equals("image/gif")) {
                            resp.setContentType("text/html;charset=utf-8");
                            PrintWriter out = resp.getWriter();
                            out.flush();
                            out.println("<script>");
                            out.println("alert('请上传图片文件!');");
                            out.println("history.back();");
                            out.println("</script>");
                            return "redirect:/staff/handIn.jsp";
                        } else {
                            //获得文件后缀名
                            String suffixName=contentType.substring(contentType.indexOf("/")+1);
                            //得到文件名（文件名由文件裸名与后缀名组合而成）
                            filename=uuid+"."+suffixName;
                            //获取商品实体类中的file属性赋值，而file属性是MultipartFile类型的，将前台上传的该图片保存至该本地路径的文件夹中
                            us.getFile().transferTo(new File(localPath+filename));
                        }
                        //把图片的相对路径保存至数据库
                        u.setHeadpath(localPath+filename); //设置商品实体类中的image属性的值
                    }

                    session.setAttribute("userInfo",u);
                    userService.insertByAdmin(temp);
                    //角色表和用户角色表
                    player player=new player();
                    player.setFoundTime(formatter.format(date));
                    playerService.insert(player);
                    int playerID=playerService.selectByTime(formatter.format(date)).getPlayerID();
                    int userID=userService.selectByRegName(username).getUserID();
                    user_player up=new user_player();
                    up.setUserID(userID);
                    up.setPlayerID(playerID);
                    user_playerService.insert(up);
                    //返回游客页面
                    return "redirect:/admin/afterLogin.jsp";

                }else{
                    return "redirect:/admin/teleError.jsp";
                }
            }else{//电话输入有误
                return "redirect:/admin/teleError.jsp";
            }
        }else{  //用户名重复
            return "redirect:/admin/registerError.jsp";
        }
    }

    //查看并且修改个人信息
    @RequestMapping("/toChange")
    public String tochange(HttpSession session){
        String name= (String) session.getAttribute("adminName");
        user t=userService.selectByRegName(name);
        session.setAttribute("adminInfo",t);
        return "redirect:/admin/changeInfo.jsp";
    }
    //修改
    @RequestMapping("/change")
    public String change(@RequestParam("username") String username, @RequestParam("realname") String realname, @RequestParam("telephone") String telephone, @RequestParam("email") String email, @RequestParam("address") String address, @RequestParam("question") String question,@RequestParam("answer") String answer,@RequestParam("gender") String gender, HttpServletRequest request,HttpSession  session) throws UnsupportedEncodingException {
        user u=new user();
        String username1= new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        String address1= new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
        String gender1= new String(request.getParameter("gender").getBytes("iso-8859-1"),"utf-8");
        String realname1= new String(request.getParameter("realname").getBytes("iso-8859-1"),"utf-8");
        String question1= new String(request.getParameter("question").getBytes("iso-8859-1"),"utf-8");
        String answer1= new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");

        // System.out.println(addr);
        u.setUsername(username1);
        u.setAddress(address1);
        u.setEmail(email);
        u.setGender(gender1);
        u.setRealname(realname1);
        u.setTelephone(telephone);
        u.setQuestion(question1);
        u.setAnswer(answer1);
        session.setAttribute("adminInfo",u);
        System.out.println(address1);
        //用户名不重复
        userService.updateInfo(u);
        return "redirect:/admin/afterLogin.jsp";
    }

    //进入重置密码
    @RequestMapping("/toReset")
    public String toReset(){
        return "redirect:/admin/resetPassword.jsp";
    }
    //重置密码
    @RequestMapping("/reset")
    public String reset(HttpServletRequest request) throws UnsupportedEncodingException {
        String username=new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        String question=new String(request.getParameter("question").getBytes("iso-8859-1"),"utf-8");
        String newPassword=new String(request.getParameter("newPassword").getBytes("iso-8859-1"),"utf-8");
        String answer=new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");
        user un=new user();
        un.setUsername(username);
        un.setQuestion(question);
        un.setPassword(newPassword);
        user getAnswer=userService.selectByQuestion(un);
        if(getAnswer.getAnswer().equals(answer)){
            userService.changePassword(un);
            return "redirect:/admin/afterLogin.jsp";
        }else{//答案输入错误
            return "redirect:/admin/answerError.jsp";
        }
    }

    //进入查看标本
    @RequestMapping("/toAllSamples")
    public String toAllSamples(HttpSession session){
        List<Sample> sampleListAll=sampleService.selectAllByAdmin();
        session.setAttribute("sampleListAll",sampleListAll);
        return "redirect:/admin/allSamples.jsp";
    }

    //进入修改标本信息页面
    @RequestMapping("/toUpdateSample/{id}")
    public String toUpdateSample(@PathVariable("id") int id,HttpSession session){
        sample sample=sampleService.selectAllByID(id);
        //存入编号
        session.setAttribute("sample",sample);
        return "redirect:/admin/updateSample.jsp";
    }

    //修改标本信息
    @RequestMapping("/updateSample")
    public String updateSample(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        sample s=new sample();
        String name= new String(request.getParameter("name").getBytes("iso-8859-1"),"utf-8");
        String birthplace= new String(request.getParameter("birthplace").getBytes("iso-8859-1"),"utf-8");
        String birthtime= new String(request.getParameter("birthtime").getBytes("iso-8859-1"),"utf-8");
        String material= new String(request.getParameter("material").getBytes("iso-8859-1"),"utf-8");
        String years= new String(request.getParameter("years").getBytes("iso-8859-1"),"utf-8");
        String recordman= new String(request.getParameter("recordman").getBytes("iso-8859-1"),"utf-8");
        String description= new String(request.getParameter("description").getBytes("iso-8859-1"),"utf-8");
        String intime= new String(request.getParameter("intime").getBytes("iso-8859-1"),"utf-8");
        System.out.println(name+"|"+material+"|"+years);
        sample temp= (sample) session.getAttribute("sample");
        int id=temp.getSampleID();
        s.setSampleID(id);
        s.setName(name);
        s.setBirthplace(birthplace);
        s.setBirthtime(birthtime);
        s.setMaterial(material);
        s.setYears(years);
        s.setRecordman(recordman);
        s.setDescription(description);
        s.setIntime(intime);
        session.setAttribute("sample",s);
        System.out.println(sampleService.updateSample(s));
        return "redirect:/admin/toAllSamples";
    }

    //标本详情
    @RequestMapping("/detail/{id}")
    public String detail(@PathVariable("id") int id,HttpSession session){
        sample sample=sampleService.selectAllByID(id);
        //存入编号
        session.setAttribute("sample",sample);
        return "redirect:/admin/sampleDetail.jsp";
    }
    @RequestMapping("/detailBack")
    public String detailBack(){
        return "redirect:/admin/toAllSamples";
    }

    //标本删除
    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id,HttpSession session){
        sampleService.deleteByID(id);
        return "redirect:/admin/toAllSamples";
    }

    //展示所有用户
    @RequestMapping("/userInfo")
    public String userInfo(HttpSession session){
        List<user> userList=userService.selectAll();
        session.setAttribute("userList",userList);
        return "redirect:/admin/allUsers.jsp";
    }

    //用户修改
    @RequestMapping("/toUpdateUser/{id}")
    public String toUpdateUser(@PathVariable("id") int id,HttpSession session){
        user user=userService.selectByID(id);
        //存入编号
        session.setAttribute("user",user);
        return "redirect:/admin/updateUser.jsp";
    }

    @RequestMapping("/updateUser")
    public String updateUser(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        user u=new user();
        String username= new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");

        String realname= new String(request.getParameter("realname").getBytes("iso-8859-1"),"utf-8");
        String telephone= new String(request.getParameter("telephone").getBytes("iso-8859-1"),"utf-8");
        String email= new String(request.getParameter("email").getBytes("iso-8859-1"),"utf-8");
        String gender= new String(request.getParameter("gender").getBytes("iso-8859-1"),"utf-8");
        String question= new String(request.getParameter("question").getBytes("iso-8859-1"),"utf-8");
        String answer= new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");

        user temp= (user) session.getAttribute("user");
        int id=temp.getUserID();
        u.setUserID(id);
        u.setRealname(username);
        u.setRealname(realname);
        u.setTelephone(telephone);
        u.setEmail(email);
        u.setGender(gender);
        u.setQuestion(question);
        u.setAnswer(answer);

        session.setAttribute("user",u);
        userService.updateByID(u);
        return "redirect:/admin/userInfo";
    }


    //用户详情
    //
    @RequestMapping("/userDetail/{id}")
    public String userDetail(@PathVariable("id") int id,HttpSession session){
        user user=userService.selectByID(id);
        //存入编号
        session.setAttribute("user",user);
        return "redirect:/admin/userDetail.jsp";
    }
    @RequestMapping("/userDetailBack")
    public String userDetailBack(){
        return "redirect:/admin/userInfo";
    }

    //用户删除
    @RequestMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") int id,HttpSession session){
        userService.deleteByID(id);
        return "redirect:/admin/userInfo";
    }

    //新建单位
    @RequestMapping("/toNewUnit")
    public String toNewUnit(){
        return "redirect:/admin/newUnit.jsp";
    }

    @RequestMapping("/newUnit")
    public String newUnit(HttpServletRequest request) throws UnsupportedEncodingException {
        unit u=new unit();
        String name= new String(request.getParameter("unitName").getBytes("iso-8859-1"),"utf-8");
        String acessMan= new String(request.getParameter("acessMan").getBytes("iso-8859-1"),"utf-8");
        String telephone= new String(request.getParameter("telephone").getBytes("iso-8859-1"),"utf-8");
        String email= new String(request.getParameter("email").getBytes("iso-8859-1"),"utf-8");
        String address= new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
        String property= new String(request.getParameter("property").getBytes("iso-8859-1"),"utf-8");

        u.setUnitName(name);
        u.setAcessMan(acessMan);
        u.setTelephone(telephone);
        u.setEmail(email);
        u.setAddress(address);
        u.setProperty(property);
        unitService.insertUnit(u);
        return "redirect:/admin/afterLogin.jsp";
    }

    //展示所有单位
    @RequestMapping("/allUnit")
    public String allUnit(HttpSession session){
        List<unit> unitList=unitService.selectAll();
        session.setAttribute("unitList",unitList);
        return "redirect:/admin/allUnit.jsp";
    }

    @RequestMapping("/toUpdateUnit/{id}")
    public String toUpdateUnit(@PathVariable("id") int id,HttpSession session) {
        unit unit = unitService.selectByID(id);
        //存入编号
        session.setAttribute("unit", unit);
        return "redirect:/admin/updateUnit.jsp";
    }

    @RequestMapping("/updateUnit")
    public String updateUnit(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        unit u=new unit();
        String unitName= new String(request.getParameter("unitName").getBytes("iso-8859-1"),"utf-8");
        String acessMan= new String(request.getParameter("acessMan").getBytes("iso-8859-1"),"utf-8");
        String telephone= new String(request.getParameter("telephone").getBytes("iso-8859-1"),"utf-8");
        String email= new String(request.getParameter("email").getBytes("iso-8859-1"),"utf-8");
        String address= new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
        String property= new String(request.getParameter("property").getBytes("iso-8859-1"),"utf-8");


        unit temp= (unit) session.getAttribute("unit");
        int id=temp.getUnitID();
        u.setUnitID(id);
        u.setProperty(property);
        u.setEmail(email);
        u.setTelephone(telephone);
        u.setAddress(address);
        u.setAcessMan(acessMan);
        u.setUnitName(unitName);

        session.setAttribute("unit",u);
        unitService.updateUnit(u);
        return "redirect:/admin/allUnit";
    }

    //单位详情
    @RequestMapping("/unitDetail/{id}")
    public String unitDetail(@PathVariable("id") int id,HttpSession session){
        unit unit=unitService.selectByID(id);
        //存入编号
        session.setAttribute("unit",unit);
        return "redirect:/admin/unitDetail.jsp";
    }

    @RequestMapping("/unitDetailBack")
    public String unitDetailBack(){
        return "redirect:/admin/allUnit";
    }

    //单位删除
    @RequestMapping("/deleteUnit/{id}")
    public String deleteUnit(@PathVariable("id") int id,HttpSession session){
        unitService.deleteByID(id);
        return "redirect:/admin/allUnit";
    }

    @RequestMapping("/queryUnit")
    public String queryUnit(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        //queryUnitName
        String queryUnitName= new String(request.getParameter("queryUnitName").getBytes("iso-8859-1"),"utf-8");
        session.setAttribute("query",queryUnitName);
        List<unit> unitList=unitService.selectByName(queryUnitName);
        session.setAttribute("unitList",unitList);
        return "redirect:/admin/allUnit.jsp";
    }


    @RequestMapping("/exit")
    public  String exit(HttpSession session){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
        Date date= (Date) session.getAttribute("thisLoginTime");
        String name= (String) session.getAttribute("username");
        user user=new user();
        user.setUsername(name);
        user.setLastLoginTime(formatter.format(date));
        userService.updateByTime(user);
        return "redirect:../homePage.jsp";
    }
}
