package com.qigame.lock.helper;

import android.util.Log;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

public class NativeRuntime {

    private static NativeRuntime theInstance = null;
    private NativeRuntime() {
        
    }
    
    /******************************************
    * 获得实例
    * ****************************************/
    public static NativeRuntime getInstance() {
        if (theInstance == null)
            theInstance = new NativeRuntime();
        return theInstance;
    }
    
    /***************************************************
    * RunExecutable 启动一个可自行的lib*.so文件<br>
    * 文件要全名filename<br>
    * alias 是别名<br>
    * args是参数
    *********************************************** **/
    public String RunExecutable(String pacaageName, String filename,String alias, String args) {
        String path = "/data/data/" + pacaageName;
        String cmd1 = path + "/lib/" + filename;
        String cmd2 = path + "/" + alias;
        String cmd2_a1 = path + "/" + alias + " " + args;
        String cmd3 = "chmod 777 " + cmd2;
        String cmd4 = "dd if=" + cmd1 + " of=" + cmd2;
        StringBuffer sb_result = new StringBuffer();
    
        if (!new File("/data/data/" + alias).exists()) {
            RunLocalUserCommand(pacaageName, cmd4, sb_result); // 拷贝lib/libtest.so到上一层目录,同时命名为test.
            sb_result.append(";");
        }
        RunLocalUserCommand(pacaageName, cmd3, sb_result); // 改变test的属性,让其变为可执行
        sb_result.append(";");
        RunLocalUserCommand(pacaageName, cmd2_a1, sb_result); // 执行test程序.
        sb_result.append(";");
        return sb_result.toString();
    }
    
    /****************************************************
    * RunLocalUserCommand<br>
    * 执行本地用户命令
    * **************************************************/
    public boolean RunLocalUserCommand(String pacaageName, String command,StringBuffer sb_out_Result) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sh"); // 获得shell进程
            DataInputStream inputStream = new DataInputStream(process.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(process.getOutputStream());
            outputStream.writeBytes("cd /data/data/" + pacaageName + "\n"); // 保证在command在自己的数据目录里执行,才有权限写文件到当前目录
    
            outputStream.writeBytes(command + " &\n"); // 让程序在后台运行，前台马上返回
            outputStream.writeBytes("exit\n");
            outputStream.flush();
            process.waitFor();
    
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            String s = new String(buffer);
            if (sb_out_Result != null)
                sb_out_Result.append("CMD Result:\n" + s);
        } catch (Exception e) {
            if (sb_out_Result != null)
                sb_out_Result.append("Exception:" + e.getMessage());
            return false;
        }
        return true;
    }
    public native void startActivity(String compname);
    public native String stringFromJNI();
    public native void startService(String packname);
    public native int findProcess(String packname);
    public native int stopService();
    static {
        try {
            Log.i("load", "loadLibrary helper start..... ");
            System.loadLibrary("helper");
            Log.i("load", "loadLibrary helper end..... ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
