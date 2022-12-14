package com.controller;

import com.pojo.player;
import com.pojo.user;
import com.pojo.user_player;
import com.service.PlayerService;
import com.service.SampleService;
import com.service.UserService;
import com.service.User_playerService;
import org.apache.ibatis.javassist.tools.reflect.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("/user")
public class UserController extends HttpServlet {

    @Autowired
    private UserService userService;
    @Autowired
    private SampleService sampleService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private User_playerService user_playerService;

    //点击游客登录
    @RequestMapping("/regOrLogin")
    public String toAddPaper() {
        return "../regOrLogin.jsp";
    }
    //进入登录界面登录
    @RequestMapping("/toLogin")
    public String toLogin(){

        return "../touristLogin.jsp";
    }

    //登录
    @RequestMapping("/Login")
    public String Login(user u,HttpSession session){
        user formatpass=userService.selectByRegName(u.getUsername());
        //成功登录
        String des="";
        System.out.println(formatpass.getUsername());
        if(formatpass!=null&&formatpass.getPassword().equals(u.getPassword())&&formatpass.getIdentity().equals("tourist")){
            session.setAttribute("touristname",u.getUsername());
            SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
            Date date = new Date(System.currentTimeMillis());
//            temp.setLastLoginTime(formatter.format(date));
            session.setAttribute("thisLoginTime",date);
            session.setAttribute("username",u.getUsername());
            des="../afterLogin.jsp";
        }else{
            des="../failToLogin.jsp";
        }
        return des;
    }
    //进入注册页面
    @RequestMapping("/register")
    public String toaddUser(user u){
        return "../touristRegister.jsp";
    }
    //注册
    @RequestMapping("/insertUser")
    public String addUser(user us, HttpServletResponse resp,HttpServletRequest request, @RequestParam("username") String useruame, @RequestParam("password") String password, @RequestParam("password2") String password2, @RequestParam("realname") String realname, @RequestParam("telephone") String telephone, @RequestParam("email") String email, @RequestParam("address") String address, @RequestParam("gender") String gender, HttpSession session, @RequestParam("question") String question, @RequestParam("answer") String answer) throws IOException {
        String des="";
        //不要让用户名重复
        user t=userService.selectByRegName(useruame);
        if(t==null&&password.equals(password2)){
            //判断电话号码是否合法
            if(telephone.length()==11){
                int flag=0;
                for(int i=0;i<telephone.length();i++){
                    if(telephone.charAt(i) <= '0'|| telephone.charAt(i) >= '9'){
                        flag=1;
                    }
                }
                if(flag==0){
                    useruame=new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
                    address=new String(request.getParameter("address").getBytes("iso-8859-1"),"utf-8");
                    gender=new String(request.getParameter("gender").getBytes("iso-8859-1"),"utf-8");
                    realname=new String(request.getParameter("realname").getBytes("iso-8859-1"),"utf-8");
                    answer=new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");
                    question=new String(request.getParameter("question").getBytes("iso-8859-1"),"utf-8");
                    //合法
                    //记录登录时间
                    SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ");
                    Date date = new Date(System.currentTimeMillis());
                    user u=new user();
                    u.setUsername(useruame);
                    u.setPassword(password);
                    u.setAddress(address);
                    u.setEmail(email);
                    u.setGender(gender);
                    u.setRealname(realname);
                    u.setTelephone(telephone);
                    u.setAnswer(answer);
                    u.setQuestion(question);
                    u.setIdentity("tourist");
                    u.setFoundtime(formatter.format(date));
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
                    userService.insertUser(u);
                    //角色表和用户角色表
                    player player=new player();
                    player.setFoundTime(formatter.format(date));
                    playerService.insert(player);
                    int playerID=playerService.selectByTime(formatter.format(date)).getPlayerID();
                    int userID=userService.selectByRegName(useruame).getUserID();
                    user_player up=new user_player();
                    up.setUserID(userID);
                    up.setPlayerID(playerID);
                    user_playerService.insert(up);
                    //返回游客页面
                    des="../regOrLogin.jsp";
                }else{
                    des="../teleError.jsp";
                }
            }else{//电话输入有误
                des="../teleError.jsp";
            }
        }else if(t==null){//两次密码输入错误
            des="../registerError2.jsp";
        }else{  //用户名重复
            des="../registerError.jsp";
        }
         return des;
    }

    //找回密码
    @RequestMapping("/tofind")
    public String toforget(HttpSession session){
        session.setAttribute("pwd","");
                return "redirect:/findpwd.jsp";
    }

    @RequestMapping("/find")
    public String find(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String username1= new String(request.getParameter("username").getBytes("iso-8859-1"),"utf-8");
        user u=userService.selectByRegName(username1);
        if(u==null){
            return "redirect:/userNameError.jsp";
        }{//找到了user
            session.setAttribute("userInfo",u);
            return "/findpwd.jsp";
        }
    }

    @RequestMapping("/findout")
    public String findout(HttpServletRequest request,HttpSession session) throws UnsupportedEncodingException {
        String answer= new String(request.getParameter("answer").getBytes("iso-8859-1"),"utf-8");
       user t= (user) session.getAttribute("userInfo");
       if(answer.equals(t.getAnswer())){
           session.setAttribute("pwd","密码是："+t.getPassword());
       }
       return "redirect:/findpwd.jsp";
    }


    //查看并且修改个人信息
    @RequestMapping("/toChange")
    public String tochange(HttpSession session){
        String name= (String) session.getAttribute("touristname");
        user t=userService.selectByRegName(name);
        session.setAttribute("userInfo",t);
        return "../changeInfo.jsp";
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
            session.setAttribute("userInfo",u);
            System.out.println(address1);
           //用户名不重复
            userService.updateInfo(u);
            return "../afterLogin.jsp";
    }

    //进入重置密码
    @RequestMapping("/toReset")
    public String toReset(){
        return "../resetPassword.jsp";
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
            return "../afterLogin.jsp";
        }else{//答案输入错误
            return "../answerError.jsp";
        }
    }

    //进入查看标本
    @RequestMapping("/toAllSamples")
    public String toAllSamples(HttpSession session){
        List<Sample> sampleList=sampleService.selectAll();
        session.setAttribute("sampleList",sampleList);
        return "../allSamples.jsp";
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
