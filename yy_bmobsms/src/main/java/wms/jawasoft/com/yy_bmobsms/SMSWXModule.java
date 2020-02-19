
package wms.jawasoft.com.yy_bmobsms;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.WXSDKEngine;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;

import cn.bmob.sms.BmobSMS;
import cn.bmob.sms.exception.BmobException;
import cn.bmob.sms.listener.RequestSMSCodeListener;
import cn.bmob.sms.listener.VerifySMSCodeListener;

public class SMSWXModule extends  WXSDKEngine.DestroyableModule {
    public String CONTENT = "phone";

    public String BMOBKEY ="bmobkey";

    public String SMSID ="smsid";
    private String content;
    private String bmobkey1;
    private String smsid1;
    private String code1 ="code";
    private String code;


    @Override
    public void destroy() {

    }
    @JSMethod(uiThread = true)
    public  void show(){
        Toast.makeText(mWXSDKInstance.getContext(),"刘原野真帅",Toast.LENGTH_LONG).show();

    }

    @JSMethod(uiThread = true)
    public void requestSMSCode(JSONObject options, final JSCallback jsCallback)  {
        if (mWXSDKInstance.getContext() instanceof Activity){
            content = options.getString(CONTENT);
            bmobkey1 = options.getString(BMOBKEY);
            smsid1 = options.getString(SMSID);

            //s == null || s.length() <= 0
            if(content == null || content.length() <= 0||
                    bmobkey1 == null || bmobkey1.length() <= 0||
                    smsid1 == null || smsid1.length() <= 0){
                Toast.makeText(mWXSDKInstance.getContext(),"手机号，key或者短信模板id为空",Toast.LENGTH_LONG).show();


            }{
                BmobSMS.initialize(mWXSDKInstance.getContext(), bmobkey1);


                BmobSMS.requestSMSCode(mWXSDKInstance.getContext(), content, smsid1,new RequestSMSCodeListener() {

                    @Override
                    public void done(Integer smsId,BmobException ex) {
                        // TODO Auto-generated method stub
                        if(ex==null){//
                            jsCallback.invoke("短信发送成功,短信id"+smsId);
                            Log.i("bmob","短信发送成功，短信id："+smsId);//用于查询本次短信发送详情
                        }else{
                            jsCallback.invoke("短信发送失败"+"errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                            Log.i("bmob","errorCode = "+ex.getErrorCode()+",errorMsg = "+ex.getLocalizedMessage());
                        }
                    }
                });
            }


        }else{
            Toast.makeText(mWXSDKInstance.getContext(),"初始化异常",Toast.LENGTH_LONG).show();

        }


    }

    @JSMethod(uiThread = true)
    public void verifySmsCode(JSONObject options, final JSCallback jsCallback){
        code = options.getString(code1);

        BmobSMS.verifySmsCode(mWXSDKInstance.getContext(), content, code, new VerifySMSCodeListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {

                    jsCallback.invoke("短信验证成功");


                } else {
                    jsCallback.invoke("短信验证失败");

                }
            }
        });

    }





    @Override
    public void onActivityCreate() {
        super.onActivityCreate();


    }



}

